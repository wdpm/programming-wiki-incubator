package io.github.wdpm.maple.render;

import io.github.wdpm.maple.Const;
import io.github.wdpm.maple.Maple;
import io.github.wdpm.maple.MapleContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * render的jsp实现
 *
 * @author evan
 * @date 2020/4/22
 */
public class JspRender implements Render {
    public static final Logger LOGGER = Logger.getLogger(JspRender.class.getName());

    @Override
    public void render(String view, Writer writer) {
        String viewPath = this.getViewPath(view);

        HttpServletRequest servletRequest = MapleContext.getCurrentContext()
                                                        .getRequest()
                                                        .getRaw();
        HttpServletResponse servletResponse = MapleContext.getCurrentContext()
                                                          .getResponse()
                                                          .getRaw();
        try {
            servletRequest.getRequestDispatcher(viewPath)
                          .forward(servletRequest, servletResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * view -> finalViewPath.
     * <p>
     * example:
     * users -> /WEB-INF/users.jsp
     * </p>
     *
     * @param view
     * @return
     */
    private String getViewPath(String view) {
        Maple  maple      = Maple.getInstance();
        String viewPrefix = maple.getConf(Const.VIEW_PREFIX_FIELD);
        String viewSuffix = maple.getConf(Const.VIEW_SUFFIX_FIELD);

        if (null == viewSuffix || viewSuffix.equals("")) {
            viewSuffix = Const.VIEW_SUFFIX;
        }
        if (null == viewPrefix || viewPrefix.equals("")) {
            viewPrefix = Const.VIEW_PREFIX;
        }
        String viewPath = viewPrefix + "/" + view;
        if (!view.endsWith(viewSuffix)) {
            viewPath += viewSuffix;
        }
        String finalPath = viewPath.replaceAll("[/]+", "/");
        return finalPath;
    }
}
