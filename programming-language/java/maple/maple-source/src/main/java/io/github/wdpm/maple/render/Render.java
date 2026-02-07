package io.github.wdpm.maple.render;

import java.io.Writer;

/**
 * @author evan
 * @date 2020/4/22
 */
public interface Render {
    /**
     * 渲染到视图
     *
     * @param view   视图名称
     * @param writer 写入对象
     */
    void render(String view, Writer writer);
}
