
public class ArrayIntSet {
	private static final double MAX_LOAD_FACTOR = 0.75;
	private HashEntry[] elementData;
	private int size;
	
	public ArrayIntSet() {elementData = new HashEntry[10];
		size = 0;
	}
	
	private int hashFunction(int value) {
		return Math.abs(value) % elementData.length;
	}
	
	public void add(int value) {
		if(!contains(value)) {
			if(loadFactor() >= MAX_LOAD_FACTOR) {
				rehash();
			}
			int bucket = hashFunction(value);
			elementData[bucket] = new HashEntry(value, elementData[bucket]);
			size++;
		}
	}
	
	private void rehash() {
		HashEntry[] oldElementData = elementData;
		elementData = new HashEntry[2*oldElementData.length];
		size = 0;
		
		for(int i=0; i<oldElementData.length; i++) {
			HashEntry current = oldElementData[i];
			while(current != null) {
				add(current.data);
				current = current.next;
			}
		}
	}
	
	public boolean contentsEqual(ArrayIntSet other) {
		boolean isEqual = true;
		
		int[] array1 = other.toArray();
		
		for (int i=0; i<array1.length; i++) {
			System.out.println("hi!");
			if(!this.contains(array1[i])) {
				isEqual = false;
			}
		}
		return isEqual;
	}
	
	public int[] toArray() {
		int[] array = new int[size];
		int j = 0;
		
		for (int i=0; i<elementData.length; i++) {
			HashEntry current = elementData[i];
			while (current != null) {
				array[j] = current.data;
				System.out.println("j is " + j);
				current = current.next;
				j++;
			}
		}
		
		for(int i=0; i<array.length; i++) {
			System.out.println(array[i]);
		}
		return array;
	}
	
	public boolean equals(ArrayIntSet other) {
		boolean same = true;
		if (this.size != other.size) return false;
		if (this.size == 0 && other.size == 0) return true;
	
		for (int i=0; i < elementData.length; i++) {
			HashEntry current1 = this.elementData[i];
			HashEntry current2 = other.elementData[i];
			
			if ((current1 == null && current2 != null) || (current1 != null && current2 == null)) return false;
			while(current1 != null && current2 != null) {
				if (current1.data != current2.data) return false;
				current1 = current1.next;
				current2 = current2.next;
			}
		}
		return same;
	}
	
	public void retainAll(ArrayIntSet other) {
		for (int i=0; i<elementData.length; i++){
			HashEntry current = this.elementData[i];
			while (current != null) {
				if (!other.contains(current.data)) this.remove(current.data);
				current = current.next;
			}
		}
	}
	
	public String toString() {
		String result = "[";
		boolean first = true;
		if (size > 0 ) {
			for (int i=0; i < elementData.length; i++) {
				HashEntry current = elementData[i];
				while (current != null) {
					if (!first) result += ", ";
					result += current.data;
					first = false;
					current = current.next;
				}
			}
			result += "]";
		}
		return result;
	}
	
	public void remove(int value) {
		int bucket = hashFunction(value);
		if(elementData[bucket] != null) {
			if (elementData[bucket].data == value) {
				elementData[bucket] = elementData[bucket].next;
				size --;
			}
			else {
				HashEntry current = elementData[bucket];
				while (current.next != null && current.next.data != value) {
					current = current.next;
				}
				if(current.next != null) {
					current.next = current.next.next;
					size --;
				}
			}
		}
	}
	
	private double loadFactor() {
		return (double) size / elementData.length;
	}
	
	public boolean contains(int value) {
		int bucket = hashFunction(value);
		HashEntry current = elementData[bucket];
		while (current != null) {
			if(current.data == value) return true;
			current = current.next;
		}
		return false;
	}
	
	private class HashEntry{
		private int data;
		private HashEntry next;
		
		public HashEntry(int data) {
			this(data, null);
		}
		
		public HashEntry(int data, HashEntry next) {
			this.data = data;
			this.next = next;
		}
		
		public boolean hasNext() {
			if (next != null) return true;
			else return false;
		}
	}
	
	public static void main(String[] args) {
		ArrayIntSet set1 = new ArrayIntSet();
		ArrayIntSet set2 = new ArrayIntSet();
		set1.add(4); set1.add(1); set1.add(5); 
		set2.add(1); set2.add(5); set2.add(3);
		
		
		System.out.println(set1.contentsEqual(set2));
	}

}
