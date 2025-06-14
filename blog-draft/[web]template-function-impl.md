# 模版函数的简易实现

```js
function template(tmplStr, data) {
    // 替换 <%= ... %> 表达式
    const interpolate = /<%=([\s\S]+?)%>/g;

    let source = "let __p='';\n";
    source += "__p+='";

    // 处理模板字符串
    let index = 0;
    tmplStr.replace(interpolate, function(match, interpolate, offset) {
        // 添加静态文本部分
        source += tmplStr.slice(index, offset).replace(/'/g, "\\'");
        source += "'+\n";

        // 添加动态表达式部分
        source += "(obj." + interpolate.trim() + ")+\n'";

        index = offset + match.length;
        return match;
    });

    // 添加剩余的静态文本
    source += tmplStr.slice(index).replace(/'/g, "\\'") + "';\n";
    source += "return __p;";

    console.log(source)
    // 创建函数并执行
    const render = new Function('obj', source);
    return data ? render(data) : render;
}

const result = template("Hello <%= name %> (logins: <%= login.length %>)", {
    name: "John",
    login: [1, 2, 3]
});

console.log(result); // 输出: "Hello John (logins: 3)"
```