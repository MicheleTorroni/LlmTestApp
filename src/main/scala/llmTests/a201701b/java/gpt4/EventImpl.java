package llmTests.a201701b.java.gpt4;

import llmTests.a201701b.java.Event;

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
