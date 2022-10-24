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
				this.getCamelContext().addRoutes(new RouteA());
				this.getCamelContext().addRoutes(new RouteB());
			}
		};
	}

	@Test
	void testException() throws InterruptedException {
		getMockEndpoint("mock:resultA1").expectedMessageCount(0);
		getMockEndpoint("mock:resultA2").expectedMessageCount(0);
		getMockEndpoint("mock:resultA3").expectedMessageCount(0);
		getMockEndpoint("mock:resultB1").expectedMessageCount(0);
		getMockEndpoint("mock:resultB2").expectedMessageCount(1);

		template.sendBody("direct:a", "Test");

		assertMockEndpointsSatisfied();
	}

}
