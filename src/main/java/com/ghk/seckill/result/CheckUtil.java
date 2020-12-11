package com.ghk.seckill.result;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    private static final Pattern mobile = Pattern.compile("1\\d{10}");

    /**
     * 校验手机号
     * @param s
     * @return
     */
    public static boolean checkMobile(String s){
        if(StringUtils.isEmpty(s))
            return false;
        Matcher m = mobile.matcher(s);
        return m.matches();
    }


}
