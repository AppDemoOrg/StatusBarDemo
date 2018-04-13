package com.pi.basic.arch.mvvm;

/**
 * @描述：     @V与VM关联接口
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public interface IView<VM extends IViewModel> {
    void setViewModel(VM viewModel);
}
