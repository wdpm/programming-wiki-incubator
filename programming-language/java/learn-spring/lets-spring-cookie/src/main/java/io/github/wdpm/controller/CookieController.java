package io.github.wdpm.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author evan
 * @date 2020/5/21
 */
@RestController
public class CookieController {

    @GetMapping("/")
    public String readCookie(@CookieValue(value = "username", defaultValue = "Spring") String username) {
        return "I am " + username;
    }

    @GetMapping("/change-username")
    public String setCookie(HttpServletResponse response) {

        // create a cookie
        Cookie cookie = new Cookie("username", "Intellij");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setSecure(true);// force https
        cookie.setHttpOnly(true); // forbidden JS
        cookie.setPath("/"); // global cookie accessible every where

        //add cookie to response
        response.addCookie(cookie);

        return "Username is changed!";
    }

    @GetMapping("/delete-username")
    public String deleteCookie(HttpServletResponse response) {

        // create a cookie
        Cookie cookie = new Cookie("username", null);// set value to null
        cookie.setMaxAge(0);// set MaxAge to 0 NOT -1
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        //add cookie to response
        response.addCookie(cookie);

        return "Username is deleted!";
    }

    @GetMapping("/all-cookies")
    public String readAllCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays
                    .stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(", "));
        }

        return "No cookies";
    }
}
