package cslist;

import java.util.Arrays;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Objects;

/**
 *  This class implements some of the methods of the Java
 *  ArrayList class.
 * @param <E> The element type
 */
public class CSArrayList<E> extends AbstractList<E> {
    // Data Fields

    /** The default initial capacity */
    private static final int INITIAL_CAPACITY = 10;
    /** The underlying data array
     *  private E[] theData declares a private array that can hold objects of a type specified by the generic parameter E. This is a common pattern in implementing generic data structures like lists, stacks, queues, where the underlying storage is an array, and the type of elements stored is flexible.*/
    private E[] theData;
    /** The current size */
    private int size = 0;
    /** The current capacity */
    private int capacity = 0;

    /**
     * Construct an empty CSArrayList with the default
     * initial capacity
     */
    /*Java provides an annotation that enables you to compile the constructor without an error message. If you place the statement @SuppressWarnings("unchecked") before the constructor, the compiler warning will not appear*/
    @SuppressWarnings({"unchecked"})
    public CSArrayList() {
        capacity = INITIAL_CAPACITY;
        theData = (E[]) new Object[capacity];
    }

    /**
     * Construct an ArrayList<E> from any Collection whose elements are E or a subtype of E.
     * @param c The Collection
     */
    @SuppressWarnings("unchecked")
    public CSArrayList(Collection<? extends E> c) {
        if (c == null) throw new NullPointerException("collection is null");
        int initial = Math.max(INITIAL_CAPACITY, c.size());
        this.capacity = Math.max(1, initial);
        this.theData = (E[]) new Object[this.capacity];
        for (E e : c) {
            add(e);
        }
    }

    /**
     * An empty CSArrayList with a specified initial capacity
     * @param capacity The initial capacity
     */
    @SuppressWarnings("unchecked")
    public CSArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity < 0");
        this.capacity = Math.max(1, capacity);
        theData = (E[]) new Object[this.capacity];
    }


    /**
     * Add an entry to the data inserting it at the end of the list.
     * @param anEntry The value to be added to the list.
     * @return true since the add always succeeds
     */
    @Override
    public boolean add(E anEntry) {
        // if the size is equal to capacity we must first allocate a new array to hold the data and then copy the data to this new array with method reallocate
        if (size == capacity) {
            reallocate();
        }
        theData[size] = anEntry;
        size++;
        modCount++; // fail-fast: structural modification
        return true;
    }

    /**
     * Add an entry to the data inserting it at index of the list.
     * @param index - The index of the item desired
     * @param anEntry The value to be added to the list.
     */
    @Override
    public void add (int index, E anEntry) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (size == capacity) {
            reallocate();
        }
        // Shift data in elements from index to size - 1
        for (int i = size; i > index; i--) {
            theData[i] = theData[i - 1];
        }
        // Insert the new item.
        theData[index] = anEntry;
        size++;
        modCount++; // fail-fast: structural modification
    }

    /**
     * Get a value in the array based on its index.
     * @param index - The index of the item desired
     * @return The contents of the array at that index
     * @throws ArrayIndexOutOfBoundsException - if the index
     *         is negative or if it is greater than or equal to the
     *         current size
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return theData[index];
    }

    /**
     * Set the value in the array based on its index.
     * @param index - The index of the item desired
     * @param newValue - The new value to store at this position
     * @return The old value at this position
     * @throws ArrayIndexOutOfBoundsException - if the index
     *         is negative or if it is greater than or equal to the
     *         current size
     */
    @Override
    public E set(int index, E newValue) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        E oldValue = theData[index];
        theData[index] = newValue;
        return oldValue;
    }

    /**
     * Remove an entry based on its index
     * @param index - The index of the entry to be removed
     * @return The value removed
     * @throws ArrayIndexOutOfBoundsException - if the index
     *         is negative or if it is greater than or equal to the
     *         current size
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        E returnValue = theData[index];
        for (int i = index + 1; i < size; i++) {
            theData[i - 1] = theData[i];
        }
        // avoid loitering
        theData[--size] = null;
        modCount++; // fail-fast: structural modification
        return returnValue;
    }

    /**
     * Allocate a new array that is twice the size of the current array. Copies the contents of the current array to the new one using .copyOf
     */
    private void reallocate() {
        capacity = Math.max(1, 2 * capacity);
        theData = Arrays.copyOf(theData, capacity);
    }

    /**
     * Get the current size of the array
     * @return The current size of the array
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element
     * @param item The object to search for
     * @return The index of the first occurrence of the specified item
     *         or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(theData[i], item)) {
                return i;
            }
        }
        return -1;
    }

    /* =======================
       Part A — Added Methods
       ======================= */

    /**
     * Return a string representation like [a, b, c]
     */
    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(theData[i]);
            if (i != size - 1) sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Remove all elements from the list (size becomes 0).
     * Capacity is unchanged.
     */
    @Override
    public void clear() {
        // Null out references for GC
        for (int i = 0; i < size; i++) {
            theData[i] = null;
        }
        size = 0;
        modCount++; // fail-fast: structural modification
    }

    /**
     * Returns true if this list contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * Returns true if this list contained the specified element.
     */
    @Override
    public boolean remove(Object o) {
        int idx = indexOf(o);
        if (idx == -1) return false;
        remove(idx); // already updates modCount and shifts
        return true;
    }

    /**
     * Ensure the internal array can hold at least minCapacity elements without resizing.
     * Does not change size.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity < 0) throw new IllegalArgumentException("minCapacity < 0");
        if (minCapacity <= capacity) return;
        // grow to at least minCapacity (doubling strategy)
        int newCap = capacity;
        if (newCap == 0) newCap = INITIAL_CAPACITY;
        while (newCap < minCapacity) {
            newCap = newCap * 2;
        }
        theData = Arrays.copyOf(theData, newCap);
        capacity = newCap;
        // Not a structural modification of the list contents; do not touch modCount
    }

    /**
     * Trims the capacity of this list instance to be the list's current size.
     */
    public void trimToSize() {
        if (capacity == size) return;
        theData = Arrays.copyOf(theData, size);
        capacity = size;
        // Not a structural modification of the list contents; do not touch modCount
    }
}
