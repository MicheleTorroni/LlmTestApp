package llmTests.a201603a.java.gpt35turbo;

import llmTests.a201603a.java.Bag;
import llmTests.a201603a.java.BagFactory;
import java.util.*;
import java.util.function.*;

public class BagFactoryImpl implements BagFactory {
    public <X> Bag<X> empty() {
        return new BagImpl<X>(new HashMap<X,Integer>());
    }

    public <X> Bag<X> fromSet(Set<X> set) {
        Map<X, Integer> map = new HashMap<X, Integer>();
        for (X element : set) {
            map.put(element, 1);
        }
        return new BagImpl<X>(map);
    }

    public <X> Bag<X> fromList(List<X> list) {
        Map<X, Integer> map = new HashMap<X, Integer>();
        for (X element : list) {
            int count = map.getOrDefault(element, 0);
            map.put(element, count + 1);
        }
        return new BagImpl<X>(map);
    }

    public <X> Bag<X> bySupplier(Supplier<X> supplier, int nElements, ToIntFunction<X> copies) {
        Map<X, Integer> map = new HashMap<X, Integer>();
        for (int i = 0; i < nElements; i++) {
            X element = supplier.get();
            int count = copies.applyAsInt(element);
            map.put(element, count);
        }
        return new BagImpl<X>(map);
    }

    public <X> Bag<X> byIteration(X first, UnaryOperator<X> next, int nElements, ToIntFunction<X> copies) {
        Map<X, Integer> map = new HashMap<X, Integer>();
        X element = first;
        for (int i = 0; i < nElements; i++) {
            int count = copies.applyAsInt(element);
            map.put(element, count);
            element = next.apply(element);
        }
        return new BagImpl<X>(map);
    }
}
