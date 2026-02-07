package io.github.wdpm.maple.sample.controller;

import io.github.wdpm.maple.servlet.wrapper.Request;
import io.github.wdpm.maple.servlet.wrapper.Response;

/**
 * @author evan
 * @date 2020/4/22
 */
public class Index {
    public void index(Request request, Response response) {
        request.attr("name", "wdpm");
        response.render("index");
    }

    public void hello(Request request, Response response) {
        response.text("hello");
    }

    public void html(Request request, Response response) {
        response.html("<h1>hello world!</h1>");
    }
}
