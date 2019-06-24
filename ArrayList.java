//Stephanie Yager
//CS211
//2/18/2019
//Implementing collection classes - in this case, a generic ArrayList

//Begin ArrayList class
public class ArrayList<E> {
    private int size = 0;       // used to track number of items currently in the list
    public static final int DEFAULT_CAPACITY = 10; //default capacity if an ArrayList object is not declared with one
    E[] elementData; //the underlying array that will hold items 
    
    //default constructor
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    //single parameter constructor that accepts the capacity of the ArrayList as a parameter
    private ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be at least 0: " + capacity);
        }
        elementData = (E[]) new Object[capacity];
    }
    
    //a method to add a value to the ArrayList
    public void add(E value) {
        add(size, value); //it calls an overloaded version of the add method
    }
    
    //this version of the add method can also add a value to a specified index in the ArrayList
    public void add(int index, E value) {
        checkIndex(index, 0, size);
        ensureCapacity(size + 1);
        
        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = value;
        size++;
    }
    
    //used to reset the ArrayList
    public void clear() {
        size = 0;
    } 
    
    //exists to increase the total capacity of the ArrayList if we would overfill it
    @SuppressWarnings("unchecked")
    public void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length * 2 + 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            E[] newList = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newList[i] = elementData[i];
            }
            elementData = newList;
        }
    }
    
    // this adds a "reflection" of the values in the ArrayList to the ones already there, effectively doubling
    // the size of it.
    public void mirror() {
    	int temp = size;
    	for (int i=temp-1; i >=0; i--) {
    		add(elementData[i]);
    	}
    }
    
    //returns true if the ArrayList is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    //removes the value at the provided index from the ArrayList and shifts all subsequent values over
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        size--;
    }
    
    //replaces the item at "index" with "value"
    public void set(int index, E value) {
        checkIndex(index);
        elementData[index] = value;
    } 
    
    //accessor for size
    public int size() {
        return size;
    }
    
    private void checkIndex(int index) {
        checkIndex(index, 0, size - 1);
    }
    
    //if something attempts to access an index outside of the current size of th ArrayList, exception thrown
    private void checkIndex(int index, int min, int max) {
        if (!(min <= index && index <= max)) {
            throw new ArrayIndexOutOfBoundsException("Index not valid");
        }
    }
    
    // I don't want hex garbage in my output, so we'd better include this!
    public String toString() { //this toString method returns the entire contents of the ArrayList between brackets and separated by commas
		if(size==0) return "[]";	
		else {
			String result = "[" + elementData[0];
			for(int i=1; i <size; i++) {
				result += ", " + elementData[i];
			}
			result += "]";
			return result;
		}
	}
}
