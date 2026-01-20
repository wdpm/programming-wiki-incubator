package io.github.wdpm.service;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/19
 */
public class MenuCreator {

    private DailySpecialService dailySpecialService;

    public MenuCreator(DailySpecialService dailySpecialService) {
        this.dailySpecialService = dailySpecialService;
    }

    public String getTodayMenu() {
        List<String> dailySpecials = dailySpecialService.getSpecials();

        StringBuilder menuBuilder = new StringBuilder("Today's specials are:\n");
        dailySpecials.forEach(s -> menuBuilder.append(" - ").append(s).append("\n"));

        return menuBuilder.toString();
    }

}