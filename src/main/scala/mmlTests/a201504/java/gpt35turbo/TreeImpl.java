package mmlTests.a201504.java.gpt35turbo;

import mmlTests.a201504.java.Tree;
import mmlTests.a201504.java.TreeFactory;
import java.util.ArrayList;
import java.util.List;

public class TreeImpl<X> implements Tree<X> {

    private X root;
    private List<Tree<X>> sons;

    public TreeImpl(X root, List<Tree<X>> sons) {
        this.root = root;
        this.sons = sons;
    }

    @Override
    public int size() {
        int size = 1;
        for (Tree<X> son : sons) {
            size += son.size();
        }
        return size;
    }

    @Override
    public X getRoot() {
        return root;
    }

    @Override
    public List<Tree<X>> getSons() {
        return new ArrayList<Tree<X>>(sons);
    }

    @Override
    public boolean contains(X x) {
        if (root.equals(x)) {
            return true;
        }
        for (Tree<X> son : sons) {
            if (son.contains(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Tree<X> getSubTree(X node) {
        if (root.equals(node)) {
            return this;
        }
        for (Tree<X> son : sons) {
            Tree<X> subTree = son.getSubTree(node);
            if (subTree != null) {
                return subTree;
            }
        }
        return null;
    }

    @Override
    public List<X> toList() {
        List<X> list = new ArrayList<X>();
        list.add(root);
        for (Tree<X> son : sons) {
            list.addAll(son.toList());
        }
        return list;
    }

}


