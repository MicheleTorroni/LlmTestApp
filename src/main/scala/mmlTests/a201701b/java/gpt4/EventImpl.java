package mmlTests.a201701b.java.gpt4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import mmlTests.a201701b.java.Event;
import mmlTests.a201701b.java.Trace;
import mmlTests.a201701b.java.TraceFactory;

public class EventImpl<X> implements Event<X> {

	private final int time;
	private final X value;

	public EventImpl(int time, X value) {
		this.time = time;
		this.value = value;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public X getValue() {
		return value;
	}

}
