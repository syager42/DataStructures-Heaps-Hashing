//Stephanie Yager
//CS 211
//2/15/2019
//Chapter 15 homework part A, on implementing a collection class - this one's a stack!

/**
 * ArrayIntStack is development to approximate functionality of a basic java stack of integers. A stack is a collection
 * with a FILO structure, with methods that interact with the most recent item to be added to the stack. 
 * @author Stephanie Yager
 * @version 1.0.0 (2/15/2019)
 */
import java.util.*;

public class ArrayIntStack{
	private int[] elementData; //this is so a user can set the size of the stack lower than 20 if they choose
	private int size; 		   //this is to help us track the size/amount of values actually stored in this stack
	public static final int DEFAULT_CAPACITY = 20; //since we're throwing a Stack Overflow at more than 20 items, this made sense
	
	/**
	 * Default constructor for the ArrayIntStack class calls single parameter constructor
	 */
	
	public ArrayIntStack() {
		this(DEFAULT_CAPACITY);
	}
	/**
	 * 
	 * Single parameter constructor for the class
	 * 
	 * @param capacity int used to define the maximum size/length of the ArrayIntStack 
	 */
	public ArrayIntStack(int capacity) {
		if(capacity <0 || capacity > 20) { //less than 0 obviously won't work, and we're limiting at 20 
			throw new IllegalArgumentException("invalid capacity: " + capacity);
		}
		elementData = new int[capacity]; //the underlying array we're using gets set to the client's specifications or the default of 20
		size = 0; //and it starts out empty
	}
	/**
	 * Accessor allowing the user to see the size of the ArrayIntStack
	 * 
	 * @return the size of the ArrayIntStack, based on how many values it currently holds
	 */
	public int size() { //letting the client see how many values are stored in this stack
		return size;
	}
	/**
	 * Checks whether the ArrayIntStack is empty or not
	 * 
	 * @return true if the stack is empty, false if it is not
	 */
	public boolean empty() { //lets the client check to see if the stack is empty or not
		return size == 0;
	}
	/**
	 * Access the top value of the ArrayIntStack without removing it
	 * 
	 * @return the value at the top of the ArrayIntStack
	 */
	public int peek() {	//peek method returns the top value of the stack without removing it
		stackLeft();
		return elementData[size-1];
	}
	/**
	 * Both returns and removes the top value of the ArrayIntStack
	 * 
	 * @return the value at the top of the ArrayIntStack - the most recent one added
	 */
	public int pop() { // pop method returns the top value of the stack and removes it 
		stackLeft();
		size--;			//when a value has been removed, the size goes down by 1 accordingly
		return elementData[size];
	}
	/**
	 * A mutator method allowing the user to add a value to the top of the ArrayIntStack
	 * 
	 * @param item an integer value to be added to the top of the ArrayIntStack
	 * @return the integer value that is being added to the top of the ArrayIntStack
	 */
	public int push(int item) { //push method lets the client add a new value to the top of the stack
		checkCapacity(size+1);  //as long as it wouldn't exceed the capacity of the stack.
		elementData[size] = item;
		size++;					//when a value is added, the size goes up by 1 accordingly
		return item;
	}
	/**
	 * Converts the entire contents of the ArrayIntStack to a string format 
	 * 
	 * @return a string representation of the contents of the ArrayIntStack
	 */
	public String toString() { //this toString method returns the entire contents of the stack neatly
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
	/**
	 * Checks whether the ArrayIntStack has the capacity to accept another value
	 * 
	 * @param capacity the size that the ArrayIntStack will be if another value is added
	 * @throws StackOverflowError if the capacity of the ArrayIntStack would be exceeded by the operation being attempted
	 */
	private void checkCapacity(int capacity) { //this helper throws an exception when operations cause the stack capacity to be exceeded
		if(capacity>elementData.length)throw new StackOverflowError("capacity cannot exceed " + elementData.length);
	}
	
	private int get (int index) { //I have this private to conform to classic stack operations, but allow the iterator to look at the current position
		return elementData[index];
	}
	private void stackLeft() { //this helper throws an exception when we try to look at stack elements that don't exist because it's empty
		if(this.empty()) {
			throw new IllegalStateException("empty stack");
		}
	}
	/**
	 * Iterator that iterates over an ArrayIntStack (this)
	 * @return a new IntStackIterator that will iterate over the ArrayIntStack
	 */
	public IntStackIterator iterator() { //this is an iterator method to allow the ArrayIntStack class to use an iterator
		return new IntStackIterator(this);
	}
	/**
	 * An iterator class designed to iterate over an ArrayIntStack object from top of the stack to the bottom
	 * @author Stephanie Yager
	 * @version 1.0.0 (2/15/2019)
	 */
	public class IntStackIterator{ //an iterator class written for ArrayIntStack
		private ArrayIntStack stack;
		private int position;
		/**
		 * Constructor for the IntStackIterator class
		 * @param list accepts an ArrayIntStack object
		 */
		public IntStackIterator(ArrayIntStack list) {//constructor for this class
			this.stack = list;
			position = size-1;			//position starts here instead of 0 so we can move from the top of the stack to the bottom
		}
		/**
		 * Checks to see if there is another value after the current position of the iterator
		 * @return true if there is another value, false if there is not
		 */
		public boolean hasNext() { //hasNext is pretty simple here
			return position >= 0;
		}
		/**
		 * Returns the value of the position of the iterator, and moves the iterator position down one into the ArrayIntStack
		 * @return the int at the current position of the iterator
		 * @throws NoSuchElementException if the iterator can not be moved further because it has already reached the final value in the ArrayIntStack
		 */
		public int next() {
			if(!hasNext()) {	//if hasNext is false, we definitely can't return anything for next
				throw new NoSuchElementException("no remaining values");
			}
			int result = stack.get(position); //otherwise, return the element at the current position and move down one
			position--;
			return result;
		}
	}
}