package ehr.cfcs.com.ehr.Source;

/**
 * Created by Admin on 09-03-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs
{
    private static SharedPreferences getSetting(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(
                SettingConstant.SP_NAME, 0);
        return sp;
    }

    // vechiel type shared
    public static String getVechileType(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.VechelType, null);
    }
    public static boolean setVechileType(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.VechelType, authKey);
        return editor.commit();
    }




    //Source Address
    public static String getSourceAddress(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.SourceAddress, null);
    }
    public static boolean setSourceAddress(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.SourceAddress, authKey);
        return editor.commit();
    }

    // User is Shared preference
    public static String getAdminId(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.AdminId, null);
    }
    public static boolean setAdminId(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.AdminId, authKey);
        return editor.commit();
    }


    // AuthCode Shared Preference
    public static String getAuthCode(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.AuthCode, null);
    }
    public static boolean setAuthCode(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.AuthCode, authKey);
        return editor.commit();
    }



    //status Refrence--------------------
    public static String getEmailId(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.EmailId, null);
    }
    public static boolean setEmailId(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.EmailId, authKey);
        return editor.commit();
    }

    // Status First Home Page Shared Preference
    public static String getStatus(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.Status, null);
    }
    public static boolean setStatus(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.Status, authKey);
        return editor.commit();
    }
    // Start Time Shard Preference
    public static String getUserName(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.UserName, null);
    }
    public static boolean setUserName(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.UserName, authKey);
        return editor.commit();
    }

    // Source Address Shared Prefrence
    public static String getUserType(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.UserType, null);
    }
    public static boolean setUserType(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.UserType, authKey);
        return editor.commit();
    }


    // Source let Shared Prefrence
    public static String getMgrDir(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.MgrDir, null);
    }
    public static boolean setMgrDir(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.MgrDir, authKey);
        return editor.commit();
    }

    // Source Log Shared Prefrence
    public static String getSourceLog(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.Sourcelog, null);
    }
    public static boolean setSourceLog(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.Sourcelog, authKey);
        return editor.commit();
    }


    //get Source Time
    public static String getCompanyId(Context context)
    {
        SharedPreferences sp = getSetting(context);
        return  sp.getString(SettingConstant.CompanyId, null);
    }
    public static boolean setCompanyId(Context context, String authKey)
    {
        SharedPreferences sp = getSetting(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingConstant.CompanyId, authKey);
        return editor.commit();
    }

}