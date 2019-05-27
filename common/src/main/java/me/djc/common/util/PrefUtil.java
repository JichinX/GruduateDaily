package me.djc.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author xujichang
 * @date 2019/05/27
 **/
public class PrefUtil {
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    public static void saveLoginInfo(Context pContext, String account, String pwd) {
        getDefaultPref(pContext).edit().putString(ACCOUNT, account).putString(PASSWORD, pwd).apply();
    }

    public static void clearLoginInfo(Context pContext) {
        getDefaultPref(pContext).edit().putString(ACCOUNT, "").putString(PASSWORD, "").apply();
    }

    private static SharedPreferences getDefaultPref(Context pContext) {
        return PreferenceManager.getDefaultSharedPreferences(pContext);
    }

    public static String getSavedAccount(Context pContext) {
        return getDefaultPref(pContext).getString(ACCOUNT, "");
    }

    public static String getSavedPwd(Context pContext) {
        return getDefaultPref(pContext).getString(PASSWORD, "");
    }
}
