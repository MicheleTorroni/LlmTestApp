package mmlTests.a201401b.java.gpt35turbo;

import java.util.ArrayList;
import java.util.List;

import mmlTests.a201401b.java.Aggregator;
import mmlTests.a201401b.java.ProgressiveAcceptor;
import mmlTests.a201401b.java.ProgressiveFilter;

public class ProgressiveAcceptorImpl<X> implements ProgressiveAcceptor<X> {

    private ProgressiveFilter<X> filter;
    private Aggregator<X> aggregator;
    private int size;
    private List<X> sequence;

    public ProgressiveAcceptorImpl() {
        sequence = new ArrayList<>();
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
        this.size = size;
    }

    @Override
    public boolean accept(int pos, X elem) {
        if (filter == null || size == 0) {
            throw new IllegalStateException();
        }
        if (pos > sequence.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (pos < sequence.size()) {
            sequence.subList(pos, sequence.size()).clear();
        }
        if (pos >= sequence.size()) {
            sequence.add(elem);
        } else {
            sequence.set(pos, elem);
        }
        return filter.isNextOK(sequence.get(pos - 1), elem);
    }

    @Override
    public X aggregateAll() {
        X aggregate = null;
        for (X elem : sequence) {
            if (aggregate == null) {
                aggregate = elem;
            } else {
                aggregate = aggregator.aggregate(aggregate, elem);
            }
        }
        return aggregate;
    }

}
