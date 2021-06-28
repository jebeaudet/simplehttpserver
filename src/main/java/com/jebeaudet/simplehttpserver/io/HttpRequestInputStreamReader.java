package com.jebeaudet.simplehttpserver.io;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestInputStreamReader {
	private static final int CR = 13;
	private static final int LF = 10;

	private InputStream inputStream;

	public HttpRequestInputStreamReader(InputStream in) {
		this.inputStream = in;
	}

	public byte[] readBytes(int numberOfBytes) throws IOException {
		return inputStream.readNBytes(numberOfBytes);
	}

	public String readLine() throws IOException {
		StringBuilder sb = new StringBuilder("");
		inputStream.available();
		while (true) {
			int character = inputStream.read();
			switch (character) {
			case CR:
				continue;
			case LF:
				return sb.toString();
			case -1:
				throw new RuntimeException("Reached end of stream without a CRLF!");
			default:
				sb.append((char) character);
			}
		}
	}
}
