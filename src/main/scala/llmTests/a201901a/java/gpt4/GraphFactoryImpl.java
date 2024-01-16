package llmTests.a201901a.java.gpt4;

import llmTests.a201901a.java.Graph;
import llmTests.a201901a.java.GraphFactory;
import llmTests.shared.java.Pair;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GraphFactoryImpl implements GraphFactory {

    @Override
    public <X> Graph<X> createDirectedChain(List<X> nodes) {
        DirectedGraph<X> graph = new DirectedGraph<>(nodes);
        for (int i = 0; i < nodes.size() - 1; i++) {
            graph.addEdge(nodes.get(i), nodes.get(i + 1));
        }
        return graph;
    }

    @Override
    public <X> Graph<X> createBidirectionalChain(List<X> nodes) {
        DirectedGraph<X> graph = new DirectedGraph<>(nodes);
        for (int i = 0; i < nodes.size() - 1; i++) {
            graph.addEdge(nodes.get(i), nodes.get(i + 1));
            graph.addEdge(nodes.get(i + 1), nodes.get(i));
        }
        return graph;
    }

    @Override
    public <X> Graph<X> createDirectedCircle(List<X> nodes) {
        DirectedGraph<X> graph = (DirectedGraph<X>) createDirectedChain(nodes);
        graph.addEdge(nodes.get(nodes.size() - 1), nodes.get(0));
        return graph;
    }

    @Override
    public <X> Graph<X> createBidirectionalCircle(List<X> nodes) {
        DirectedGraph<X> graph = (DirectedGraph<X>) createBidirectionalChain(nodes);
        graph.addEdge(nodes.get(nodes.size() - 1), nodes.get(0));
        graph.addEdge(nodes.get(0), nodes.get(nodes.size() - 1));
        return graph;
    }

    @Override
    public <X> Graph<X> createDirectedStar(X center, Set<X> nodes) {
        DirectedGraph<X> graph = new DirectedGraph<>(nodes);
        graph.addNode(center);
        for (X node : nodes) {
            graph.addEdge(center, node);
        }
        return graph;
    }

    @Override
    public <X> Graph<X> createBidirectionalStar(X center, Set<X> nodes) {
        DirectedGraph<X> graph = new DirectedGraph<>(nodes);
        graph.addNode(center);
        for (X node : nodes) {
            graph.addEdge(center, node);
            graph.addEdge(node, center);
        }
        return graph;
    }

    @Override
    public <X> Graph<X> createFull(Set<X> nodes) {
        DirectedGraph<X> graph = new DirectedGraph<>(nodes);
        for (X node1 : nodes)
            for (X node2 : nodes)
                if (!node1.equals(node2))
                    graph.addEdge(node1, node2);
        return graph;
    }

    @Override
    public <X> Graph<X> combine(Graph<X> g1, Graph<X> g2) {
        DirectedGraph<X> graph = new DirectedGraph<>(g1.getNodes());
        graph.addNodes(g2.getNodes());
        g1.getEdgesStream().forEach(edge -> graph.addEdge(edge.getFst(), edge.getSnd()));
        g2.getEdgesStream().forEach(edge -> graph.addEdge(edge.getFst(), edge.getSnd()));
        return graph;
    }

    static class DirectedGraph<X> implements Graph<X> {

        private Map<X, Set<X>> adjacencyList = new HashMap<>();

        public DirectedGraph() {};

        public DirectedGraph(Collection<X> nodes) {
            for (X node : nodes) addNode(node);
        }

        public void addNode(X node) {
            adjacencyList.putIfAbsent(node, new HashSet<>());
        }

        public void addNodes(Collection<X> nodes) {
            for (X node : nodes) addNode(node);
        }

        public void addEdge(X start, X end) {
            addNode(start);
            adjacencyList.get(start).add(end);
        }

        @Override
        public Set<X> getNodes() {
            return adjacencyList.keySet();
        }

        @Override
        public boolean edgePresent(X start, X end) {
            return adjacencyList.getOrDefault(start, Collections.emptySet()).contains(end);
        }

        @Override
        public int getEdgesCount() {
            return adjacencyList.values().stream().mapToInt(Set::size).sum();
        }

        @Override
        public Stream<Pair<X, X>> getEdgesStream() {
            return adjacencyList.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream().map(node -> new Pair<>(entry.getKey(), node)));
        }
    }
}