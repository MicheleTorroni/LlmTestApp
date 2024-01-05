package mmlTests.a201701b.java;

/**
 * An immutable representation of an event happened at a given discrete time, carrying a value 
 */
public interface Event<X> {
	
	int getTime();
	
	X getValue();
}
