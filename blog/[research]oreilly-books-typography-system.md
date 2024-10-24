# Oreilly books typography system - Atlas

Oreilly 动物书享誉计算机技术丛书领域，它背后的书籍写作系统是这样一个叫 [Atlas](https://docs.atlas.oreilly.com/) 的软件。

官网有这么一段介绍：

>Atlas是O'Reilly的生产应用程序，用于创建书籍的印刷版和网络版。使用Atlas，您可以：
>- 在可视化编辑器中或使用三种标记语言之一进行写作：AsciiDoc、HTMLBook或DocBook XML
>- 从一组源文件构建EPUB、PDF和HTML
>- 利用Git基础设施，实现版本控制和协作

先看第一点，AsciiDoc和目前流行的Markdown相比，只能说是互有优劣。
[HTMLBook](https://github.com/oreillymedia/HTMLBook) 是一个基于XHTML5的标准，
用于排印数字出版领域。HTMLBook本质上和HTML5标准没特别大的区别，都是一种XML标记语言，用CSS对内容进行美化。从长远来看，选择跟随HTML标准规范来走更好。
HTMLBook毕竟属于一种私有定义的规范。何况，还有Epub这样一种被广泛接受和采纳的数字书籍排版方案，选择HTMLBook的机会就更少了。
DocBook XML是一种基于XMl，专门用于技术书籍的语义标记的文件格式。下面是一个DocBook的示例：
```xml
 <?xml version="1.0" encoding="UTF-8"?>
<book xml:id="simple_book" xmlns="http://docbook.org/ns/docbook" version="5.0">
    <title>Very simple book</title>
    <chapter xml:id="chapter_1">
        <title>Chapter 1</title>
        <para>Hello world!</para>
        <para>I hope that your day is proceeding <emphasis>splendidly</emphasis>!
        </para>
    </chapter>
    <chapter xml:id="chapter_2">
        <title>Chapter 2</title>
        <para>Hello again, world!</para>
    </chapter>
</book>
```
它通过语义化的XML标签，定义了一本书应该是什么样子，例如 `<chapter>`标签定义了章节。这和Epub 中定义章节的设计思路是不一样的。

DocBook的专业性很强，是一个不错选择。

接着看第二点，"从一组源文件构建EPUB、PDF和HTML"只是描述了输出格式，没有特别的地方。

第三点是Git版本控制，和Atlas没有很大的关系，只能说是源码版本控制采取分布式版本控制来管理写作流程。

最后，Atlas不开源，只是Oreilly公司自身在排版书籍领域所使用的内部私有产品。