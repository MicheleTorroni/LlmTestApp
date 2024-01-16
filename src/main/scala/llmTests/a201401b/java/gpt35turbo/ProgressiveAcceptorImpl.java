package llmTests.a201401b.java.gpt35turbo;

import llmTests.a201401b.java.Aggregator;
import llmTests.a201401b.java.ProgressiveAcceptor;
import llmTests.a201401b.java.ProgressiveFilter;

import java.util.ArrayList;
import java.util.List;

public class ProgressiveAcceptorImpl<X> implements ProgressiveAcceptor<X> {

    private List<X> sequence;
    private ProgressiveFilter<X> filter;
    private Aggregator<X> aggregator;
    private int size;

    public ProgressiveAcceptorImpl() {
        this.sequence = new ArrayList<>();
    }

    @Override
    public void setProgressiveFilter(ProgressiveFilter<X> filter) {
        this.filter = filter;
    }

    @Override
    public void setAggregator(Aggregator<X> aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative");
        }
        this.size = size;
    }

    @Override
    public boolean accept(int pos, X elem) {
        if (filter == null || aggregator == null || size == 0) {
            throw new IllegalStateException();
        }
        if (pos == 0) {
            sequence.clear();
        }
        boolean isNextOK = true;
        if (pos == sequence.size()) {
            if (pos >= size) {
                sequence.remove(0);
                pos--;
            }
            for (int i = 0; i < sequence.size(); i++) {
                X previous = sequence.get(i);
                if (!filter.isNextOK(previous, elem)) {
                    isNextOK = false;
                }
            }
            if (isNextOK) {
                sequence.add(elem);
            }
        } else if (pos < sequence.size()) {
            for (int i = pos; i < sequence.size(); i++) {
                sequence.remove(i);
            }
            accept(pos, elem);
        } else {
            throw new IllegalArgumentException();
        }
        return isNextOK;
    }

    @Override
    public X aggregateAll() {
        X result = null;
        if (sequence.size() > 0) {
            result = sequence.get(0);
            for (int i = 1; i < sequence.size(); i++) {
                X next = sequence.get(i);
                result = aggregator.aggregate(result, next);
            }
        }
        return result;
    }
}
