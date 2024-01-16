package llmTests.a201504.java.gpt4;

import llmTests.a201504.java.Tree;
import llmTests.a201504.java.TreeFactory;
import java.util.*;

public class TreeFactoryImpl<X> implements TreeFactory<X> {

    @Override
    public Tree<X> emptyTree() {
        return new TreeImpl<>(null, Collections.emptyList(), true);
    }

    @Override
    public Tree<X> consTree(X root, List<Tree<X>> sons) {
        return new TreeImpl<>(root, sons, false);
    }


    class TreeImpl<X> implements Tree<X> {

        private final X root;
        private final List<Tree<X>> sons;
        private final boolean isEmpty;

        public TreeImpl(X root, List<Tree<X>> sons, boolean isEmpty) {
            this.root = root;
            this.isEmpty = isEmpty;
            this.sons = sons == null ? Collections.emptyList() : new ArrayList<>(sons);
        }

        public int size() {
            if (isEmpty) {
                return 0;
            } else {
                return 1 + sons.stream().mapToInt(Tree::size).sum();
            }
        }

        public X getRoot() {
            if (isEmpty) {
                throw new IllegalStateException();
            } else {
                return root;
            }
        }

        public List<Tree<X>> getSons() {
            if (isEmpty) {
                throw new IllegalStateException();
            } else {
                return new ArrayList<>(sons);
            }
        }

        public boolean contains(X x) {
            if (isEmpty) {
                return false;
            } else if (root.equals(x)) {
                return true;
            } else {
                return sons.stream().anyMatch(s -> s.contains(x));
            }
        }

        public Tree<X> getSubTree(X node) {
            if (isEmpty) {
                throw new IllegalStateException();
            } else if (root.equals(node)) {
                return this;
            } else {
                for (Tree<X> son : sons) {
                    Tree<X> sub = son.getSubTree(node);
                    if (sub != null) {
                        return sub;
                    }
                }
                return null;
            }
        }

        public List<X> toList() {
            if (isEmpty) {
                return Collections.emptyList();
            } else {
                List<X> list = new ArrayList<>();
                list.add(root);
                sons.forEach(s -> list.addAll(s.toList()));
                return list;
            }
        }
    }
}
