// 此代码用于在Mac上自动完成验证流程，详情请看: https://medium.com/@TwitterArchiveEraser/notarize-electron-apps-7a5f988406db

const fs = require('fs');
const path = require('path');
var electron_notarize = require('electron-notarize');

module.exports = async function (params) {
    // Only notarize the app on Mac OS only.
    if (process.platform !== 'darwin') {
        return;
    }

     console.log('===准备进行 Notarize（验证）===');

    // Same appId in electron-builder.
    let appId = 'com.reader.neat';

    let appPath = path.join(params.appOutDir, `${params.packager.appInfo.productFilename}.app`);
    if (!fs.existsSync(appPath)) {
        throw new Error(`目标路径中无法找到应用: ${appPath}`);
    }

    console.log(`===开始进行 Notarizing，此过程需要将应用传至 Apple 服务器，预计耗时5~15分钟...`);

    try {
        //其中的appleIdPassword是在https://appleid.apple.com/上专门为Notarize而生成的密码
        await electron_notarize.notarize({
            appBundleId: appId,
            appPath: appPath,
            appleId: 'neatreader@163.com',
            appleIdPassword: 'mips-wkjo-ckvr-jzzx',
        });
    } catch (error) {
        console.error(error);
    }


    console.log(`===成功完成 Notarize，可以进行发布啦===`);
};