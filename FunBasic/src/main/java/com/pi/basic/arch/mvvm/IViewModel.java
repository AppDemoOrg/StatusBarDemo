package com.pi.basic.arch.mvvm;

/**
 * @描述：     @ViewModel业务接口
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public interface IViewModel<N extends INavigator> {

    void initialize();
    void setNavigator(N navigator);
}
