# 通过本地的开发 CI 来缩短反馈循环

```
"scripts": {
    "inspect:sanity-testing": "mocha **/**--test.js --grep \"sanity\"",
    "inspect:lint": "eslint .",
    "inspect:vulnerabilities": "npm audit",
    "inspect:license": "license-checker --failOn GPLv2",
    "inspect:complexity": "plato .",

    "inspect:all": "concurrently -c \"bgBlue.bold,bgMagenta.bold,yellow\" \"npm:inspect:quick-testing\" \"npm:inspect:lint\" \"npm:inspect:vulnerabilities\" \"npm:inspect:license\""
  },
  "husky": {
    "hooks": {
      "precommit": "npm run inspect:all",
      "prepush": "npm run inspect:all"
    }
}
```
> [来源](https://github.com/goldbergyoni/javascript-testing-best-practices/blob/master/readme-zh-CN.md#-%EF%B8%8F-52-%E9%80%9A%E8%BF%87%E6%9C%AC%E5%9C%B0%E7%9A%84%E5%BC%80%E5%8F%91-ci-%E6%9D%A5%E7%BC%A9%E7%9F%AD%E5%8F%8D%E9%A6%88%E5%BE%AA%E7%8E%AF)

## 自动升级依赖
- [NCU](https://github.com/raineorshine/npm-check-updates)