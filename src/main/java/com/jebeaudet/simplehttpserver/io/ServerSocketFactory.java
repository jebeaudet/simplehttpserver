package com.jebeaudet.simplehttpserver.io;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactory {

	public ServerSocket createSocket(int port) throws IOException {
		var socket = new ServerSocket(port, 200);
		socket.setSoTimeout(0);

		return socket;
	}
}
