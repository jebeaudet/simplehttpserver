package com.jebeaudet.simplehttpserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.jebeaudet.simplehttpserver.http.constants.HttpMethod;
import com.jebeaudet.simplehttpserver.http.constants.HttpVersion;
import com.jebeaudet.simplehttpserver.io.HttpRequestInputStreamReader;

public class HttpRequestParser {
	private static final Pattern SPACE_PATTERN = Pattern.compile(" ");

	private HttpRequestInputStreamReader httpRequestReader;
	private State state;
	private Integer contentLength = null;

	public HttpRequestParser(InputStream inputStream) {
		this.httpRequestReader = new HttpRequestInputStreamReader(inputStream);
		this.state = State.REQUEST_LINE;
	}

	public HttpRequest parse() throws IOException {
		var requestBuilder = HttpRequest.builder();
		Map<String, String> headers = new HashMap<>();
		String line = httpRequestReader.readLine();
		while (state != State.DONE && line != null) {
			switch (state) {
			case REQUEST_LINE:
				String[] elements = SPACE_PATTERN.split(line);
				requestBuilder.method(HttpMethod.fromString(elements[0]));
				requestBuilder.requestLine(elements[1]);
				requestBuilder.version(HttpVersion.fromString(elements[2]));
				state = State.HEADERS;
				line = httpRequestReader.readLine();
				break;
			case HEADERS:
				if (line.isEmpty()) {
					requestBuilder.headers(headers);
					contentLength = Optional.ofNullable(headers.get(HttpHeaders.CONTENT_LENGTH)).map(Integer::valueOf)
							.orElse(null);
					state = contentLength != null ? State.BODY : State.DONE;
					break;
				}
				int colonIndex = line.indexOf(":");
				headers.put(line.substring(0, colonIndex).toLowerCase(), line.substring(colonIndex + 1).trim());
				line = httpRequestReader.readLine();
				break;
			case BODY:
				requestBuilder.body(httpRequestReader.readBytes(contentLength));
				state = State.DONE;
				break;
			case DONE:
				break;
			}
		}
		return requestBuilder.build();
	}

	public void reset() {
		state = State.REQUEST_LINE;
		contentLength = null;
	}

	private enum State {
		REQUEST_LINE, HEADERS, BODY, DONE
	}
}
