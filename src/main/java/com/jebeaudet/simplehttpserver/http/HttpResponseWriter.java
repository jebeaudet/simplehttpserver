package com.jebeaudet.simplehttpserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseWriter {
	private static final byte[] CRLF = "\r\n".getBytes(StandardCharsets.UTF_8);

	private OutputStream outputStream;

	public HttpResponseWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void write(HttpRequest request, HttpResponse response) throws IOException {
		String statusCode = String.format("%s %d OK \r\n", request.version(), response.statusCode());
		outputStream.write(statusCode.getBytes(StandardCharsets.UTF_8));

		Map<String, String> headersMap = addContentLength(response);
		String headers = headersMap.entrySet().stream()
				.map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
				.collect(Collectors.joining("\r\n"));
		outputStream.write(headers.getBytes(StandardCharsets.UTF_8));

		outputStream.write(CRLF);
		outputStream.write(CRLF);

		if (!isArrayEmpty(response.body())) {
			outputStream.write(response.body());
		}
		outputStream.flush();
	}

	private Map<String, String> addContentLength(HttpResponse response) {
		if (isArrayEmpty(response.body())) {
			return response.headers();
		}
		Map<String, String> headersMap = new HashMap<>(response.headers());
		headersMap.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(response.body().length));
		return headersMap;
	}

	private boolean isArrayEmpty(byte[] array) {
		return array == null || array.length == 0;
	}
}
