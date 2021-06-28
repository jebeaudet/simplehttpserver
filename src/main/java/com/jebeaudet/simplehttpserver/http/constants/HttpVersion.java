package com.jebeaudet.simplehttpserver.http.constants;

public enum HttpVersion {
	HTTP11("HTTP/1.1");

	private String value;

	HttpVersion(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public static HttpVersion fromString(String value) {
		for (HttpVersion httpVersion : HttpVersion.values()) {
			if (httpVersion.value.equalsIgnoreCase(value)) {
				return httpVersion;
			}
		}

		throw new IllegalArgumentException(String.format("Invalid HTTP version string '%s'.", value));
	}

	@Override
	public String toString() {
		return value;
	}
}
