# Electron playground 笔记摘录

> 项目来源 https://github.com/wdpm/electron-playground

## 错误上报

集成sentry.js，或者实现收集错误的后端服务器设施。



## 打包

- [electron-packager](https://github.com/electron/electron-packager)
- [electron-forge](https://github.com/electron-userland/electron-forge)
- [electron-builder](https://github.com/electron-userland/electron-builder)

`electron-packager`较为轻量，适合简单的项目打包。相对而言`electron-builder`配置更加复杂和全面一些。

插件加载

```js
let pluginName
switch (process.platform) {
  case 'win32':
    pluginName = 'pepflashplayer.dll'
    break
  case 'darwin':
    pluginName = 'PepperFlashPlayer.plugin'
    break
}
app.commandLine.appendSwitch('ppapi-flash-path', path.join(__dirname, '..','plugins', pluginName as string))

const window = new BrowserWindow({webPreferences: {preload: PRELOAD, plugins: true}})
```



### 多平台多环境打包的命令行工具

根据功能流程设计来划分功能模块：

1. Inquirer: 读取用户输入项并提供校验; 
2. CommandExecutor: 执行命令行命令的函数; 
3. JsonUpdater: 提供读写操作更新package.json和package-lock.json; 
4. ChangelogUpdater: 更新CHANGELOG 
5. Builder: 使用electron-builder进行打包操作
6.  FileUploader: 通过ftp进行文件上传

`build/packaging-cli/inquirer.ts`

```js
import * as inquirer from "inquirer";

const envs = ["test", "prod"] as const;
const platforms = ["win", "mac"] as const;

export type envType = typeof envs[number]
export type platformType = typeof platforms[number]

const options = [
  { type: "input", name: "version", message: `版本号？` },
  { type: "input", name: "releaseName", message: `更新标题`, default: "更新" },
  { type: "editor", name: "releaseNotes", message: `更新描述:` },
  {
    type: "list",
    name: "env",
    message: "环境？",
    choices: envs.map((e) => ({ name: e, value: e })),
  },
  {
    type: "list",
    name: "platforms",
    message: "平台？",
    choices: [
      { name: "all", value: platforms },
      ...platforms.map((p) => ({ name: p, value: [p] })),
    ],
  },
];

interface QueryResult {
  env: envType;
  platforms: platformType[];
  version: string;
  releaseName: string;
  releaseNotes: string;
}

export async function query() {
  const result = await inquirer.prompt<QueryResult>(options);
  console.log(result);
  return result;
}
```

`build/packaging-cli/command-executor.ts`

```js
import { spawn } from 'child_process'

export function execCommand(command: string, args: string[]) {
  return new Promise((resolve, reject) => {
    const ls = spawn(command, args, { stdio: 'inherit' })

    ls.on('error', error => {
      console.error(error.message)
    })

    ls.on('close', code => {
      console.log(`[${command} ${args.join(' ')}]` + `exited with code ${code}`)
      code === 0 ? resolve() : reject(code)
    })
  })
}
```

`build/packaging-cli/json-updater.ts`

```js
import * as path from "path";
import { readJsonSync, writeJSONSync } from "fs-extra";

export const PACKAGE_JSON_PATH = path.resolve(__dirname, "..", "..", "package.json");
export const PACKAGE_JSON_LOCK_PATH = path.resolve(__dirname, "..", "..", "package-lock.json");

// 读取json内容
export const readJSON = (path: string) => () => readJsonSync(path);

// 覆写json变量
export const writeJSON = (path: string) => (vars: any) => writeJSONSync(path, vars);
```

`build/packaging-cli/builder.ts`

```js
import { envType, platformType } from "./inquirer";
import { execCommand } from "./command-executor";

export async function build(env: envType, platforms: platformType[]) {
  process.env.ENV = env;
  await execCommand(`npm`, ["run", "build"]);

  let buildArgs = ["build"];
  if (platforms.includes("win")) buildArgs.push("--win");
  if (platforms.includes("mac")) buildArgs.push("--mac");

  await execCommand(`electron-builder`, buildArgs);
}
```

`file-uploader.ts`

```js
import FTPClient from "ftp";
import * as path from "path";
import * as fs from "fs";

const clientConfig = {
  host: "host.to.your.server",
  port: 60021,
  user: "username",
  password: "password",
};

const Client = new FTPClient();

function connectClient() {
  return new Promise((resolve, reject) => {
    Client.on("ready", resolve);
    Client.on("error", reject);
    Client.connect(clientConfig);
  });
}

function putFile(file: string, dest: string) {
  return new Promise((resolve, reject) => {
    Client.put(file, dest, (err) => (err ? reject(err) : resolve()));
  });
}

export async function uploadDir(dir: string, dest: string) {
  await connectClient();

  const task: [string, string][] = fs
    .readdirSync(dir)
    .map((f) => path.resolve(dir, f))
    .filter((f) => fs.statSync(f).isFile())
    .map((f) => ([f, `${dest}/${path.basename(f)}`]));

  await Promise.all(task.map(([src, dest]) => putFile(src, dest)));

  Client.end();
}
```

最后是一个index入口文件: `build/packaging-cli/index.ts`

```js
import { query } from "./inquirer";
import { readJSON, PACKAGE_JSON_PATH } from "./json-updater";
import { execCommand } from "./command-executor";
import { updateChangeLog } from "./changelog-updater";
import { build } from "./builder";
import * as path from "path";
import { uploadDir } from "./file-uploader";

const RELEASE_DIR = path.resolve(__dirname, "..", "..", "release");

async function startPackaging() {
  let { version, env, platforms, releaseName, releaseNotes } = await query();

  // 更新版本号
  await execCommand("npm", ["version", version ? version : "patch"]);
  version = readJSON(PACKAGE_JSON_PATH)().version;
  // 更新CHANGELOG
  updateChangeLog({ version, releaseName, releaseNotes });
  // 开始打包
  await build(env, platforms);
  // 上传文件
  await uploadDir(path.resolve(RELEASE_DIR, version), "test-app-temp")
}

startPackaging();
```

在package.json中添加命令

```json
"pack": "ts-node ./build/packaging-cli/index.ts",
```

## 程序更新

基于[electron-updater]()

### API

API方法 / 功能

- `checkForUpdates()` 检查更新
- `checkForUpdatesAndNotify() `检查更新，有更新则提示
- `downloadUpdate(cancellationToken) `下载更新
- `getFeedURL() `获取更新服务链接
- `setFeedURL(options)` 设置更新服务链接
- `quitAndInstall(isSilent, isForceRunAfter) `退出应用并安装更新

### Event

事件 / 触发

- error 更新错误
- checking-for-update 检查更新中
- update-available 有可用更新
- update-not-available 没有可用更新
- download-progress 下载更新中
- update-downloaded 更新下载完成

在主进程监听检查更新事件

```js
import { autoUpdater } from 'electron-updater'
import { ipcMain } from 'electron'

ipcMain.on('CHECK_FOR_UPDATE', function(){
  autoUpdater.checkForUpdatesAndNotify()
})
```

在渲染进程点击按钮发送ipc事件检查更新(以React为例)

```js
import React from 'react';
import { ipcRenderer } from 'electron'
import './App.css';

function App() {
  return (
    <div className="App">
      <button onClick={ipcRenderer.send('CHECK_FOR_UPDATE')}>检查更新</button>
    </div>
  );
}

export default App;
```

### 更新服务的设计

需要实现的功能

1. 查看更新信息
2. 用户手动检查更新;
3. 应用启动时静默检查更新;
4. 应用在后台定时检查更新;
5. 用户手动下载更新;
6. 下载进度显示;
7. 用户手动退出安装更新;
8. 通过版本号控制强制更新;
9. 日志;
10. 开发时请求本地服务做测试;

更新过程的所有状态:

| 状态        | 描述         |
| ----------- | ------------ |
| Idle        | 空闲         |
| Checking    | 检查中       |
| Available   | 有可下载更新 |
| Downloading | 下载中       |
| Downloaded  | 下载完成     |

更新服务的初步的设计

```js
// app/updater.ts
import { autoUpdater, UpdateInfo } from 'electron-updater'

interface CheckResult{
  // 是否有更新
  available: boolean
  // 更新内容
  updateInfo: UpdateInfo
}

interface ProgressInfo {
  total: number
  delta: number
  transferred: number
  percent: number
  bytesPerSecond: number
}

// 下载进度回调
type DownloadProgressCallback = (p: ProgressInfo) => void
// 下载结束回调
type DownloadedCallback = () => void

abstract class AppUpdateService {
  // 检查更新
  public abstract checkUpdate(): CheckResult
  // 下载更新
  public abstract downloadUpdate(params: {onDownloadProgress: DownloadProgressCallback, onDownloaded: DownloadedCallback }): void
  // 应用更新
  public abstract applyUpdate(): void
}
```

由于ipc通信的限制，无法传递回调函数，因此在这里考虑将更新服务的业务功能封装都移到渲染进程，主进程只提供基本的初始化服务和接口方法的封装。

app/updater.ts

```js
import { autoUpdater } from "electron-updater";
import logger from "electron-log";
import { BrowserWindow, ipcMain, app } from 'electron';

function checkUpdate() {
  return autoUpdater.checkForUpdates();
}

function downloadUpdate() {
  return autoUpdater.downloadUpdate();
}

function applyUpdate() {
  return autoUpdater.quitAndInstall();
}

function sendToAllBrowserWindows(channel: string, ...args: unknown[]) {
  const browserWindows = BrowserWindow.getAllWindows()
  // 这里向每一个活跃的window发送event是合理的吗？
  browserWindows.forEach(bw=>bw.webContents.send(channel, ...args))
}

function init() {
  // 日志
  logger.transports.file.level = "info";
  autoUpdater.logger = logger;

  // 禁用自动下载
  autoUpdater.autoDownload = false;
  // 启用退出app时自动安装更新
  autoUpdater.autoInstallOnAppQuit = true;

  // 监听事件并发送到渲染进程
  const events = [
    "error",
    "checking-for-update",
    "update-available",
    "update-not-available",
    "download-progress",
    "update-downloaded",
  ]
  //这里应该传eventName到sendToAllBrowserWindows
  //即 sendToAllBrowserWindows.bind(null, 'APP_UPDATER/STATUS_CHANGE',eventName)
  events.forEach((eventName) => autoUpdater.on(eventName, sendToAllBrowserWindows.bind(null, 'APP_UPDATER/STATUS_CHANGE')));

  // 通过接收渲染进程发送的ipc调用方法
  ipcMain.on('APP_UPDATER/CHECK_UPDATE', checkUpdate)
  ipcMain.on('APP_UPDATER/DOWNLOAD_UPDATE', downloadUpdate)
  ipcMain.on('APP_UPDATER/APPLY_UPDATE', applyUpdate)
}

app.once('will-finish-launching', init)

export const AppUpdater = {
  checkUpdate,
  downloadUpdate,
  applyUpdate,
}
```

在渲染进程，首先创建一个自定义hooks来实现接收更新状态变更并通过`createContext`来实现组件状态共享。 `renderer/src/Hooks/useAppUpdate.js`

```js
import React, { useState, useEffect, useContext, createContext } from "react";
import { ipcRenderer } from "electron";

export function useAppUpdate() {
  const [status, setStatus] = useState(null);
  const [updateInfo, setUpdateInfo] = useState(null);
  const [updateProgressInfo, setUpdateProgressInfo] = useState(null);
  const [error, setError] = useState(null);

  const checkUpdate = () => ipcRenderer.send('APP_UPDATER/CHECK_UPDATE')
  const downloadUpdate = () => ipcRenderer.send('APP_UPDATER/DOWNLOAD_UPDATE')
  const applyUpdate = () => ipcRenderer.send('APP_UPDATER/APPLY_UPDATE')

  useEffect(() => {
     // (event, updateEventName, ...args) 这个函数签名和前面似乎不对应？
    ipcRenderer.on("APP_UPDATER/STATUS_CHANGE", (event, updateEventName, ...args) => {
        console.log(`updater#${updateEventName}: `, ...args);

        setStatus(updateEventName);

        switch (updateEventName) {
          case "error":
            setError(args[0]);
            break;
          case "checking-for-update":
            break;
          case "update-available":
            setUpdateInfo(args[0]);
            break;
          case "update-not-available":
            break;
          case "download-progress":
            setUpdateProgressInfo(args[0]);
            break;
          case "update-downloaded":
            setUpdateInfo(args[0]);
            break;

          default:
            break;
        }
      }
    );
  }, []);

  return { 
    status, updateInfo, updateProgressInfo, error, 
    checkUpdate, downloadUpdate, applyUpdate,
  };
}

const UpdaterContext = createContext();

export const UpdaterProvider = ({ children }) => {
  const state = useAppUpdate();
  return <UpdaterContext.Provider value={state}>{children}</UpdaterContext.Provider>;
};

export function useUpdaterContext(){
  const store = useContext(UpdaterContext)
  return store
}
```

新建一个AboutPanel组件，在组件中显示更新信息下载进度，已经更新按钮等 `renderer/src/Components/AboutPanel/index.jsx`

```js
import React, {useMemo} from 'react'
import { useUpdaterContext } from '../../Hooks/useAppUpdate'

export function AboutPanel() {
  const { 
    status, updateInfo, updateProgressInfo, error, 
    checkUpdate, downloadUpdate, applyUpdate,
   } = useUpdaterContext()

   const Button = useMemo(()=>{
    if(status === 'update-available'){
      return <button onClick={downloadUpdate}>Download Updates</button>
    }
    if(status === 'download-progress'){
      return <button>Downloading...</button>
    }
    if(status === 'update-downloaded'){
      return <button onClick={applyUpdate}>Apply Updates</button>
    }
    return <button onClick={checkUpdate}>Check for Updates</button>
   }, [status])

   const Info = useMemo(()=>{
     if(status === 'error'){
       console.log('error',error)
       return <> 
          <p style={{color: 'lightpink'}}>{error?.name}</p> 
          <p style={{color: 'lightpink'}}>{error?.message}</p> 
          <p style={{color: 'lightpink'}}>{error?.stack}</p> 
        </>
     }
     if(status === 'checking-for-update'){
       return <p>Checking...</p>
     }
    if(status ==='update-not-available'){
      return <p>No Updates Available</p>
    }
    if(updateInfo){
      const {version, releaseName, releaseNotes, releaseDate} = updateInfo
      return <>
        <p>version: {version}</p>
        <p>date: {releaseDate}</p>
        <p>name: {releaseName}</p>
        <p>notes: {releaseNotes}</p>
      </>
    }
   }, [status, updateInfo, error])

   return <div>
      {Info}

      {
        status === 'download-progress' && Boolean(updateProgressInfo) && 
        <div style={{backgroundColor: 'grey', width: 300, height: 20, margin: '12px auto'}}>
          <div style={{backgroundColor: 'cornflowerblue', height: 20, width: 300 * updateProgressInfo.percent / 100}}></div>
        </div>
      }

      {Button}
   </div>
}
```

新建一个UpdateChecker组件，在这个组件中做静默检查、定时检查和更新提示 `renderer/src/Components/UpdateChecker/index.jsx`

```js
import React from 'react'
import { useAppUpdate } from '../../Hooks/useAppUpdate'
import { useEffect } from 'react'

export function UpdateChecker(){
  const {checkUpdate, downloadUpdate, applyUpdate, updateInfo, status, updateProgressInfo} = useAppUpdate()

  useEffect(()=>{
     // 这里是定时检测更新，如果更细一点，暴露API设置检查更新的间隔
    let timeout
    function scheduleCheckUpdate(){
      if(!['checking-for-update', 'update-available', 'download-progress', 'update-downloaded'].includes(status)){
        checkUpdate()
      }
      timeout = setTimeout(() => {
        scheduleCheckUpdate()
      }, 1000 * 60 *60);  
    }
    scheduleCheckUpdate()

    // 取消定时检测更新，这个函数返回值怎么触发？
    // https://zhuanlan.zhihu.com/p/489229220
    // 1、首先渲染，并不会执行useEffect中的 return
    // 2、变量修改后，导致的重新render，会先执行 useEffect 中的 return，再执行useEffect内除了return部分代码。
    return () => clearTimeout(timeout)
  }, [])

  useEffect(() => {
    if(status === 'update-available'){
		  // eslint-disable-next-line no-restricted-globals
      const result = confirm('Updates available, download instantly?')
      if(result){
        downloadUpdate()
      }
    }
    if(status === 'update-downloaded'){
		  // eslint-disable-next-line no-restricted-globals
      const result = confirm('Download completed, apply updates?')
      if(result){
        applyUpdate()
      }
    }
  }, [status])

  return null
}
```



## 应用协议

从网页端唤起特定的Electron应用，通过自定义协议来实现，示例代码：

```js
const agreement = 'my-electron-app' // 自定义协议名
app.setAsDefaultProtocolClient(agreement)
```

唤起方式：

```
my-electron-app:path/to
```

应用程序唤起，mac系统会触发open-url事件，window系统会触发second-instance事件。

```js
const { app, dialog } = require('electron')
const agreement = 'my-electron-app'
const AGREEMENT_REGEXP = new RegExp(`^${agreement}://`)

// 监听自定义协议唤起
function watchProtocol() {
  // mac唤醒应用 会激活open-url事件 在open-url中判断是否为自定义协议打开事件
  app.on('open-url', (event, url) => {
    const isProtocol = AGREEMENT_REGEXP.test(url)
    if (isProtocol) {
      dialog.showMessageBox({
        type: 'info',
        message: 'Mac protocol 自定义协议打开',
        detail: `自定义协议链接:${url}`,
      })
    }
  })
  // window系统下唤醒应用会激活second-instance事件 它在ready执行之后才能被监听
  app.on('second-instance', (event, commandLine) => {
    // commandLine 是一个数组， 唤醒的链接作为数组的一个元素放在这里面
    commandLine.forEach(str => {
      if (AGREEMENT_REGEXP.test(str)) {
        dialog.showMessageBox({
          type: 'info',
          message: 'window protocol 自定义协议打开',
          detail: `自定义协议链接:${str}`,
        })
      }
    })
  })
}

// 在ready事件回调中监听自定义协议唤起
watchProtocol()
```

一些其他API

- **app.removeAsDefaultProtocolClient(protocol)** 删除注册的协议, 返回是否成功删除的Boolean
- **Mac: app.isDefaultProtocolClient(protocol)** 当前程序是否为协议的处理程序。
- **app.getApplicationNameForProtocol(url)** 获取该协议链接的应用处理程序



## 托盘

```js
const { Tray, Menu, nativeTheme, BrowserWindow } = require('electron')
const path = require('path')

let tray

// 设置顶部APP图标的操作和图标
const lightIcon = path.join(__dirname, '..', '..', 'resources', 'tray', 'StatusIcon_light.png')
const darkIcon = path.join(__dirname, '..', '..', 'resources', 'tray', 'StatusIcon_dark.png')

// 根据系统主题显示不同的主题图标
tray = new Tray(nativeTheme.shouldUseDarkColors ? darkIcon : lightIcon)

tray.setToolTip('Electron-Playground')

const contextMenu = Menu.buildFromTemplate([
  {
    label: '打开新窗口',
    click: () => {
      let child = new BrowserWindow({ parent: BrowserWindow.getFocusedWindow() })
      child.loadURL('https://electronjs.org')
      child.show()
    },
  },
  {
    label: '删除图标',
    click: () => {
      tray.destroy()
    },
  },
])

tray.setContextMenu(contextMenu)
```

我们设置了托盘根据系统主题显示不同的图标，但是系统主题是动态的，继续完善：

```js
nativeTheme.on('updated', () => {
  tray.setImage(nativeTheme.shouldUseDarkColors ? darkIcon : lightIcon)
})
```

---

### 有未读消息时图标闪动(windows)

```js
const { Tray, Menu, nativeTheme, BrowserWindow, nativeImage } = require('electron')
const path = require('path')

let tray
let timer
let toggle = true
let haveMessage = true

const lightIcon = path.join(__dirname, '..', '..', 'resources', 'tray', 'StatusIcon_light.png')
const darkIcon = path.join(__dirname, '..', '..', 'resources', 'tray', 'StatusIcon_dark.png')

const win = BrowserWindow.getFocusedWindow();

tray = new Tray(lightIcon)

const contextMenu = Menu.buildFromTemplate([
  {
    label: '张三的消息',
    click: () => {
      let child = new BrowserWindow({ parent: BrowserWindow.getFocusedWindow() })
      child.loadURL('https://electronjs.org')
      child.show()
    },
  },
  { type: 'separator' },
  {
    label: '删除图标',
    click: () => {
      tray.destroy()
      clearInterval(timer)
    },
  },
])

tray.setContextMenu(contextMenu)

tray.setToolTip('Electron-Playground')

if (haveMessage) {
  timer = setInterval(() => {
    toggle = !toggle
    if (toggle) {
      tray.setImage(nativeImage.createEmpty())
    } else {
      tray.setImage(lightIcon)
    }
  }, 600)
}
```



## 下载管理器

在 electron 中的下载行为，都会触发 session 的 [will-download](https://www.electronjs.org/docs/api/session#instance-events) 事件。在该事件里面可以获取到 [downloadItem](https://www.electronjs.org/docs/api/download-item) 对象，通过 [downloadItem](https://www.electronjs.org/docs/api/download-item) 对象实现一个简单的文件下载管理器：

由于 electron 是基于 chromium 实现的，通过调用 webContents 的 [downloadURL](https://www.electronjs.org/docs/api/web-contents#contentsdownloadurlurl) 方法，相当于调用了 chromium 底层实现的下载，会忽略响应头信息，触发 [will-download](https://www.electronjs.org/docs/api/session#instance-events) 事件。

```js
// 触发下载
win.webContents.downloadURL(url)

// 监听 will-download
session.defaultSession.on('will-download', (event, item, webContents) => {})
```

在上面的效果图中，实现的简单文件下载管理器功能包含：

- 设置保存路径
- 暂停/恢复和取消
- 下载进度
- 下载速度
- 下载完成
- 打开文件和打开文件所在位置
- 文件图标
- 下载记录

### 设置保存路径

如果没有设置保存路径，electron 会自动弹出系统的保存对话框。不想使用系统的保存对话框，可以使用 [setSavePath](https://www.electronjs.org/docs/api/download-item#downloaditemsetsavepathpath) 方法，当有重名文件时，会直接覆盖下载。

```js
item.setSavePath(path)
```

为了更好的用户体验，可以让用户自己选择保存位置操作。当点击位置输入框时，渲染进程通过 ipc 与主进程通信，打开系统文件选择对话框。

主进程实现代码：

```js
/**
 * 打开文件选择框
 * @param oldPath - 上一次打开的路径
 */
const openFileDialog = async (oldPath: string = app.getPath('downloads')) => {
  if (!win) return oldPath

  const { canceled, filePaths } = await dialog.showOpenDialog(win, {
    title: '选择保存位置',
    properties: ['openDirectory', 'createDirectory'],
    defaultPath: oldPath,
  })

  return !canceled ? filePaths[0] : oldPath
}

ipcMain.handle('openFileDialog', (event, oldPath?: string) => openFileDialog(oldPath))
```

渲染进程代码：

```js
const path = await ipcRenderer.invoke('openFileDialog', 'PATH')

console.log(path)
```

### 暂停/恢复和取消

拿到 [downloadItem](https://www.electronjs.org/docs/api/download-item) 后，暂停、恢复和取消分别调用 `pause`、`resume` 和 `cancel` 方法。当我们要删除列表中正在下载的项，需要先调用 cancel 方法取消下载。

### 下载进度

在 [downloadItem](https://www.electronjs.org/docs/api/download-item) 中监听 updated 事件，可以实时获取到已下载的字节数据，来计算下载进度和每秒下载的速度。

```js
// 计算下载进度
const progress = item.getReceivedBytes() / item.getTotalBytes()
```

在下载的时候，想在 Mac 系统的程序坞和 Windows 系统的任务栏展示下载信息，比如：

- 下载数：通过 app 的 [badgeCount](https://www.electronjs.org/docs/api/app#appbadgecount-linux-macos) 属性设置，当为 0 时，不会显示。也可以通过 dock 的 [setBadge](https://www.electronjs.org/docs/api/app#appsetbadgecountcount-linux-macos) 方法设置，该方法支持的是字符串，如果不要显示，需要设置为 ''。
- 下载进度：通过窗口的 [setProgressBar](https://www.electronjs.org/docs/api/browser-window#winsetprogressbarprogress-options) 方法设置。

由于 Mac 和 Windows 系统差异，下载数仅在 Mac 系统中生效。加上 process.platform === 'darwin' 条件，避免在非 Mac、Linux 系统下出现异常错误。

```js
// mac 程序坞显示下载数：
// 方式一
app.badgeCount = 1
// 方式二
app.dock.setBadge('1')

// mac 程序坞、windows 任务栏显示进度
win.setProgressBar(progress)
```

### 下载速度

由于 [downloadItem](https://www.electronjs.org/docs/api/download-item) 没有直接为我们提供方法或属性获取下载速度，需要自己实现。

思路：在 updated 事件里通过 getReceivedBytes 方法拿到本次下载的字节数据减去上一次下载的字节数据。这个差值就是这次下载的数据大小。

```js
// 记录上一次下载的字节数据
let prevReceivedBytes = 0

item.on('updated', (e, state) => {
  const receivedBytes = item.getReceivedBytes()
  // 计算每秒下载的速度
  downloadItem.speed = receivedBytes - prevReceivedBytes
  prevReceivedBytes = receivedBytes
})
```

需要注意的是，updated 事件执行的时间约 500ms 一次，上面的代码中时间delta=500ms。实际上，应该换算为每秒的速度。

### 下载完成

当一个文件下载完成、中断或者被取消，需要通知渲染进程修改状态，通过监听 [downloadItem](https://www.electronjs.org/docs/api/download-item) 的 done 事件。

```js
item.on('done', (e, state) => {
  downloadItem.state = state
  downloadItem.receivedBytes = item.getReceivedBytes()
  downloadItem.lastModifiedTime = item.getLastModifiedTime()

  // 通知渲染进程，更新下载状态
  webContents.send('downloadItemDone', downloadItem)
})
```

### 打开文件和打开文件所在位置

使用 electron 的 shell 模块来实现打开文件（[openPath](https://www.electronjs.org/docs/api/shell#shellopenpathpath)）和打开文件所在位置（[showItemInFolder](https://www.electronjs.org/docs/api/shell#shellshowiteminfolderfullpath)）。

- 由于 openPath 方法支持返回值 `Promise<string>`，当不支持打开的文件，系统会有相应的提示，
- showItemInFolder 方法返回值是 `void`。如果需要更好的用户体验，可使用 nodejs 的 fs 模块，先检查文件是否存在。

```js
import fs from 'fs'

// 打开文件
const openFile = (path: string): boolean => {
  if (!fs.existsSync(path)) return false

  shell.openPath(path)
  return true
}

// 打开文件所在位置
const openFileInFolder = (path: string): boolean => {
  if (!fs.existsSync(path)) return false

  shell.showItemInFolder(path)
  return true
}
```

### 文件图标

很方便的是使用 app 模块的 [getFileIcon](https://www.electronjs.org/docs/api/app#appgetfileiconpath-options) 方法来获取系统关联的文件图标，返回的是 `Promise<NativeImage>` 类型，可以用 toDataURL 方法转换成 base64，不需要我们去处理不同文件类型显示不同的图标。

```js
const getFileIcon = async (path: string) => {
  const iconDefault = './icon_default.png'
  if (!path) Promise.resolve(iconDefault)

  const icon = await app.getFileIcon(path, {
    size: 'normal'
  })

  return icon.toDataURL()
}
```

### 下载记录

使用 [electron-store](https://github.com/sindresorhus/electron-store) 将下载记录保存在本地。

