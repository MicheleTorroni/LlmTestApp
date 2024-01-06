//package mmlTests.a201901a.java.gpt35turbo;
//
//import mmlTests.a201901a.java.Graph;
//import mmlTests.a201901a.java.GraphFactory;
//import mmlTests.shared.java.Pair;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Stream;
//
//class GraphImpl<X> implements Graph<X> {
//
//    private Set<X> nodes;
//    private Set<Pair<X, X>> edges;
//
//    public GraphImpl() {
//        this.nodes = new HashSet<>();
//        this.edges = new HashSet<>();
//    }
//
//    @Override
//    public Set<X> getNodes() {
//        return nodes;
//    }
//
//    @Override
//    public boolean edgePresent(X start, X end) {
//        return edges.contains(new Pair<>(start, end));
//    }
//
//    @Override
//    public int getEdgesCount() {
//        return edges.size();
//    }
//
//    @Override
//    public Stream<Pair<X, X>> getEdgesStream() {
//        return edges.stream();
//    }
//
//    public void addEdge(X start, X end) {
//        nodes.add(start);
//        nodes.add(end);
//        edges.add(new Pair<>(start, end));
//    }
//}
//
