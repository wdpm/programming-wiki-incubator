# 笔记

本质上，就是对一个JSON文件的读写，把JSON文件当成数据库来使用。每次读都是读整个JSON数据作为数据库，每次写也是写整个JSON数据库。
因此IO瓶颈是必然的，对于性能有所要求的场景，要考虑使用传统的数据库系统。
这个库的意义主要还是API封装，对于简单的用户配置场景，可以使用。

## src目录组织

src folder

```
│  browser.ts                     
│  index.ts
│  node.ts
│  
├─adapters
│  │  Memory.test.ts
│  │  Memory.ts
│  │
│  ├─browser
│  │      LocalStorage.ts
│  │      SessionStorage.ts
│  │      WebStorage.test.ts
│  │      WebStorage.ts
│  │
│  └─node
│          DataFile.ts
│          JSONFile.test.ts
│          JSONFile.ts
│          TextFile.test.ts
│          TextFile.ts
│
├─core
│      Low.test.ts
│      Low.ts
│
├─examples
│      browser.ts   => LocalStorage adapter example
│      cli.ts       => Simple CLI using JSONFileSync adapter
│      in-memory.ts => Example showing how to use in-memory adapter to write fast tests
│      README.md
│      server.ts    => Express example using JSONFile adapter
│
│
└─presets
        browser.ts
        node.ts

```

examples文件夹是用例，不是重点先跳过。我们观察其他文件夹：

- adapters 是适配器，一共三种环境：内存型、浏览器、Node
- core 是核心，大部分分逻辑应该在这里
- presets 是预设，应该指的是一些实用的封装类或者方法

我们先从Low开始读

### Low

> src/core/Low.ts

文件开头是接口和实用方法

```ts
export interface Adapter<T> {
    read: () => Promise<T | null>
    write: (data: T) => Promise<void>
}

export interface SyncAdapter<T> {
    read: () => T | null
    write: (data: T) => void
}

function checkArgs(adapter: unknown, defaultData: unknown) {
    if (adapter === undefined) throw new Error('lowdb: missing adapter')
    if (defaultData === undefined) throw new Error('lowdb: missing default data')
}
```

这里定义了两个适配器接口，分别为同步和异步类型，里面的方法都是读和写两个方法，注意这里用了泛型T作为data类型的占位符。
checkArgs更加一目了然，是入参非空检测。

往下可以看到Low类和LowSync，那么先看Low

```ts
export class Low<T = unknown> {
    adapter: Adapter<T>
    data: T

    constructor(adapter: Adapter<T>, defaultData: T) {
        checkArgs(adapter, defaultData)
        this.adapter = adapter
        this.data = defaultData
    }

    async read(): Promise<void> {
        const data = await this.adapter.read()
        if (data) this.data = data
    }

    async write(): Promise<void> {
        if (this.data) await this.adapter.write(this.data)
    }

    async update(fn: (data: T) => unknown): Promise<void> {
        fn(this.data)
        await this.write()
    }
}
```

- constructor 是经典的依赖注入，这里是保存data数据和adapter实例
- read方法将实现委托到了实际的adapter示例，符合依赖倒置原则（DIP）：依赖抽象（Adapter<T>
  ），而非具体实现。注意read方法只是将外部data读取到自身的data属性中。
- write方法同理只是委托给了adapter.write()
- update是更新数据的含义，具体更新逻辑交给了第一个参数fn。fn是一个函数，它的第一个参数是data属性的引用，因此外部逻辑可以自由修改data数据。
  修改完之后，update使用自身的write()将数据刷回磁盘。

关于同步模式的LowSync，逻辑基本一致，这里就不赘述了。

到了这里，我们就可以顺藤摸瓜去深入研究adapter的设计了。

### 内存型 adapter

```ts
import {Adapter, SyncAdapter} from '../core/Low.js'

export class Memory<T> implements Adapter<T> {
    #data: T | null = null

    read(): Promise<T | null> {
        return Promise.resolve(this.#data)
    }

    write(obj: T): Promise<void> {
        this.#data = obj
        return Promise.resolve()
    }
}

export class MemorySync<T> implements SyncAdapter<T> {
    #data: T | null = null

    read(): T | null {
        return this.#data || null
    }

    write(obj: T): void {
        this.#data = obj
    }
}
```

内存型adapter的读写都是操纵自身在内存中的data属性。

接着看浏览器类型的adapter。

### 浏览器 adapter

- [LocalStorage.ts]
- [SessionStorage.ts]
- [WebStorage.ts]

这里似乎有三个实现，实际上LocalStorage和SessionStorage都是WebStorage的套壳。

```
import { WebStorage } from './WebStorage.js'

export class SessionStorage<T> extends WebStorage<T> {
  constructor(key: string) {
    super(key, sessionStorage)
  }
}
```

```
import { WebStorage } from './WebStorage.js'

export class LocalStorage<T> extends WebStorage<T> {
  constructor(key: string) {
    super(key, localStorage)
  }
}
```

构造函数的第一个参数key相当于标识符，用于后面查询数据。
第二个参数是底层实现的委托，SessionStorage委托给了sessionStorage，LocalStorage委托给了localStorage。

接下来直接看WebStorage.ts的实现。

```ts
import {SyncAdapter} from '../../core/Low.js'

export class WebStorage<T> implements SyncAdapter<T> {
    #key: string
    #storage: Storage

    constructor(key: string, storage: Storage) {
        this.#key = key
        this.#storage = storage
    }

    read(): T | null {
        const value = this.#storage.getItem(this.#key)
        if (value === null) {
            return null
        }
        return JSON.parse(value) as T
    }

    write(obj: T): void {
        this.#storage.setItem(this.#key, JSON.stringify(obj))
    }
}
```
实现非常直接，是JSON对象的序列化和反序列化。之所以可以这么实现还是归结于 sessionStorage 和 localStorage 都有相同的读写接口
getItem()和setItem()。

最后，我们看Node环境的adapter如何实现。

### Node adapter

- [TextFile.ts]
- [DataFile.ts]
- [JSONFile.ts]

第一个问题可能是这三者有什么本质的区分？
```
export class TextFile implements Adapter<string>

export class DataFile<T> implements Adapter<T>
export class JSONFile<T> extends DataFile<T>
```
DataFile 中 #adapter私有属性 是 TextFile 类的实例。实际使用时，是使用TextFile和JSONFile。

那么我们先看 TextFile ，然后是 DataFile，最后才是 JSONFile

### TextFile 实现

```ts
import {PathLike, readFileSync, renameSync, writeFileSync} from 'node:fs'
import {readFile} from 'node:fs/promises'
import path from 'node:path'

import {Writer} from 'steno'

import {Adapter, SyncAdapter} from '../../core/Low.js'

export class TextFile implements Adapter<string> {
  #filename: PathLike
  #writer: Writer

  constructor(filename: PathLike) {
    this.#filename = filename
    this.#writer = new Writer(filename)
  }

  async read(): Promise<string | null> {
    let data

    try {
      data = await readFile(this.#filename, 'utf-8')
    } catch (e) {
      if ((e as NodeJS.ErrnoException).code === 'ENOENT') {
        return null
      }
      throw e
    }

    return data
  }

  write(str: string): Promise<void> {
    return this.#writer.write(str)
  }
}
```
TextFile 构造函数中保存了要写入文件的文件名filename，以及一个writer实例。

TextFileSync的实现有一个特别的地方，因此这里也观察分析一下
```ts
export class TextFileSync implements SyncAdapter<string> {
  #tempFilename: PathLike
  #filename: PathLike

  constructor(filename: PathLike) {
    this.#filename = filename
    const f = filename.toString()
    this.#tempFilename = path.join(path.dirname(f), `.${path.basename(f)}.tmp`)
  }

  read(): string | null {
    let data

    try {
      data = readFileSync(this.#filename, 'utf-8')
    } catch (e) {
      if ((e as NodeJS.ErrnoException).code === 'ENOENT') {
        return null
      }
      throw e
    }

    return data
  }

  write(str: string): void {
    writeFileSync(this.#tempFilename, str)
    renameSync(this.#tempFilename, this.#filename)
  }
}
```
这里，我们需要注意的是构造函数以及write方法。

构造函数中，对于传入的filename，不仅仅是保存属性。还需要根据filename生成一个临时的中转文件名。
例如filename是 `/path/to/db.json`，那么tempFilename会是 `/path/to/db.json.tmp`。至于为什么要有这样一个中转变量，需要看
write()实现。

我们来仔细分析write
```
write(str: string): void {
  writeFileSync(this.#tempFilename, str)
  renameSync(this.#tempFilename, this.#filename)
}
```
可以看到这里分了两个步骤:
1. 将数据写入到临时文件；
2. 将临时文件重命名为之前的filename。这步等于覆盖或者称之为替代。

因为这里分了两个操作，我们很容易产生一个疑问，这不是原子性的写文件操作，如果其中某个步骤失败了，会怎样？

- 1成功 2失败（抛出异常） => 临时文件保存了最新的数据；但是之前文件filename的内容没有写入，还是旧数据。
- 1成功 2成功 => 最理想的状态，也是大部分情况。
- 1失败（抛出异常） 2不会执行 => write失败，外界可以观测到，应进行后续处理。
- 1失败（抛出异常） 2不会执行 => write失败，外界可以观测到，应进行后续处理。

不管如何，要么成功，要么抛异常。代码逻辑还是没问题的。

### DataFile 实现
```ts
import { PathLike } from 'fs'

import { Adapter, SyncAdapter } from '../../core/Low.js'
import { TextFile, TextFileSync } from './TextFile.js'

export class DataFile<T> implements Adapter<T> {
  #adapter: TextFile
  #parse: (str: string) => T
  readonly #stringify: (data: T) => string

  constructor(
    filename: PathLike,
    {
      parse,
      stringify,
    }: {
      parse: (str: string) => T
      stringify: (data: T) => string
    },
  ) {
    this.#adapter = new TextFile(filename)
    this.#parse = parse
    this.#stringify = stringify
  }

  async read(): Promise<T | null> {
    const data = await this.#adapter.read()
    if (data === null) {
      return null
    } else {
      return this.#parse(data)
    }
  }

  write(obj: T): Promise<void> {
    return this.#adapter.write(this.#stringify(obj))
  }
}
```
构造函数中，除了之前熟悉的filename，第二个参数是一个对象，而且必须包含特定的key，这是鸭子类型。
```
{
  parse,
  stringify,
}: {
  parse: (str: string) => T
  stringify: (data: T) => string
}
```
parse 属性必须是形如 (str: string) => T 的函数；stringify 必须是形如 (data: T) => string 的函数。
这其实将反序列化和序列化的逻辑外置给了用户实现，也是依赖注入。

这两个方法其实很熟悉，浏览器内置对象JSON就是一个这样经典的实现，包含同名方法。这两个方法分别用于read()和write()内部实现。

DataFileSync 的实现类似，这里就跳过了。

### JSONFile 实现

```ts
import { PathLike } from 'fs'

import { DataFile, DataFileSync } from './DataFile.js'

export class JSONFile<T> extends DataFile<T> {
  constructor(filename: PathLike) {
    super(filename, {
      parse: JSON.parse,
      stringify: (data: T) => JSON.stringify(data, null, 2),
    })
  }
}
```
JSONFile正如前面所言，是DataFile的一种常见实现。

接着来看presets是什么设计与实现。

### presets

`src/presets/browser.ts`
```ts
import { LocalStorage } from '../adapters/browser/LocalStorage.js'
import { SessionStorage } from '../adapters/browser/SessionStorage.js'
import { LowSync } from '../index.js'

export function LocalStoragePreset<Data>(
  key: string,
  defaultData: Data,
): LowSync<Data> {
  const adapter = new LocalStorage<Data>(key)
  const db = new LowSync<Data>(adapter, defaultData)
  db.read()
  return db
}

export function SessionStoragePreset<Data>(
  key: string,
  defaultData: Data,
): LowSync<Data> {
  const adapter = new SessionStorage<Data>(key)
  const db = new LowSync<Data>(adapter, defaultData)
  db.read()
  return db
}
```
浏览器环境中，是LocalStoragePreset和SessionStoragePreset两个实用类。这里提供了初始数据作为参数。

而在Node环境中，提供了JSONFilePreset

```ts
import { PathLike } from 'node:fs'

import { Memory, MemorySync } from '../adapters/Memory.js'
import { JSONFile, JSONFileSync } from '../adapters/node/JSONFile.js'
import { Low, LowSync } from '../core/Low.js'

export async function JSONFilePreset<Data>(
  filename: PathLike,
  defaultData: Data,
): Promise<Low<Data>> {
  const adapter =
    process.env.NODE_ENV === 'test'
      ? new Memory<Data>()
      : new JSONFile<Data>(filename)
  const db = new Low<Data>(adapter, defaultData)
  await db.read()
  return db
}

//省略其他
```
最后，是examples用例。没有太多值得注意的地方。

### 入口文件的设计

src/browser.ts
```ts
export * from './adapters/browser/LocalStorage.js'
export * from './adapters/browser/SessionStorage.js'
export * from './presets/browser.js'
```

src/node.ts
```ts
export * from './adapters/node/DataFile.js'
export * from './adapters/node/JSONFile.js'
export * from './adapters/node/TextFile.js'
export * from './presets/node.js'
```

## test script

在package.json 中，有时命令会比较复杂，此时可以将命令逻辑外置到独立的js脚本。

```
"scripts": {
  "test": "node test-runner.js",
}
```

然后，对测试脚本`test-runner.js`自定义

```js
import {execSync} from 'child_process'
import {globSync} from 'glob'

// 使用 glob 匹配所有测试文件
const testFiles = globSync('src/**/*.test.ts')

if (testFiles.length === 0) {
    console.error('No test files found.')
    process.exit(1)
}

// 运行 tsx --test 命令
const command = `tsx --test ${testFiles.join(' ')}`
console.log(`Running: ${command}`)
execSync(command, {stdio: 'inherit'})
```

## tsx 编译lib的相关配置

tsconfig.json

```json
{
  "extends": "@sindresorhus/tsconfig",
  "compilerOptions": {
    "outDir": "./lib"
  }
}
```

继承了一个外部的配置预设，然后设置了输出目录。

## 一些npm库的使用

- steno

> Specialized fast async file writer Steno makes writing to the same file often/concurrently fast and safe

从它的描述来看，这个库解决了同一个文件的并发写的性能问题。

- tempy

> Get a random temporary file or directory path

临时文件读写的工具库，挺常用的。

- cross-env

这个库一般是为了抹平不同平台在执行npm scripts时的差异问题。

- del-cli

删除文件/文件夹的工具库。类似于python生态的shutil。

## 类似的库
- [electron-settings](https://github.com/nathanbuchar/electron-settings)
- [electron-store](https://github.com/sindresorhus/electron-store)
- [electron-storage](https://github.com/Cocycles/electron-storage)