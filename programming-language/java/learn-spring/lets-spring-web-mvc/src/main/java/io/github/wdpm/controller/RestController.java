package io.github.wdpm.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 因为Jackson 2在 classpath 上，自动选择MappingJackson2HttpMessageConverter转换JSON
 *
 * @author evan
 * @date 2020/5/20
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/json")
    public String json() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        return jsonObject.toString();
    }
}
