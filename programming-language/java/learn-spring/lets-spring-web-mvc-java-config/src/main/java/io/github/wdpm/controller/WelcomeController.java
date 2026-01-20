package io.github.wdpm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author evan
 * @date 2020/5/20
 */
@Controller
public class WelcomeController {

    @RequestMapping(value = "/mvc", method = RequestMethod.GET)
    public String displayWelcomeMessage(ModelMap modelMap) {
        modelMap.addAttribute("message", "Welcome message from the Spring MVC Controller");
        return "welcome";
    }
}