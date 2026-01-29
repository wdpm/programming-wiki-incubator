const path = require("path")
const fs = require('fs');
var appRoot = require('app-root-path');

let loadedLanguage;

module.exports = i18n;

function i18n () {

    const supportLanguage = {
        "zh_CN": true,
        "zh_TW": true,
        "en": true,
        "es_ES": true,
        "ru_RU": true,
        "de_DE": true,
        "ko_KR": true,
        "ja_JP": true
    };
    const lngMaps = {
        'zh_Hant': 'zh_TW',
        'zh_Hans': 'zh_CN',
        "zh_CN": 'zh_CN',
        "zh_TW": "zh_TW",
        'ru': 'ru_RU',
        "zh": "zh_CN",
        'es': 'es_ES',
        'de': 'de_DE',
        'ja': 'ja_JP',
        'ko': 'ko_KR',
        'en': 'en'
    };
    var locale;
    var preference;

    try {
        const appDataPath = require(appRoot + '/my_modules/appdata-path');    // 2ms
        preference = JSON.parse(fs.readFileSync(`${appDataPath()}/Eagle/Settings`, "utf-8")).preferences;
    }
    catch (err) {
        const settings = require(appRoot + '/my_modules/electron-settings');
        preference = settings.getSync('preferences');
    }
    
    // 如果有最新版的结构
    if (preference && preference.general && preference.general.language) {
        locale = preference.general.language;
        console.log("Using preference.general.language: %s!", locale);
    }
    // 如果有旧版的结构
    else if (preference && preference.language) {
        locale = preference.language;
        console.log("Using preference.language: %s!", locale);
    }
    else {
        // const osLocale = require('os-locale');
        // locale = osLocale.sync();
        console.log("No preferences exist.");
        try {
            var electron = require('electron');
            var app = electron.app || electron.remote.app;
            locale = app.getLocale() || "en";
            locale = locale.replace("-", "_");
            if (!supportLanguage[locale]) {
                var lng = locale.split("_")[0];
                if (lngMaps[lng]) {
                    locale = lngMaps[lng];
                }
                else {
                    locale = "en";
                }
            }
        }
        catch (err) {
            locale = "en";
        }
    }

    // 正规划，避免出现不支持的语系
    if (supportLanguage[locale]) {
        // console.log("Yep, Eagle is support %s!", locale);
    }
    else {
        // console.log("Ohh on, Eagle is not support %s!", locale);
        locale = "en";
    }

    this.locale = locale;

    console.log("Eagle using %s as default language.", locale);

    if(fs.existsSync(path.join(__dirname, locale + '.js'))) {
        try {
            loadedLanguage = JSON.parse(fs.readFileSync(path.join(__dirname, locale + '.js'), 'utf8'))
        }
        catch (err) {
            loadedLanguage = require(path.join(__dirname, 'en.js'));
            console.log(loadedLanguage);
            // loadedLanguage = JSON.parse(fs.readFileSync(path.join(__dirname, 'en.js'), 'utf8'))
        }
    }
    else {
        loadedLanguage = JSON.parse(fs.readFileSync(path.join(__dirname, 'en.js'), 'utf8'))
    }
}

i18n.prototype.reload = function () {
    const settings = require(appRoot + '/my_modules/electron-settings');
    var preference = settings.getSync('preferences') || {};
    var locale = preference.language || "en";
    if(fs.existsSync(path.join(__dirname, locale + '.js'))) {
         loadedLanguage = JSON.parse(fs.readFileSync(path.join(__dirname, locale + '.js'), 'utf8'))
    }
    else {
        loadedLanguage = JSON.parse(fs.readFileSync(path.join(__dirname, 'en.js'), 'utf8'))
    }
};

i18n.prototype.__ = function(phrase) {
    let translation = loadedLanguage[phrase]
    if(translation === undefined) {
         translation = phrase
    }
    return translation
}
