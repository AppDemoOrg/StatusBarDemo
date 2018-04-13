package com.pi.basic.concurrent.builder;



import com.pi.basic.concurrent.ThreadType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @描述：     @自定义线程池
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class CustomBuilder extends AbstractBuilder<ExecutorService> {
	/** 核心线程池大小 */
	private int mCorePoolSize = 1;
	/** 最大线程池大小 */
	private int mMaximumPoolSize = Integer.MAX_VALUE;
	/** 线程任务空闲保留时间 */
	private long mKeepAliveTime = 60;
	/** 线程任务空闲保留时间单位 */
	private TimeUnit mUnit = TimeUnit.SECONDS;
	/** 任务等待策略 */
	private BlockingQueue<Runnable> mWorkQueue = new SynchronousQueue<Runnable>();
    
	@Override
	protected ExecutorService create() {
		return new ThreadPoolExecutor(mCorePoolSize,
				mMaximumPoolSize, mKeepAliveTime, mUnit, mWorkQueue);
	}
	
	@Override
	protected ThreadType getType() {
		return ThreadType.CUSTOM;
	}
	
	public CustomBuilder corePoolSize(int corePoolSize) {
		mCorePoolSize = corePoolSize;
		return this;
	}

	public CustomBuilder maximumPoolSize(int maximumPoolSize) {
		mMaximumPoolSize = maximumPoolSize;
		return this;
	}

	public CustomBuilder keepAliveTime(long keepAliveTime) {
		mKeepAliveTime = keepAliveTime;
		return this;
	}

	public CustomBuilder unit(TimeUnit unit) {
		mUnit = unit;
		return this;
	}

	public CustomBuilder workQueue(BlockingQueue<Runnable> workQueue) {
		mWorkQueue = workQueue;
		return this;
	}
}
