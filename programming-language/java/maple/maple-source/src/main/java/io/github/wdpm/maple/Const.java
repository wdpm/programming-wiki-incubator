package io.github.wdpm.maple;

/**
 * 常量类
 *
 * @author evan
 */
public final class Const {

    // 不允许初始化
    private Const() {
    }

    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHAR_SET = "UTF-8";

    /**
     * 当前版本号
     */
    public static final String MAPLE_VERSION = "1.0.0";

    /**
     * 读取视图前缀的字段。自定义值。
     */
    public static final String VIEW_PREFIX_FIELD = "maple.view.prefix";

    /**
     * 读取视图后缀的字段。自定义值。
     */
    public static final String VIEW_SUFFIX_FIELD = "maple.view.suffix";

    /**
     * 视图路径前缀。默认值
     */
    public static final String VIEW_PREFIX = "/WEB-INF/";

    /**
     * 视图后缀。默认值
     */
    public static final String VIEW_SUFFIX = ".jsp";


}
