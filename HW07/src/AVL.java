import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Michael Edoigiawerie
 * @version 1.0
 * @userid medoigiawerie3
 * @GTID 903626849
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Null data passed in/data element is null.");
        }
        for (T d: data) {
            add(d);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        root = rAdd(data, root);
    }

    /**
     * A private recursive helper method that adds an element to the AVL Tree
     * @param data The data being added
     * @param curr The traversal node
     * @return An AVL Node
     */
    private AVLNode<T> rAdd(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(data, curr.getRight()));
        }
        update(curr);
        return balance(curr);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = rRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     * A private recursive helper method that removes an element from an AVL Tree.
     * @param data The data being removed
     * @param curr The current Node being compared
     * @param dummy The dummy node passed in
     * @return The data that was removed from the tree
     */
    private AVLNode<T> rRemove(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data you're trying to remove doesn't exist");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(data, curr.getLeft(), dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(data, curr.getRight(), dummy));
        } else {
            size--;
            dummy.setData(curr.getData());
            if (curr.getRight() == null) {
                return curr.getLeft();
            }
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setLeft(getPredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        update(curr);
        return balance(curr);
    }

    /**
     * Private recursive method to retrieve the predecessor node.
     * @param curr The traversal node
     * @param dummy The temp node
     * @return The predecessor node
     */
    private AVLNode<T> getPredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(getPredecessor(curr.getRight(), dummy));
        update(curr);
        return balance(curr);
    }

    /**
     * A helper method used to update the height and balance factor of a node in an AVL Tree.
     * @param curr The node to update
     */
    private void update(AVLNode<T> curr) {
        int newHeight;
        int newBF;
        if (curr.getRight() == null && curr.getLeft() == null) {
            newHeight = 0;
            newBF = 0;
        } else if (curr.getLeft() == null) {
            newHeight = curr.getRight().getHeight() + 1;
            newBF = -1 - curr.getRight().getHeight();
        } else if (curr.getRight() == null) {
            newHeight = curr.getLeft().getHeight() + 1;
            newBF = curr.getLeft().getHeight() + 1;
        } else {
            newHeight = Math.max(curr.getLeft().getHeight(), curr.getRight().getHeight()) + 1;
            newBF = curr.getLeft().getHeight() - curr.getRight().getHeight();
        }
        curr.setHeight(newHeight);
        curr.setBalanceFactor(newBF);
    }

    /**
     * A private helper method used to balance an AVL node by performing rotations.
     * @param node The desired node to balance
     * @return The balanced node
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotation(node.getRight()));
            }
            node = leftRotation(node);
        }
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotation(node.getLeft()));
            }
            node = rightRotation(node);
        }
        return node;
    }

    /**
     * A private helper method that performs left rotations.
     * @param node Node to left-rotate
     * @return The resulting node
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);
        update(node);
        update(rightChild);
        return rightChild;
    }

    /**
     * A private helper method that performs right rotations.
     * @param node Node to right-rotate
     * @return The resulting node
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);
        update(node);
        update(leftChild);
        return leftChild;
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        return rGet(data, root);
    }

    /**
     * private recursive helper method to search for an element in the AVL Tree.
     * @param data The target data to search
     * @param currentNode The traversal node
     * @return The data stored in the AVL Tree
     */
    private T rGet(T data, AVLNode<T> currentNode) {
        if (currentNode == null) {
            throw new NoSuchElementException("The desired data isn't in the tree");
        }
        if (data.compareTo(currentNode.getData()) < 0) {
            return rGet(data, currentNode.getLeft());
        }
        if (data.compareTo(currentNode.getData()) > 0) {
            return rGet(data, currentNode.getRight());
        }
        return currentNode.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        return rContains(data, root);
    }

    /**
     * Private recursive helper method to check if the AVL Tree contains a specific element.
     * @param data The data being searched for
     * @param currentNode The traversal node
     * @return true if the target element is in the AVL Tree, and false otherwise
     */
    private boolean rContains(T data, AVLNode<T> currentNode) {
        if (currentNode == null) {
            return false;
        }
        if (data.compareTo(currentNode.getData()) == 0) {
            return true;
        }
        if (data.compareTo(currentNode.getData()) < 0) {
            return rContains(data, currentNode.getLeft());
        }
        return rContains(data, currentNode.getRight());
    }


    /**
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return rMaxNode(root);
    }

    /**
     * A private recursive helper method that finds the maximum deepest node.
     * @param curr The traversal node
     * @return The Maximum Deepest Node
     */
    private T rMaxNode(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        int leftHeight = curr.getLeft() != null ? curr.getLeft().getHeight() : -1;
        int rightHeight = curr.getRight() != null ? curr.getRight().getHeight() : -1;
        if (curr.getHeight() == 0) {
            return curr.getData();
        }
        if (leftHeight > rightHeight) {
            return rMaxNode(curr.getLeft());
        }
        return rMaxNode(curr.getRight());
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        return rSuccessor(data, root, root);
    }

    /**
     * Private recursive helper method used to retrieve the data in the successor node.
     * @param data The data passed in
     * @param curr The traversal node
     * @param pS Potential Successor
     * @return The successor of the data passed in.
     */
    private T rSuccessor(T data, AVLNode<T> curr, AVLNode<T> pS) {
        if (curr == null) {
            throw new NoSuchElementException("The data doesn't exist in the tree.");
        }
        if (data.compareTo(curr.getData()) < 0) {
            return rSuccessor(data, curr.getLeft(), curr);
        }
        if (data.compareTo(curr.getData()) > 0) {
            return rSuccessor(data, curr.getRight(), curr);
        }
        if (curr.getRight() == null) {
            int currP = curr.getData().compareTo(pS.getData());
            int currR = curr.getData().compareTo(root.getData());
            if (currP > 0 && currR > 0) {
                return null;
            }
            if (currP > 0) {
                return root.getData();
            }
            return pS.getData();
        }
        return getSuccessor(curr.getRight()).getData();
    }

    /**
     * Private recursive method to retrieve the successor node.
     * @param curr The traversal node
     * @return The successor node
     */
    private AVLNode<T> getSuccessor(AVLNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr;
        }
        return getSuccessor(curr.getLeft());
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
