package com.pi.basic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @描述： @属性文件工具类
 * @作者： @蒋诗朋
 * @创建时间： @2017-04-25
 */
public final class PropertiesUtil {

    /**
     * 加载属性文件
     * @param filepath
     * @return
     */
    private static final Properties read(String filepath) {
        FileInputStream fis = null;
        Properties props    = new Properties();
        try {
            fis = new FileInputStream(new File(filepath));
            props.load(fis);
            CloseUtil.closeIO(fis);
        } catch (IOException e) {
            final File file   = new File(filepath);
            final File parent = file.getParentFile();
            if(!parent.exists()) parent.mkdirs();
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return props;
    }

    /**
     * 设置属性
     * @param filepath
     * @param p
     */
    private static final void setProps(String filepath,Properties p) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(filepath));
            p.store(fos, null);
            fos.flush();
            CloseUtil.closeIO(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外提供的保存key value方法
     * @param key
     * @param value
     */
    public static final void set(String filepath,String key, String value) {
        Properties props = read(filepath);
        props.setProperty(key, value);
        setProps(filepath,props);
    }

    /**
     * 批量保存
     * @param filepath
     * @param map
     */
    public static final void set(String filepath,HashMap<String,Object> map){
        Properties props = read(filepath);
        for(Map.Entry<String,Object> entry:map.entrySet()){
            props.setProperty(entry.getKey(),entry.getKey());
        }
        setProps(filepath,props);
    }

    /**
     * 获取属性
     * @param filepath
     * @param key
     * @return
     */
    public static final String get(String filepath,String key) {
        Properties props = read(filepath);
        return (props != null) ? props.getProperty(key) : null;
    }

    /**
     * 获取全部属性
     * @param filepath
     * @param key
     * @return
     */
    public static final HashMap<String,String> get(String filepath,String ...key){
        Properties props = read(filepath);
        if(null != props){
            final HashMap<String,String> map = new HashMap<>();
            for(String k:key){
                map.put(k,props.getProperty(k));
            }
            return map;
        }
        return null;
    }


}
