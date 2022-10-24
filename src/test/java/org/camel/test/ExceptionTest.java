package org.camel.test;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

class ExceptionTest extends CamelTestSupport {

	@Override
	protected RoutesBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				//@formatter:off
				onException(Exception.class)
					.handled(true)
					.to("mock:result3");

				from("direct:a")
					.doTry()
						.to("direct:b")
						.to("mock:result1")
					.endDoTry()
					.doCatch(Exception.class)
						.to("mock:result2")
					.end();

				from("direct:b")
					.onException(Exception.class)
						.handled(true)
						.to("mock:result4")
					.end()
					.throwException(new IllegalArgumentException("Forced"))
					.to("mock:result5");
				//@formatter:on
			}
		};
	}

	@Test
	void testException() throws InterruptedException {
		getMockEndpoint("mock:result1").expectedMessageCount(0);
		getMockEndpoint("mock:result2").expectedMessageCount(0);
		getMockEndpoint("mock:result3").expectedMessageCount(0);
		getMockEndpoint("mock:result4").expectedMessageCount(1);
		getMockEndpoint("mock:result5").expectedMessageCount(0);

		template.sendBody("direct:a", "Test");

		assertMockEndpointsSatisfied();
	}

}
