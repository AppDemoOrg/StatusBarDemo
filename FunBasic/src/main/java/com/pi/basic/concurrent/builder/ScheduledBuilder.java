package com.pi.basic.concurrent.builder;



import com.pi.basic.concurrent.ThreadType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @描述：     @调度线程池
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class ScheduledBuilder extends AbstractBuilder<ExecutorService> {
	/** 固定线程池大小 */
	private int mSize = 1;
	
    protected ScheduledExecutorService mExecutorService = null;
    
	@Override
	protected ScheduledExecutorService create() {
		return Executors.newScheduledThreadPool(mSize);
	}
	
	@Override
	protected ThreadType getType() {
		return ThreadType.SCHEDULED;
	}
	
	@Override
	public ScheduledExecutorService builder() {
		if (mThreadPoolMap.get(getType() + "_" + mPoolName) != null) {
			mExecutorService = (ScheduledExecutorService)mThreadPoolMap.
					get(getType() + "_" + mPoolName);
		} else {
			mExecutorService = create();
			mThreadPoolMap.put(getType() + "_" + mPoolName, mExecutorService);
		}
		return mExecutorService;
	}
	
	public ScheduledExecutorService getExecutorService() {
		return mExecutorService;
	}
	
	public ScheduledBuilder size(int size) {
		this.mSize = size;
		return this;
	}
}
