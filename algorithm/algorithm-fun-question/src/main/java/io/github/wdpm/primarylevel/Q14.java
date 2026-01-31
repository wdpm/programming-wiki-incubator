package io.github.wdpm.primarylevel;

//2014年FIFA 世界杯参赛国
//Brazil   Croatia          Mexico
//Cameroon  Spain           Netherlands
//Chile     Australia       Colombia
//Greece    Cote d'Ivoire   Japan
//Uruguay   Costa Rica      England
//Italy     Switzerland     Ecuador
//France    Honduras        Argentina
//Bosnia and Herzegovina    Iran Nigeria
//Germany   Portugal        Ghana
//USA       Belgium         Algeria
//Russia   Korea Republic

//那么连续3 个国名之后接龙就结束了
//（因为没有英文名称以D 开头的国家）。
//“Japan” →“ Netherlands” →“ Switzerland”

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

//假设每个国名只能使用一次，求能连得最长的顺序，以及相应的国名个数。
public class Q14 {
    private static int maxDepth = 0;
    public static void main(String[] args) {
        String[] countries = {"Brazil", "Croatia", "Mexico", "Cameroon",
                "Spain", "Netherlands", "Chile", "Australia",
                "Colombia", "Greece", "Cote d'Ivoire", "Japan",
                "Uruguay", "Costa Rica", "England", "Italy",
                "Switzerland", "Ecuador", "France", "Honduras",
                "Argentina", "Bosnia and Herzegovina", "Iran",
                "Nigeria", "Germany", "Portugal", "Ghana",
                "USA", "Belgium", "Algeria", "Russia",
                "Korea Republic"};
        ArrayList<String> countryList = new ArrayList<>(Arrays.asList(countries));

        for (String c : countryList) {
            ArrayList<String> clone = (ArrayList<String>) countryList.clone();//浅复制
            clone.remove(c);
            search(clone, c, 1);
        }

        System.out.println(maxDepth);
    }

    private static void search(ArrayList<String> countries, String country,
                               int depth) {
        char nextChar = country.toUpperCase().charAt(country.length() - 1);//l->L

        // 下一个可用的国家名list
        ArrayList<String> next_countries = (ArrayList<String>) countries.stream()
                .filter(c -> c.charAt(0) == nextChar)
                .collect(Collectors.toList());

        if (next_countries.size() > 0) {//分别对collect中的每一个，进行+1层的搜索
            for (String c : next_countries) {
                ArrayList<String> clone = (ArrayList<String>) countries.clone();
                clone.remove(c);
                search(clone, c, depth + 1);
            }
        } else {
            maxDepth = Math.max(maxDepth, depth);
        }
    }
}

//8