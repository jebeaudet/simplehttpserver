package com.jebeaudet.simplehttpserver.server;

public class HttpConfiguration {
	private int port;
	private int numberOfThreads;
	private long idleTimeoutMs;
	private int clientTimeoutMs;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	public long getIdleTimeoutMs() {
		return idleTimeoutMs;
	}

	public void setIdleTimeoutMs(long idleTimeoutMs) {
		this.idleTimeoutMs = idleTimeoutMs;
	}

	public int getClientTimeoutMs() {
		return clientTimeoutMs;
	}

	public void setClientTimeoutMs(int clientTimeoutMs) {
		this.clientTimeoutMs = clientTimeoutMs;
	}
}
