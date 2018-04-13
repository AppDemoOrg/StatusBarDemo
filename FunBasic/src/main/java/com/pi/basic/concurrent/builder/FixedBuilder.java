package com.pi.basic.concurrent.builder;



import com.pi.basic.concurrent.ThreadType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述：     @固定大小线程池
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class FixedBuilder extends AbstractBuilder<ExecutorService> {
	/** 固定线程池  */
	private int mSize = 1;
	
	@Override
	protected ExecutorService create() {
		return Executors.newFixedThreadPool(mSize);
	}

	@Override
	protected ThreadType getType() {
		return ThreadType.FIXED;
	}
	
	public FixedBuilder setSize(int size) {
		mSize = size;
		return this;
	}
}
