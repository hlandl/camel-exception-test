package org.camel.test;

import org.apache.camel.Exchange;

public class Print {

	public static void all(String id, Exchange exchange) {
		System.out.println(".");
		System.out.println(id + ": Print ALL");
		System.out.println(id + ": Headers:");
		exchange.getIn().getHeaders().forEach((key, val) -> System.out.println(key + "=" + val));

		System.out.println(id + ": Properties:");
		exchange.getProperties().forEach((key, val) -> System.out.println(key + "=" + val));

		System.out.println(id + ": Exception:");
		System.out.println(exchange.getException());
		
		System.out.println("Transacted: " + exchange.getUnitOfWork().isTransacted());
		System.out.println("Exchange Pattern: " + exchange.getPattern().toString());
		//exchange pattern
		//transacted
	}
}
