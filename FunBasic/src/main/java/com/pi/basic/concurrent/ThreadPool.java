package com.pi.basic.concurrent;




import com.pi.basic.concurrent.builder.AbstractBuilder;
import com.pi.basic.concurrent.builder.CachedBuilder;
import com.pi.basic.concurrent.builder.CustomBuilder;
import com.pi.basic.concurrent.builder.FixedBuilder;
import com.pi.basic.concurrent.builder.ScheduledBuilder;
import com.pi.basic.concurrent.builder.SingleBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @描述：     @并发库组件入口
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class ThreadPool {
	
	public static class Builder {
		/** 线程名称 */
		private String mName 		 			   	   = null;
		/** 线程类型 */
		private ThreadType mType 			   	   	   = null;
		/** 固定线程池  */
		private int mSize 			 			   	   = 1;
		/** 核心线程池大小 */
		private int mCorePoolSize    			   	   = 1;
		/** 最大线程池大小 */
		private int mMaximumPoolSize 			   = Integer.MAX_VALUE;
		/** 线程任务空闲保留时间 */
		private long mKeepAliveTime  			   = 60;
		/** 线程任务空闲保留时间单位 */
		private TimeUnit mUnit 		 		       = TimeUnit.SECONDS;
		/** 任务等待策略 */
		private BlockingQueue<Runnable> mWorkQueue   = new SynchronousQueue<Runnable>();
		/** 是否立即关闭线程池 */
		private boolean mImmediatelyShutdown	   = false;

		private AbstractBuilder<ExecutorService> mThreadPoolBuilder = null;

		public Builder(ThreadType type) {
			mType = type;
		}
		
		public Builder(ThreadType type, int size) {
			mType = type;
			mSize = size;
		}
		
		public Builder(ThreadType type,
				int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
			mType = type;
			mCorePoolSize = corePoolSize;
			mMaximumPoolSize = maximumPoolSize;
			mKeepAliveTime = keepAliveTime;
			mUnit = unit;
			mWorkQueue = workQueue;
		}


		public final Builder setImmediatelyShutdown(boolean mImmediatelyShutdown) {
			this.mImmediatelyShutdown = mImmediatelyShutdown;
			return this;
		}

		public static final Builder cached() {
			return new Builder(ThreadType.CACHED);
		}

		public static final Builder fixed(int size) {
			return new Builder(ThreadType.FIXED, size);
		}

		public static final Builder single() {
			return new Builder(ThreadType.SINGLE);
		}

		public static final Builder schedule(int size) {
			return new Builder(ThreadType.SCHEDULED, size);
		}
		
		public static final Builder custom(int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
			return new Builder(ThreadType.CUSTOM, corePoolSize,
					maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		public final Builder setName(String name) {
			mName = name;
			return this;
		}

		/**
		 * 构建一般线程
		 * @return
		 */
		public final AbstractBuilder<ExecutorService> builder() {
			innnerCreateThreadPoolBuilder();
			return mThreadPoolBuilder;
		}

		/**
		 * 构建调度线程
		 * @return
		 */
		public final ScheduledExecutorService scheduleBuilder() {
			innnerCreateThreadPoolBuilder();
			if (mThreadPoolBuilder.builder() instanceof ScheduledExecutorService) {
				return (ScheduledExecutorService)mThreadPoolBuilder.builder();
			}
			return null;
		}
		
		private final void innnerCreateThreadPoolBuilder() {
			switch (mType){
				//缓存
				case CACHED:
					mThreadPoolBuilder = new CachedBuilder().poolName(mName);
					break;
				//固定频率
				case FIXED:
					mThreadPoolBuilder = new FixedBuilder().
							setSize(mSize).poolName(mName);
					break;
				//调度
				case SCHEDULED:
					mThreadPoolBuilder = new ScheduledBuilder().poolName(mName);
					break;
				//单个
				case SINGLE:
					mThreadPoolBuilder = new SingleBuilder().poolName(mName);
					break;
				//自定义
				case CUSTOM:
					mThreadPoolBuilder = new CustomBuilder().corePoolSize(mCorePoolSize)
							.maximumPoolSize(mMaximumPoolSize).keepAliveTime(mKeepAliveTime).
									unit(mUnit).workQueue(mWorkQueue).poolName(mName);
					break;
			}
			mThreadPoolBuilder.setImmediatelyShutdown(mImmediatelyShutdown);
			mThreadPoolBuilder.builder();
		}
	}
}
