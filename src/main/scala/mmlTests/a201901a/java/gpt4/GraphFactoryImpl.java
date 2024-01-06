package mmlTests.a201901a.java.gpt4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mmlTests.a201901a.java.*;
import mmlTests.shared.java.Pair;

public class GraphFactoryImpl implements GraphFactory {

    @Override
    public <X> Graph<X> createDirectedChain(List<X> nodes) {
        return new GraphImpl<>(new HashSet<>(nodes), getEdges(nodes, false));
    }

    @Override
    public <X> Graph<X> createBidirectionalChain(List<X> nodes) {
        return new GraphImpl<>(new HashSet<>(nodes), getEdges(nodes, true));
    }

    @Override
    public <X> Graph<X> createDirectedCircle(List<X> nodes) {
        List<Pair<X,X>> edges = getEdges(nodes, false);
        edges.add(new Pair<>(nodes.get(nodes.size()-1), nodes.get(0)));
        return new GraphImpl<>(new HashSet<>(nodes), edges);
    }

    @Override
    public <X> Graph<X> createBidirectionalCircle(List<X> nodes) {
        List<Pair<X,X>> edges = getEdges(nodes, true);
        edges.add(new Pair<>(nodes.get(nodes.size()-1), nodes.get(0)));
        return new GraphImpl<>(new HashSet<>(nodes), edges);
    }

    @Override
    public <X> Graph<X> createDirectedStar(X center, Set<X> nodes) {
        List<Pair<X,X>> edges = nodes.stream().map(n -> new Pair<>(center, n)).collect(Collectors.toList());
        nodes.add(center);
        return new GraphImpl<>(nodes, edges);
    }

    @Override
    public <X> Graph<X> createBidirectionalStar(X center, Set<X> nodes) {
        List<Pair<X,X>> edges = nodes.stream().flatMap(n -> Stream.of(new Pair<>(n, center), new Pair<>(center, n))).collect(Collectors.toList());
        nodes.add(center);
        return new GraphImpl<>(nodes, edges);
    }

    @Override
    public <X> Graph<X> createFull(Set<X> nodes) {
        List<Pair<X,X>> edges = new ArrayList<>();
        for(X node1 : nodes) {
            for(X node2 : nodes) {
                if(!node1.equals(node2)) {
                    edges.add(new Pair<>(node1, node2));
                }
            }
        }
        return new GraphImpl<>(nodes, edges);
    }

    @Override
    public <X> Graph<X> combine(Graph<X> g1, Graph<X> g2) {
        Set<X> combinedNodes = new HashSet<>(g1.getNodes());
        combinedNodes.addAll(g2.getNodes());

        List<Pair<X,X>> combinedEdges = new ArrayList<>(g1.getEdgesStream().collect(Collectors.toList()));
        combinedEdges.addAll(g2.getEdgesStream().collect(Collectors.toList()));

        return new GraphImpl<>(combinedNodes,combinedEdges);
    }

    private <X> List<Pair<X,X>> getEdges(List<X> nodes, boolean bidirectional) {
        List<Pair<X,X>> edges = new ArrayList<>();
        for(int i=0; i<nodes.size()-1; i++) {
            edges.add(new Pair<>(nodes.get(i), nodes.get(i+1)));
            if(bidirectional) {
                edges.add(new Pair<>(nodes.get(i+1), nodes.get(i)));
            }
        }
        return edges;
    }

    private class GraphImpl<X> implements Graph<X> {
        private final Set<X> nodes;
        private final List<Pair<X, X>> edges;

        GraphImpl(Set<X> nodes, List<Pair<X, X>> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        @Override
        public Set<X> getNodes() {
            return nodes;
        }

        @Override
        public boolean edgePresent(X start, X end) {
            return edges.contains(new Pair<>(start, end));
        }

        @Override
        public int getEdgesCount() {
            return edges.size();
        }

        @Override
        public Stream<Pair<X, X>> getEdgesStream() {
            return edges.stream();
        }
    }
}