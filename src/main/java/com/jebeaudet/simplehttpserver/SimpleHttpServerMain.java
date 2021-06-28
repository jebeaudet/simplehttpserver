package com.jebeaudet.simplehttpserver;

import java.util.concurrent.TimeUnit;

import com.jebeaudet.simplehttpserver.server.HttpConfiguration;
import com.jebeaudet.simplehttpserver.server.SimpleHttpServer;

public class SimpleHttpServerMain {

	public static void main(String[] args) throws Exception {
		var httpConfiguration = new HttpConfiguration();
		httpConfiguration.setPort(8765);
		httpConfiguration.setNumberOfThreads(100);
		httpConfiguration.setIdleTimeoutMs(TimeUnit.SECONDS.toMillis(10));
		httpConfiguration.setClientTimeoutMs((int) TimeUnit.SECONDS.toMillis(5));

		var httpServer = new SimpleHttpServer(httpConfiguration);

		httpServer.startComponent();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stopComponent()));
	}
}
