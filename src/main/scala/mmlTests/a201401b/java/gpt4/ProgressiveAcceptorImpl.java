package mmlTests.a201401b.java.gpt4;

import java.util.ArrayList;
import java.util.List;

import mmlTests.a201401b.java.Aggregator;
import mmlTests.a201401b.java.ProgressiveAcceptor;
import mmlTests.a201401b.java.ProgressiveFilter;

public class ProgressiveAcceptorImpl<X> implements ProgressiveAcceptor<X> {

    private List<X> list;
    private Aggregator<X> aggregator;
    private ProgressiveFilter<X> filter;
    private int size;

    public ProgressiveAcceptorImpl() {
        list = new ArrayList<>();
    }

    @Override
    public void setProgressiveFilter(ProgressiveFilter<X> filter) {
        if (filter == null) {
            throw new NullPointerException();
        }
        this.filter = filter;
    }

    @Override
    public void setAggregator(Aggregator<X> aggregator) {
        if (aggregator == null) {
            throw new NullPointerException();
        }
        this.aggregator = aggregator;
    }

    @Override
    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
    }

    @Override
    public boolean accept(int pos, X elem) {
        if (aggregator == null || filter == null) {
            throw new IllegalStateException();
        }
        if (pos < 0 || pos > size || pos > list.size()) {
            return false;
        }
        if (!list.isEmpty() && pos > 0) {
            if (!filter.isNextOK(list.get(pos - 1), elem)) {
                return false;
            }
        }
        if (pos < list.size()) {
            list.subList(pos, list.size()).clear();
        }
        list.add(elem);
        return true;
    }

    @Override
    public X aggregateAll() {
        if (list.isEmpty()) throw new IllegalStateException();
        X result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = aggregator.aggregate(result, list.get(i));
        }
        return result;
    }
}
