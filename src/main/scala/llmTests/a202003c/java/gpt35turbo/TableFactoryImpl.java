package llmTests.a202003c.java.gpt35turbo;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import llmTests.shared.java.Pair;
import llmTests.a202003c.java.*;

/**
 * An interface modelling a factory for various kinds of tables
 *
 */
/**
 * @author mirko
 *
 */
public class TableFactoryImpl implements TableFactory {

    @Override
    public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
        Table<R, C, V> table = new TableImpl<>();
        for (Map.Entry<Pair<R, C>, V> entry : map.entrySet()) {
            Pair<R, C> pair = entry.getKey();
            R row = pair.getFst();
            C column = pair.getSnd();
            V value = entry.getValue();
            table.putValue(row, column, value);
        }
        return table;
    }

    @Override
    public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
        Table<R, C, V> table = new TableImpl<>();
        for (R row : rows) {
            for (C column : columns) {
                V value = valueFunction.apply(row, column);
                table.putValue(row, column, value);
            }
        }
        return table;
    }

    @Override
    public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {
        Table<G, G, Boolean> table = new TableImpl<>();
        for (Pair<G, G> edge : edges) {
            G from = edge.getFst();
            G to = edge.getSnd();
            table.putValue(from, to, true);
        }
        return table;
    }

    @Override
    public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
        Table<Integer, Integer, V> table = new TableImpl<>();
        int numRows = values.length;
        int numCols = values[0].length;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                V value = values[i][j];
                table.putValue(i, j, value);
            }
        }
        return table;
    }
}
