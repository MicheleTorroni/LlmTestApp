package llmTests.a202003c.java.gpt4;

import llmTests.a202003c.java.Table;
import llmTests.a202003c.java.TableFactory;
import llmTests.shared.java.*;

import java.util.*;
import java.util.function.BiFunction;

public class TableFactoryImpl implements TableFactory {

    @Override
    public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
        final Map<R, Map<C, V>> rowMap = new HashMap<>();
        for(Pair<R,C> pair : map.keySet()){
            rowMap.computeIfAbsent(pair.getFst(), k -> new HashMap<>()).put(pair.getSnd(), map.get(pair));
        }
        return new TableImpl<>(rowMap);
    }

    @Override
    public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
        final Map<R, Map<C, V>> rowMap = new HashMap<>();
        for(R row : rows){
            for(C column : columns){
                rowMap.computeIfAbsent(row, k -> new HashMap<>()).put(column, valueFunction.apply(row, column));
            }
        }
        return new TableImpl<>(rowMap);
    }

    @Override
    public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {
        final Map<G, Map<G, Boolean>> rowMap = new HashMap<>();
        for(Pair<G,G> edge : edges){
            rowMap
                    .computeIfAbsent(edge.getFst(), k -> new HashMap<>())
                    .put(edge.getSnd(), true);
        }
        return new TableImpl<>(rowMap);
    }

    @Override
    public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
        final Map<Integer, Map<Integer, V>> rowMap = new HashMap<>();
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < values[i].length; j++){
                rowMap
                        .computeIfAbsent(i, k -> new HashMap<>())
                        .put(j, values[i][j]);
            }
        }
        return new TableImpl<>(rowMap);
    }


    class TableImpl<R, C, V> implements Table<R, C, V> {

        private final Map<R, Map<C, V>> rowMap;

        TableImpl(Map<R, Map<C, V>> rowMap){
            this.rowMap = rowMap;
        }

        @Override
        public Set<R> rows() {
            return this.rowMap.keySet();
        }

        @Override
        public Set<C> columns() {
            Set<C> columns = new HashSet<>();
            for(Map<C,V> columnMap : rowMap.values()) {
                columns.addAll(columnMap.keySet());
            }
            return columns;
        }

        @Override
        public Map<C, Map<R, V>> asColumnMap() {
            Map<C, Map<R, V>> columnMap = new HashMap<>();
            for(R row : this.rowMap.keySet()){
                for(C column : this.rowMap.get(row).keySet()){
                    columnMap.computeIfAbsent(column, k -> new HashMap<>())
                            .put(row, this.rowMap.get(row).get(column));
                }
            }
            return columnMap;

        }

        @Override
        public Map<R, Map<C, V>> asRowMap() {
            return this.rowMap;
        }

        @Override
        public Optional<V> getValue(R row, C column) {
            if(rowMap.containsKey(row) && rowMap.get(row).containsKey(column)){
                return Optional.of(rowMap.get(row).get(column));
            }
            return Optional.empty();
        }

        @Override
        public void putValue(R row, C column, V value) {
            this.rowMap
                    .computeIfAbsent(row, k -> new HashMap<>())
                    .put(column, value);

        }
    }

}