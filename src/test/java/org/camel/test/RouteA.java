package org.camel.test;

import org.apache.camel.builder.RouteBuilder;

public class RouteA extends RouteBuilder {

	/**
	 * Creates a new RouteA.
	 */
	public RouteA() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	//@formatter:off
	public void configure() throws Exception {

		onException()
			.maximumRedeliveries(0)
			.handled(false)
			.logStackTrace(false)
			.logExhausted(false)
			.to("mock:resultA3");

		from("direct:a")
			.doTry()
				.to("direct:b")
				.to("mock:resultA1")
			.endDoTry()
			.doCatch(Exception.class)
				.to("mock:resultA2")
			.end();

	}

}
