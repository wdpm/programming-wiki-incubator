# setup wakatime in github profile

## 建立步骤
1. 注册用户，访问 [用户界面](https://wakatime.com/settings/account) 并复制 Secret API Key
2. 在常用的 IDE 中安装对应的 wakatime plugin，重启并将 Secret API Key 填入。
3. 访问自己 github 的 profile 仓库，链接格式： `https://github.com/{user}/{user}`。
    将下面内容填入 README.md 文件
    ```
    <!--START_SECTION:waka-->
    <!--END_SECTION:waka-->
    ```
4. 访问 【设置 -> 密钥】 页面，添加一个名称为 WAKATIME_API_KEY 的密钥，值为上面 Secret API Key 的值。
   > If you're not using profile repository, add another secret named GH_TOKEN and insert your GitHub token* in place of value.

5. 创建一个 github workflow 配置文件，路径为 `.github/workflows/waka-readme.yml`。示例：
    ```yml
    name: Waka Readme
    
    on:
      workflow_dispatch: # for manual workflow trigger
      schedule:
        - cron: '0 0 * * *' # runs at every 12AM UTC
    
    jobs:
      update-readme:
        name: WakaReadme DevMetrics
        runs-on: ubuntu-latest
        steps:
          - uses: athul/waka-readme@master
            with:
              WAKATIME_API_KEY: ${{secrets.WAKATIME_API_KEY}}
              # following flags are required, only if this is not on
              # profile readme, remove the leading `#` to use them
              #GH_TOKEN: ${{secrets.GH_TOKEN}}
              #REPOSITORY: <gh_username/gh_username>
    ```
    [参数参考](https://github.com/marketplace/actions/waka-readme#tweaks) 。
6. 上面步骤都完成后，手动触发一次 wakatime action，发现 README 初始化为如下样子：
    ```
    No activity tracked
    ```

上面就是建立 wakatime 遥测的操作，实际上需要在多个 IDE 都设置 waka，否则遥测的数据并不准确。例如：
- vscode
- idea
- Pycharm
- Clion
- Webstorm
- Goland
- Fleet
- DataGrip