package com.pi.pilot.common.system;

/**
 * @描述：     @获取系统时间与监听电池电量监听器
 * @作者：     @蒋诗朋
 * @创建时间： @2017-12-01
 */
public interface NetworkChangeListener {

    /**
     * 网络状态改变
     */
    void onNetworkChanged();
}
