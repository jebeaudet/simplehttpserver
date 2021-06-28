package com.jebeaudet.simplehttpserver.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jebeaudet.simplehttpserver.io.AcceptorThread;
import com.jebeaudet.simplehttpserver.lifecycle.Lifecycle;

public class SimpleHttpServer implements Lifecycle {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

	private HttpConfiguration httpConfiguration;

	private List<Lifecycle> components = new ArrayList<>();

	public SimpleHttpServer(HttpConfiguration httpConfiguration) {
		this.httpConfiguration = Objects.requireNonNull(httpConfiguration);
	}

	public void startComponent() {
		try {
			logger.info("Starting server on port '{}'.", httpConfiguration.getPort());
			var socketDispatcher = new SocketDispatcher(httpConfiguration.getNumberOfThreads(),
					httpConfiguration.getIdleTimeoutMs(), httpConfiguration.getClientTimeoutMs());
			addComponent(socketDispatcher);

			var acceptorThread = new AcceptorThread(httpConfiguration.getPort(), socketDispatcher);
			addComponent(acceptorThread);

			acceptorThread.run();
		} catch (Exception e) {
			throw new RuntimeException("Could not start server!", e);
		}
	}

	public void stopComponent() {
		logger.info("Stopping server.");
		components.forEach(component -> {
			try {
				component.stopComponent();
			} catch (Exception e) {
				logger.info("Error while shutting down server.", e);
			}
		});
	}

	private void addComponent(Lifecycle component) {
		components.add(component);
	}
}
