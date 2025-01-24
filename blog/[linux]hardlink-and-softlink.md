# 硬链接和软链接

问题：

1. 硬链接本质是什么？删除原文件后，硬链接的内容还存在吗？
2. 软链接本质是什么？删除原文件后，软链接的内容还存在吗？
3. 硬链接和软链接中，谁会影响文件的 Links 计数器？

## 硬链接和软链接测试

```bash
#!/bin/bash

# 创建测试目录
mkdir /tmp/linktest
cd /tmp/linktest

# 创建原始文件
echo "测试文件内容" > original.txt

# 创建软链接和硬链接
ln -s original.txt softlink.txt
ln original.txt hardlink.txt

# 显示链接状态
echo "原始文件链接数:"
stat original.txt | grep Links

# 删除原始文件
rm original.txt

echo "删除原始文件后:"

# 测试软链接
echo "尝试访问软链接:"
cat softlink.txt || echo "软链接已失效"

# 测试硬链接
echo "尝试访问硬链接:"
cat hardlink.txt
```

将上面的命令保存为一个 bash 脚本，给予运行权限运行，结果如下：

```bash
原始文件链接数:
Device: 8,32    Inode: 43741       Links: 2

删除原始文件后:
尝试访问软链接:
cat: softlink.txt: No such file or directory
软链接已失效

尝试访问硬链接:
测试文件内容
```

这就回答了之前的第 1 和第 2 个问题：
> 1. 硬链接本质是什么？删除原文件后，硬链接的内容还存在吗？

hardlink 本质上是对 inode 的引用，底层指向同一个 inode。删除原文件不影响其他硬链接，硬链接的内容还存在。原文件本质上也是一个对
inode 的引用。
Inode 删除 = 硬链接计数为 0，此时，对应的这个内容才会被回收。

> 2. 软链接本质是什么？删除原文件后，软链接的内容还存在吗？

softlink 本质上是快捷方式，依赖原始文件路径，原文件删除会导致链接失效。

## Links 计数器测试

```bash
# 创建原始文件
touch original.txt

# 查看原始文件的链接数
stat original.txt | grep Links
# 输出应该是 Links: 1

# 创建软链接
ln -s original.txt softlink.txt

# 再次查看原始文件的链接数
stat original.txt | grep Links
# 输出仍然是 Links: 1

# 创建硬链接
ln original.txt hardlink.txt

# 查看原始文件的链接数
stat original.txt | grep Links
# 输出变为 Links: 2
```

输出：

```
Device: 8,32    Inode: 43744       Links: 1
Device: 8,32    Inode: 43744       Links: 1
Device: 8,32    Inode: 43744       Links: 2
```
> 3. 硬链接和软链接中，谁会影响文件的 Links 计数器？

软链接不会增加原文件的 Links 计数，硬链接会增加 Links 计数。 Links 计数反映的是硬链接的数量，不包括软链接。