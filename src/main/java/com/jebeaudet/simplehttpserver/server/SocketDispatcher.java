package com.jebeaudet.simplehttpserver.server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jebeaudet.simplehttpserver.http.HttpRequestHandler;
import com.jebeaudet.simplehttpserver.lifecycle.Lifecycle;

public class SocketDispatcher implements Lifecycle {
	private ExecutorService executorService;
	private long idleTimeout;
	private int clientTimeout;

	public SocketDispatcher(int numberOfThreads, long idleTimeout, int clientTimeout) {
		this.executorService = Executors.newFixedThreadPool(numberOfThreads,
				new NamedThreadFactory("simplehttpserver"));
		this.idleTimeout = idleTimeout;
		this.clientTimeout = clientTimeout;
	}

	public void dispatchSocket(Socket socket) {
		HttpRequestHandler handler = new HttpRequestHandler(socket, idleTimeout, clientTimeout);
		executorService.submit(handler);
	}

	@Override
	public void stopComponent() throws Exception {
		executorService.shutdownNow();
	}
}
