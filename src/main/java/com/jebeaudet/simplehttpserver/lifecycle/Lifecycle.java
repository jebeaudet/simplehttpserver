package com.jebeaudet.simplehttpserver.lifecycle;

public interface Lifecycle {
	default void startComponent() {
	}

	default void stopComponent() throws Exception {
	}
}
