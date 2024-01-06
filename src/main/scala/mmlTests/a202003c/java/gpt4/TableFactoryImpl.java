package mmlTests.a202003c.java.gpt4;

import java.util.*;
import java.util.function.BiFunction;
import mmlTests.shared.java.Pair;
import mmlTests.a202003c.java.*;

public class TableFactoryImpl implements TableFactory {

    @Override
    public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
        return new TableImpl<>(map);
    }

    @Override
    public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
        Map<Pair<R, C>, V> map = new HashMap<>();
        for (R row : rows) {
            for (C column : columns) {
                V value = valueFunction.apply(row, column);
                map.put(new Pair<>(row, column), value);
            }
        }
        return new TableImpl<>(map);
    }

    @Override
    public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {
        Map<Pair<G, G>, Boolean> map = new HashMap<>();
        for (Pair<G, G> edge : edges) {
            map.put(edge, true);
        }
        return new TableImpl<>(map);
    }

    @Override
    public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
        Map<Pair<Integer, Integer>, V> map = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                map.put(new Pair<>(i, j), values[i][j]);
            }
        }
        return new TableImpl<>(map);
    }

    private static class TableImpl<R, C, V> implements Table<R, C, V> {

        private final Map<R, Map<C, V>> table;

        public TableImpl(Map<Pair<R, C>, V> map) {
            this.table = new HashMap<>();
            for (Map.Entry<Pair<R, C>, V> entry : map.entrySet()) {
                Pair<R, C> pair = entry.getKey();
                R row = pair.getFst();
                C column = pair.getSnd();
                V value = entry.getValue();
                table.computeIfAbsent(row, k -> new HashMap<>()).put(column, value);
            }
        }

        @Override
        public Set<R> rows() {
            return table.keySet();
        }

        @Override
        public Set<C> columns() {
            Set<C> columns = new HashSet<>();
            for (Map<C, V> row : table.values()) {
                columns.addAll(row.keySet());
            }
            return columns;
        }

        @Override
        public Map<C, Map<R, V>> asColumnMap() {
            Map<C, Map<R, V>> columnMap = new HashMap<>();
            for (Map.Entry<R, Map<C, V>> entry : table.entrySet()) {
                R row = entry.getKey();
                Map<C, V> rowMap = entry.getValue();
                for (Map.Entry<C, V> rowEntry : rowMap.entrySet()) {
                    C column = rowEntry.getKey();
                    V value = rowEntry.getValue();
                    columnMap.computeIfAbsent(column, k -> new HashMap<>()).put(row, value);
                }
            }
            return columnMap;
        }

        @Override
        public Map<R, Map<C, V>> asRowMap() {
            return table;
        }

        @Override
        public Optional<V> getValue(R row, C column) {
            Map<C, V> rowMap = table.get(row);
            if (rowMap != null) {
                V value = rowMap.get(column);
                if (value != null) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

        @Override
        public void putValue(R row, C column, V value) {
            table.computeIfAbsent(row, k -> new HashMap<>()).put(column, value);
        }
    }
}
