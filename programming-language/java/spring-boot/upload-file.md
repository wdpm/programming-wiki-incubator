# Upload file
> https://spring.io/guides/gs/uploading-files/

要使用Servlet容器上传文件，需要注册一个MultipartConfigElement类（在web.xml中为<multipart-config>）。
Spring Boot 已自动配置。

在生产环境中，您更有可能将文件存储在临时位置，数据库或Mongo的GridFS之类的NoSQL存储中。最好不要使用内容加载应用程序的文件系统。

1.定义 interface
```java
public interface StorageService {
    void init();// 初始化步骤，可以创建保存文件的目录

    void store(MultipartFile file);// 保存上传文件

    Stream<Path> loadAll();// 以Stream形式加载目录下所有文件

    Path load(String filename);// 根据 filename 加载 Path

    Resource loadAsResource(String filename);// 根据 filename 记载 Resource

    void deleteAll();// 递归清空保存文件的目录
}
```
2.创建 FileSystemStorageService 实现 StorageService 所有接口

- 注意使用 @Service 将 FileSystemStorageService 注册为组件。
- 可以同时创建 StorageProperties 自定义一些属性用于文件上传、保存。需要在主类App开启:
```java
@EnableConfigurationProperties(StorageProperties.class)
```

3.定义 FileUploadController 并使用构造器参数依赖注入 StorageService 类型的bean
```java
public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
}
```
实现必要的 Http 方法
```java
@GetMapping("/")
public String listUploadedFiles(Model model)

@GetMapping("/files/{filename:.+}")
@ResponseBody
public ResponseEntity<Resource> getFile(@PathVariable String filename)

@PostMapping("/")
public String handleFileUpload(@RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes)
```

4.主类在启动时进行初始化工作

使用 CommandLineRunner 执行 storageService.init()进行初始化

5.测试代码
```java
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 方法名升序执行
public class UploadFileApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;
    
    @Test
    public void should_01_ListAllFiles() throws Exception {
        given(this.storageService.loadAll())
                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(model().attribute("files",
                        Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")));
    }
```