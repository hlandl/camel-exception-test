package org.camel.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class RouteSplit extends RouteBuilder {

	/**
	 * Creates a new RouteB.
	 */
	public RouteSplit() {
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
					Print.all("Split Error", exchange);
				}
			})
			.to("mock:splitError");

		from("direct:split")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("Split Start", exchange);
				}
			})
			.split().body()
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("Split Before Exc", exchange);
					}
				})
				.throwException(new Exception("Forced"))
			.end()
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("Split End", exchange);
				}
			})
			.to("mock:splitSuccess");

	}

}
