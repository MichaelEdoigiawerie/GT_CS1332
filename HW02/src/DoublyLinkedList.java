import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Please don't pass in an index less than 0 or greater than the size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Please don't pass in null data");
        }
        if (head == null) {
            head = new DoublyLinkedListNode<>(data);
            tail = head;
        } else if (index == 0) {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data);
            head.setPrevious(curr);
            curr.setNext(head);
            head = head.getPrevious();
        } else if (index == size) {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data);
            tail.setNext(curr);
            curr.setPrevious(tail);
            tail = tail.getNext();
        } else {
            DoublyLinkedListNode<T> curr = head;
            DoublyLinkedListNode<T> newData = new DoublyLinkedListNode<>(data);
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            curr.getPrevious().setNext(newData);
            newData.setPrevious(curr.getPrevious());
            newData.setNext(curr);
            curr.setPrevious(newData);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Please don't pass in null data");
        }
        if (head == null) {
            head = new DoublyLinkedListNode<>(data);
            tail = head;
        } else {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data);
            head.setPrevious(curr);
            curr.setNext(head);
            head = head.getPrevious();
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Please don't pass in null data");
        }
        if (head == null) {
            head = new DoublyLinkedListNode<>(data);
            tail = head;
        } else {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data);
            tail.setNext(curr);
            curr.setPrevious(tail);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Please don't pass in an index less than 0 or greater than the size.");
        }
        T removed;
        if (head == tail) {
            removed = head.getData();
            clear();
            return removed;
        }
        if (index == 0) {
            removed = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return removed;
        }
        if (index == size - 1) {
            removed = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed;
        }
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        removed = curr.getData();
        curr.getPrevious().setNext(curr.getNext());
        curr.getNext().setPrevious(curr.getPrevious());
        size--;
        return removed;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("The head of the linked list is null");
        }
        T removed = head.getData();
        if (head == tail) {
            clear();
            return removed;
        }
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return removed;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("The head of the linked list is null");
        }
        T removed = tail.getData();
        if (head == tail) {
            clear();
            return removed;
        }
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return removed;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Please don't pass in an index less than 0 or greater than the size.");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        }
        int i = 0;
        DoublyLinkedListNode<T> curr = head;
        while (i < index) {
            curr = curr.getNext();
            i++;
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        T removed;
        if (head == tail && head.getData().equals(data)) {
            removed = tail.getData();
            clear();
            return removed;
        }
        if (tail.getData().equals(data)) {
            removed = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            DoublyLinkedListNode<T> curr = tail.getPrevious();
            while (curr.getPrevious() != null) {
                if (curr.getData().equals(data)) {
                    break;
                }
                curr = curr.getPrevious();
            }
            removed = curr.getData();
            if (!curr.getData().equals(data)) {
                throw new NoSuchElementException("The data passed in cannot be found.");
            } else if (curr.getPrevious() == null) {
                curr = curr.getNext();
                head = curr;
            } else {
                curr.getNext().setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(curr.getNext());
            }
        }
        size--;
        return removed;
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] items = (T[]) new Object[size];
        int i = 0;
        DoublyLinkedListNode<T> current = head;
        while (i < items.length) {
            items[i] = current.getData();
            current = current.getNext();
            i++;
        }
        return items;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
