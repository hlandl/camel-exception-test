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
				this.getContext().addRoutes(new RouteA());
				this.getContext().addRoutes(new RouteB());
				this.getContext().addRoutes(new RouteSplit());
				this.getContext().addRoutes(new RouteSplitCaller());

				
//				this.getCamelContext().addRoutes(new RouteA());
//				this.getCamelContext().addRoutes(new RouteB());
			}
		};
	}

	@Test
	void testException() throws InterruptedException {
		getMockEndpoint("mock:AOK").expectedMessageCount(0);
		getMockEndpoint("mock:ACatch").expectedMessageCount(0);
		getMockEndpoint("mock:AError").expectedMessageCount(0);
		getMockEndpoint("mock:BOK").expectedMessageCount(0);
		getMockEndpoint("mock:BError").expectedMessageCount(1);

		template.sendBody("direct:a", "Test");

		assertMockEndpointsSatisfied();
	}

	@Test
	void testSplit() throws InterruptedException {
		getMockEndpoint("mock:splitCallerSuccess").expectedMessageCount(1);
		getMockEndpoint("mock:splitCallerCatch").expectedMessageCount(0);
		getMockEndpoint("mock:splitCallerError").expectedMessageCount(0);
		getMockEndpoint("mock:splitSuccess").expectedMessageCount(1);
		getMockEndpoint("mock:splitError").expectedMessageCount(3);

		template.sendBody("direct:splitcaller", "A,B,C");

		assertMockEndpointsSatisfied();
	}
}
