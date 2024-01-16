package llmTests.a201706.java.gpt35turbo;

import llmTests.a201706.java.Sequence;
import llmTests.a201706.java.SequenceHelpers;
import llmTests.shared.java.Pair;

import java.util.List;
import java.util.function.BinaryOperator;

public class SequenceHelpersImpl implements SequenceHelpers {

	public <X> Sequence<X> of(X x) {
		return new Sequence<X>() {
			@Override
			public X nextElement() {
				return x;
			}
		};
	}

	public <X> Sequence<X> cyclic(List<X> l) {
		return new Sequence<X>() {

			private int index = 0;

			@Override
			public X nextElement() {
				X elem = l.get(index);
				index = (index + 1) % l.size();
				return elem;
			}
		};
	}

	public Sequence<Integer> incrementing(int start, int increment) {
		return new Sequence<Integer>() {

			private int element = start;

			@Override
			public Integer nextElement() {
				int elem = element;
				element += increment;
				return elem;
			}
		};
	}

	public <X> Sequence<Pair<X, Integer>> zip(Sequence<X> input) {
		return new Sequence<Pair<X, Integer>>() {

			private int index = 0;

			@Override
			public Pair<X, Integer> nextElement() {
				X elem = input.nextElement();
				int currentIndex = index++;
				return new Pair<X, Integer>(elem, currentIndex);
			}
		};
	}

	public <X> Sequence<X> accumulating(Sequence<X> input, BinaryOperator<X> op) {
		return new Sequence<X>() {

			private X current = input.nextElement();

			@Override
			public X nextElement() {
				X elem = current;
				current = op.apply(current, input.nextElement());
				return elem;
			}
		};
	}
}
