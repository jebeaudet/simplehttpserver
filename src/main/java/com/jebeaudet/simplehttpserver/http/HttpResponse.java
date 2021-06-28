package com.jebeaudet.simplehttpserver.http;

import java.util.Map;

public class HttpResponse {
	private byte[] body;
	private Map<String, String> headers;
	private int statusCode;

	public int statusCode() {
		return statusCode;
	}

	public HttpResponse withStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public byte[] body() {
		return body;
	}

	public HttpResponse withBody(byte[] body) {
		this.body = body;
		return this;
	}

	public Map<String, String> headers() {
		return headers;
	}

	public HttpResponse withHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}
}
