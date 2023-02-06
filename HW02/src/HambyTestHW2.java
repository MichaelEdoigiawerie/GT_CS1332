import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class HambyTestHW2 {

    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;

    @Before
    public void setUp() throws Exception {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() throws Exception {
        for(int i = 0; i < 1000; i++) {
            list.addToFront(String.valueOf(i));
        }

        Object[] listArray = list.toArray();

        for(int i = 999, j = 0; j < 1000; i--, j++) {
            assertEquals(String.valueOf(i), listArray[j]);
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNull() throws Exception {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() throws Exception {
        for(int i = 0; i < 1000; i++) {
            list.addToBack(String.valueOf(i));
        }

        Object[] listArray = list.toArray();

        for(int i = 0; i < 1000; i++) {
            assertEquals(String.valueOf(i), listArray[i]);
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNull() throws Exception {
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() throws Exception {
        list.addAtIndex(0, "a");
        list.addAtIndex(1, "b");
        list.addAtIndex(2, "c");
        list.addAtIndex(0, "d");
        list.addAtIndex(2, "e");
        list.addAtIndex(1, "f");
        list.addAtIndex(3, "g");

        assertEquals(list.size(), 7);

        Object[] expected = {"d", "f", "a", "g", "e", "b", "c"};

        assertArrayEquals(expected, list.toArray());

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNull() {
        list.addAtIndex(0, "a");
        list.addAtIndex(1, "b");
        list.addAtIndex(1, null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooSmall() {
        list.addAtIndex(0, "a");
        list.addAtIndex(0, "b");
        list.addAtIndex(-1, "c");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooBig() {
        list.addAtIndex(1, "a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooBig2() {
        list.addAtIndex(0, "a");
        list.addAtIndex(1, "a");
        list.addAtIndex(3, "b");
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        for(int i = 0; i < 25; i++) {
            list.addToBack(String.valueOf(i));
        }

        assertEquals(list.size(), 25);

        assertNull(list.getTail().getNext());
        assertNull(list.getHead().getPrevious());

        assertEquals("23", list.removeAtIndex(23));
        assertEquals(list.size(), 24);
        assertEquals("4", list.removeAtIndex(4));
        assertEquals("0", list.removeAtIndex(0));
        assertEquals("1", list.removeAtIndex(0));
        assertEquals(list.size(), 21);
        assertEquals(list.toArray().length, 21);

        assertNull(list.getTail().getNext());
        assertNull(list.getHead().getPrevious());

        assertEquals( "24", list.removeAtIndex(list.size()-1));
        assertEquals("22", list.removeAtIndex(19));
        assertEquals(list.size(), 19);
        assertEquals(list.toArray().length, 19);

        assertNull(list.getTail().getNext());
        assertNull(list.getHead().getPrevious());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexTooSmall() {
        list.addToBack("a");
        list.addToBack("b");
        list.removeAtIndex(0);
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexTooBig() {
        list.addToBack("a");
        list.addToBack("b");
        list.removeAtIndex(1);
        list.removeAtIndex(1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexEmpty() {
        list.addToBack("a");
        list.addToBack("b");
        list.removeAtIndex(1);
        list.removeAtIndex(0);
        list.removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        for(int i = 0; i < 1000; i++) {
            list.addToBack(String.valueOf(i));
        }

        for(int i = 0; i < 500; i++) {
            assertEquals(String.valueOf(i), list.removeFromFront());
        }
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        for(int i = 500; i < 1000; i++) {
            assertEquals(String.valueOf(i), list.removeFromFront());
        }

        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmpty() {
        list.addToBack("a");
        list.removeFromFront();
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        for(int i = 0; i < 1000; i++) {
            list.addToFront(String.valueOf(i));
        }

        for(int i = 0; i < 500; i++) {
            assertEquals(String.valueOf(i), list.removeFromBack());
        }
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        for(int i = 500; i < 1000; i++) {
            assertEquals(String.valueOf(i), list.removeFromBack());
        }

        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackEmpty() {
        list.addToBack("a");
        list.removeFromBack();
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        for(int i = 0; i < 1000; i++) {
            list.addToBack(String.valueOf(i));
        }

        for(int i = 0; i < 1000; i++) {
            assertEquals(list.get(i), String.valueOf(i));
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetTooBig() {
        for(int i = 0; i < 1000; i++) {
            list.addToBack(String.valueOf(i));
        }
        list.get(1000);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetTooSmall() {
        for(int i = 0; i < 1000; i++) {
            list.addToBack(String.valueOf(i));
        }
        list.get(-1);
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.addToBack("a");
        assertFalse(list.isEmpty());
        list.removeFromFront();
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.clear();
        list.addToBack("a");
        assertFalse(list.isEmpty());
        assertEquals(list.size(), 1);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(list.size(), 0);
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {
        list.addToBack("a");
        list.addToBack("b");
        list.addToBack("c");
        list.addToBack("d");
        list.addToBack("a");
        list.addToBack("e");
        list.addToBack("f");

        Object[] desired = {"a", "b", "c", "d", "e", "f"};

        assertEquals("a", list.removeLastOccurrence("a"));

        assertArrayEquals(desired, list.toArray());

        Object[] desired2 = {"a", "b", "c", "d", "e"};

        assertEquals("f", list.removeLastOccurrence("f"));
        assertArrayEquals(desired2, list.toArray());

        Object[] desired3 = {"b", "c", "d", "e"};
        assertEquals("a", list.removeLastOccurrence("a"));
        assertArrayEquals(desired3, list.toArray());

        list.clear();
        list.addToBack("1");
        assertEquals("1", list.removeLastOccurrence("1"));
        assertArrayEquals(new Object[0], list.toArray());

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveLastOccurrenceNull() {
        list.addToBack("1");
        list.removeLastOccurrence(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceNotThere() {
        list.addToBack("a");
        list.addToBack("b");
        list.addToBack("c");
        list.removeLastOccurrence("d");
    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {
        assertArrayEquals(new Object[0], list.toArray());

        for(int i = 0; i < 5; i++) {
            list.addToBack(String.valueOf(i));
        }

        Object[] desired = {"0", "1", "2", "3", "4"};
        assertArrayEquals(desired, list.toArray());
    }
}