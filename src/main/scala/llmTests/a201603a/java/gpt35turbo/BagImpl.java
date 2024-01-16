package llmTests.a201603a.java.gpt35turbo;

import java.util.*;

import llmTests.a201603a.java.Bag;
class BagImpl<X> implements Bag<X> {
    private final Map<X, Integer> bag;

    public BagImpl(Map<X, Integer> bag) {
        this.bag = bag;
    }

    public int numberOfCopies(X x) {
        return bag.getOrDefault(x, 0);
    }

    public int size() {
        int size = 0;
        for (int count : bag.values()) {
            size += count;
        }
        return size;
    }

    public List<X> toList() {
        List<X> list = new ArrayList<X>();
        for (Map.Entry<X, Integer> entry : bag.entrySet()) {
            X element = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                list.add(element);
            }
        }
        return list;
    }
}
