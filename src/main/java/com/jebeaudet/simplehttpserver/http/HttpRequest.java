package com.jebeaudet.simplehttpserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.jebeaudet.simplehttpserver.http.constants.HttpMethod;
import com.jebeaudet.simplehttpserver.http.constants.HttpVersion;

public class HttpRequest {
	private HttpMethod method;
	private HttpVersion version;
	private String requestUri;
	private Map<String, String> headers;
	private byte[] body;

	private HttpRequest(HttpMethod method, HttpVersion version, String requestUri, Map<String, String> headers,
			byte[] body) {
		this.method = method;
		this.version = version;
		this.requestUri = requestUri;
		this.headers = headers;
		this.body = body;
	}

	public HttpMethod method() {
		return method;
	}

	public HttpVersion version() {
		return version;
	}

	public String requestUri() {
		return requestUri;
	}

	public Map<String, String> headers() {
		return headers;
	}

	public byte[] body() {
		return body;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, headers, method, requestUri, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpRequest other = (HttpRequest) obj;
		return Objects.equals(body, other.body) && Objects.equals(headers, other.headers) && method == other.method
				&& Objects.equals(requestUri, other.requestUri) && version == other.version;
	}

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", version=" + version + ", requestUri=" + requestUri + ", headers="
				+ headers + ", body=" + Arrays.toString(body) + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private HttpMethod method;
		private HttpVersion version;
		private String requestUri;
		private Map<String, String> headers;
		private byte[] body;

		private Builder() {
		}

		public Builder method(HttpMethod method) {
			this.method = Objects.requireNonNull(method);
			return this;
		}

		public Builder version(HttpVersion version) {
			this.version = Objects.requireNonNull(version);
			return this;
		}

		public Builder requestLine(String requestUri) {
			this.requestUri = Objects.requireNonNull(requestUri);
			return this;
		}

		public Builder headers(Map<String, String> headers) {
			this.headers = Map.copyOf(headers);
			return this;
		}

		public Builder body(byte[] body) {
			this.body = body;
			return this;
		}

		public HttpRequest build() {
			return new HttpRequest(method, version, requestUri, headers, body);
		}
	}
}
