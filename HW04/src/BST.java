import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("The data passed in is null,"
                    + " or one of the elements in the data is null");
        }
        for (T d: data) {
            add(d);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        root = rAdd(data, root);
    }

    /**
     * Private recursive helper function that adds a node onto the BST in O(log n) time.
     * @param data The data to add
     * @param currentNode The current node in the BST being compared.
     * @return The current node that was recursively passed in
     */
    private BSTNode<T> rAdd(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(rAdd(data, currentNode.getLeft()));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(rAdd(data, currentNode.getRight()));
        }
        return currentNode;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     * A private recursive helper method that removes and returns the removed node from a BST in O(log n) time.
     * @param data The data that was removed
     * @param currentNode The current node in the BST being compared
     * @param dummy The temp node
     * @return The current node
     */
    private BSTNode<T> rRemove(T data, BSTNode<T> currentNode, BSTNode<T> dummy) {
        if (currentNode == null) {
            throw new NoSuchElementException("The data you're trying to remove doesn't exist");
        } else if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(rRemove(data, currentNode.getLeft(), dummy));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(rRemove(data, currentNode.getRight(), dummy));
        } else {
            size--;
            dummy.setData(currentNode.getData());
            if (currentNode.getRight() == null) {
                return currentNode.getLeft();
            } else if (currentNode.getLeft() == null) {
                return currentNode.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                currentNode.setRight(getSuccessor(currentNode.getRight(), dummy2));
                currentNode.setData(dummy2.getData());
            }
        }
        return currentNode;
    }

    /**
     * Private recursive helper method to retrieve the successor node.
     * @param currentNode The traversal node
     * @param dummy The temp node
     * @return The successor node
     */
    private BSTNode<T> getSuccessor(BSTNode<T> currentNode, BSTNode<T> dummy) {
        if (currentNode.getLeft() == null) {
            dummy.setData(currentNode.getData());
            return currentNode.getRight();
        }
        currentNode.setLeft(getSuccessor(currentNode.getLeft(), dummy));
        return currentNode;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        return rGet(data, root);
    }

    /**
     * private recursive helper method to search for an element in the BST.
     * @param data The target data to search
     * @param currentNode The traversal node
     * @return The data stored in the BST
     */
    private T rGet(T data, BSTNode<T> currentNode) {
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
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        return rContains(data, root);
    }

    /**
     * Private recursive helper method to check if the BST contains a specific element.
     * @param data The data being searched for
     * @param currentNode The traversal node
     * @return true if the target element is in the BST, and false otherwise
     */
    private boolean rContains(T data, BSTNode<T> currentNode) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        rPreorder(list, root);
        return list;
    }

    /**
     * A private recursive helper method to traverse the BST in preorder format.
     * @param list Stores the elements of the BST in a specific order
     * @param currentNode The traversal node
     */
    private void rPreorder(List<T> list, BSTNode<T> currentNode) {
        if (currentNode != null) {
            list.add(currentNode.getData());
            rPreorder(list, currentNode.getLeft());
            rPreorder(list, currentNode.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        rInorder(list, root);
        return list;
    }

    /**
     * A private recursive helper method to traverse and store the BST's elements in ascending order.
     * @param list Stores the elements of a BST in a specific order
     * @param currentNode The traversal node
     */
    private void rInorder(List<T> list, BSTNode<T> currentNode) {
        if (currentNode != null) {
            rInorder(list, currentNode.getLeft());
            list.add(currentNode.getData());
            rInorder(list, currentNode.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        rPostorder(list, root);
        return list;
    }

    /**
     * A private recursive helper method to traverse the BST in postorder format.
     * @param list Stores the elements of a BST in a specific order
     * @param currentNode The traversal node
     */
    private void rPostorder(List<T> list, BSTNode<T> currentNode) {
        if (currentNode != null) {
            rPostorder(list, currentNode.getLeft());
            rPostorder(list, currentNode.getRight());
            list.add(currentNode.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        BSTNode<T> currentNode;
        while (!q.isEmpty()) {
            currentNode = q.remove();
            if (currentNode != null) {
                list.add(currentNode.getData());
                q.add(currentNode.getLeft());
                q.add(currentNode.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Private recursive helper method that finds the height of a BST.
     * @param currentNode The traversal node
     * @return The height of the BST
     */
    private int rHeight(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return -1;
        }
        if (currentNode.getRight() == null && currentNode.getLeft() == null) {
            return 0;
        }
        return Math.max(rHeight(currentNode.getLeft()), rHeight(currentNode.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<>();
        if (root != null) {
            list.add(root.getData());
            rMaxDataPerLevel(list, root, 0, 0);
        }
        return list;
    }

    /**
     * Private recursive helper method that records the right-most node's data on each level.
     * @param list Stores the right-most node's data on each level.
     * @param currentNode The traversal node
     * @param depth The depth of the node
     * @param prevDepth the depth of the previous node
     */
    private void rMaxDataPerLevel(List<T> list, BSTNode<T> currentNode, int depth, int prevDepth) {
        if (currentNode != null) {
            if (depth > prevDepth) {
                list.add(currentNode.getData());
                prevDepth++;
            }
            rMaxDataPerLevel(list, currentNode.getRight(), depth + 1, prevDepth);
            rMaxDataPerLevel(list, currentNode.getLeft(), depth, prevDepth);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
