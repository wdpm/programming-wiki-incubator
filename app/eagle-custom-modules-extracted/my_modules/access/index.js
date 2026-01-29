const fs = require("fs");
const path = require("path");
const ipcRenderer = require('electron').ipcRenderer;

module.exports = {
    safeAccessSync: function(outputDir) {
        try {
            fs.accessSync(outputDir, fs.W_OK)
        } catch (err) {
            ipcRenderer.send('electron-log', `[bg] No access/write permission: ${outputDir}`);
            try {
                ipcRenderer.send('electron-log', `[bg] Try to remove "Rread-only" permission properity`);
                fs.chmodSync(outputDir, 0755);
                ipcRenderer.send('electron-log', `[bg] "Read-only" remove successfully`);
            } catch (err) {
                ipcRenderer.send('electron-log', `[bg] "Read-only" remove fail`);
                ipcRenderer.send('electron-log', "" + err.stack || err);
            }

            try {
                fs.accessSync(outputDir, fs.W_OK)
            } catch (err) {
                ipcRenderer.send('electron-log', `Still no access/write permission: ${outputDir}`);
                return false;
            }
        }
        return true;
    },
    // 检查 Windows 路径是否有实际的写入权限
    checkALCs: function(dir) {
        // if (process.platform === 'darwin') return true;
        try {
            let tempDirName = `${Date.now()}.tmp`;
            let tempDir = path.normalize(`${dir}/${tempDirName}`);
            fs.mkdirSync(tempDir, 0755);
            fs.rmdirSync(tempDir);
            return true;
        } catch (err) {
            console.log(err);
            ipcRenderer.send('electron-log', `[bg] ACL check fail: ${dir}`);
            return false;
        }
    },
    showReadOnlyDialog: function (dir) {
        ipcRenderer.send('show-read-only-message', dir); 
    },
    showALCDialog: function (dir) {
        ipcRenderer.send('show-alc-message', dir); 
    },
};