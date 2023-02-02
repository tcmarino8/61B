import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Tyler Marino
 */
public class BSTStringSet implements StringSet, Iterable<String>, SortedStringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }
    private Node findNodePlace(String s, Node node) {
        if (node == null) {
            return node;
        }
        while (node.left != null || node.right != null) {
            if (node.s.compareTo(s) == 0) {
                return null;
            }
            if (node.s.compareTo(s) > 0) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    break;
                }
            } else if (node.s.compareTo(s) < 0) {
                if (node.right != null) {
                    node = node.right;
                } else {
                    break;
                }
            }
        }
        return node;
    }

    @Override
    public void put(String s) {
        if (_root == null) {
            _root = new Node(s);
        } else {
            if (findNodePlace(s, _root) == null) {
                return;
            }
            if (findNodePlace(s, _root).s.compareTo(s) == 0) {
                return;
            }
            if (findNodePlace(s, _root).s.compareTo(s) > 0) {
                findNodePlace(s, _root).left = new Node(s);
            } else {
                findNodePlace(s, _root).right = new Node(s);
            }
        }
    }
        // FIXME: PART A

    @Override
    public boolean contains(String s) {
        if (findNodePlace(s, _root) == null) {
            return true;
        } else if (findNodePlace(s, _root).s == null){
            return false;
        } else {
            return (findNodePlace(s, _root).s.compareTo(s) == 0);
        }
        // FIXME: PART A
    }

    @Override
    public List<String> asList() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> treeIter = iterator();
        while(treeIter.hasNext()) {
            list.add(treeIter.next());
        }
        /*while (_root.left != null) {
            _root = _root.left;
        }
        list.add(_root.s);
        if (_root.right != null) {
            replaceWithRight();
            asList();
        } else {
            //go up one
            list.add(_root.s);
            _root.left = null;
            asList();
        }*/
        return list; // FIXME: PART A. MUST BE IN SORTED ORDER, ASCENDING
    }
    private void replaceWithRight() {
        _root = _root.right;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    // FIXME: UNCOMMENT THE NEXT LINE FOR PART B
    //@Override
    public Iterator<String> iterator(String low, String high) {
        return null;  // FIXME: PART B (OPTIONAL)
    }


    /** Root node of the tree. */
    private Node _root;
}
