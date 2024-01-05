package mmlTests.a201603a.javaTest.solMV;

import mmlTests.a201603a.javaTest.Bag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BagImpl<X>  implements Bag<X> {
    
    private final Map<X,Integer> map;
    
    //package protected.. accessible to FactoryImpl
    BagImpl(Map<X, Integer> map) {
        super();
        this.map = Collections.unmodifiableMap(map);
    }
    
    @Override
    public int numberOfCopies(X x) {
        return map.getOrDefault(x, 0);
    }

    @Override
    public int size() {
        return map.values().stream().collect(Collectors.summingInt(Integer::intValue));
    }

    @Override
    public List<X> toList() {
        return map.entrySet()
                  .stream()
                  .map(e -> Collections.nCopies(e.getValue(), e.getKey()))
                  .map(e -> e.stream())
                  .flatMap(x->x)
                  .collect(Collectors.toList());
    }
    
    

}
