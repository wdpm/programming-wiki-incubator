// var Actions = require(appRoot + '/my_modules/actions')
// Actions.init({
//     logger: electronLog,
//     rootDir: $bodyScope.rootDir, 
//     callback: function () {},
// });

const fs = require("fs");
const path = require("path");
const jsonName = "actions.json";

var onChangeCallback;
var electronLog;
var Actions = {
    rootDir: "",
    jsonPath: "",
    actions: [],
    formatShortcut: function (shortcut) {
        if (process.platform === 'win32') {
            return shortcut.replace("Command", "CmdOrCtrl");
        }
        else {
            return shortcut;
        }
    },
    // JSON 讀取、快速鍵初始化
    init: function ({rootDir, logger, onChange, callback}) {
        try {
            electronLog = logger;
            onChangeCallback = onChange;
            Actions.rootDir = rootDir;
            var jsonPath = path.normalize(rootDir + "/" + jsonName);
            var readData = function (filePath, callback) {
                var json = fs.readFileSync(filePath, 'utf8');
                var data = JSON.parse(json);
                Actions.actions = data;
                callback && callback();
            };

            if (!fs.existsSync(jsonPath)) {
                writeFileAtomic(jsonPath, JSON.stringify([]), function () {
                    readData(jsonPath, callback);
                });
            }
            else {
                readData(jsonPath, callback);
            }
            Actions.jsonPath = jsonPath;
        }
        catch (err) {
            Actions.jsonPath = jsonPath;
            Actions.actions = [];
            electronLog && electronLog.error(err.stack || err);
            callback && callback();
        }
        // 監聽檔案修改
        Actions.watch();
    },
    // 檔案監聽，支援同步工具
    watch: function () {
        try {
            if (fs.existsSync(Actions.jsonPath)) {
                fs.watchFile(Actions.jsonPath, { persistent: true, interval: 4000 }, function(curr, prev) {
                    if (curr.mtimeMs === prev.mtimeMs) return;
                    if (!fs.existsSync(Actions.jsonPath)) return;
                    electronLog.info(`[app] The actions.json file is detected to be modified.`);
                    Actions.unwatch();
                    Actions.init({
                        rootDir: Actions.rootDir,
                        onChange: onChangeCallback,
                        callback: function () {
                            onChangeCallback && onChangeCallback();
                        },
                        logger: electronLog
                    });
                });
            }
        }
        catch (err) {}
    },
    // 取消監聽
    unwatch: function () {
        try {
            if (fs.existsSync(Actions.jsonPath)) {
                fs.unwatchFile(Actions.jsonPath);
            }
        }
        catch (err) {}
    },
    save: function (actions) {
        electronLog.info(`[app] Saving Actions to ${Actions.jsonPath}`);
        try {
            Actions.unwatch();
            let data = Actions.actions;
            writeFileAtomic(Actions.jsonPath, JSON.stringify(data), function () {
                Actions.watch();
            });
        }
        catch (err) {
            electronLog && electronLog.error(err.stack || err);
        }
    }
}

module.exports = Actions;