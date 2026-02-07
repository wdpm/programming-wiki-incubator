package io.github.wdpm.maple.sample.controller;

import io.github.wdpm.maple.MapleContext;
import io.github.wdpm.maple.db.MapleDB;
import io.github.wdpm.maple.sample.model.User;
import io.github.wdpm.maple.servlet.wrapper.Request;
import io.github.wdpm.maple.servlet.wrapper.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author evan
 * @date 2020/4/22
 */
public class UserController {

    /**
     * 用户列表
     *
     * @param request
     * @param response
     */
    public void users(Request request, Response response) {
        List<User> users = MapleDB.getList("select * from t_user", User.class);
        request.attr("users", users);
        response.render("users");
    }

    /**
     * 添加用户界面
     *
     * @param request
     * @param response
     */
    public void show_add(Request request, Response response) {
        response.render("user_add");
    }

    /**
     * 保存方法
     *
     * @param request
     * @param response
     * @throws ParseException
     */
    public void save(Request request, Response response) throws ParseException {
        String  name = request.query("name");
        Integer age  = request.queryAsInt("age");
        String  date = request.query("birthday");

        if (null == name || null == age || null == date) {
            request.attr("res", "error");
            response.render("user_add");
            return;
        }

        Date bir = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        int res = MapleDB.insert("insert into t_user(name, age, birthday)", name, age, bir);
        if (res > 0) {
            String ctx = MapleContext.getCurrentContext()
                                     .getServletContext()
                                     .getContextPath();
            String location = ctx + "/users";
            response.redirect(location.replaceAll("[/]+", "/"));
        } else {
            request.attr("res", "error");
            response.render("user_add");
        }
    }

    /**
     * 编辑页面
     *
     * @param request
     * @param response
     */
    public void edit(Request request, Response response) {
        Integer id = request.queryAsInt("id");
        if (null != id) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            User user = MapleDB.get("select * from t_user where id = :id", User.class, map);
            request.attr("user", user);
            response.render("user_edit");
        }
    }

    /**
     * 修改信息
     *
     * @param request
     * @param response
     */
    public void update(Request request, Response response) {
        Integer id   = request.queryAsInt("id");
        String  name = request.query("name");
        Integer age  = request.queryAsInt("age");

        if (null == id || null == name || null == age) {
            request.attr("res", "error");
            response.render("user_edit");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("age", age);

        int res = MapleDB.update("update t_user set name = :name, age = :age where id = :id", map);
        if (res > 0) {
            String ctx = MapleContext.getCurrentContext()
                                     .getServletContext()
                                     .getContextPath();
            String location = ctx + "/users";
            response.redirect(location.replaceAll("[/]+", "/"));
        } else {
            request.attr("res", "error");
            response.render("user_edit");
        }
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     */
    public void delete(Request request, Response response) {
        Integer id  = request.queryAsInt("id");
        int     res = 0;
        if (null != id) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            res = MapleDB.update("delete from t_user where id = :id", map);
        }

        if (res > 0) {
            String ctx = MapleContext.getCurrentContext()
                                     .getServletContext()
                                     .getContextPath();
            String location = ctx + "/users";
            response.redirect(location.replaceAll("[/]+", "/"));
        } else {
            request.attr("res", "error");
            response.render("user_edit");
        }
    }
}