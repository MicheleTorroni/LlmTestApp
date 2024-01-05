package mmlTests.a201603a.javaTest.gpt4;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import mmlTests.a201603a.javaTest.Bag;
import mmlTests.a201603a.javaTest.BagFactory;

public class BagFactoryImpl implements BagFactory {

    @Override
    public <X> Bag<X> empty() {
        return new BagImpl<X>(new ArrayList<>());
    }

    @Override
    public <X> Bag<X> fromSet(Set<X> set) {
        return new BagImpl<X>(new ArrayList<>(set));
    }

    @Override
    public <X> Bag<X> fromList(List<X> list) {
        return new BagImpl<X>(list);
    }

    @Override
    public <X> Bag<X> bySupplier(Supplier<X> supplier, int nElements, ToIntFunction<X> copies) {
        List<X> list = new ArrayList<>();
        IntStream.range(0, nElements).forEach(i -> {
            X element = supplier.get();
            IntStream.range(0, copies.applyAsInt(element)).forEach(j -> list.add(element));
        });
        return new BagImpl<X>(list);
    }

    @Override
    public <X> Bag<X> byIteration(X first, UnaryOperator<X> next, int nElements, ToIntFunction<X> copies) {
        List<X> list = new ArrayList<>();
        X element = first;
        for (int i = 0; i < nElements; i++) {
            int copy = copies.applyAsInt(element);
            for (int j = 0; j < copy; j++) {
                list.add(element);
            }
            element = next.apply(element);
        }
        return new BagImpl<X>(list);
    }

}
