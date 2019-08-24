package com.bigdata.project.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LogParser {

    private final static int IP_INDEX = 13;
    private final static int URL_INDEX = 1;
    private final static int TIME_INDEX = 17;

    public Map<String, String> parse(String log) {
        Map<String, String> info = new HashMap<>(16);
        String country;
        String province;
        String city;
        if (StringUtils.isNotBlank(log)) {
            String[] logs = log.split("\001");
            String ip = logs[IP_INDEX];
            IPParser.RegionInfo regionInfo = IPParser.getInstance().analyseIp(ip);

            country = regionInfo.getCountry() == null ? "-" : regionInfo.getCountry();
            province = regionInfo.getProvince() == null ? "-" : regionInfo.getProvince();
            city = regionInfo.getCity() == null ? "-" : regionInfo.getCity();

            info.put("ip", ip);
            info.put("country", country);
            info.put("province", province);
            info.put("city", city);
            info.put("url", logs[URL_INDEX]);
            info.put("date", logs[TIME_INDEX]);
        }
        return info;
    }

    public Map<String, String> parseV2(String log) {
        Map<String, String> info = new HashMap<>(16);
        String country = "-";
        String province = "-";
        String city = "-";
        if (StringUtils.isNotBlank(log)) {
            String[] logs = log.split("\t");
            String ip = logs[0];

            country = logs[1];
            province = logs[2].equals("null") ? "-" : logs[2];
            city = logs[3];

            info.put("ip", ip);
            info.put("country", country);
            info.put("province", province);
            info.put("city", city);
            info.put("url", logs[4]);
            info.put("date", logs[5]);
            info.put("pageId", logs[6]);
        }
        return info;
    }
}
