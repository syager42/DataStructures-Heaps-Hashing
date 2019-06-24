import java.util.Arrays;

public class HeapIntPriorityQueue {
	private int[] elementData;
	private int size;
	
	public HeapIntPriorityQueue() {
		elementData = new int[10];
		size = 0;
	}
	
	private int parent(int index) {
		return index/2;
	}
	private int leftChild(int index) {
		return index*2;
	}
	private int rightChild(int index) {
		return index*2+1;
	}
	private boolean hasParent(int index) {
		return index > 1;
	}
	
	private boolean hasLeftChild(int index) {
		return leftChild(index) <= size;
	}
	private boolean hasRightChild(int index) {
		return rightChild(index) <= size;
	}
	private void swap(int[] a, int index1, int index2) {
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
	
	public void add(int value) {
		
//		if(size + 1 >= elementData.length) elementData = Arrays.copyOf(elementData, elementData.length*2);
		elementData[size+1] = value;
		
		int index = size+1;
		boolean found = false;
		while(!found && hasParent(index)) {
			int parent = parent(index);
			if (elementData[index] < elementData[parent]) {
				swap(elementData, index, parent(index));
				index = parent(index);
				
			}
			else {
				found = true; //found proper location, stop loop
			}
		}
		size++;
	}
	
	public void removeMin() {
		elementData[1] = elementData[size];
		size --;
		
		int index = 1;
		boolean found = false;
		while(!found && hasLeftChild(index)) {
			int left = leftChild(index);
			int right = rightChild(index);
			int child = left;
			if(hasRightChild(index) && elementData[right] < elementData[left]) {
				child = right;
			}
			
			if (elementData[index] > elementData[child]) {
				swap(elementData, index, child);
				index = child;
			}
			else {
				found = true;
			}
		}
	}
	
	public String toString() {
		String result = "[";
		boolean first = true;
		if (size > 0) {
			for (int i=0; i<size; i++) {
				if (!first) result += ", ";
				result += elementData[i+1];
				first = false;
			}
		}
		result += "]";
		return result;
	}
	
	public void merge(HeapIntPriorityQueue other) {
		for (int i=0; i< other.size; i++) {
			this.add(other.elementData[i+1]);
		}
	}
	
	public static void main(String[] args) {
		HeapIntPriorityQueue q1 = new HeapIntPriorityQueue();
		HeapIntPriorityQueue q2 = new HeapIntPriorityQueue();
		q1.add(4); q1.add(3); q1.add(11);
		q2.add(-3); q2.add(-4);
		System.out.println(q1);
		System.out.println(q2);
		
		q1.merge(q2);
		
		System.out.println(q1);
	}
	
}
