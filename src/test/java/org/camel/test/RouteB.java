package org.camel.test;

import org.apache.camel.builder.RouteBuilder;

public class RouteB extends RouteBuilder {

	/**
	 * Creates a new RouteB.
	 */
	public RouteB() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	//@formatter:off
	public void configure() throws Exception {

		onException()
			.handled(true)
			.logStackTrace(false)
			.logExhausted(false)
			.to("mock:resultB2");

		from("direct:b")
			.throwException(new IllegalArgumentException("Forced"))
			.to("mock:resultB1");

	}

}
