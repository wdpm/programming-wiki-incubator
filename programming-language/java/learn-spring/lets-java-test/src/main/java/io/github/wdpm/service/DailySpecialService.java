package io.github.wdpm.service;

/**
 * @author evan
 * @date 2020/5/19
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DailySpecialService {

    private final String SPECIALS_URL = "http://www.mocky.io/v2/5ec38313300000940039c0fd";

    public List<String> getSpecials() {
        try {
            String json = getJsonFromUrl();
            return parseFlavorsFromJson(json);
        } catch (IOException e) {
            System.out.println("Error retrieving daily specials!");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private String getJsonFromUrl() throws IOException {
        URL               url = new URL(SPECIALS_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String        inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    List<String> parseFlavorsFromJson(String json) {
        //这部分代码只为说明问题
        //实际代码会使用JSON 解析库
        final String REGEX_PATTERN = "\"flavor\":\\s*\"(?<theFlavor>[\\w ]+)\"";
        Pattern      flavorRegex   = Pattern.compile(REGEX_PATTERN);
        Matcher      matcher       = flavorRegex.matcher(json);
        List<String> flavors       = new ArrayList<>();
        while (matcher.find()) {
            flavors.add(matcher.group("theFlavor"));
        }
        return flavors;
    }
}