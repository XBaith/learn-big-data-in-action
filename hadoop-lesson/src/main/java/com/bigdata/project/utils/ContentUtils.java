package com.bigdata.project.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentUtils {

    private static Pattern TOPICID = Pattern.compile("topicId=[0-9]+");

    public static String getPageId(String url){
        String pageId = "-";
        Matcher matcher = TOPICID.matcher(url);

        if(matcher.find()){
            pageId = matcher.group().split("topicId=")[1];
        }
        return pageId;
    }
}
