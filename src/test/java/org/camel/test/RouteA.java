package org.camel.test;

import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					Print.all("A Error", exchange);
				}
			})
			.to("mock:AError");

		from("direct:a")
			.doTry()
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("A Start", exchange);
					}
				})
				.to("direct:b")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						Print.all("A After", exchange);
					}
				})
				.to("mock:AOK")
			.endDoTry()
			.doCatch(Exception.class)
				.to("mock:ACatch")
			.end();

	}

}
