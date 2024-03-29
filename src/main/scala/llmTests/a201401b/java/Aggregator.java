package llmTests.a201401b.java;

/**
 * A functional interface, with a method that takes two elements and combine/aggregate them into one
 * E.g., it could be the sum of the numbers, or concatenation of two strings
 */

public interface Aggregator<X> {
	
	X aggregate(X one, X two);
}
