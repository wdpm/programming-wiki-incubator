package io.github.wdpm.maple.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author evan
 * @date 2020/4/22
 */
public class PathUtil {
    public static final  String VAR_REGEXP  = ":(\\w+)";
    public static final  String VAR_REPLACE = "([^#/?]+)";
    private static final String SLASH       = "/";

    public static String fixPath(String path) {
        if (path == null) {
            return "/";
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);//path[0...n-2]
        }
        // 形式为 /users,开头必须有/,末尾不允许有/
        return path;
    }

    /**
     * 对于请求：http://localhost:8080/test/login.jsp
     * <li>
     * request.getRequestURL() => http://localhost:8080/test/login.jsp
     * </li>
     * <li>
     * request.getRequestURI() => /test/login.jsp
     * </li>
     * <li>
     * request.getContextPath() => /test
     * </li>
     * <li>
     * request.getServletPath() => /login.jsp
     * </li>
     *
     * @param request
     * @return
     */
    public static String getRelativePath(HttpServletRequest request) {
        String path = request.getServletPath();//  /login.jsp
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return path;
    }
}
