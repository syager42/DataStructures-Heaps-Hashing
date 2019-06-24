//Stephanie Yager
//CS211
//3-11-2019
//Chapter 18 Quiz - adding contentsEqual and longestLinkedList methods to the HashSet class

import javax.swing.text.html.parser.Element;


// Implements a set of objects using a hash table.
// The hash table uses separate chaining to resolve collisions.
public class HashSet<E> {
    private static final double MAX_LOAD_FACTOR = 0.75;
    private HashEntry<E>[] elementData;
    private int size;
    
    // Constructs an empty set.
    @SuppressWarnings("unchecked")
	public HashSet() {
        elementData = new HashEntry[10];
        size = 0;
    }
    
    
    //contentsEqual checks to see if two HashSets contain the same contents, even if they are hashed differently
    @SuppressWarnings("unchecked")
    public boolean contentsEqual(HashSet other) {
		boolean isEqual = true; //we'll start out assuming they are equal
		
		E[] array1 = (E[]) other.toArray(); //I'm using the helper toArray method to keep this method clean
		
		for (int i=0; i<array1.length; i++) {
			if(!this.contains(array1[i])) {
				return false;		//as soon as a value from one isn't found in the other, it's false
			}
		}
		return isEqual;
	}
	
    //toArray takes the contents of a HashSet and converts them into a standard array
    @SuppressWarnings("unchecked")
	public E[] toArray() {
		E[] array = (E[]) new Object[size];
		int j = 0;
		
		for (int i=0; i<elementData.length; i++) {
			HashEntry current = elementData[i];
			while (current != null) {
				array[j] = (E) current.data;
				current = current.next;
				j++;
			}
		}
		return array;
    }
    //longestLinkedList returns the amount of nodes in the longest internal linked list in the HashSet
    public int longestLinkedList() {
    	int longest = 0;		//the least it could be is 0, in an empty HashSet, so let's start there.
    	
    	if(size > 0) {			//let's not go to all that work if it's empty
	    	for(int i=0; i<elementData.length; i++) {
	    		HashEntry current = elementData[i];
	    		int j=0;		//j is used to track whether the current linked list is longer than the previous longest
	    		while(current != null) {
	    			j++;
	    			current = current.next;
	    		}
	    		if (j > longest) longest = j;
	    	}
    	}
    	return longest;
    }
   
    //toString2 returns a String that is a visual representation of the structure of the HashSet 
    @SuppressWarnings("unchecked")
	public String toString2() {
    	String result = "";
    	int num = 0;		//I kept trying to come up with a clever way to traverse the structure with recursion but never got it and settled on
    	int size = size();  //tracking how many values have been added so far compared to how many total are in the HashSet
    	E[] arr = (E[])new Object[size];	//This isn't as elegant as I wanted either, we end up destroying the HashSet as we go and rebuilding it at the end
    	if(!isEmpty()) {	//This if statement and its contents add the indexes of the underlying array to the String
    		for (int i=0; i < elementData.length; i++) {
    			String temp2 = "";
    			result += "[" + i +"]";
    			temp2 += i;
    			for (int j=0; j<8-temp2.length(); j++) result+= " ";
    		}
    		result += "\n";
		
    		while (num<size) {
        		for (int i=0; i < elementData.length; i++) {
        			HashEntry<E> current = elementData[i];
        			current = elementData[i];
        			String temp = "";
        			if (current != null) {
        				result += current.data;
        				temp += current.data;
        				for (int j=0; j<10-temp.length(); j++) result+= " ";
        				arr[num] = current.data;
        				remove(current.data);
        				num++;
        			}
        			else result += "          ";
        		}
        		result += "\n";
        	}
		}
    	for (int i=0; i < arr.length; i++) this.add(arr[i]);
    	return result;
    }
   
    
    // Adds the given element to this set, if it was not already
    // contained in the set.
    public void add(E value) {
        if (!contains(value) || contains(value)) {
            if (loadFactor() >= MAX_LOAD_FACTOR) {
                rehash();
            }
            
            // insert new value at front of list
            int bucket = hashFunction(value);
            elementData[bucket] = new HashEntry<E>(value, elementData[bucket]);
            size++;
        }
    }
    
    // Removes all elements from the set.
    public void clear() {
        for (int i = 0; i < elementData.length; i++) {
            elementData[i] = null;
        }
        size = 0;
    }
    
    // Returns true if the given value is found in this set.
    public boolean contains(E value) {
        int bucket = hashFunction(value);
        HashEntry<E> current = elementData[bucket];
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // Returns true if there are no elements in this queue.
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Removes the given value if it is contained in the set.
    // If the set does not contain the value, has no effect.
    public void remove(E value) {
        int bucket = hashFunction(value);
        if (elementData[bucket] != null) {
            // check front of list
            if (elementData[bucket].data.equals(value)) {
                elementData[bucket] = elementData[bucket].next;
                size--;
            } else {
                // check rest of list
                HashEntry<E> current = elementData[bucket];
                while (current.next != null && !current.next.data.equals(value)) {
                    current = current.next;
                }
                
                // if the element is found, remove it
                if (current.next != null && current.next.data.equals(value)) {
                    current.next = current.next.next;
                    size--;
				}
            }
        }
    }
    
    // Returns the number of elements in the queue.
    public int size() {
        return size;
    }
    
    // Returns a string representation of this queue, such as "[10, 20, 30]";
    // The elements are not guaranteed to be listed in sorted order.
    public String toString() {
        String result = "[";
        boolean first = true;
        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {
                HashEntry<E> current = elementData[i];
                while (current != null) {
                    if (!first) {
                        result += ", ";
                    }
                    result += current.data;
                    first = false;
                    current = current.next;
                }
            }
        }
        return result + "]";
    }
    
    
    // Returns the preferred hash bucket index for the given value.
    private int hashFunction(E value) {
        return Math.abs(value.hashCode()) % elementData.length;
    }
    
    private double loadFactor() {
        return (double) size / elementData.length;
    }
    
    // Resizes the hash table to twice its former size.
    @SuppressWarnings("unchecked")
	private void rehash() {
        // replace element data array with a larger empty version
        HashEntry<E>[] oldElementData = elementData;
        elementData = new HashEntry[2 * oldElementData.length];
        size = 0;

        // re-add all of the old data into the new array
        for (int i = 0; i < oldElementData.length; i++) {
            HashEntry<E> current = oldElementData[i];
            while (current != null) {
                add((E)current.data);
                current = current.next;
            }
        }
    }
    
    // Represents a single value in a chain stored in one hash bucket.
    @SuppressWarnings("hiding")
	private class HashEntry<E> {
        public E data;
        public HashEntry<E> next;

        @SuppressWarnings("unused")
		public HashEntry(E data) {
            this(data, null);
        }

        public HashEntry(E data, HashEntry<E> next) {
            this.data = data;
            this.next = next;
        }
    }

}
