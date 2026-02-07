package io.github.wdpm.maple.servlet.wrapper;

import io.github.wdpm.maple.Maple;
import io.github.wdpm.maple.render.Render;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * HttpServletResponse增强
 * <br>
 * 1. 使用Render类型的实例来执行渲染页面操作。
 *
 * @author evan
 * @see Render
 */
public class Response {

    private HttpServletResponse raw;

    private Render render;

    public Response(HttpServletResponse httpServletResponse) {
        this.raw = httpServletResponse;
        this.raw.setHeader("Framework", "Maple");
        this.render = Maple.getInstance()
                           .getRender();
    }

    public void text(String text) {
        raw.setContentType("text/plan;charset=UTF-8");
        this.print(text);
    }

    public void html(String html) {
        raw.setContentType("text/html;charset=UTF-8");
        this.print(html);
    }

    private void print(String str) {
        try (OutputStream outputStream = raw.getOutputStream()) {
            outputStream.write(str.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cookie(String name, String value) {
        raw.addCookie(new Cookie(name, value));
    }

    public HttpServletResponse getRaw() {
        return raw;
    }

    public void render(String view) {
        render.render(view, null);
    }

    public void redirect(String location) {
        try {
            raw.sendRedirect(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}