该项目仅用于Electron版本的Neat Reader打包使用

使用说明请看有道云笔记的《Electron版Neat Reader开发与打包流程说明》

步骤简介：

1.在“开发项目”中，修改好src/config-env.js的输出，然后使用 npm run build，把react应用打包。（打包完的文件在build文件夹中）

2.把“开发项目”中，build文件夹下的所有文件，复制到“打包项目”中的build-app文件夹下。并删掉static下，js和css文件夹中的.map文件

3.把“开发项目”中，根目录下的electron-main.js文件，复制到“打包项目”中的根目录，覆盖已有文件。修改文件顶部的config文件的evn为“pack”

4.在“打包项目中”，对package.json文件进行必要的更新，最重要的是version信息要更新。（如果有新的dependency，也要加入并install）

5.在“打包项目中”，使用 npm run dist 命令，在dist目录中生成可发布的安装包。

    5.1.关于如何为Windows系统分别打包64位安装包和32位安装包
    在package.json中，找到build > win > target > arch选项，可以看到有两个数组元素："x64"和"ia32"。
    如果打包时，上述两个值都在的话，打包出来的安装包就包含了64位和32位两个包，用户安装时，系统会自动选择合适的包去进行安装。
    如果需要单独为某一个平台打包，只需要删掉另一个，然后跑dist命令，就可以打包出专门为64位或是32位平台准备的安装包。这样可以减少大概一半安装包的大小。

6.（暂无，待确认）把dist文件夹中的所有文件，放到release服务器的xxx目录中，用于进行auto-update

==================================
文件结构说明：

build-app/
下面放的就是从正式NeatReaderDeskApp中拿到的，react build完的文件

node_modules/
该文件夹下的node_modules里面，放的是electron需要的包，“不包括”react应用需要的包。react需要的包，在build react应用中已经被压进了js文件里。
但是！！！要注意，有些在react组件里引入的库，如果是用window.ElectronRequire形式引入的，这些库其实是Electron需要用的到。因此要安装到当前“打包”项目中。
也就是说，如果在实际开发的项目，有些库是用window.ElectronRequire引入的，那就要在这边的package.json文件中设为dependency，install一下。

res/
electron应用打包需要用的东西，比如windows安装包的icon

===========================================