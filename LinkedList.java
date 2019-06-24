//Stephanie Yager
//CS211
//2-25-2019
//Chapter 16 Quiz: writing stutter related methods for LinkedList class

import java.util.*;
// Class LinkedList<E> can be used to store a list of values of type E.
// from Buildingjavaprograms.com
// modified by W.P. Iverson, Bellevue College, January 2017 
// added backwards() to check list in backwards order

public class LinkedList<E extends Comparable<E>> implements Iterable<E>{  
	// removed implements List due to version differences of List
    private ListNode<E> front;  // first value in the list
    private ListNode<E> back;   // last value in the list
    private int size;           // current number of elements

    // post: constructs an empty list
    public LinkedList() {
        front = new ListNode<E>(null);
        back = new ListNode<E>(null);
        clear();
    }
    
// ADD MORE METHODS HERE (like for assigned CS211 work):
    
//   
// Begin methods added by Stephanie Yager:
//
    
    //deleteBack removes the last value at the back of the list and returns that removed value
    //throws an exception in the case of empty lists
    public E deleteBack(){
    	if (isEmpty()) throw new NoSuchElementException("empty list");
    	E temp = get(size-1);
    	remove(size-1);
    	return temp;
    }

    //switchPairs switches values in the list in a pairwise fashion, and if there is an odd number of items, leaves the last one
    public void switchPairs() {
    	for(int i=0; i<size; i+=2) {			//every pass through the loop takes care of 2 values, so we increment i by 2 
    		if(!nodeAt(i+1).equals(back)) {		//don't try to switch if the next node is the back of the list
	    		ListNode<E> current = nodeAt(i+1);
	    		remove(i+1);
	    		add(i, current.data);
    		}
    	}
    }
    
    //stutter duplicates every value in the list, doubling the size of the list
    public void stutter() {
    	int temp = size;				//we'll change the size of the list, so we need to hang on to this value
    	for(int i=0; i<temp; i++) {
    		ListNode<E> current = nodeAt(i*2); //the way we're duplicating values means what was originally at i will be at i*2 by the time we get to it
    		add(i*2, current.data);			   //simply add that same value where it previously was, and the add method moves the rest of the list
    	}
    }
    
    //removeAll removes all occurrences of value from the list
    public void removeAll(E value) {
    	for (int i=0; i<size; i++) {
    		ListNode<E> current = nodeAt(i);
    		if (current.data.equals(value)) { 	//using comparable, we can compare the values being referenced
    			remove(i);
    			i--;	//if we remove a value, the next one over takes its place, and we need to move i back so we can examine it too
    		}
    	}
    }
    
    //this method doubles the list by basically adding a copy of all of its contents to the back of the list
    public void doubleList() {
    	int temp = size;
    	ListNode<E> current;
    		current = front;
            for (int i = 0; i < temp-1 + 1; i++) {
                current = current.next;
                ListNode<E> newNode = new ListNode<E>(current.data, back, back.prev);
                back.prev.next = newNode;
                back.prev = newNode;
                size++;
    	}
    }
    
    //this method removes the elements of all of the indexes - inclusive - between the two integers provided
    public void removeRange(int min, int max) {
    	if(min < 0 || max < 0) throw new IllegalArgumentException("Index may not be negative");
    	for(int i=max; i>=min; i--) {
    		remove(i);
    	}
    }
    
    //this method checks to see if the list has been stuttered or contains values matching a stuttered list
    public boolean isPerfectStutter() {
    	boolean stutter = true;
    	if (size%2 == 1 || size ==0) return false; //if there is an odd number of elements or none, it is false by default
    	else {
    		for(int i=0; i < size; i+=2) {		//otherwise, we compare every other node starting at 0 to the node after it
    			if (!nodeAt(i).data.equals(nodeAt(i+1).data)) stutter = false;
    		}
    	}
    	return stutter;
    }
    
    //this method undoes a stutter by removing elements that could have been added by the stutter method
    public void undoStutter() {
    	if(isPerfectStutter()) {
    		for(int i=0; i<size; i++) { //every time we remove a value, the next one we can remove moves to the "i" index
    			remove(i);
    		}
    	}
    }

    //
    //Ends the methods added by Stephanie Yager
    //
    
    
    // post: returns the current number of elements in the list
    public int size() {
        return size;
    }

    // pre : 0 <= index < size() (throws IndexOutOfBoundsException if not)
    // post: returns the value at the given index in the list
    public E get(int index) {
        checkIndex(index);
        ListNode<E> current = nodeAt(index);
        return current.data;
    }

    // post: creates a comma-separated, bracketed version of the list
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            String result = "[" + front.next.data;
            ListNode<E> current = front.next.next;
            while (current != back) {
                result += ", " + current.data;
                current = current.next;
            }
            result += "]";
            return result;
        }
    }
    
    // post: creates a comma-separated, bracketed version of the list
    // Iverson creation
    public String backwards() {
        if (size == 0) {
            return "[]";
        } else {
            String result = "[" + back.prev.data;
            ListNode<E> current = back.prev.prev;
            while (current != front) {
                result += ", " + current.data;
                current = current.prev;
            }
            result += "]";
            return result;
        }
    }

    // post : returns the position of the first occurrence of the given
    //        value (-1 if not found)
    public int indexOf(E value) {
        int index = 0;
        ListNode<E> current = front.next;
        while (current !=  back) {
            if (current.data.equals(value)) {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1;
    }

    // post: returns true if list is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }

    // post: returns true if the given value is contained in the list,
    //       false otherwise
    public boolean contains(E value) {
        return indexOf(value) >= 0;
    }

    // post: appends the given value to the end of the list
    public void add(E value) {
        add(size, value);
    }

    // pre: 0 <= index <= size() (throws IndexOutOfBoundsException if not)
    // post: inserts the given value at the given index, shifting subsequent
    //       values right
    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        ListNode<E> current = nodeAt(index - 1);
        ListNode<E> newNode = new ListNode<E>(value, current.next, current);
        current.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    // post: appends all values in the given list to the end of this list
    public void addAll(List<E> other) {
        for (E value: other) {
            add(value);
        }
    }

    // pre : 0 <= index < size() (throws IndexOutOfBoundsException if not)
    // post: removes value at the given index, shifting subsequent values left
    public void remove(int index) {
        checkIndex(index);
        ListNode<E> current = nodeAt(index - 1);
        current.next = current.next.next;
        current.next.prev = current;
        size--;
    }

    // pre : 0 <= index < size() (throws IndexOutOfBoundsException if not)
    // post: replaces the value at the given index with the given value
    //public void set(int index, E value) {
    //    checkIndex(index);
    //    ListNode<E> current = nodeAt(index);
    //    current.data = value;
    //}

    // post: list is empty
    public void clear() {
        front.next = back;
        back.prev = front;
        size = 0;
    }

    // post: returns an iterator for this list
    public Iterator<E> iterator() {
        return new LinkedIterator();
    }

    // pre : 0 <= index < size()
    // post: returns the node at a specific index.  Uses the fact that the list
    //       is doubly-linked to start from the front or the back, whichever
    //       is closer.
    private ListNode<E> nodeAt(int index) {
        ListNode<E> current;
        if (index < size / 2) {
            current = front;
            for (int i = 0; i < index + 1; i++) {
                current = current.next;
            }
        } else {
            current = back;
            for (int i = size; i >= index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    // post: throws an IndexOutOfBoundsException if the given index is
    //       not a legal index of the current list
    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    private static class ListNode<E> {
        public E data;         // data stored in this node
        public ListNode<E> next;  // link to next node in the list
        public ListNode<E> prev;  // link to previous node in the list

        // post: constructs a node with given data and null links
        public ListNode(E data) {
            this(data, null, null);
        }

        // post: constructs a node with given data and given links
        public ListNode(E data, ListNode<E> next, ListNode<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private class LinkedIterator implements Iterator<E> {
        private ListNode<E> current;  // location of next value to return
        private boolean removeOK;  // whether it's okay to remove now

        // post: constructs an iterator for the given list
        public LinkedIterator() {
            current = front.next;
            removeOK = false;
        }

        // post: returns true if there are more elements left, false otherwise
        public boolean hasNext() {
            return current != back;
        }

        // pre : hasNext()
        // post: returns the next element in the iteration
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = current.data;
            current = current.next;
            removeOK = true;
            return result;
        }

        // pre : next() has been called without a call on remove (i.e., at most
        //       one call per call on next)
        // post: removes the last element returned by the iterator
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            ListNode<E> prev2 = current.prev.prev;
            prev2.next = current;
            current.prev = prev2;
            size--;
            removeOK = false;
        }
    }
}