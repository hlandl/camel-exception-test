package org.camel.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("B Error", exchange);
				}
			})
			.to("mock:BError");

		from("direct:b")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("B Before Error", exchange);
				}
			})
			.throwException(new IllegalArgumentException("Forced"))
			.to("mock:BOK");

	}

}
