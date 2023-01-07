# app 单例模式

> 参考：https://juejin.cn/post/6938573705047965704

单窗口实现，使用的是requestSingleInstanceLock api来获取是否有窗口正在运行。
样板代码：

```js
const gotTheLock = app.requestSingleInstanceLock()
// 获取单体实例锁，设置应用单开
if (!gotTheLock) {
  app.quit()
} else {
  app.on('second-instance', (event, commandLine, workingDirectory) => {
    // 当运行第二个实例时,将会聚焦到mainWindow这个窗口
    if (mainWindow) {
      if (mainWindow.isMinimized()) mainWindow.restore()
      mainWindow.focus()
    }
  })
  app.on('ready', createWindow)
  app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
      app.quit()
    }
  })
  app.on('activate', () => {
    if (mainWindow === null) {
      createWindow()
    }
  })
```