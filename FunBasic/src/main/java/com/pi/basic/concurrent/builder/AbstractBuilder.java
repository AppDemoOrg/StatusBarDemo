package com.pi.basic.concurrent.builder;




import com.pi.basic.concurrent.ThreadType;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @描述：     @基类线程池
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public abstract class AbstractBuilder<T extends ExecutorService> {
	protected static Map<String, ExecutorService> mThreadPoolMap = new ConcurrentHashMap<String, ExecutorService>();
	protected ExecutorService mExecutorService = null;
	protected String mPoolName 				  = "default";
	/** 是否立即关闭线程池 */
	private boolean mImmediatelyShutdown	  = false;
	protected abstract T create();
	protected abstract ThreadType getType();


	/**
	 * 构建线程执行者
	 * @return
	 */
	public  ExecutorService builder() {
		if (mThreadPoolMap.get(getType() + "_" + mPoolName) != null) {
			mExecutorService = mThreadPoolMap.get(getType() + "_" + mPoolName);
		} else {
			mExecutorService = create();
			mThreadPoolMap.put(getType() + "_" + mPoolName, mExecutorService);
		}
		return mExecutorService;
	}

	/**
	 * 执行一个异步任务
	 */
	public final void execute(final Runnable runnable){
		if(null != mExecutorService){
			try {
				mExecutorService.execute(runnable);
				if (mImmediatelyShutdown) {
					mExecutorService.shutdown();
					mThreadPoolMap.remove(getType() + "_" + mPoolName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 提交异步任务
	 * @param callable
	 * @return
	 */
	public final Future<?> execute(final Callable<?> callable){
		if(null != mExecutorService){
			try {
				final Future<?> future = mExecutorService.submit(callable);
				if (mImmediatelyShutdown) {
					mExecutorService.shutdown();
					mThreadPoolMap.remove(getType() + "_" + mPoolName);
				}
				return future;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 线程池的名称
	 * @param poolName
	 * @return
	 */
	public final AbstractBuilder<T> poolName(String poolName) {
		if (poolName != null && poolName.length() > 0) {
			mPoolName = poolName;
		}
		return this;
	}

	/**
	 * 设置是否立即关闭线程服务
	 * @param immediatelyShutdown
	 */
	public final AbstractBuilder<T> setImmediatelyShutdown(boolean immediatelyShutdown) {
		this.mImmediatelyShutdown = immediatelyShutdown;
		return this;
	}
}
