package com.jebeaudet.simplehttpserver.http;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

	private long idleTimeout;
	private long socketCreationTimestamp;

	private HttpRequestParser httpRequestParser;
	private HttpResponseWriter httpResponseWriter;
	private Socket socket;

	private int clientTimeout;

	public HttpRequestHandler(Socket socket, long idleTimeout, int clientTimeout) {
		this.socket = socket;
		this.idleTimeout = idleTimeout;
		this.socketCreationTimestamp = System.currentTimeMillis();
		this.clientTimeout = clientTimeout;
		try {
			httpRequestParser = new HttpRequestParser(socket.getInputStream());
			httpResponseWriter = new HttpResponseWriter(socket.getOutputStream());
		} catch (IOException e) {
			logger.info("Error processing the request, closing the socket.", e);
			closeSocketQuietly();
		}
	}

	@Override
	public void run() {
		try {
			socket.setSoTimeout(clientTimeout);
			while (socketIsAlive() && socketIsWithinIdleTimeout() && !Thread.currentThread().isInterrupted()) {
				if (socket.getInputStream().available() == 0) {
					Thread.sleep(50);
					continue;
				}
				resetSocketAndParser();

				HttpRequest request = httpRequestParser.parse();

				// Do something meaningful with the request!

				var response = new HttpResponse().withStatusCode(200).withBody(request.body()).withHeaders(Map.of(
						HttpHeaders.CONTENT_TYPE, request.headers().get(HttpHeaders.CONTENT_TYPE), "hello", "world"));
				httpResponseWriter.write(request, response);
			}
		} catch (IOException e) {
			logger.info("IOException processing the request, closing the socket.", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		closeSocketQuietly();
	}

	private void resetSocketAndParser() {
		socketCreationTimestamp = System.currentTimeMillis();
		httpRequestParser.reset();
	}

	private boolean socketIsWithinIdleTimeout() {
		return socketCreationTimestamp + idleTimeout > System.currentTimeMillis();
	}

	private boolean socketIsAlive() {
		return !socket.isClosed() && !socket.isInputShutdown() && !socket.isOutputShutdown();
	}

	private void closeSocketQuietly() {
		try {
			socket.close();
		} catch (IOException e1) {
			// ignore
		}
	}
}
