const {app, Menu,Tray, BrowserWindow, ipcMain, globalShortcut, webContents, crashReporter} = require('electron');
const electron = require('electron');
const fs = require('fs');
const fse = require('fs-extra');
const os = require('os');
const process = require('process');
const path = require('path');
const url = require('url');
const fontList = require('font-list');
const electronLogger = require('electron-log');
const config = {
    //evn: 'dev'
    evn:'pack'
}; //electron运行的环境变量，dev在“开发项目”里使用，pack在“打包项目”里使用

let tray = null;
//顶部menubar的模板
let menuTemplate = null;

if (process.platform === 'darwin') {
    menuTemplate = [
        {
            label: "Application",
            submenu: [
                {
                    label: "Quit", accelerator: "Command+Q", click: function () {
                    app.quit();
                }
                }
            ]
        },
        {
            label: "Edit",
            submenu: [
                {label: "Copy", accelerator: "CmdOrCtrl+C", selector: "copy:"},
                {label: "Paste", accelerator: "CmdOrCtrl+V", selector: "paste:"},
            ]
        }
    ];

} else {
    menuTemplate = [
        {
            label: 'Quit', click: function () {
            app.quit();
        }
        }
    ];
}


//创建全局的变量
global.sharedObj = {
    osType: process.platform === 'win32' ? 'windows' : 'mac',
    appPath: app.getAppPath(),
    appDataPath: '',
    userDataPath: app.getPath('userData'),
    localHttpServer: "",//记录localhost的完整地址，含port，比如"http://localhost:8000"
    localHttpServerStaticFolder: path.join(app.getPath('userData'), 'www'), //localHost静态文件资源所在的绝对路径
    doubleClickBookPath: '',//用于记录哪本书被双击打开了
};

//console.log(app.getName());

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let initialWindow;
let mainWindow;
let loginAndRegisterWindow;
let globalSettingWindow;
let debugCenterWindow;
let readerWindowArray = []; //是一个数组，用来保存对每个readerWindow的引用

//用于保证只会有一个应用实例运行，参考信息：https://newsn.net/say/electron-single-instance-lock.html
const singleInstanceLock = app.requestSingleInstanceLock();

if (!singleInstanceLock) {
    app.quit()
}
else {
    //当用户试图开启第二个应用实例时
    app.on('second-instance', (event, commandLine, workingDirectory) => {
        //如果mainWindow还在，则直接恢复这个windows
        if (mainWindow) {
            if (mainWindow.isVisible() === false) mainWindow.show();
            if (mainWindow.isMinimized()) mainWindow.restore();
            mainWindow.focus()
        } else {
            //如果mainWindow不在，则重新进入打开流程
            //打开主界面
            openMainWindow()
        }
    })
}
//END 用于保证只会有一个应用实例运行

//创建AppInitWindow，进行程序的初始化
function openInitialWindow() {

    // Create the browser window.
    initialWindow = new BrowserWindow({
        width: 280,
        height: 300,
        resizable: false,
        movable: false,
        frame: false,
        show: false,
        webPreferences: {
            nodeIntegration: true,
            enableRemoteModule: true,
            contextIsolation: false
        }
    });

    let loadUrl = '';
    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        loadUrl = 'http://localhost:3000';
    } else {
        loadUrl = url.format({
            pathname: path.join(__dirname, './build-app/index.html'),
            protocol: 'file:',
            slashes: true
        });
    }
    initialWindow.loadURL(loadUrl);

    // Open the DevTools.
    //initialWindow.webContents.openDevTools();

    //可以展示时，打开窗口
    initialWindow.once('ready-to-show', () => {
        //设置zoomFactor
        //initialWindow.webContents.setZoomFactor(zoomRatio);
        //显示窗口
        initialWindow.show();
    });

    // Emitted when the window is closed.
    initialWindow.on('closed', function () {
        // Dereference the window object, usually you would store windows
        // in an array if your app supports multi windows, this is the time
        // when you should delete the corresponding element.
        initialWindow = null
    });
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', async () => {

    //允许跨域访问
    //相关说明：https://stackoverflow.com/questions/55898000/blocked-a-frame-with-origin-file-from-accessing-a-cross-origin-frame
    app.commandLine.appendSwitch('disable-site-isolation-trials');

    //先获取appData里的数据，如果没有时，就是null
    appData = getAppData();

    //启动一个本地的http server
    await startLocalHttpServer();

    //获取argv数据，并输出到log中
    //electronLogger.info(process.argv);

    //打开mainWindow
    openMainWindow();
    //openReadiumDemoWindow();


    //输出Electron应用启动的环境
    console.log('App Config --->', config.evn);

    //设置托盘信息（只有windows有）
    if (process.platform === 'win32') {//windows下就会返回win32，哪怕是64位也会返回win32
        if (config.evn === 'dev') {
            tray = new Tray('./public/images/app-logo.png');
        } else {
            tray = new Tray(path.join(__dirname, './build-app/images/app-logo.png'));
        }

        tray.setToolTip('Neat Reader');
        tray.on('click', ()=> {
            console.log('Tray图标被单击click了');
            //如果是隐藏了，就恢复出来；如果是最小化了，就恢复一下，最后focus；如果没有了，就打开一个新的
            //如果mainWindow还在，则直接恢复这个windows
            if (mainWindow) {
                //如果是隐藏了，就恢复出来；如果是最小化了，就恢复一下，最后focus
                if (mainWindow.isVisible() === false) {
                    mainWindow.show()
                }
                if (mainWindow.isMinimized()) {
                    mainWindow.restore();
                }
                mainWindow.focus();

            } else {
                //如果mainWindow不在，则重新进入打开流程
                //打开主界面
                openMainWindow()
            }
        });
        var contextMenu = Menu.buildFromTemplate([
            {
                label: 'Quit', click: function () {
                app.quit();
            }
            }
        ]);
        tray.setContextMenu(contextMenu);
    }


    //注册快捷键：打开当前Window的DevTool
    globalShortcut.register('CommandOrControl+Shift+7', () => {
        console.log('快捷键：打开当前页面DevTool');
        let focusWeb = webContents.getFocusedWebContents();

        focusWeb.openDevTools({mode: 'detach'});
    });

    //注册快捷键：打开DEBUG中心界面
    globalShortcut.register('CommandOrControl+Shift+9', () => {
        console.log('快捷键：打开DEBUG中心');
        debugCenterWindow = new BrowserWindow({
            width: 1200,
            height: 710,
            autoHideMenuBar: true,
            show: false,
            resizable: false,
            maximizable: false,
            webPreferences: {
                nodeIntegration: true,
                enableRemoteModule: true,
                contextIsolation: false
            }
        });

        let loadUrl = '';
        //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
        if (config.evn === 'dev') {
            loadUrl = 'http://localhost:3000/#/debugcenter';
        } else {
            loadUrl = url.format({
                pathname: path.join(__dirname, './build-app/index.html'),
                protocol: 'file:',
                slashes: true,
                hash: 'debugcenter'
            });
        }
        debugCenterWindow.loadURL(loadUrl);

        // Open the DevTools.
        //initialWindow.webContents.openDevTools();

        //可以展示时，打开窗口
        debugCenterWindow.once('ready-to-show', () => {
            //显示窗口
            debugCenterWindow.show();
        });

        // Emitted when the window is closed.
        debugCenterWindow.on('closed', function () {
            debugCenterWindow = null
        });
    });

    //注册快捷键：老板键
    globalShortcut.register('CommandOrControl+Shift+|', () => {
        console.log('快捷键：老板键，隐藏或显示所有阅读界面');
        //代码逻辑：如果有一个是显示的，则全部改为隐藏；如果全都是隐藏的，则全部显示出来
        var visibleWindowNum = 0;//显示的窗口数
        var invisibleWindowNum = 0;//不可见的窗口数
        for (var i = 0; i < readerWindowArray.length; i++) {
            let currentWindow = BrowserWindow.fromId(readerWindowArray[i]);
            if (currentWindow.isVisible()) {
                visibleWindowNum++;
            } else {
                invisibleWindowNum++;
            }
        }

        //如果有任意一个窗口是可见的，则全部都先弄成不可见
        if (visibleWindowNum > 0) {
            for (var y = 0; y < readerWindowArray.length; y++) {
                let currentWindow = BrowserWindow.fromId(readerWindowArray[y]);
                if (currentWindow.isVisible()) {
                    currentWindow.hide();
                }
            }
        }

        //如果所有窗口都不可见，则全部一次性可见
        if (invisibleWindowNum === readerWindowArray.length) {
            for (var x = 0; x < readerWindowArray.length; x++) {
                let currentWindow = BrowserWindow.fromId(readerWindowArray[x]);
                if (currentWindow.isVisible() === false) {
                    currentWindow.show();
                }
            }
        }


    });

});
//app.on('ready', createTest);

// Quit when all windows are closed.
app.on('window-all-closed', function () {
    // On OS X it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
        app.quit();
    }

});

// App即将退出时调用
app.on('will-quit', async () => {
    // 注销所有快捷键
    globalShortcut.unregisterAll();

    //清理www文件夹
    await cleanWwwFolder();

    //如果tray对象存在，清理系统托盘（Mac没有这个）
    if (tray) {
        tray.destroy();
    }

});

app.on('activate', function () {
    // On OS X it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (mainWindow === null) {
        openMainWindow();
        //createTest()
    } else {
        //如果存在，说明是hide起来了，则直接show就行了
        if (mainWindow.isVisible() === false) {
            mainWindow.show()
        }
        if (mainWindow.isMinimized()) {
            mainWindow.restore();
        }
        mainWindow.focus();
    }
});

//打开应用主界面：图书管理页面
function openMainWindow() {

    //创建新的window
    mainWindow = new BrowserWindow({
        width: 1080,
        height: 680,
        autoHideMenuBar: true,
        show: false,
        minWidth: 1020,
        minHeight: 680,
        frame: false,
        webPreferences: {
            nodeIntegration: true,
            enableRemoteModule: true,
            contextIsolation: false
        }
    });

    //设置要打开的url是什么
    let loadUrl = '';
    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        loadUrl = 'http://localhost:3000/#/library';
    } else {
        loadUrl = url.format({
            pathname: path.join(__dirname, './build-app/index.html'),
            protocol: 'file:',
            slashes: true,
            hash: 'library'
        });
    }
    //console.log(loadUrl);
    mainWindow.loadURL(loadUrl);

    //开发环境下，打开开发者工具
    if (config.evn === 'dev') {
        mainWindow.webContents.openDevTools();
    }
    //设置menu
    const menu = Menu.buildFromTemplate(menuTemplate);
    Menu.setApplicationMenu(menu);

    //可以展示时，打开窗口
    mainWindow.once('ready-to-show', () => {
        //设置zoomFactor
        //mainWindow.webContents.setZoomFactor(zoomRatio);
        //显示窗口
        // mainWindow.show();

        //关闭启动页面
        if (initialWindow) {
            initialWindow.close(); //关闭启动窗口
        }
    });


    //当窗口被关闭时的操作
    mainWindow.on('closed', function () {
        console.log('mainWindow ---> closed');
        mainWindow = null;

        //在Mac下，需要特别处理，当mainWindow关闭，并且没有其他reader界面时，需要彻底退出APP
        if (process.platform === 'darwin') {
            if (readerWindowArray.length === 0) {
                //没有打开的图书时，才关闭
                app.quit();
                app.dock.hide();
            }
        }
    });

}


/*打开图书阅读界面
 * bookGuid：字符串。如果有，则传递真实的guid值，如果不知道，则传递x，代表未知
 * ownerWindowId：数字。如果是应用内部打开的，就传要求打开图书的那个Window的ID；如果是应用外部双击打开，则传递-1即可，代表无主人
 * openRequestFrom：字符串。代表是从哪里要求打开的，值可以是inside和outside。
 *
 * 如果是inside打开的，说明用户是在应用内操作打开图书的，可以认为图书已经添加完成；
 * 如果是outside打开，则说明是通过双击等操作打开的，图书可能并不存在于本地数据库中，需要进行判断处理。
 * */
function openReaderWindow(bookGuid, fileType, ownerWindowId, openRequestFrom) {
    //获取显示器的宽高
    const size = electron.screen.getPrimaryDisplay().workAreaSize;
    let windowWidth = size.width;
    let windowHeight = size.height;
    //console.log(electron.screen.getPrimaryDisplay());
    //获取保存的系统数据
    const appData = getAppData();
    let readerWindowSizeInfo = {};
    if (appData && appData.readerWindowSizeInfo) {
        readerWindowSizeInfo = JSON.parse(appData.readerWindowSizeInfo);
    }

    if (readerWindowSizeInfo.rect && process.platform === 'win32') {
        const { width, height } = readerWindowSizeInfo.rect;
        windowWidth = width;
        windowHeight = height;
    }

    let readerWindow = new BrowserWindow({
        width: windowWidth,
        height: windowHeight,
        autoHideMenuBar: true,
        minWidth: 600,
        minHeight: 500,
        backgroundColor: '#e4e4e4',
        show: true,
        webPreferences: {
            nodeIntegration: true,
            webSecurity: false,
            enableRemoteModule: true,
            contextIsolation: false
        },
    });
    //记录一下这本书guid，未来再打开时，可以判断是否重复，如果已经存在，则无需再次打开，只需focus即可
    readerWindow.bookGuid = bookGuid;
    //窗口最大化
    if (appData) {
        if (process.platform === 'win32' && readerWindowSizeInfo.isMaximized) {
            readerWindow.maximize();
        }
    } else {
        //没有appData，说明是第一次打开，默认就最大化
        readerWindow.maximize();
    }

    let loadUrl = '';
    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        if(fileType===2){
            //说明是pdf
            loadUrl = `http://localhost:3000/#/pdfreader?bookGuid=${bookGuid}&fileType=${fileType}&ownerWindowId=${ownerWindowId}&openRequestFrom=${openRequestFrom}`;
        }else{
            //说明是epub\txt或kindle家族的
            loadUrl = `http://localhost:3000/#/epubreader?bookGuid=${bookGuid}&fileType=${fileType}&ownerWindowId=${ownerWindowId}&openRequestFrom=${openRequestFrom}`;
        }

    } else {
        if(fileType===2){
            loadUrl = url.format({
                pathname: path.join(__dirname, './build-app/index.html'),
                protocol: 'file:',
                slashes: true,
                hash: 'pdfreader',
                search: `bookGuid=${bookGuid}&fileType=${fileType}&ownerWindowId=${ownerWindowId}&openRequestFrom=${openRequestFrom}`
            });
        }else{
            loadUrl = url.format({
                pathname: path.join(__dirname, './build-app/index.html'),
                protocol: 'file:',
                slashes: true,
                hash: 'epubreader',
                search: `bookGuid=${bookGuid}&fileType=${fileType}&ownerWindowId=${ownerWindowId}&openRequestFrom=${openRequestFrom}`
            });
        }

    }

    readerWindow.loadURL(loadUrl);

    //开发环境下，打开开发者工具
    if (config.evn === 'dev') {
        readerWindow.webContents.openDevTools();
    }

    //把这个window的id保存下来，便于控制整个应用的生命周期
    let windowId = readerWindow.id;
    readerWindowArray.push(readerWindow.id);

    readerWindow.on('closed', function () {
        //删除在list中的引用
        readerWindowArray.forEach(function (item, index) {
            if (windowId === item) {
                readerWindowArray.splice(index, 1);//删除对应的数值
            }
        });

        //清空这个window的引用
        readerWindow = null;
    });
}

//打开主界面，即图书管理界面的window。从初始化页面(other/AppInitialPage)发送过来的通知
ipcMain.on('openMainWindow', (event, arg) => {
    console.log('openMainWindow:', arg);
    //如果mainWindow还在，则直接恢复这个windows
    if (mainWindow) {
        //如果是隐藏了，就恢复出来；如果是最小化了，就恢复一下，最后focus
        if (mainWindow.isVisible() === false) {
            mainWindow.show()
        }
        if (mainWindow.isMinimized()) {
            mainWindow.restore();
        }
        mainWindow.focus();

    } else {
        //如果mainWindow不在，则重新进入打开流程
        //打开主界面
        openMainWindow()
    }
});


//进入地区初始化选择的界面
ipcMain.on('openLocaleInitSettingWindow', (event, arg) => {

    console.log('打开openLocaleInitSettingWindow');

    "use strict";
    let localeInitSettingWindow = new BrowserWindow({
        width: 600,
        height: 440,
        autoHideMenuBar: true,
        show: false,
        resizable: false, //不支持修改尺寸
        maximizable: false, //不支持最大化
        webPreferences: {
            nodeIntegration: true,
            enableRemoteModule: true,
            contextIsolation: false
        }
    });

    let loadUrl = '';
    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        loadUrl = `http://localhost:3000/#/localesetting`;
    } else {
        loadUrl = url.format({
            pathname: path.join(__dirname, './build-app/index.html'),
            protocol: 'file:',
            slashes: true,
            hash: 'localesetting'
        });
    }

    localeInitSettingWindow.loadURL(loadUrl);

    //可以展示时，打开窗口
    localeInitSettingWindow.once('ready-to-show', () => {
        //设置zoomFactor
        //mainWindow.webContents.setZoomFactor(zoomRatio);
        //显示窗口
        localeInitSettingWindow.show();

        //关闭图书管理页面
        if (mainWindow) {
            mainWindow.hide(); //关闭启动窗口
        }
    });

    localeInitSettingWindow.on('closed', function () {
        //清空这个window的引用
        localeInitSettingWindow = null;
        if (mainWindow) {
            mainWindow.close(); //关闭启动窗口
        }
    });
});


//接收新开图书通知（这个方法只用于在应用内部，要求打开图书）
ipcMain.on('openReaderWindow', (event, arg) => {
    //新开一本图书
    console.log('openReaderWindow:', arg);

    //判断当前要求打开的图书，是否已经打开过，如果是，则直接show对应的窗口即可，否则需要再打开新的窗口
    let isAlreadyOpen = false;
    for (let i = 0; i < readerWindowArray.length; i++) {
        let wbObject = BrowserWindow.fromId(readerWindowArray[i]);
        if (wbObject.bookGuid === arg.bookGuid) {
            isAlreadyOpen = true;
            wbObject.show();//展示对应的window
        }
    }

    //说明没有打开过，则执行新的打开图书操作
    if (isAlreadyOpen === false) {
        openReaderWindow(arg.bookGuid, arg.fileType, arg.ownerWindowId, arg.openRequestFrom);
    }

});

//接收子进程发来的通知，打开登录或注册页面
ipcMain.on('openLoginAndRegisterWindow', (event, arg) => {
    console.log('openLoginAndRegisterWindow: ', arg);

    //计算zoom的值，如果有appData，就取appData里的那个值
    //let appData = getAppData();
    //let zoomRatio = 1;
    //if (appData != null) {
    //    zoomRatio = appData.zoomRatio;
    //}

    //let width = parseInt(388 * zoomRatio);
    //let height = parseInt(638 * zoomRatio);

    loginAndRegisterWindow = new BrowserWindow({
        width: 320,
        height: 580,
        resizable: false,
        autoHideMenuBar: true,
        parent: mainWindow,
        modal: false,
        maximizable: false, //不支持最大化
        webPreferences: {
            nodeIntegration: true,
            enableRemoteModule: true,
            contextIsolation: false
        }
    });

    let startUrl = '';

    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        startUrl = 'http://localhost:3000/#/loginandregister';
    } else {
        startUrl = url.format({
            pathname: path.join(__dirname, './build-app/index.html'),
            protocol: 'file:',
            slashes: true,
            hash: 'loginandregister'
        });
    }

    loginAndRegisterWindow.loadURL(startUrl);

    //loginAndRegister.webContents.openDevTools();

    loginAndRegisterWindow.on('closed', function () {
        loginAndRegisterWindow = null;
    });
});

//接收子进程发来的通知，打开全局设置页面
ipcMain.on('openGlobalSettingWindow', (event, arg) => {
    "use strict";

    console.log('openGlobalSettingWindow');

    globalSettingWindow = new BrowserWindow({
        width: 600,
        height: 420,
        autoHideMenuBar: true,
        parent: mainWindow,
        modal: false,
        resizable: false, //不支持修改尺寸
        maximizable: false, //不支持最大化
        webPreferences: {
            nodeIntegration: true,
            enableRemoteModule: true,
            contextIsolation: false
        }
    });

    let startUrl = '';

    //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    if (config.evn === 'dev') {
        startUrl = 'http://localhost:3000/#/globalsettingwindow';
    } else {
        startUrl = url.format({
            pathname: path.join(__dirname, './build-app/index.html'),
            protocol: 'file:',
            slashes: true,
            hash: 'globalsettingwindow'
        });
    }

    globalSettingWindow.loadURL(startUrl);

    //loginAndRegister.webContents.openDevTools();

    globalSettingWindow.on('closed', function () {
        globalSettingWindow = null;
    });
});


//接收子进程发来的通知，关闭全局设置页面
ipcMain.on('closeGlobalSettingWindow', (event, arg) => {
    "use strict";

    //关闭启动页面
    if (globalSettingWindow) {
        globalSettingWindow.close(); //关闭启动窗口
    }
});

//接收更多设置页面发来的通知，获取系统字体
ipcMain.on('get-system-font', (event, arg) => {
    (async () => {
        let fonts = await fontList.getFonts();
        let list = [];
        fonts.forEach((ele, index) => {
            list.push({
                value: ele.replace(/\"/g, ''),
                index: index
            })
        });

        event.sender.send('get-system-font-finished', list)
    })()
});


//接收登录注册子进程发送过来的通知，重启应用
ipcMain.on('loginOrRegisterSuccess', (event, arg) => {
    console.log('loginOrRegisterSuccess:', arg);
    // app.quit();
    mainWindow.reload();
});

//接收退出功能发送过来的通知，重启应用
ipcMain.on('handleLogout', (event, arg) => {
    //重启整个应用
    console.log('handleLogout:', arg);
    app.relaunch();
    app.quit();
});

//接收地区初始化设置子进程发送过来的通知，重启应用
ipcMain.on('regionAndLanguageChanged', (event, arg) => {
    console.log('regionAndLanguageChanged:', arg);
    app.relaunch();
    app.quit();
});

//接收任意子模块发来的通知，关闭当前窗口
ipcMain.on('closeWindow', (event, arg) => {
    "use strict";
    console.log('closeWindow:', arg);
});

ipcMain.on('three-loginToken', (event, arg) => {
    "use strict";

    // let threeLoginWindow = new BrowserWindow({show: false});
    //
    // let loadUrl = '';
    // //自动判断，是开发中，还是要打包了；开发中加载localhost:3000，打包则是加载app文件夹下的文件
    // if (config.evn === 'dev') {
    //     loadUrl = 'http://localhost:3000/#/wx_login?loginToken=' + arg;
    // } else {
    //     loadUrl = url.format({
    //         pathname: path.join(__dirname, './build-app/index.html'),
    //         protocol: 'file:',
    //         slashes: true,
    //         hash: 'wx_login',
    //         search: '?loginToken=' + arg
    //     });
    // }
    //
    // threeLoginWindow.loadURL(loadUrl);
    //
    // threeLoginWindow.on('closed', function () {
    //     threeLoginWindow = null;
    // });

    console.log('three-loginToken', arg);

    loginAndRegisterWindow.webContents.send('three-login-success', arg);
});


//辅助方法：获得appData数据，如果没有，则返回null
function getAppData() {
    "use strict";
    let appData = null;
    let appDataFilePath = null;
    if (process.platform === 'win32') {//windows下就会返回win32，哪怕是64位也会返回win32
        appDataFilePath = app.getPath('userData') + `\\appData`;
    } else {
        appDataFilePath = app.getPath('userData') + `\/appData`;
    }

    global.sharedObj.appDataPath = appDataFilePath;

    try {
        appData = fse.readJsonSync(appDataFilePath);
    } catch (err) {
        //console.log('getAppData()',err);
    }

    return appData;
}

//启动一个本地的 http server
async function startLocalHttpServer() {

    const detect = require('detect-port');
    const Koa = require('koa');
    const staticFolder = require('koa-static');

    const app = new Koa();

    const hostName = 'localhost'; //ip
    const port = 8000; //端口

    app.use(staticFolder(
        global.sharedObj.localHttpServerStaticFolder
    ));

    //确认www文件夹是否已经存在，不存的话，需要创建一个
    try {
        fse.ensureDirSync(global.sharedObj.localHttpServerStaticFolder);
        console.log('www文件夹已存在、或创建成功');
    } catch (err) {
        console.error(err)
    }


    //监测端是否可以用，如果不可用，则自动使用另一个
    const _port = await detect(port);

    if (port === _port) {
        console.log(`Port: ${port} was not occupied`);
    } else {
        console.log(`Port: ${port} was occupied, try port: ${_port}`);
    }

    app.listen(_port, hostName, function () {
        console.log(`Server is running at http://${hostName}:${_port}`);
        global.sharedObj.localHttpServer = `http://${hostName}:${_port}`;
    });

}


//清理www文件夹，避免内容太多，产生不好的影响（目前使用最简单粗暴的清理文件夹的方式，以后可以优化）
async function cleanWwwFolder() {
    /*
     * 删除文件规则：退出时，检测www文件夹中所有的文件夹，并对文件夹进行排序(按创建时间倒叙)，保留最后阅读的5本书
     * */
    // 公共路径
    const publicPath = global.sharedObj.localHttpServerStaticFolder;
    // 返回一个promise对象，对象里面是map循环后返回的一个数组，包含所有文件夹创建的时间及文件夹名 [{createTime: xxxx, folderName: xxxx}]
    const returnFolderInfoAsync = async (folders) => {
        return Promise.all(folders.map(async item => mapFolderAsyncFn(item)));
    };
    // 同步循环文件夹，每次操作返回一个promise对象，里面包含文件夹的创建时间和文件夹名
    const mapFolderAsyncFn = async (item) => {
        const fileDir = path.join(publicPath, item);
        const stats = await fs.lstatSync(fileDir);
        const folderData = {
            createTime: parseInt(stats.ctimeMs / 1000, 10),
            folderName: item
        };
        return Promise.resolve(folderData);
    };
    // 创建时间按倒叙进行排序
    const createTimeSortDesc = (a, b) => {
        return b.createTime - a.createTime;
    };
    // 读取www下所有的文件夹
    const folders = await fs.readdirSync(publicPath);
    // 所有的文件夹信息
    const allFolderInfoData = await returnFolderInfoAsync(folders);
    // console.log('allFolderInfoData', allFolderInfoData);
    // 排序后的文件夹信息
    const orderData = allFolderInfoData.sort(createTimeSortDesc);
    // 需要删除的文件夹信息
    const deleteFolderInfo = orderData.length > 5 ? orderData.splice(5, orderData.length) : orderData;
    // 循环删除经过排序后的文件夹
    deleteFolderInfo.forEach(async item => {
        await fse.removeSync(path.join(publicPath, item.folderName))
    });
}


//记录崩溃日志
crashReporter.start({
    productName: 'NeatReader Desk App',
    companyName: 'Gauzy Tech',
    submitURL: 'http://localhost:9000',
    uploadToServer: true
});
