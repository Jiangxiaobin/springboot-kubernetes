package com.example.utils;

public class StringUtil {
    public static boolean emptyString(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str) || "".equals(str.trim());
    }
}
