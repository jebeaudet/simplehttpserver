package com.jebeaudet.simplehttpserver.server;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	public NamedThreadFactory(String name) {
		group = Thread.currentThread().getThreadGroup();
		namePrefix = String.format("%s-%s-thread-", name, poolNumber.getAndIncrement());
	}

	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
		return thread;
	}
}
