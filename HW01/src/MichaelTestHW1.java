import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


public class JaredTestHW1 {
    private ArrayList<String> list;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    /**
     * Initializes the test
     */
    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    /**
     * Tests adding a null value
     */
    @Test (timeout = TIMEOUT)
    public void testNull() {
        try {
            list.addAtIndex(0, null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests basic adding to front and back
     */
    @Test (timeout = TIMEOUT)
    public void testAddToFrontBack() {
        list.addToBack("back");
        list.addToFront("front");
        list.addToBack("actually back");
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "front";
        expected[1] = "back";
        expected[2] = "actually back";

        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Tests adding at a specific index
     */
    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Tests adding to the front of the ArrayList
     */
    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("4a");  // 4a
        list.addToFront("3a");  // 3a, 4a
        list.addToFront("2a");  // 2a, 3a, 4a
        list.addToFront("1a");  // 1a, 2a, 3a, 4a
        list.addToFront("0a");  // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Tests adding to the back of the arrayList
     */
    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * Tests adding at an index when full
     */
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWhenFull() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }
        list.addAtIndex(5, "9a");

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 5; i++) {
            expected[i] = i + "a";
        }
        expected[5] = "9a";
        for (int i = 6; i < 10; i++) {
            expected[i] = (i - 1) + "a";
        }
        assertArrayEquals(expected, list.getBackingArray());
        assertEquals(10, list.size());
    }

    /**
     * Tests adding to the front when the array is full
     */
    @Test(timeout = TIMEOUT)
    public void testAddToFrontIndexWhenFull() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }
        list.addToFront("9a");

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[0] = "9a";
        for (int i = 1; i < 10; i++) {
            expected[i] = (i - 1) + "a";
        }
        assertArrayEquals(expected, list.getBackingArray());
        assertEquals(10, list.size());
    }

    /**
     * Tests adding to the back when the array is full
     */
    @Test(timeout = TIMEOUT)
    public void testAddToBackWhenFull() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }
        list.addToBack("9a");

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[9] = "9a";
        for (int i = 0; i < 9; i++) {
            expected[i] = i + "a";
        }
        assertArrayEquals(expected, list.getBackingArray());
        assertEquals(10, list.size());
    }

    /**
     * Tests removing an element when the array is empty
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveWhenEmpty() {
        try {
            list.removeAtIndex(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } finally {
            Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
            assertArrayEquals(list.getBackingArray(), expected);
        }
    }

    /**
     * tests removing an element from the array when the array is full.
     */
    @Test
    public void testRemoveWhenFull() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }

    }

    /**
     * tests removing at a specific index
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = "2a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, temp);   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * tests removing from the front
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront());   // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * tests removing from the back
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack());    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * tests the get method
     */
    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    /**
     * tests the isEmpty Method
     */
    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());
    }

    /**
     * tests the clear method
     */
    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        // Clearing the list should empty the array and reset size
        list.clear();

        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test (timeout = TIMEOUT)
    public void testNewArray() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY], list.getBackingArray());
    }

    /**
     * Tests adding at an Index, including resizing the array.
     */
    @Test (timeout = TIMEOUT)
    public void indexAddTests() {
        for (int i = 1; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }

        assertEquals(8, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";

        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(7, "9a");
        assertEquals(9, list.size());
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "9a";
        expected[8] = "8a";

        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(5, "5ab");
        assertEquals(10, list.size());
        Object[] expected2 = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected2[0] = "1a";
        expected2[1] = "2a";
        expected2[2] = "3a";
        expected2[3] = "4a";
        expected2[4] = "5a";
        expected2[5] = "5ab";
        expected2[6] = "6a";
        expected2[7] = "7a";
        expected2[8] = "9a";
        expected2[9] = "8a";

        assertArrayEquals(expected2, list.getBackingArray());
    }

    /**
     * Tests removeFromBack when the array is full
     */
    @Test (timeout = TIMEOUT)
    public void removeFullBackTest() {
        for (int i = 1; i <= ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(i + "a");
        }

        assertEquals(9, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";

        assertArrayEquals(expected, list.getBackingArray());

        String value = list.removeFromBack();
        expected[8] = null;

        assertArrayEquals(expected, list.getBackingArray());
        assertEquals("9a", value);
    }

    /**
     * tests various methods when the size is 1.
     */
    @Test (timeout = TIMEOUT)
    public void lengthOneTest() {
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";

        list.addToBack("1a");
        assertArrayEquals(expected, list.getBackingArray());

        list.removeFromFront();
        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());

        list.addToFront("1a");
        expected[0] = "1a";
        assertArrayEquals(expected, list.getBackingArray());

        list.removeFromBack();
        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(0, "1a");
        expected[0] = "1a";
        assertArrayEquals(expected, list.getBackingArray());

        list.removeAtIndex(0);
        expected[0] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    /**
     * tests all things that can throw an outofbounds exception
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void exceptionTest1() {
        int counter = 0;
        list.addToFront("Hello");
        try {
            list.addAtIndex(-1, "Blah");
        } catch (IndexOutOfBoundsException x) {
            counter++;
        }
        try {
            list.addAtIndex(2, "Blah");
        } catch (IndexOutOfBoundsException x) {
            counter++;
        }
        try {
            list.removeAtIndex(-1);
        } catch (IndexOutOfBoundsException x) {
            counter++;
        }
        try {
            list.removeAtIndex(list.size());
        } catch (IndexOutOfBoundsException x) {
            counter++;
        }
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException x) {
            counter++;
        }
        assertEquals(5, counter);
        list.get(list.size());
    }

    /**
     * Tests everything that may throw an Illegal Argument Exception
     */
    @Test (expected = IllegalArgumentException.class)
    public void exceptionTest2() {
        int counter = 0;
        list.addToFront("Hello");
        try {
            list.addAtIndex(0, null);
        } catch (IllegalArgumentException x) {
            counter++;
        }
        try {
            list.addToFront(null);
        } catch (IllegalArgumentException x) {
            counter++;
        }
        assertEquals(2, counter);
        list.addToBack(null);
    }

    /**
     * Tests everything that may throw a noSuchElementException
     */
    @Test (expected = NoSuchElementException.class)
    public void exceptionTest3() {
        int counter = 0;
        try {
            list.removeFromBack();
        } catch (NoSuchElementException x) {
            counter++;
        }
        assertEquals(1, counter);
        list.removeFromFront();
    }
}
