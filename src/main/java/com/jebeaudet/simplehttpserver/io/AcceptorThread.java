package com.jebeaudet.simplehttpserver.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jebeaudet.simplehttpserver.lifecycle.Lifecycle;
import com.jebeaudet.simplehttpserver.server.SocketDispatcher;

public class AcceptorThread extends Thread implements Lifecycle {
	private static final Logger logger = LoggerFactory.getLogger(AcceptorThread.class);

	private ServerSocket serverSocket;
	private int port;
	private SocketDispatcher socketDispatcher;

	private volatile boolean running;

	public AcceptorThread(int port, SocketDispatcher socketDispatcher) {
		this.port = port;
		this.socketDispatcher = socketDispatcher;
	}

	@Override
	public void run() {
		try {
			var serverSocketFactory = new ServerSocketFactory();
			serverSocket = serverSocketFactory.createSocket(port);
			running = true;
		} catch (IOException e) {
			throw new RuntimeException("Could not start acceptor thread!", e);
		}

		while (running) {
			try {
				Socket clientSocket = serverSocket.accept();
				socketDispatcher.dispatchSocket(clientSocket);
			} catch (IOException e) {
				logger.info("Caught IOException in the acceptor thread, exiting.", e);
				running = false;
			}
		}
	}

	public void stopComponent() throws IOException {
		running = false;
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

}
