package com.jebeaudet.simplehttpserver.http.constants;

public enum HttpMethod {
	GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE, CONNECT;

	public static HttpMethod fromString(String value) {
		for (HttpMethod httpMethod : HttpMethod.values()) {
			if (httpMethod.name().equalsIgnoreCase(value)) {
				return httpMethod;
			}
		}

		throw new IllegalArgumentException(String.format("Invalid HTTP method string '%s'.", value));
	}
}
