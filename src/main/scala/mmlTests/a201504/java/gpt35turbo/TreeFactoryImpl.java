package mmlTests.a201504.java.gpt35turbo;

import mmlTests.a201504.java.Tree;
import mmlTests.a201504.java.TreeFactory;

import java.util.ArrayList;
import java.util.List;

public class TreeFactoryImpl<X> implements TreeFactory<X> {

    @Override
    public Tree<X> emptyTree() {
        return new TreeImpl<X>(null, new ArrayList<Tree<X>>());
    }

    @Override
    public Tree<X> consTree(X root, List<Tree<X>> sons) {
        return new TreeImpl<X>(root, sons);
    }

}