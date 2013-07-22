package com.bes.common.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class TestAppender extends AppenderSkeleton {
	private String padding; // »’÷æÃÓ≥‰”Ôæ‰

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	@Override
	protected void append(LoggingEvent event) {
		System.out.println("hello "+padding+ event.getMessage());
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
}
