package com.grucee.util;

import java.util.List;

public class LogUtils {
    private void LogUtils(){}

    public static void log(List<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void log(String line) {
        System.out.println(line);
    }
}
