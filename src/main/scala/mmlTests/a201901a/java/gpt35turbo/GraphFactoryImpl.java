//package mmlTests.a201901a.java.gpt35turbo;
//
//import mmlTests.a201901a.java.Graph;
//import mmlTests.a201901a.java.GraphFactory;
//import mmlTests.a201901a.java.gpt35turbo.GraphImpl;
//import mmlTests.shared.java.Pair;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Stream;
//
//class GraphFactoryImpl implements GraphFactory {
//
//    @Override
//    public <X> Graph<X> createDirectedChain(List<X> nodes) {
//        GraphImpl<X> graph = new GraphImpl<>();
//        for (int i = 0; i < nodes.size() - 1; i++) {
//            graph.addEdge(nodes.get(i), nodes.get(i + 1));
//        }
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createBidirectionalChain(List<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        for (int i = 0; i < nodes.size() - 1; i++) {
//            graph.addEdge(nodes.get(i), nodes.get(i + 1));
//            graph.addEdge(nodes.get(i + 1), nodes.get(i));
//        }
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createDirectedCircle(List<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        for (int i = 0; i < nodes.size() - 1; i++) {
//            graph.addEdge(nodes.get(i), nodes.get(i + 1));
//        }
//        graph.addEdge(nodes.get(nodes.size() - 1), nodes.get(0));
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createBidirectionalCircle(List<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        for (int i = 0; i < nodes.size() - 1; i++) {
//            graph.addEdge(nodes.get(i), nodes.get(i + 1));
//            graph.addEdge(nodes.get(i + 1), nodes.get(i));
//        }
//        graph.addEdge(nodes.get(nodes.size() - 1), nodes.get(0));
//        graph.addEdge(nodes.get(0), nodes.get(nodes.size() - 1));
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createDirectedStar(X center, Set<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        for (X node : nodes) {
//            graph.addEdge(center, node);
//        }
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createBidirectionalStar(X center, Set<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        for (X node : nodes) {
//            graph.addEdge(center, node);
//            graph.addEdge(node, center);
//        }
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> createFull(Set<X> nodes) {
//        Graph<X> graph = new GraphImpl<>();
//        List<X> nodeList = new ArrayList<>(nodes);
//        for (int i = 0; i < nodeList.size() - 1; i++) {
//            for (int j = i + 1; j < nodeList.size(); j++) {
//                graph.addEdge(nodeList.get(i), nodeList.get(j));
//            }
//        }
//        return graph;
//    }
//
//    @Override
//    public <X> Graph<X> combine(Graph<X> g1, Graph<X> g2) {
//        Graph<X> graph = new GraphImpl<>();
//        for (Pair<X, X> edge : g1.getEdgesStream().toArray(Pair[]::new)) {
//            graph.addEdge(edge.getFst(), edge.getSnd());
//        }
//        for (Pair<X, X> edge : g2.getEdgesStream().toArray(Pair[]::new)) {
//            graph.addEdge(edge.getFst(), edge.getSnd());
//        }
//        return graph;
//    }
//}