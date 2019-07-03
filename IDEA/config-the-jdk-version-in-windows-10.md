# Config the JDK version in windows 10
> https://intellij-support.jetbrains.com/hc/en-us/articles/206544879-Selecting-the-JDK-version-the-IDE-will-run-under

我的电脑 -> 高级系统设置 -> 环境变量。
在当前 XXX用户变量 和 系统变量 中都加入:
```bash
IDEA_JDK_64
C:\Java\jdk1.8.0_211
```
因为 IDEA IDE启动时的JDK获取顺序为：
```
1. IDEA_JDK_64 / PHPSTORM_JDK_64 / WEBIDE_JDK_64 / PYCHARM_JDK_64 
/ RUBYMINE_JDK_64 / CL_JDK_64 / DATAGRIP_JDK_64 / GOLAND_JDK_64 environment variable
2. idea.config.path\<product>64.jdk file
3. ..\jre64 directory
4. system Registry
5. JDK_HOME environment variable
6. JAVA_HOME environment variable
```
如果在安装 IDEA IDE 时勾选了下载JRE，那么会对应上面第三点。路径大概如下：
```
C:\Program Files\JetBrains\IntelliJ IDEA 2019.1.3\jre64
```
这个jre64是64位的OpenJDK，可能会出现Markdown预览字体模糊的问题。