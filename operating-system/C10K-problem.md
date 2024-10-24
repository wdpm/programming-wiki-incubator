# The C10K problem

> http://www.kegel.com/c10k.html

## I/O frameworks

介绍了很多 C++ I/O 框架，底层技术为 select, poll, epoll, kqueue, sigio 等。

## I/O strategies

从单个线程发出多个 I/O 调用的方式和方法:

- 不要；在整个过程中使用阻塞 / 同步调用，并可能使用多个线程或进程来实现并发。
- 使用非阻塞调用（例如，在设置为 O_NONBLOCK 的套接字上使用 write()）来启动 I/O，并使用就绪通知（例如，poll() 或
  /dev/poll）来知道何时可以在该通道上启动下一个 I/O。通常仅适用于网络 I/O，而不适用于磁盘 I/O。
- 使用异步调用（例如，aio_write()）来启动 I/O，并使用完成通知（例如，信号或完成端口）来知道 I/O 何时完成。适用于网络和磁盘 I/O。

---
如何控制为每个客户端提供服务的代码：

- 为每个客户端一个进程（经典的 Unix 方法，自 1980 年左右开始使用）。
- 一个操作系统级线程处理多个客户端；每个客户端由以下方式控制：
    - 用户级线程（例如 GNU 状态线程，经典 Java 的绿色线程）；
    - 状态机（有点深奥，但在某些圈子里很流行；我最喜欢的）；
    - 延续 continuation （有点深奥，但在某些圈子里很流行）。
- 为每个客户端一个操作系统级线程（例如经典 Java 的本地线程）；
- 为每个活动客户端一个操作系统级线程（例如带有 Apache 前端的 Tomcat；NT 完成端口；线程池）。

---
是否使用标准操作系统服务，或者将一些代码放入内核中（例如，在自定义驱动程序、内核模块或 VxD 中）

The following five combinations seem to be popular:

- Serve many clients with each thread, and use nonblocking I/O and level-triggered readiness notification
- Serve many clients with each thread, and use nonblocking I/O and readiness change notification
- Serve many clients with each server thread, and use asynchronous I/O
- serve one client with each server thread, and use blocking I/O
- Build the server code into the kernel

### Serve many clients with each thread, and use nonblocking I/O and level-triggered readiness notification

在所有网络句柄上设置非阻塞模式，并使用 select()或 poll()
来判断哪个网络句柄有数据等待。这是传统的首选方案。在这种方案中，内核会告诉你文件描述符是否准备好，无论你自上次内核告诉你关于该文件描述符的情况以来是否对其进行了任何操作。（'level
triggered' 这个名称来源于计算机硬件设计；它与 'edge triggered' 相对。Jonathon Lemon
在他的 [BSDCON 2000 paper on kqueue()](http://people.freebsd.org/~jlemon/papers/kqueue.pdf) 中介绍了这些术语。)

> 注意：请特别记住，内核的就绪通知仅仅是一个提示；当您尝试从文件描述符读取时，它可能不再处于就绪状态。
> 这就是为什么在使用就绪通知时，使用非阻塞模式非常重要。

这种方法中的一个重要瓶颈是，如果页面此时不在内存中，read()或 sendfile()
从磁盘读取会被阻塞；在磁盘文件句柄上设置非阻塞模式没有效果。内存映射磁盘文件也是如此。服务器第一次需要磁盘 I/O
时，其进程会被阻塞，所有客户端必须等待，这种原始的非线程性能就会浪费。

这就是异步 I/O 的目的，但在缺乏 AIO 的系统上，执行磁盘 I/O 的工作线程或进程也可以绕过这个瓶颈。
一种方法是使用内存映射文件，如果 mincore() 指示需要 I/O，则请求一个工作线程进行 I/O，并继续处理网络流量。Jef Poskanzer
提到，Pai、Druschel 和 Zwaenepoel 的 1999 年 Flash 网络服务器使用了这个技巧；他们在 Usenix '99 上进行了相关演讲。看起来
mincore() 在 BSD 派生的 Unix 系统中可用，如 FreeBSD 和 Solaris，但不属于单一 Unix 规范。它作为 Linux 的一部分自内核 2.3.51
起可用，感谢 Chuck Lever。

他们发现的一个瓶颈是 mincore（我想这并不是一个好主意）。另一个瓶颈是 sendfile 在磁盘访问时会阻塞；他们通过引入一个修改过的
sendfile() 来提高性能，当它获取的磁盘页面尚未在内存中时，返回类似 EWOULDBLOCK 的结果。
(不确定如何告诉用户页面现在已驻留... 在我看来，这里真正需要的是 aio_sendfile()。) 他们优化的最终结果是在 1GHZ/1GB 的
FreeBSD 机器上获得了大约 800 的 SpecWeb99 分数，这比 spec.org 上的任何文件都要好。

有几种方法可以让单个线程判断一组非阻塞套接字中哪些准备好进行输入 / 输出操作：

- 传统的 select()

不幸的是，select() 的句柄数量受到 FD_SETSIZE 的限制。这个限制是编译到标准库和用户程序中的。（某些版本的 C
库允许您在用户应用程序编译时提高此限制。）请参见 Poller_select (cc, h) 以获取如何将 select() 与其他就绪通知方案互换使用的示例。

- The traditional poll()

poll() 可以处理的文件描述符数量没有硬编码限制，但在几千个文件描述符时会变得缓慢，
因为大多数文件描述符在任何时刻都是空闲的，扫描数千个文件描述符需要时间。

一些操作系统（例如 Solaris 8）通过使用像轮询提示这样的技术来加速 poll() 等函数，这项技术是由 Niels Provos 于 1999 年为
Linux 实现并进行基准测试的。

请参见 Poller_poll (cc, h, benchmarks) 以获取如何将 poll() 与其他就绪通知方案互换使用的示例。

- /dev/poll

这是 Solaris 推荐的 poll 替代方案。/dev/poll 的理念是利用 poll() 通常会多次使用相同参数的事实。使用 /dev/poll，您可以获得一个打开的
/dev/poll 句柄，并通过写入该句柄仅一次告知操作系统您感兴趣的文件；从那时起，您只需从该句柄读取当前就绪的文件描述符集合。

它在 Solaris 7 中悄然出现（参见 patchid 106541），但它的首次公开亮相是在 Solaris 8；根据 Sun 的说法，在 750 个客户端时，它的开销仅为
poll() 的 10%。

在 Linux 上尝试了各种 /dev/poll 的实现，但没有一个能像 epoll 那样表现良好，并且从未真正完成。在 Linux 上不推荐使用
/dev/poll。

请参见 Poller_devpoll（cc，h 基准测试）以获取如何与许多其他就绪通知方案交替使用 /dev/poll 的示例。（注意 - 该示例适用于 Linux
/dev/poll，可能在 Solaris 上无法正常工作。）

- kqueue()
  kqueue() 是 FreeBSD（以及即将到来的 NetBSD）的推荐轮询替代方案。

请见下文。kqueue() 可以指定边缘触发或水平触发。

### 2. Serve many clients with each thread, and use nonblocking I/O and readiness change notification

就绪状态更改通知（或边沿触发的就绪通知） 意味着你给内核一个文件描述符，然后，当该描述符从 还没准备好变为准备好，内核会以某种方式通知你。然后它假设你
知道文件描述符已准备好，并且不会再发送任何该文件描述符的该类型的通知，直到您执行某些操作导致文件描述符不再准备好（例如，直到您收到
发送、接收或接受调用或者发送或接收传输时发生 EWOULDBLOCK 错误 小于请求的字节数）

当您使用就绪更改通知时，您必须为虚假事件做好准备，因为一种常见的实现是每当收到任何数据包时就发出就绪信号，无论文件描述符是否已就绪。

这与“级别触发”就绪通知相反。它对编程错误的宽容度有点低，因为如果您只错过一个事件，该事件所针对的连接就会永远卡住。尽管如此，我发现边缘触发的就绪通知使使用
OpenSSL 进行非阻塞客户端编程变得更加容易，因此值得尝试。

[Banga, Mogul, Drusha '99] 在 1999 年描述了这种方案。

有几个 API 可以让应用程序检索“文件描述符已就绪”通知：

- kqueue() 这是 FreeBSD（以及很快的 NetBSD）推荐的边缘触发轮询替代品。

FreeBSD 4.3 及更高版本以及截至 2002 年 10 月的 NetBSD 当前版本，支持 poll()的通用替代方案，称为 kqueue()/kevent()
；它支持边沿触发和水平触发。（另请参阅 Jonathan Lemon 的页面和他关于 kqueue() 的 BSDCon 2000 论文。）

与 /dev/poll 类似，您分配一个侦听对象，但不是打开文件 /dev/poll，而是调用 kqueue() 来分配一个侦听对象。要更改您正在侦听的事件，或者获取当前事件的列表，您可以在
kqueue()返回的描述符上调用 kevent()。它不仅可以侦听套接字准备情况，还可以侦听普通文件准备情况、信号，甚至 I/O 完成情况。

注意：截至 2000 年 10 月，FreeBSD 上的线程库无法与 kqueue()很好地交互；显然，当 kqueue() 阻塞时，整个进程都会阻塞，而不仅仅是调用线程。

- epoll 这是 2.6 Linux 内核推荐的边沿触发 poll 替代方案。

2001 年 7 月 11 日，Davide Libenzi 提出了实时信号的替代方案；他的补丁提供了他现在所说的
/dev/epoll www.xmailserver.org/linux-patches/nio-improve.html 。这就像实时信号就绪通知一样，但它合并了冗余事件，并且具有更有效的批量事件检索方案。

在其接口从 /dev 中的特殊文件更改为系统调用 sys_epoll 后，epoll 从 2.5.46 开始被合并到 2.5 内核树中。旧版本 epoll 的补丁可用于
2.4 内核。

2002 年万圣节前后，关于统一 epoll、aio 和其他事件源在 Linux 内核邮件列表上进行了长时间的争论。这可能会发生，但 Davide
首先集中精力在总体上巩固 epoll。

Polyakov 的 kevent (Linux 2.6+) 新闻快讯：2006 年 2 月 9 日和 2006 年 7 月 9 日，Evgeniy Polyakov 发布了似乎统一了 epoll 和
aio 的补丁；他的目标是支持网络 AIO。参阅：

- the LWN article about kevent
- his July announcement
- his kevent page
- his naio page
- some recent discussion

---

- Drepper 的新网络接口（针对 Linux 2.6+ 的建议）

在 OLS 2006 上，Ulrich Drepper 提出了一种新的高速异步网络 API。 看：

- his paper, ["The Need for Asynchronous, Zero-Copy Network I/O"](http://people.redhat.com/drepper/newni.pdf)
- his slides
- LWN article from July 22

- Realtime Signals 实时信号

这是 2.4 Linux 内核推荐的边沿触发 poll 替代方案。

```c
/* Mask off SIGIO and the signal you want to use. */
sigemptyset(&sigset);
sigaddset(&sigset, signum);
sigaddset(&sigset, SIGIO);
sigprocmask(SIG_BLOCK, &m_sigset, NULL);
/* For each file descriptor, invoke F_SETOWN, F_SETSIG, and set O_ASYNC. */
fcntl(fd, F_SETOWN, (int) getpid());
fcntl(fd, F_SETSIG, signum);
flags = fcntl(fd, F_GETFL);
flags |= O_NONBLOCK|O_ASYNC;
fcntl(fd, F_SETFL, flags);
```

当 read()或 write() 等普通 I/O 函数完成时，就会发送该信号。 要使用它，请编写一个普通的 poll()外部循环，并在你处理了 poll()
发现的所有 fd 之后，你循环调用 sigwaitinfo() 。

如果 sigwaitinfo 或 sigtimedwait 返回实时信号，则 siginfo.si_fd 和 siginfo.si_band 提供在调用 poll() 后与 pollfd.fd 和
pollfd.revents 几乎相同的信息 ，于是你处理 I/O，然后继续调用 sigwaitinfo()。

[Provos、Lever 和 Tweedie 2000] 描述了 phhttpd 的最新基准测试，它使用 sigtimedwait()的变体 sigtimedwait4()
，它可以让您通过一次调用检索多个信号。有趣的是，sigtimedwait4() 对他们来说的主要好处似乎是它允许应用程序测量系统过载（因此它可以适当地运行）。
（请注意，poll() 提供了相同的系统过载测量方法。）

---

- Signal-per-fd

Chandra 和 Mosberger 提出了一种对实时信号方法的修改，通过合并冗余事件，使用称为 “signal-per-fd”的方法减少或消除实时信号队列溢出
。但它的性能并不优于
epoll。 他们的论文（ www.hpl.hp.com/techreports/2000/HPL-2000-174.html ）将该方案的性能与 select() 和 /dev/poll 进行了比较。

Vitaly Luban 于 2001 年 5 月 18 日宣布了实施该方案的补丁；他的补丁位于 www.luban.org/GPL/gpl.html 。 （注：截至 2001 年 9
月，该补丁在重负载下仍可能存在稳定性问题。dkftpbench 在大约 4500 个用户时可能会触发 oops。）

### 3. Serve many clients with each server thread, and use asynchronous I/O

这在 Unix 中尚未流行，可能是因为很少有操作系统支持异步 I/O，也可能是因为它（如非阻塞 I/O）需要重新考虑您的应用程序。在标准
Unix 下，异步 I/O 由 aio_ 接口提供（从该链接向下滚动到“异步输入和输出”），它将信号和值与每个 I/O
操作相关联。信号及其值被排队并有效地传递到用户进程。这来自 POSIX 1003.1b 实时扩展，也包含在 Single Unix 规范版本 2 中。

AIO 通常与边沿触发的完成通知一起使用，即操作完成时信号会排队。（它也可以通过调用 aio_suspend()
与水平触发的完成通知一起使用，尽管我怀疑很少有人这样做。）

glibc 2.1 及更高版本提供了为标准合规性而不是性能而编写的通用实现。

自 2.5.32 起，Ben LaHaise 的 Linux AIO 实现已合并到主 Linux 内核中。它不使用内核线程，并且具有非常高效的底层 API，但（截至
2.6.0-test2）尚不支持套接字。 （还有一个针对 2.4 内核的 AIO 补丁，但 2.5/2.6 的实现有些不同。）更多信息：

- The page "[Kernel Asynchronous I/O (AIO) Support for Linux](http://lse.sourceforge.net/io/aio.html)" which tries to tie together all info about the 2.6 kernel's implementation of AIO (posted 16 Sept 2003)
- [Round 3: aio vs /dev/epoll](http://www.linuxsymposium.org/2002/view_txt.php?text=abstract&talk=11) by Benjamin C.R. LaHaise (presented at 2002 OLS)
- [Asynchronous I/O Suport in Linux 2.5](http://archive.linuxsymposium.org/ols2003/Proceedings/All-Reprints/Reprint-Pulavarty-OLS2003.pdf), by Bhattacharya, Pratt, Pulaverty, and Morgan, IBM; presented at OLS '2003
- [Design Notes on Asynchronous I/O (aio) for Linux](http://sourceforge.net/docman/display_doc.php?docid=12548&group_id=8875) by Suparna Bhattacharya -- compares Ben's AIO with SGI's KAIO and a few other AIO projects
- [Linux AIO home page](http://www.kvack.org/~blah/aio/) - Ben's preliminary patches, mailing list, etc.
- [linux-aio mailing list archives](http://marc.theaimsgroup.com/?l=linux-aio)
- [libaio-oracle](http://www.ocfs.org/aio/) - library implementing standard Posix AIO on top of libaio. [First mentioned by Joel Becker on 18 Apr 2003](http://marc.theaimsgroup.com/?l=linux-aio&m=105069158425822&w=2).

[Red Hat AS](http://www.ussg.iu.edu/hypermail/linux/kernel/0209.0/0832.html)和 Suse SLES 都在 2.4 内核上提供了高性能实现；它与 2.6 内核实现相关，但不完全相同。

2006年2月，进行新的尝试，提供网络AIO；请[参阅上面关于 Evgeniy Polyakov 的基于 kevent 的 AIO 的注释](http://www.kegel.com/c10k.html#kevent)。

1999年， **[SGI为Linux实现了高速AIO](http://oss.sgi.com/projects/kaio/)** 。从版本 1.1 开始，据说它可以很好地与磁盘 I/O 和套接字配合使用。看来是用了内核线程。对于那些迫不及待想要 Ben's AIO 支持套接字的人来说，它仍然很有用。

O'Reilly 的书[《POSIX.4：Programming for the Real World》](http://www.oreilly.com/catalog/posix4/)据说包含对 aio 的很好的介绍。

[Sunsite](http://sunsite.nstu.nsk.su/sunworldonline/swol-03-1996/swol-03-aio.html)上有关于 Solaris 上早期非标准 aio 实现的教程。它可能值得一看，但请记住，您需要在心里将“aioread”转换为“aio_read”等。

请注意，AIO 不提供在不阻塞磁盘 I/O 的情况下打开文件的方法；如果您关心打开磁盘文件引起的睡眠， [Linus 建议](http://www.ussg.iu.edu/hypermail/linux/kernel/0102.1/0124.html)您只需在不同的线程中执行 open()，而不是希望使用 aio_open() 系统调用。

在 Windows 下，异步 I/O 与术语“重叠 I/O”和 IOCP 或“I/O 完成端口”相关联。 Microsoft 的 IOCP 将现有技术（如异步 I/O（如 aio_write）和排队完成通知（如将 aio_sigevent 字段与 aio_write 一起使用时））组合，提出了一个新的想法,即保留一些请求,以尝试保持与单个 IOCP 关联的运行线程数量不变。有关详细信息，请参阅 sysinternals.com 上 Mark Russinovich 的[Inside I/O Completion Ports](http://www.sysinternals.com/ntw2k/info/comport.shtml) 、Jeffrey Richter 的书“Programming Server-Side Applications for Microsoft Windows 2000”（ [Amazon](http://www.amazon.com/exec/obidos/ASIN/0735607532) 、 [MSPress](http://www.microsoft.com/mspress/books/toc/3402.asp) ）、[美国专利 #06223207](http://patft.uspto.gov/netacgi/nph-Parser?Sect1=PTO1&Sect2=HITOFF&d=PALL&p=1&u=/netahtml/srchnum.htm&r=1&f=G&l=50&s1='6223207'.WKU.&OS=PN/6223207&RS=PN/6223207)或[MSDN](http://msdn.microsoft.com/library/default.asp?url=/library/en-us/fileio/filesio_4z1v.asp) 。



### 4. Serve one client with each server thread

...并让 read() 和 write() 阻塞。缺点是为每个客户端使用整个堆栈帧，这会消耗内存。许多操作系统在处理超过数百个线程时也遇到困难。如果每个线程获得 2MB 堆栈（并非罕见的默认值），则在具有 1GB 用户可访问 VM 的 32 位计算机上，您会在 (2^30 / 2^21) = 512 个线程上耗尽*虚拟内存*（例如，例如，Linux 通常在 x86 上运行）

> 公式分析
> 1. 2^30：这是一个表示 1GB 的数字，因为 2^{30} 字节等于 1024 乘以 1024 乘以 1024 字节，即 1,073,741,824 字节。
> 2. 2^21：这是一个表示 2MB 的数字，因为 2^{21} 字节等于 2,048 乘以 1,024 字节，即 2,097,152 字节。

您可以通过为每个线程提供较小的堆栈来解决此问题，但由于大多数线程库不允许在创建后增加线程堆栈，因此这样做意味着设计您的程序以最小化堆栈使用。您还可以通过迁移到 64 位处理器来解决此问题。

Linux、FreeBSD 和 Solaris 中的线程支持正在不断改进，即使对于主流用户来说，64 位处理器也指日可待。也许在不久的将来，那些喜欢为每个客户端使用一个线程的人甚至可以为 10000 个客户端使用该范例。然而，目前，如果您确实想支持那么多客户，那么使用其他范例可能会更好。

对于毫不掩饰支持线程的观点，请参阅由 von Behren、Condit 和 Brewer、UCB 在 HotOS IX 上发表的[《Why Events Are A Bad Idea (for High-concurrency Servers)》](http://www.usenix.org/events/hotos03/tech/vonbehren.html) 。反线程阵营中的任何人愿意指出一篇反驳这篇文章的论文吗？ :-)

#### LinuxThreads Linux线程

[LinuxTheads](http://pauillac.inria.fr/~xleroy/linuxthreads/)是 标准 Linux 线程库的名称。它被集成到 glibc 中 glibc2.0，并且大部分符合 Posix 标准，但性能较差 和信号支持。

#### NGPT：Linux 的下一代 Posix 线程

[NGPT](http://www-124.ibm.com/pthreads/)是一个项目 由 IBM 发起，旨在为 Linux 带来良好的 Posix 兼容线程支持。它是 现在是稳定版本 2.2，并且运行良好...但是 NGPT 团队已经 [宣布](http://www-124.ibm.com/pthreads/docs/announcement) 他们正在将 NGPT 代码库置于仅支持模式 因为他们认为这是“支持社区的最佳方式 从长远来看”。NGPT团队将继续努力改进 Linux 线程支持，但现在专注于改进 NPTL。 （感谢 NGPT 团队的出色工作和他们优雅的方式 承认《NPTL》。）

#### NPTL：Linux 的本机 Posix 线程库

[NPTL](http://people.redhat.com/drepper/nptl/)是一个项目 [乌尔里希·德雷珀](http://people.redhat.com/drepper/) （仁慈的字典^H^H^H^H维护者 [glibc](http://www.gnu.org/software/libc/) ）和 [英戈·莫尔纳](http://people.redhat.com/mingo/) 为 Linux 带来世界一流的 Posix 线程支持。

截至 2003 年 10 月 5 日，NPTL 现已作为附加目录合并到 glibc cvs 树中（就像 linuxthreads 一样），因此几乎可以肯定它会与下一个版本的 glibc 一起发布。

第一个包含 NPTL 早期快照的主要发行版是 Red Hat 9。（这对某些用户来说有点不方便，但必须有人打破僵局......）

NPTL links: NPTL 链接：

- [Mailing list for NPTL discussion](https://listman.redhat.com/mailman/listinfo/phil-list)
- [NPTL source code ](http://people.redhat.com/drepper/nptl/)
- [Initial announcement for NPTL](http://lwn.net/Articles/10465/)
- [Original whitepaper describing the goals for NPTL](http://people.redhat.com/drepper/glibcthreads.html)
- [Revised whitepaper describing the final design of NPTL](http://people.redhat.com/drepper/nptl-design.pdf)
- [Ingo Molnar's](http://marc.theaimsgroup.com/?l=linux-kernel&m=103230439008204&w=2) first benchmark showing it could handle 10^6 threads

- [Ulrich's benchmark](http://marc.theaimsgroup.com/?l=linux-kernel&m=103269598000900&w=2) comparing performance of LinuxThreads, NPTL, and IBM's [NGPT](http://www.kegel.com/c10k.html#threads.ngpt). It seems to show NPTL is much faster than NGPT.

[2002 年 3 月，NGPT 团队的 Bill Abt、glibc 维护者 Ulrich Drepper 和其他人聚在一起](http://people.redhat.com/drepper/glibcthreads.html)讨论如何处理 LinuxThreads。会议提出的一个想法是提高互斥性能； Rusty Russell[等人](http://marc.theaimsgroup.com/?l=linux-kernel&m=103284847815916&w=2)随后实现了[快速用户空间互斥体（futexes](http://marc.theaimsgroup.com/?l=linux-kernel&m=102196625921110&w=2) ），现在 NGPT 和 NPTL 都在使用。大多数与会者认为 NGPT 应该合并到 glibc 中。

不过，Ulrich Drepper 不喜欢 NGPT，并认为他可以做得更好。 （对于那些曾经尝试为 glibc 提供补丁的人来说，这可能不会让人感到意外:-) 在接下来的几个月里，Ulrich Drepper、Ingo Molnar 和其他人贡献了 glibc 和内核更改，这些更改构成了称为本机 Posix 线程库 (NPTL)。 NPTL 使用为 NGPT 设计的所有内核增强功能，并利用了一些新功能。 Ingo Molnar 对内核增强功能[的描述](https://listman.redhat.com/pipermail/phil-list/2002-September/000013.html)如下：

> *While NPTL uses the three kernel features introduced by NGPT: getpid() returns PID, CLONE_THREAD and futexes; NPTL also uses (and relies on) a much wider set of new kernel features, developed as part of this project.
> 而 NPTL 使用了 NGPT 引入的三个内核功能： getpid() 返回 PID、CLONE_THREAD 和 futexes； NPTL 还使用（并依赖） 作为该项目的一部分开发的更广泛的新内核功能集。*
>
> 
>
> *Some of the items NGPT introduced into the kernel around 2.5.8 got modified, cleaned up and extended, such as thread group handling (CLONE_THREAD). [the CLONE_THREAD changes which impacted NGPT's compatibility got synced with the NGPT folks, to make sure NGPT does not break in any unacceptable way.]
> NGPT 在 2.5.8 左右引入内核的一些项目得到了修改、清理和扩展，例如线程组处理 (CLONE_THREAD)。 [影响 NGPT 兼容性的 CLONE_THREAD 更改已与 NGPT 人员同步，以确保 NGPT 不会以任何不可接受的方式中断。]*
>
> 
>
> *The kernel features developed for and used by NPTL are described in the design whitepaper, http://people.redhat.com/drepper/nptl-design.pdf ...
> 设计白皮书中描述了为 NPTL 开发和使用的内核功能，http://people.redhat.com/drepper/nptl-design.pdf *
>
> 
>
> *...A short list: TLS support, various clone extensions (CLONE_SETTLS, CLONE_SETTID, CLONE_CLEARTID), POSIX thread-signal handling, sys_exit() extension (release TID futex upon VM-release), the sys_exit_group() system-call, sys_execve() enhancements and support for detached threads.
> 简短列表：TLS 支持、各种克隆扩展（CLONE_SETTLS、CLONE_SETTID、CLONE_CLEARTID）、POSIX 线程信号处理、sys_exit() 扩展（在 VM 释放时释放 TID futex）、sys_exit_group() 系统调用、sys_execve() 增强以及对分离线程的支持。*
>
> 
>
> *There was also work put into extending the PID space - eg. procfs crashed due to 64K PID assumptions, max_pid, and pid allocation scalability work. Plus a number of performance-only improvements were done as well.
> 还进行了扩展 PID 空间的工作 - 例如。 procfs 由于 64K PID 假设、max_pid 和 pid 分配可扩展性工作而崩溃。此外，还进行了一些仅针对性能的改进。*
>
> 
>
> *In essence the new features are a no-compromises approach to 1:1 threading - the kernel now helps in everything where it can improve threading, and we precisely do the minimally necessary set of context switches and kernel calls for every basic threading primitive.
> 从本质上讲，新功能是一种不折不扣的 1:1 线程方法 - 内核现在可以在可以改进线程的所有方面提供帮助，并且我们精确地为每个基本线程原语执行最低限度必要的上下文切换和内核调用集。*

两者之间的一大区别是NPTL是1:1的线程模型， 而 NGPT 是 M:N 线程模型（见下文）。尽管如此， [乌尔里希的初始基准](https://listman.redhat.com/pipermail/phil-list/2002-September/000009.html) 似乎表明NPTL确实比NGPT快得多。 （NGPT 团队 期待看到 Ulrich 的基准代码来验证结果。）

#### FreeBSD 线程支持

FreeBSD 支持 LinuxThreads 和用户空间线程库。 此外，FreeBSD 5.0 中还引入了称为 KSE 的 M:N 实现。 有关概述，请参阅[www.unobvious.com/bsd/freebsd-threads.html](http://www.unobvious.com/bsd/freebsd-threads.html) 。

而在 2006 年 7 月， [Robert Watson 提议 1:1 线程实现成为 FreeBsd 7.x 中的默认实现](http://marc.theaimsgroup.com/?l=freebsd-threads&m=115191979412894&w=2)：

> 我知道过去已经讨论过这个问题，但我认为随着 7.x 的不断向前发展，是时候再次考虑这个问题了。在许多常见应用程序和场景的基准测试中，libthr 表现出明显优于 libpthread 的性能...libthr 也在我们的更多平台上实现，并且已经在多个平台上实现了 libpthread。我们向 MySQL 和其他重线程用户提出的第一个建议是“切换到 libthr”，这也很有启发性！ ...所以稻草人的建议是：将 libthr 作为 7.x 上的默认线程库。*

#### NetBSD 线程支持

> *基于 Scheduler Activations 模型的内核支持的 M:N 线程库已于 2003 年 1 月 18 日合并到 NetBSD-current 中。*

#### Solaris 线程支持

Solaris 中的线程支持正在不断发展...从 Solaris 2 到 Solaris 8（默认值） 线程库使用 M:N 模型，但 Solaris 9 默认为 1:1 模型线程支持。 请参阅[Sun 的多线程编程指南](http://docs.sun.com/db/doc/805-5080)以及[Sun 关于 Java 和 Solaris 线程的说明](http://java.sun.com/docs/hotspot/threads/threads.html)。

#### JDK 1.3.x 及更早版本中的 Java 线程支持

众所周知，Java在JDK1.3.x之前不支持任何其他方法处理网络连接，只能是每个客户端一个线程。 [Volanomark](http://www.volano.com/report/)是一个很好的微基准测试 它测量各种情况下每秒消息的吞吐量 同时连接的数量。截至 2003 年 5 月，JDK 1.3 不同供应商的实现实际上能够处理 一万个同时连接——尽管有显着的 性能下降。见表[4](http://www.volano.com/report/#nettable) 了解哪些 JVM 可以处理 10000 个连接，以及如何处理 随着连接数量的增加，性能会受到影响。

#### Note: 1:1 threading vs. M:N threading

实现线程库时有一个选择：您可以 将所有线程支持放入内核中（这称为 1:1 线程 模型），或者您可以将其中一部分移动到用户空间（这称为 M:N 线程模型）。曾经一度认为 M:N 具有更高的性能， 但它太复杂了，很难做到正确，而且大多数人都在远离它。

- [Why Ingo Molnar prefers 1:1 over M:N](http://marc.theaimsgroup.com/?l=linux-kernel&m=103284879216107&w=2)
- [Sun is moving to 1:1 threads](http://java.sun.com/docs/hotspot/threads/threads.html)
- [NGPT](http://www-124.ibm.com/pthreads/) is an M:N threading library for Linux.
- Although [Ulrich Drepper planned to use M:N threads in the new glibc threading library](http://people.redhat.com/drepper/glibcthreads.html), he has since [switched to the 1:1 threading model.](http://people.redhat.com/drepper/nptl-design.pdf)
- [MacOSX appears to use 1:1 threading.](http://developer.apple.com/technotes/tn/tn2028.html#MacOSXThreading)
- [FreeBSD](http://people.freebsd.org/~julian/) and [NetBSD](http://web.mit.edu/nathanw/www/usenix/freenix-sa/) appear to still believe in M:N threading... The lone holdouts? Looks like freebsd 7.0 might switch to 1:1 threading (see above), so perhaps M:N threading's believers have finally been proven wrong everywhere.

### 5. Build the server code into the kernel

据说 Novell 和 Microsoft 都曾多次这样做过， 至少有一个 NFS 实现可以做到这一点， [khttpd](http://www.fenrus.demon.nl/)为 Linux 执行此操作 和静态网页，以及 [“TUX”（线程 linUX Web 服务器）](http://slashdot.org/comments.pl?sid=00/07/05/0211257&cid=218)是 Ingo Molnar 为 Linux 开发的一款极其快速且灵活的内核空间 HTTP 服务器。 Ingo [2000 年 9 月 1 日的公告](http://marc.theaimsgroup.com/?l=linux-kernel&m=98098648011183&w=2) 说可以从以下位置下载 TUX 的 alpha 版本 [ftp://ftp.redhat.com/pub/redhat/tux](ftp://ftp.redhat.com/pub/redhat/tux) ， 并解释如何加入邮件列表以获取更多信息。

linux-kernel list 一直在讨论这个的优缺点 方法，共识似乎是而不是移动网络服务器 进入内核，内核应该添加尽可能小的钩子 提高网络服务器的性能。这样，其他类型的服务器 可以受益。参见例如 [扎克·布朗的言论](http://www.linuxhq.com/lnxlists/linux-kernel/lk_9906_03/msg01041.html) 关于用户态与内核 http 服务器。 看来 2.4 linux 内核为用户程序提供了足够的能力，如下所示 [X15](http://www.kegel.com/c10k.html#x15)服务器的运行速度与 Tux 一样快，但不使用任何 内核修改。



### 6. Bring the TCP stack into userspace

例如参见 [netmap](http://info.iet.unipi.it/~luigi/netmap/)数据包 I/O 框架和[Sandstorm](http://conferences.sigcomm.org/hotnets/2013/papers/hotnets-final43.pdf) 基于它的概念验证 Web 服务器。



## Limits on open filehandles

- Any Unix: the limits set by ulimit or setrlimit.

- Solaris: see [the Solaris FAQ, question 3.46](http://www.wins.uva.nl/pub/solaris/solaris2/Q3.46.html) (or thereabouts; they renumber the questions periodically).

- FreeBSD:

  Edit /boot/loader.conf, add the line

  ```
  set kern.maxfiles=XXXX
  ```

- Linux: See [Bodo Bauer's /proc documentation](http://asc.di.fct.unl.pt/~jml/mirror/Proc/). On 2.4 kernels:

```
echo 32768 > /proc/sys/fs/file-max
```

increases the system limit on open files, and

```
ulimit -n 32768
```

increases the current process' limit.



## Limits on threads 线程限制

在任何体系结构上，您可能需要减少为每个线程分配的堆栈空间量，以避免耗尽虚拟内存。如果您使用 pthread，则可以在运行时使用 pthread_attr_init() 设置它。

- 具有 NPTL 的 Linux 2.6 内核：/proc/sys/vm/max_map_count 可能需要增加到 32000 个左右的线程以上。 （不过，除非您使用的是 64 位处理器，否则您需要使用非常小的堆栈线程来获得接近该数量的线程。）请参阅 NPTL 邮件列表，例如主题为“[无法创建超过 32K”](https://listman.redhat.com/archives/phil-list/2003-August/msg00005.html)的线程[线程？](https://listman.redhat.com/archives/phil-list/2003-August/msg00005.html) ”，了解更多信息。

- Linux 2.4: /proc/sys/kernel/threads-max 是最大线程数；在我的 Red Hat 8 系统上它默认为 2047。您可以像往常一样通过将新值回显到该文件中来设置增加此值，例如“echo 4000 > /proc/sys/kernel/threads-max”

- Java：请参阅[Volano 的详细基准信息](http://www.volano.com/benchmarks.html)，以及有关[如何调整各种系统以处理大量线程的信息](http://www.volano.com/server.html)。



## Java issues

2001 年 5 月， [JDK 1.4](http://java.sun.com/j2se/1.4/)引入了[java.nio](http://java.sun.com/j2se/1.4/docs/guide/nio/)包，为非阻塞 I/O（以及其他一些好处）提供全面支持。请参阅[发行说明](http://java.sun.com/j2se/1.4/relnotes.html#nio)以了解一些注意事项。

2000 年，Matt Welsh 为 Java 实现了非阻塞套接字；他的性能基准测试表明，与处理许多（最多 10000 个）连接的服务器中的阻塞套接字相比，它们具有优势。他的类库叫做[java-nbio](http://www.cs.berkeley.edu/~mdw/proj/java-nbio/) ；这是[沙尘暴](http://www.cs.berkeley.edu/~mdw/proj/sandstorm/)项目的一部分。提供了显示[10000 个连接性能的](http://www.cs.berkeley.edu/~mdw/proj/sandstorm/iocore-bench/)基准。

- Matt Welsh 的[Jaguar 系统](http://www.cs.berkeley.edu/~mdw/proj/jaguar/)提出了预序列化对象、新的 Java 字节码和内存管理更改，以允许在 Java 中使用异步 I/O。
- [将 Java 与虚拟接口架构连接](http://www.cs.cornell.edu/Info/People/chichao/papers.htm)，作者：CC。 Chang 和 T. von Eicken 提出了内存管理更改，以允许在 Java 中使用异步 I/O。

- [JSR-51](http://java.sun.com/aboutJava/communityprocess/jsr/jsr_051_ioapis.html)是 Sun 项目，它提供了 java.nio 包。 Matt Welsh 参与了（谁说Sun 不听？）。



## Other tips 

- Zero-Copy 零拷贝

  通常，数据在从这里到那里的过程中会被复制很多次。 任何将这些副本消除到最低物理量的方案都称为“零副本”

  - [Thomas Ogrisegg 在 Linux 2.4.17-2.4.20 下针对 mmaped 文件的零拷贝发送补丁](http://marc.theaimsgroup.com/?l=linux-kernel&m=104121076420067&w=2)。声称它比 sendfile() 更快。
  - [IO-Lite](http://www.usenix.org/publications/library/proceedings/osdi99/full_papers/pai/pai_html/pai.html)是一组 I/O 原语的提议，它消除了对许多副本的需要。
  - [Alan Cox 早在 1999 年就指出，零拷贝有时并不值得这么麻烦。](http://www.linuxhq.com/lnxlists/linux-kernel/lk_9905_01/msg00263.html) （不过，他确实喜欢 sendfile()。）
  - Ingo 于 2000 年 7 月在 TUX 1.0 的 2.4 内核中[实现了一种零拷贝 TCP 形式](http://boudicca.tux.org/hypermail/linux-kernel/2000week36/0979.html)，并表示他将很快将其提供给用户空间。
  - [Drew Gallatin 和 Robert Picco 为 FreeBSD 添加了一些零拷贝功能](http://people.freebsd.org/~ken/zero_copy/)；这个想法似乎是这样的 如果您在套接字上调用 write() 或 read()，则指针是页对齐的， 并且传输的数据量至少是一页，*并且*您 不要立即重用缓冲区，内存管理技巧将被 用于避免复制。但看 [linux-kernel 上此消息的后续内容](http://boudicca.tux.org/hypermail/linux-kernel/2000week39/0249.html) 人们对这些内存管理技巧的速度的疑虑

  根据 Noriyuki Soda 的注释：

  > *自 NetBSD-1.6 版本起，通过指定“SOSEND_LOAN”内核选项支持发送端零拷贝。现在，此选项在 NetBSD-current 上是默认选项（您可以通过在 NetBSD_current 上的内核选项中指定“SOSEND_NO_LOAN”来禁用此功能）。利用此功能，如果指定发送的数据超过 4096 字节，则自动启用零复制。*

  - sendfile()系统调用可以实现零拷贝网络。

    Linux 和 FreeBSD 中的 sendfile() 函数可让您告诉内核发送部分 或整个文件。这使得操作系统能够尽可能高效地完成任务。 它同样可以在使用线程的服务器或使用 非阻塞 I/O。 （在 Linux 中，目前它的文档很少；[使用 _syscall4 来调用它](http://www.dejanews.com/getdoc.xp?AN=422899634)。Andi Kleen 正在编写新的手册页来介绍这一点。 另请参阅 Jeff Tranter 在 Linux Gazette 第 91 期中[探索 sendfile 系统调用](http://www.linuxgazette.com/issue91/tranter.html)。） [有传言说](http://www.dejanews.com/getdoc.xp?AN=423005088)， ftp.cdrom.com 从 sendfile() 中受益匪浅。

    sendfile() 的零拷贝实现即将在 2.4 内核中实现。参见[LWN 2001 年 1 月 25 日](http://lwn.net/2001/0125/kernel.php3)。

    Solaris 8（截至 2001 年 7 月更新）有一个新的系统调用“sendfilev”。[手册页的副本位于此处。](http://www.kegel.com/sendfilev.txt) 。 Solaris 8 7/01[发行说明](http://www.sun.com/software/solaris/fcc/ucc-details701.html)也提到了这一点。我怀疑这在以阻塞模式发送到套接字时最有用；使用非阻塞套接字会有点痛苦。

- 使用 writev （或 TCP_CORK）避免小帧

  Linux 下的一个新套接字选项 TCP_CORK 告诉内核 避免发送部分帧，这会有所帮助，例如当有 由于某种原因，许多小的 write() 调用无法捆绑在一起。 取消设置该选项会刷新缓冲区。不过，最好使用 writev()...

- Behave sensibly on overload.

  [[普罗沃斯、杠杆和特威迪 2000](http://www.citi.umich.edu/techreports/reports/citi-tr-00-7.ps.gz) ] 请注意，当服务器 过载改善了性能曲线的形状， 并降低了总体错误率。他们使用了平滑的 “I/O 就绪的客户端数量”版本作为衡量标准 的过载。该技术应该很容易适用于使用 select、poll 或任何每次调用能够返回就绪事件的计数（例如 /dev/poll 或 sigtimedwait4()）的系统调用编写的服务器。

- 某些程序可以从使用非 Posix 线程中受益。

  并非所有线程都是一样的。 Linux中的clone()函数 （以及其他操作系统中的朋友） 允许您创建一个具有自己的当前工作目录的线程， 例如，这在实现 ftp 服务器时非常有帮助。 有关使用本机线程而不是 pthread 的示例，请参阅 Hoser FTPd。

- 缓存自己的数据有时可能是一种胜利。

  “回复：修复混合服务器问题”，作者：Vivek Sadananda Pai （vivek@cs.rice.edu）于 [new-httpd](http://www.humanfactor.com/cgi-bin/cgi-delegate/apache-ML/nh/1999/) ，5 月 9 日，指出：

  > “我在 FreeBSD 和 Solaris/x86 上比较了基于 select 的服务器与多进程服务器的原始性能。在微基准测试中，由于软件架构而导致的性能只有微小的差异。select 的巨大性能优势基于应用程序级缓存，虽然多进程服务器可以以更高的成本实现这一点，但在实际工作负载上获得相同的好处（与微基准测试相比）却更困难。下一次 Usenix 会议上将出现的论文 如果您有后记，可以在http://www.cs.rice.edu/~vivek/flash99/上找到该论文。

## Other limits 其他限制

- 旧的系统库可能使用 16 位变量来保存文件句柄，这会导致超过 32767 个句柄的问题。 glibc2.1应该没问题。
- 许多系统使用 16 位变量来保存进程或线程 ID。将[Volano 可扩展性基准](http://www.volano.com/benchmarks.html)移植到 C 语言并查看不同操作系统的线程数量上限是很有趣的。
- 某些操作系统预分配了过多的线程本地内存；如果每个线程获得 1MB，并且总 VM 空间为 2GB，则创建的线程上限为 2000 个。

- 看最下面的性能对比图 http://www.acme.com/software/thttpd/benchmarks.html 。 请注意，即使在 Solaris 2.6 上，各种服务器在超过 128 个连接时也会出现问题？ 谁知道为什么，请告诉我。

  注意：如果TCP协议栈有bug导致短暂（200ms） SYN 或 FIN 时间延迟，如 Linux 2.2.0-2.2.6 所具有的那样，并且操作系统或 http 守护进程对打开的连接数有硬性限制， 你会期望这种行为。可能还有其他原因。

## Measuring Server Performance

有两个测试特别简单、有趣且困难：

1. raw connections per second (how many 512 byte files per second can you serve?)
   每秒原始连接（每秒可以服务多少个 512 字节文件？）
2. total transfer rate on large files with many slow clients (how many 28.8k modem clients can simultaneously download from your server before performance goes to pot?)
   具有许多慢速客户端的大文件的总传输速率（在性能发挥作用之前，有多少个 28.8k 调制解调器客户端可以同时从服务器下载？）

Jef Poskanzer 发布了比较许多 Web 服务器的基准。请参阅http://www.acme.com/software/thttpd/benchmarks.html了解他的结果。

IBM 有一篇名为[Java 服务器基准测试](http://www.research.ibm.com/journal/sj/391/baylor.html)的优秀论文 [Baylor et al, 2000]。值得一读。

## Examples 示例

[Nginx](http://nginx.org/)是一个 Web 服务器，可以使用任何 目标操作系统具备高效的网络事件机制。 它越来越流行；甚至还有关于它的[书籍](http://www.amazon.com/Nginx-HTTP-Server-Clément-Nedelcu/dp/1849510865/)（由于本页最初是写的，所以还有更多，包括该书的[第四版](https://www.amazon.com/Nginx-HTTP-Server-Harness-infrastructure/dp/178862355X)。）

### 有趣的基于 select() 的服务器

- [thttpd](http://www.acme.com/software/thttpd/) Very simple. Uses a single process. It has good performance, but doesn't scale with the number of CPU's. Can also use kqueue.
- [mathopd](http://mathop.diva.nl/). Similar to thttpd.
- [fhttpd](http://www.fhttpd.org/)
- [boa](http://www.boa.org/)
- [Roxen](http://www.roxen.com/)
- [Zeus](http://www.zeustech.net/), a commercial server that tries to be the absolute fastest. See their [tuning guide](http://support.zeustech.net/faq/entries/tuning.html).
- The other non-Java servers listed at http://www.acme.com/software/thttpd/benchmarks.html
- [BetaFTPd](http://ca.us.mirrors.freshmeat.net/appindex/1999/02/17/919251275.html)
- [Flash-Lite](http://www.cs.rice.edu/~vivek/iol98/) - web server using IO-Lite.
- [Flash: An efficient and portable Web server](http://www.cs.rice.edu/~vivek/flash99/) -- uses select(), mmap(), mincore()
- [The Flash web server as of 2003](http://www.cs.princeton.edu/~yruan/debox/) -- uses select(), modified sendfile(), async open()
- [xitami](http://www.imatix.com/html/xitami/) - uses select() to implement its own thread abstraction for portability to systems without threads.
- [Medusa](http://www.nightmare.com/medusa/medusa.html) - a server-writing toolkit in Python that tries to deliver very high performance.
- [userver](http://www.hpl.hp.com/research/linux/userver/) - a small http server that can use select, poll, epoll, or sigio

### Interesting /dev/poll-based servers

*N. Provos，C. Lever* ， [“Linux 中的可扩展网络 I/O”，](http://www.citi.umich.edu/techreports/reports/citi-tr-00-4.pdf) 2000 年 5 月。 USENIX 2000，圣地亚哥，加利福尼亚州（2000 年 6 月）。] 描述了经过修改以支持 /dev/poll 的 thttpd 版本。性能与 phhttpd 进行比较。

### Interesting epoll-based servers

- [ribs2](https://github.com/Adaptv/ribs2)
- [cmogstored](http://bogomips.org/cmogstored/README) - uses epoll/kqueue for most networking, threads for disk and accept4

### Interesting kqueue()-based servers

- [thttpd](http://www.acme.com/software/thttpd/) (as of version 2.21?)
- Adrian Chadd says "I'm doing a lot of work to make squid actually LIKE a kqueue IO system"; it's an official Squid subproject; see http://squid.sourceforge.net/projects.html#commloops. (This is apparently newer than [Benno](http://www.advogato.org/person/benno/)'s [patch](http://netizen.com.au/~benno/squid-select.tar.gz).)

### Interesting realtime signal-based servers

- Chromium 的X15。它使用了 2.4 内核的 SIGIO 功能以及 sendfile() 和 TCP_CORK，据报道其速度甚至比 TUX 还要高。该[源可在社区源（非开源）许可证下使用](http://www.chromium.com/cgi-bin/crosforum/YaBB.pl)。请参阅 Fabio Riccardi 的[原始公告](http://boudicca.tux.org/hypermail/linux-kernel/2001week21/1624.html)。

- Zach Brown 的[phhttpd](http://www.zabbo.net/phhttpd/) - “一个快速的 Web 服务器，旨在展示 sigio/siginfo 事件模型。如果您尝试在生产环境中使用它，请考虑此代码是高度实验性的，并且您自己非常精神。”使用 2.3.21 或更高版本的[siginfo](http://www.kegel.com/c10k.html#nb.sigio)功能，并包含早期内核所需的补丁。据说比 khttpd 还要快。一些注释请参见[他 1999 年 5 月 31 日的帖子](http://www.cs.helsinki.fi/linux/linux-kernel/Year-1999/1999-22/0453.html)。

### Interesting thread-based servers

- [Hoser FTPD](http://www.zabbo.net/hftpd/). See their [benchmark page](http://www.zabbo.net/hftpd/bench.html).
- [Peter Eriksson's phttpd](http://ca.us.mirrors.freshmeat.net/appindex/1999/02/06/918317238.html) and
- [pftpd](http://ca.us.mirrors.freshmeat.net/appindex/1999/02/06/918313631.html)
- The Java-based servers listed at http://www.acme.com/software/thttpd/benchmarks.html
- Sun's [Java Web Server](http://jserv.javasoft.com/) (which has been [reported to handle 500 simultaneous clients](http://archives.java.sun.com/cgi-bin/wa?A2=ind9901&L=jserv-interest&F=&S=&P=47739))

### Interesting in-kernel servers

- [khttpd](http://www.fenrus.demon.nl/)
- ["TUX" (Threaded linUX webserver)](http://slashdot.org/comments.pl?sid=00/07/05/0211257&cid=218) by Ingo Molnar et al. For 2.4 kernel.



## Other interesting links

- [Jeff Darcy's notes on high-performance server design](http://pl.atyp.us/content/tech/servers.html)
- [Ericsson's ARIES project](http://www2.linuxjournal.com/lj-issues/issue91/4752.html) -- benchmark results for Apache 1 vs. Apache 2 vs. Tomcat on 1 to 12 processors
- [Prof. Peter Ladkin's Web Server Performance](http://nakula.rvs.uni-bielefeld.de/made/artikel/Web-Bench/web-bench.html) page.
- [Novell's FastCache](http://www.novell.com/bordermanager/ispcon4.html) -- claims 10000 hits per second. Quite the pretty performance graph.
- Rik van Riel's [Linux Performance Tuning site](http://linuxperf.nl.linux.org/)