package io.github.wdpm.maple.sample;

import io.github.wdpm.maple.Bootstrap;
import io.github.wdpm.maple.Maple;
import io.github.wdpm.maple.db.MapleDB;
import io.github.wdpm.maple.sample.controller.Index;
import io.github.wdpm.maple.sample.controller.UserController;

/**
 * @author evan
 * @date 2020/4/22
 */
public class App implements Bootstrap {
    @Override
    public void init(Maple maple) {
        // here do banner output
        System.out.println("=========maple=============");

        Index index = new Index();
        maple.addRoute("/", "index", index);
        maple.addRoute("/hello", "hello", index);
        maple.addRoute("/html", "html", index);

        UserController userController = new UserController();
        // maple.addRoute(path,methodName,controller)
        maple.addRoute("/users", "users", userController);
        maple.addRoute("/user/add", "show_add", userController);
        maple.addRoute("/user/save", "save", userController);
        maple.addRoute("/user/edit", "edit", userController);
        maple.addRoute("/user/update", "update", userController);
        maple.addRoute("/user/del", "delete", userController);

        try {
            // 解决tomcat下报错
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // fixme config data source
        MapleDB.init("jdbc:mysql://192.168.137.12:3306/maple_sample", "root", "Root_2019");
    }
}
