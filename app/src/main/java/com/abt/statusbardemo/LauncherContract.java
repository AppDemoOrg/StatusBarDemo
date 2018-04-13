package com.abt.statusbardemo;

/**
 * @描述：     @LauncherContract
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-09
 */
public class LauncherContract {

    /**
     * 视图接口
     */
    public interface ILauncher {

        void clickCamera();

        void clickGallery();

        void clickLive();

        void clickSetting();
    }
}
