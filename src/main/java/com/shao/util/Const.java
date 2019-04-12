package com.shao.util;

import org.springframework.stereotype.Component;

/**
 * Created by shao on 2019/4/11 17:23.
 */
@Component
public class Const {

    public static final String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";
    public static String LOGIN_SESSION_KEY = "user";
    public static int COOKIE_TIMEOUT= 30*24*60*60;
    public static String LAST_REFERER = "LAST_REFERER";
    public static String DES3_KEY = "9964DYByKL967c3308imytCB";
}
