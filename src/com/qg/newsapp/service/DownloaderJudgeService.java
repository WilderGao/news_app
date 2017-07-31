package com.qg.newsapp.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloaderJudgeService {
    public static boolean IsManager(String downloaderName){
        Pattern judgeDownloadName = Pattern.compile("[\\w]+@[0-9a-z]+(\\.+[a-z]{2,4}){1,3}");
        Matcher downloadMatcher = judgeDownloadName.matcher(downloaderName);
        //假如匹配成功他就是管理员
        if (downloadMatcher.matches())
            return true;
        else
            return false;
    }
}
