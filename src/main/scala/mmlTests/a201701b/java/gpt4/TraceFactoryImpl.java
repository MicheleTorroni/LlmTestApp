package mmlTests.a201701b.java.gpt4;

import mmlTests.a201701b.java.Event;
import mmlTests.a201701b.java.Trace;
import mmlTests.a201701b.java.TraceFactory;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
public class TraceFactoryImpl implements TraceFactory {

	@Override
	public <X> Trace<X> fromSuppliers(Supplier<Integer> sdeltaTime, Supplier<X> svalue, int size) {
		List<Event<X>> events = new ArrayList<>();
		int currentTime = 0;
		for (int i = 0; i < size; i++) {
			int deltaTime = sdeltaTime.get();
			X value = svalue.get();
			Event<X> event = new EventImpl<>(currentTime, value);
			events.add(event);
			currentTime += deltaTime;
		}
		return new TraceImpl<>(events.iterator());
	}

	@Override
	public <X> Trace<X> constant(Supplier<Integer> sdeltaTime, X value, int size) {
		Supplier<X> svalue = () -> value;
		return fromSuppliers(sdeltaTime, svalue, size);
	}

	@Override
	public <X> Trace<X> discrete(Supplier<X> svalue, int size) {
		Supplier<Integer> sdeltaTime = new Supplier<Integer>() {
			private int currentTime = 0;

			@Override
			public Integer get() {
				int deltaTime = currentTime;
				currentTime++;
				return deltaTime;
			}
		};
		return fromSuppliers(sdeltaTime, svalue, size);
	}

}
