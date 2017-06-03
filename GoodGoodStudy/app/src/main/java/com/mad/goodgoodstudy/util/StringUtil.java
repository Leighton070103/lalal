package com.mad.goodgoodstudy.util;

/**
 * Created by Tong on 2017/5/31.
 */

public class StringUtil {
    public static String getQueryStrFromName(String name){
        return com.orm.StringUtil.toSQLName(name) + " = ?";
    }

    public static boolean isEmpty(String str){
        return ( str == null || str.equals(""));
    }

}
