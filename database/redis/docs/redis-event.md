# redis 事件
- redis 服务器是事件驱动，服务器事件分为时间事件和文件事件。
- 文件事件是对socket操作的抽象：每次socket变为accept、writable、readable时，
相应文件事件产生。
- 文件事件分为AE_READABLE和AE_WRITABLE事件。
- 时间事件分为定时事件和周期性事件，也就是timeout和internal事件。