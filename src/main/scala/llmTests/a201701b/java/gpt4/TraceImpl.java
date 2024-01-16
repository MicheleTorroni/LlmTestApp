package llmTests.a201701b.java.gpt4;

import llmTests.a201701b.java.Event;
import llmTests.a201701b.java.Trace;

import java.util.Iterator;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class TraceImpl<X> implements Trace<X> {

	private final Iterator<Event<X>> iterator;

	public TraceImpl(Iterator<Event<X>> iterator) {
		this.iterator = iterator;
	}

	@Override
	public Optional<Event<X>> nextEvent() {
		if (iterator.hasNext()) {
			return Optional.of(iterator.next());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Iterator<Event<X>> iterator() {
		return iterator;
	}

	@Override
	public void skipAfter(int time) {
		while (iterator.hasNext()) {
			Event<X> event = iterator.next();
			if (event.getTime() > time) {
				break;
			}
		}
	}

	@Override
	public Trace<X> combineWith(Trace<X> trace) {
		List<Event<X>> combinedEvents = new ArrayList<>();
		while (iterator.hasNext()) {
			combinedEvents.add(iterator.next());
		}
		Iterator<Event<X>> traceIterator = trace.iterator();
		while (traceIterator.hasNext()) {
			combinedEvents.add(traceIterator.next());
		}
		return new TraceImpl<>(combinedEvents.iterator());
	}

	@Override
	public Trace<X> dropValues(X value) {
		List<Event<X>> filteredEvents = new ArrayList<>();
		while (iterator.hasNext()) {
			Event<X> event = iterator.next();
			if (!event.getValue().equals(value)) {
				filteredEvents.add(event);
			}
		}
		return new TraceImpl<>(filteredEvents.iterator());
	}

}
