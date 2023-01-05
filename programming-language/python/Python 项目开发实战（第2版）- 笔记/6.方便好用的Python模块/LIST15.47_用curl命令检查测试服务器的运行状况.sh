$ curl "http://127.0.0.1:5000/get?foo=bar"  # 向测试服务器发送GET请求
{
  "args": {  # args为GET参数
    "foo": "bar"
  },
  "headers": {
    "Accept": "*/*",
    "Content-Length": "",
    "Content-Type": "",
    "Host": "127.0.0.1:5000",
    "User-Agent": "curl/7.35.0"
  },
  "origin": "127.0.0.1",
  "url": "http://127.0.0.1:5000/get?foo=bar"
}
$ curl -X post -d foo=bar http://127.0.0.1:5000/post  # 向测试服务器发送POST请求
{
  "args": {},
  "data": "",  # data为发送数据的正文（未经过编码的表单数据除外）
  "files": {},  # files为被上传的文件
  "form": {  # form为经过编码的表单数据
    "foo": "bar"
  },
  "headers": {
    "Accept": "*/*",
    "Content-Length": "7",
    "Content-Type": "application/x-www-form-urlencoded",
    "Host": "127.0.0.1:5000",
    "User-Agent": "curl/7.35.0"
  },
  "json": null,
  "origin": "127.0.0.1",
  "url": "http://127.0.0.1:5000/post"
}
