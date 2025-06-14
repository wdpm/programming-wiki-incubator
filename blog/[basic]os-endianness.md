# 字节序
字节序定义了多字节数据（如整数、浮点数）在内存中的存储顺序。

```js
// Little-Endian：0x12345678 → 内存中存储为 78 56 34 12（低位在前）
// 内存地址	存储的字节
// 0x1000	0x78	← 最低位字节（LSB）
// 0x1001	0x56
// 0x1002	0x34
// 0x1003	0x12	← 最高位字节（MSB）
const buf1 = Buffer.from([0x78, 0x56, 0x34, 0x12]);
console.log(buf1.readUInt32LE(0).toString(16)); // 输出: 12345678

// Big-Endian：0x12345678 → 内存中存储为 12 34 56 78（高位在前）
// 内存地址	存储的字节
// 0x1000	0x12	← 最高位字节（MSB）
// 0x1001	0x34
// 0x1002	0x56
// 0x1003	0x78	← 最低位字节（LSB）
const buf2 = Buffer.from([0x12, 0x34, 0x56, 0x78]);
console.log(buf2.readUInt32BE(0).toString(16)); // 输出: 12345678

const os = require('os');
console.log(os.endianness()); // 'LE' 或 'BE'
```

- x86/x64 CPU 原生采用 Little-Endian，因其简化了算术运算（如加法从低位开始进位）。
- 网络协议（如 TCP/IP）默认使用 Big-Endian，是因为早期网络设备采用BE，RFC 1700 规定网络协议必须使用BE，以及跨平台兼容性。