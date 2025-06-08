# electron-settings 源码阅读
repo: [electron-settings](https://github.com/nathanbuchar/electron-settings)

## src
主题源码就一个文件 src/settings.ts

### KeyPath 定义

```ts
type KeyPath = string | Array<string | number>;
```
- 要么是 string
- 要么是 Array<string | number>：表示一个数组，其元素可以是 string 或 number 类型（或两者的混合）。

这个 path 表示的是访问路径，有多种形式，非常灵活：

- settings.get('color.name'); dot 链式访问深层属性
- settings.get(['color','hue']); 提供数组来顺序访问属性，效果和 dot 方式一致
- settings.get('color.code.rgb[1]'); 也可以访问特定数组元素，例如 [1]

这个库使用了 https://lodash.com/docs/4.17.15#get 作为底层实现，支持链式访问就不奇怪了。


### datatypes
```ts
type SettingsValue = null | boolean | string | number | SettingsObject | SettingsValue[];

type SettingsObject = {
    [key: string]: SettingsValue;
};
```
数据的值类型。注意这里的定义是可以递归的 SettingsValue 可以是 SettingsObject 类型，而 SettingsObject 的属性值又是 SettingsValue。
从描述文档可知，值类型和JSON对象的值类型一致，不支持Date类型。这个库对Date的处理是仅保存unix timestamp，你需要手动转化。
```ts
await settings.set('user.lastLogin', new Date());

const lastLogin = await settings.get('user.lastLogin');
const lastLoginDate = new Date(lastLogin);
```
接下来是配置的类型。

### Config
```ts
type Config = {

  /**
   * Whether or not to save the settings file atomically.
   */
  atomicSave: boolean;

  /**
   * The path to the settings directory. Defaults to your
   * app's user data direcory.
   */
  dir?: string;

  /**
   * A custom Electron instance to use. Great for testing!
   */
  electron?: typeof Electron;

  /**
   * The name of the settings file that will be saved to
   * the disk.
   */
  fileName: string;

  /**
   * The number of spaces to use when stringifying the data
   * before saving to disk if `prettify` is set to `true`.
   */
  numSpaces: number;

  /**
   * Whether or not to prettify the data when it's saved to
   * disk.
   */
  prettify: boolean;
};
```
这里有一个原子写入的flag设置atomicSave，它是怎样实现的呢？其实是委托给了 writeFileAtomic 来实现，后续会仔细说明。

此外，程序一般都有内部的默认设置，如下
```ts
/** @internal */
const defaultConfig: Config = {
  atomicSave: true,
  fileName: 'settings.json',
  numSpaces: 2,
  prettify: false,
};

/** @internal */
let config: Config = {
  ...defaultConfig,
};
```
`@internal` 是typedoc的标注，表示这是内部代码，不要生成外部文档说明。
默认情况下，原子写入是打开的，格式化是关闭的，保存在 settings.json 文件。

接下来，就全是方法定义了。

### Electron 相关方法

关于Electron实例。如果配置了那就返回用户配置的electron，否则就是默认的electron
```ts
function getElectron(): typeof Electron {
    return config.electron ?? electron;
}
```

关于 App，如果是main process，那就是.app；如果是 renderer process，需要从.remote获取
```ts
function getElectronApp(): Electron.App {
  const e = getElectron();
  return e.app ?? e.remote.app;
}
```

### 配置路径相关处理

```ts
function getSettingsDirPath(): string {
  return config.dir ?? getElectronApp().getPath('userData');
}

function getSettingsFilePath(): string {
    const dir = getSettingsDirPath();
    return path.join(dir, config.fileName);
}
```
配置文件夹和配置文件路径的获取。接下来是工具函数，保证目录和文件提前准备好。

下面是 ensureSettingsFile() 的异步实现。
```ts
function ensureSettingsFile(): Promise<void> {
    const filePath = getSettingsFilePath();
    return new Promise((resolve, reject) => {
        fs.stat(filePath, (err) => {
            if (err) {
                if (err.code === 'ENOENT') {
                    // first time
                    saveSettings({}).then(resolve, reject);
                } else {
                    // exception
                    reject(err);
                }
            } else {
                // not first time, and succeded
                resolve();
            }
        });
    });
}
```
由于是async实现，因此返回的是promise。fs.stat()是获取文件状态的，如果文件不存在，那就回抛出ENOENT错误码。
- 在第一次程序执行时，应该会进入此分支 `err.code === 'ENOENT'`，此时需要初始化保存文件;
- 但是，如果查询文件状态时，发生了其他错误，那就是异常，对应reject(err);
- 后续的正常访问，会进入else分支，抵达 resolve()。

ensureSettingsFileSync的实现类似，这里不赘述。
而对于 ensureSettingsDir 和 ensureSettingsDirSync, 大部分逻辑一致，只是换成了要递归创建文件夹。

下面看加载配置

```ts
function loadSettings(): Promise<SettingsObject> {
    return ensureSettingsFile().then(() => {
        const filePath = getSettingsFilePath();
        return new Promise((resolve, reject) => {
            fs.readFile(filePath, 'utf-8', (err, data) => {
                if (err) {
                    reject(err);
                } else {
                    try {
                        resolve(JSON.parse(data));
                    } catch (err) {
                        reject(err);
                    }
                }
            });
        });
    });
}
```
happy path的条件是读取文件内容成功 + JSON解析成功。其余情况一律reject。

loadSettingsSync的实现反而非常简短。
```ts
function loadSettingsSync(): SettingsObject {
    const filePath = getSettingsFilePath();
    ensureSettingsFileSync();
    const data = fs.readFileSync(filePath, 'utf-8');
    return JSON.parse(data);
}
```

最后，是保存配置的函数实现

```ts
function saveSettings(obj: SettingsObject): Promise<void> {
    return ensureSettingsDir().then(() => {
        const filePath = getSettingsFilePath();
        const numSpaces = config.prettify ? config.numSpaces : 0;
        const data = JSON.stringify(obj, null, numSpaces);

        return new Promise((resolve, reject) => {
            if (config.atomicSave) {
                writeFileAtomic(filePath, data, (err) => {
                    return err ? reject(err) : resolve();
                });
            } else {
                fs.writeFile(filePath, data, (err) => {
                    return err ? reject(err) : resolve();
                });
            }
        });
    });
}
```
这里是先确保目录存在，然后写filePath。

### 非核心 API 函数

获取配置文件路径
```ts
function file(): string {
  return getSettingsFilePath();
}
```
- **macOS** - `~/Library/Application\ Support/<Your App>`
- **Windows** - `%APPDATA%/<Your App>`
- **Linux** - Either `$XDG_CONFIG_HOME/<Your App>` or `~/.config/<Your App>`

实时调整配置
```ts
function configure(customConfig: Partial<Config>): void {
    config = { ...config, ...customConfig };    
}
```
ts 的Partial<XXX>在这里得到应用，取部分key-value。

重置配置
```ts
function reset(): void {
  config = { ...defaultConfig };
}
```

### 核心 API 函数

一共有四类接口簇，分别是has、get、set、unset，分别对应判断、获取、设置、重置。

得益于lodash的强大，这里的实现非常简短。

has
```ts
async function has(keyPath: KeyPath): Promise<boolean> {
    const obj = await loadSettings();
    return _has(obj, keyPath);
}

function hasSync(keyPath: KeyPath): boolean {
    const obj = loadSettingsSync();
    return _has(obj, keyPath);
}
```
---
get

首选定义函数的类型声明，然后实现
```ts
async function get(): Promise<SettingsObject>;
async function get(keyPath: KeyPath): Promise<SettingsValue>;

async function get(keyPath?: KeyPath): Promise<SettingsObject | SettingsValue> {
    const obj = await loadSettings();
    if (keyPath) {
        return _get(obj, keyPath);
    } else {
        return obj;
    }
}
```
实际调用get()时，如果不传keyPath，那就是对应第一个声明；如果存在keyPath，对应第二个声明。
注意返回类型使用 Promise<SettingsObject | SettingsValue> 兼容了两种情况。

getSync实现类似，不赘述。

---
set

```ts
async function set(obj: SettingsObject): Promise<void>;
async function set(keyPath: KeyPath, obj: SettingsValue): Promise<void>;

async function set(...args: [SettingsObject] | [KeyPath, SettingsValue]): Promise<void> {
    if (args.length === 1) {
        const [value] = args;
        return saveSettings(value);
    } else {
        const [keyPath, value] = args;
        const obj = await loadSettings();

        _set(obj, keyPath, value);

        return saveSettings(obj);
    }
}
```
根据 args.length 数量来走分支处理。注意，当 saveSettings(obj) 发生 reject(err) 时：set 函数会抛出一个异常（即返回一个被拒绝的 Promise）。
调用 set 的代码需要通过 try/catch 或 .catch() 捕获这个错误。

---
unset

```ts
async function unset(): Promise<void>;
async function unset(keyPath: KeyPath): Promise<void>;

async function unset(keyPath?: KeyPath): Promise<void> {
  if (keyPath) {
    const obj = await loadSettings();
    _unset(obj, keyPath);
    return saveSettings(obj);
  } else {
    // Unset all settings by saving empty object.
    return saveSettings({});
  }
}
```
如果是全部unset，这里是直接用一个空对象来覆盖整个文件的内容。

### 模块导出
```ts
export = {
  file,
  configure,
  reset,
  has,
  hasSync,
  get,
  getSync,
  set,
  setSync,
  unset,
  unsetSync,
};
```
这里就是公共导出的定义。整体来看，借助lodash来套壳，这个库的代码量大减。

## tsc 编译配置

`tsconfig.json`

```
{
  "compilerOptions": {
    "strict": true,
    "module": "CommonJS",
    "target": "ES5",
    "outDir": "dist",
    "declaration": true, <--- 输出 type
    "sourceMap": true,   <--- 输出 sourcemap
    "esModuleInterop": true,
    "skipLibCheck": true
  },
  "include": [
    "src/**/*"
  ],
  "exclude": [
    "**/*.test.ts"
  ]
}
```

## 脚本命令

```
"scripts": {
    "build": "tsc",
    "lint": "eslint src --ext .ts",
    "docs": "typedoc && echo \"electron-settings.js.org\" > docs/CNAME",
    "release": "standard-version",
    "prepublishOnly": "tsc",
    "test": "npm run lint && npm run test:main && npm run test:renderer",
    "test:main": "electron-mocha --reporter spec --require ts-node/register 'src/**/*.test.ts'",
    "test:renderer": "electron-mocha --renderer --reporter spec --require ts-node/register 'src/**/*.test.ts'"
}
```

- build 和 prepublishOnly 都是 tsc 编译
- docs 使用了 typedoc 库来生成文档，并设置了 CNAME 域名
- test 是一个综合的测试命令
- test:main 和 test:renderer 分别测试对应的模块，使用了 electron-mocha 库。这是一个在 electron 环境运行的 Mocha 测试 runner。

关于这个命令的解释

```
electron-mocha --reporter spec --require ts-node/register 'src/**/*.test.ts'
```

- `--reporter spec`: 指定输出报告的格式，spec 使得报告具有层级结构。
- `--require ts-node/register`: 直接加载 ts-node，使得 mocha 不需要编译 ts 就能在运行时执行 ts。

## 依赖

```
"dependencies": {
    "lodash": "^4.17.21",
    "mkdirp": "^1.0.4",
    "write-file-atomic": "^3.0.3"
}
```

- mkdir: 作用是 mkdir -p, but in Node. js
- write-file-atomic: node's fs 模块的一个扩展。writeFile 使得写操作原子性，并允许你设置 ownership (uid/gid)

## NOTICE
这是关于使用开源软件作为依赖的声明。文档格式值得参考
```
# Notice

This project uses third party material from the projects listed below.

**lodash**
\```
The MIT License

Copyright JS Foundation and other contributors <https://js.foundation/>

Based on Underscore.js, copyright Jeremy Ashkenas,
DocumentCloud and Investigative Reporters & Editors <http://underscorejs.org/>

This software consists of voluntary contributions made by many
individuals. For exact contribution history, see the revision history
available at https://github.com/lodash/lodash

The following license applies to all parts of this software except as
documented below:

====

...
\```

**mkdirp**
\```
Copyright James Halliday (mail@substack.net) and Isaac Z. Schlueter (i@izs.me)

This project is free software released under the MIT license:

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
\```

**write-file-atomic**
\```
Copyright (c) 2015, Rebecca Turner

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
\```
```

## typedoc 配置

typedoc.json

```json
{
  "name": "Electron Settings",
  "includeVersion": true,
  "includes": "src",
  "exclude": "**/*.test.ts",
  "out": "docs",
  "readme": "none"
}
```
定义了源文件夹、排除文件的 pattern、输出文件夹。

## linebreak-style 设置
.eslintrc.js
```js
module.exports = {
  rules: {
    'linebreak-style': ['error', 'unix'],
  },
};
```

"linebreak-style": ["error", "unix"] = 强制使用 LF 换行符。如果设置成 windows，那就是强制使用 CRLF 换行符。
含义为：如果违反了换行符的设定，那么就会发生 error 错误。

这里有一个问题需要思考。一般而言，一个代码库本质上只能有一种标准，unix 和 windows 的换行符是互斥的，只能二选一。
当选择了 LF 作为远程代码库的标准后，对于 clone 到本地的各个代码副本而言，换行符可以是各种系统风格的。例如 团队成员 A 使用 windows 系统，团队成员 B
使用 macos 系统。那么当提交代码时，不管你本地是哪种形式，要注意全部转化为 LF，才能进入版本控制系统。

- 最佳实践：通过 .editorconfig + Git 配置 确保团队统一。
- 补充的修复方法：用 eslint --fix 或手动转换文件，例如 dos2unix 工具库或者 sed 命令。

方法一：使用 .editorconfig 时
```
[*]
end_of_line = lf
```

方法二：用 Git 自动转换

For widows user:
```
git config --global core.autocrlf true
```
For *unix user:
```
git config --global core.autocrlf input
```