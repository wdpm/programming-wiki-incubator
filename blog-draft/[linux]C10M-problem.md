## C10M problem

来源：
- 原文：https://highscalability.com/the-secret-to-10-million-concurrent-connections-the-kernel-i/
- 网络译文参考：https://www.oschina.net/translate/the-secret-to-10-million-concurrent-connections-the-kernel?comments&p=4

关键要点总结：

- 内核是问题的根源，而非解决方案。需要将数据包处理、内存管理等从内核移出，由你自己的应用程序处理。
- 绕过操作系统内核，** 自行开发驱动程序 **。
- 采用 ** 无锁的数据 ** 结构和线程模型。
- ** 优化内存访问 **，减少缓存缺失。