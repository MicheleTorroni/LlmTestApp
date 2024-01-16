package llmTests.a202003c.java.gpt35turbo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import llmTests.a202003c.java.*;

public class TableImpl<R, C, V> implements Table<R, C, V> {
    private Map<R, Map<C, V>> table;

    public TableImpl() {
        table = new HashMap<>();
    }

    @Override
    public Set<R> rows() {
        return table.keySet();
    }

    @Override
    public Set<C> columns() {
        Set<C> columns = new HashSet<>();
        for (Map<C, V> rowValues : table.values()) {
            columns.addAll(rowValues.keySet());
        }
        return columns;
    }

    @Override
    public Map<C, Map<R, V>> asColumnMap() {
        Map<C, Map<R, V>> columnMap = new HashMap<>();
        for (C column : columns()) {
            Map<R, V> rowValues = new HashMap<>();
            for (Map.Entry<R, Map<C, V>> entry : table.entrySet()) {
                R row = entry.getKey();
                V value = entry.getValue().get(column);
                rowValues.put(row, value);
            }
            columnMap.put(column, rowValues);
        }
        return columnMap;
    }

    @Override
    public Map<R, Map<C, V>> asRowMap() {
        return table;
    }

    @Override
    public Optional<V> getValue(R row, C column) {
        if (table.containsKey(row) && table.get(row).containsKey(column)) {
            return Optional.of(table.get(row).get(column));
        }
        return Optional.empty();
    }

    @Override
    public void putValue(R row, C column, V value) {
        if (!table.containsKey(row)) {
            table.put(row, new HashMap<>());
        }
        table.get(row).put(column, value);
    }
}
