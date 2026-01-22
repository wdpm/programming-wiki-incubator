package io.github.wdpm.jdk7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 同时捕获多个异常
 *
 * @author evan
 * @since 2020/4/19
 */
public class CatchMultiException {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(""));
            Connection     con    = null;
            Statement      stmt   = con.createStatement();
        } catch (IOException | SQLException e) {
            //捕获多个异常
            e.printStackTrace();
        }
    }
}
