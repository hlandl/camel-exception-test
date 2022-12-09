package org.camel.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class RouteSplitCaller extends RouteBuilder {

	/**
	 * Creates a new RouteSplitCaller.
	 */
	public RouteSplitCaller() {
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
			.to("mock:splitCallerError");

		from("direct:splitcaller")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("SC Start", exchange);
				}
			})
			.doTry()
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("SC Try", exchange);
					}
				})
				.to("direct:split")
				.to("mock:splitCallerSuccess")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("SC Try-End", exchange);
					}
				})
			.endDoTry()
			.doCatch(Exception.class)
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("SC Catch", exchange);
					}
				})
				.to("mock:splitCallerCatch")
			.end();

	}

}
