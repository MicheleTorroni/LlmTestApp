package llmTests.a201706.java.gpt4;

import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

import llmTests.a201706.java.Sequence;
import llmTests.a201706.java.SequenceHelpers;
import llmTests.shared.java.Pair;

public class SequenceHelpersImpl implements SequenceHelpers {
    public <X> Sequence<X> of(X x) {
        return new Sequence<X>(){
            public X nextElement() {
                return x;
            }
        };
    }

    public <X> Sequence<X> cyclic(List<X> l) {
        return new Sequence<X>(){
            private Iterator<X> it = l.iterator();
            public X nextElement() {
                if (!it.hasNext()) it = l.iterator();
                return it.next();
            }
        };
    }

    public Sequence<Integer> incrementing(int start, int increment) {
        return new Sequence<Integer>(){
            private int current = start;
            public Integer nextElement() {
                int old = current;
                current += increment;
                return old;
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
        return new Sequence<X>(){
            private X accumulated = input.nextElement();
            public X nextElement() {
                X old = accumulated;
                accumulated = op.apply(accumulated, input.nextElement());
                return old;
            }
        };
    }
}