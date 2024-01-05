package mmlTests.a201603a.javaTest.gpt4;

import java.util.*;

import mmlTests.a201603a.javaTest.Bag;


class BagImpl<X> implements Bag<X> {
    private final List<X> list;

    public BagImpl(List<X> list) {
        this.list = list;
    }

    @Override
    public int numberOfCopies(X x) {
        return (int) list.stream().filter(element -> element.equals(x)).count();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<X> toList() {
        return new ArrayList<X>(list);
    }
}
