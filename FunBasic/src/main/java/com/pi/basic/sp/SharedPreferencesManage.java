package com.pi.basic.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * @描述：     @Preference管理工具类
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class SharedPreferencesManage {

    private static SharedPreferences           sPreference;
    private static SharedPreferences.Editor    sEditor;


    /**
     * 初始化
     * @param name
     */
    public static void initialize(Context context,String name){
        if(sPreference == null){
            sPreference = context.getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            sEditor     = sPreference.edit();
        }
    }


    private static final boolean isValidate() {
        if (null == sPreference
                || null == sEditor) {
            return false;
        }
        return true;
    }

    /**
     * 清除数据
     */
    public static final void clear() {
        sEditor.clear();
        sEditor.commit();
    }

    /**
     * 获取string键值
     * @param key
     * @param defValue
     * @return
     */
    public static final String getString(String key, String defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getString(key, defValue);
    }

    /**
     * 存放string键值
     * @param key
     * @param value
     */
    public static final void putString(String key, String value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putString(key, value).commit();
    }

    /**
     * 获取整型
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getInt(key, defValue);
    }


    /**
     * 存储整型
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putInt(key, value).commit();
    }


    /**
     * 获取长整型
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String key, long defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getLong(key, defValue);

    }

    /**
     * 存储长整型
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putLong(key, value).commit();

    }

    /**
     * 获取浮点型
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String key, float defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getFloat(key, defValue);

    }

    /**
     * 存储浮点型
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putFloat(key, value).commit();

    }


    /**
     * 获取boolean类型
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getBoolean(key, defValue);

    }

    /**
     * 存储boolean类型
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putBoolean(key, value).commit();
    }

    /**
     * 获取集合
     * @param key
     * @param defValue
     * @return
     */
    public static final Set<String> getStringSet(String key, Set<String> defValue) {
        if (!isValidate() || key == null)
            return defValue;
        return sPreference.getStringSet(key, defValue);

    }

    /**
     * 移除key
     * @param key
     */
    public static final void remove(String key) {
        if (!isValidate() || key == null)
            return;
        sEditor.remove(key).commit();
    }

    /**
     * 存储集合
     * @param key
     * @param value
     */
    public static void putStringSet(String key, Set<String> value) {
        if (!isValidate() || key == null)
            return;
        sEditor.putStringSet(key, value).commit();
    }
}
