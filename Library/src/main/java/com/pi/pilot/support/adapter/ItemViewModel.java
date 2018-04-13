package com.pi.pilot.support.adapter;

import android.databinding.BaseObservable;

public class ItemViewModel<T> extends BaseObservable{
    public T item;
    public final void setItem(T item) {
        this.item = item;
        onAttachItem();
    }

    protected void onAttachItem(){

    }
}
