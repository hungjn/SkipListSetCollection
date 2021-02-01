import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.lang.reflect.Array;
import java.lang.UnsupportedOperationException;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {

	ArrayList<SkipListSetItem> head = new ArrayList<SkipListSetItem>();
	public int max_height; // max height every skip list item can be
	public int element_count; // tracks number of elements in skip list

	// Creates an empty skiplist set
	public SkipListSet () {
		int i;
		element_count = 0;
		max_height = 3; // minimum height for skiplists will be 3
		
		// Initialize the head array
		for (i = 0; i < max_height; i++) 
			head.add(null);
		
	}
	
	public SkipListSet (Collection<? extends T> c) {
		// Initializes the variables to an empty skiplist, then add each collection value
		int i;
		element_count = 0;
		max_height = 3;
		
		// Initialize the head array
		for (i = 0; i < max_height; i++) 
			head.add(null);

		// Add all the values from the collection into our skiplist
		addAll(c);
		
	}
	
	// Additional print function, just prints all the values in the skiplist line by line
	public void print() {
		Iterator<T> it = iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	// Returns the current size of the skiplist (number of items)
	@Override
	public int size() {
		return element_count;
	}

	@Override
	public boolean isEmpty() {
		return element_count == 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {

		
		int i = max_height - 1;
		ArrayList<SkipListSetItem> temp = new ArrayList<SkipListSetItem>(); // temp pointer
		
		temp = head;
		
		// Traverse each level till you finished traversing the bottom level
		while (i >= 0) { // i will represent the height we are at in the skiplist
			
			// If the element to the right is null, traverse down
			if (temp.get(i) == null) {
				i--;
				continue;
			}
			
			// If the element to the right is the value we are looking for, return true
			if (temp.get(i).value.compareTo((T)o) == 0) {
				return true;
			} 
			
			// If the element to the right is greater than the passed value, traverse down from the current element position
			else if (temp.get(i).value.compareTo((T)o) > 0 || temp.get(i).value == null) {
				i--;
				continue;
			} 
			
			// If the element to the right is less than the passed value, keep traversing right
			else if (temp.get(i).value.compareTo((T)o) < 0){
				temp = temp.get(i).array;
			}
		}
		
		// If you haven't found it in the skiplist return false
		return false;
	}

	@Override
	public Iterator<T> iterator() {

		SkipListSetIterator<T> iterate = new SkipListSetIterator<T>();
		
		return iterate;
	}

	@Override
	public Object[] toArray() {
		int i = 0;
		Object obj[] = new Object[element_count];
		
		Iterator<T> it = iterator();
		
		while (it.hasNext()) {
			obj[i] = it.next();
			i++;
		}
		
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		
		int i = 0;
		Iterator<T> it = iterator();

		if (a.length < element_count) { 
			  a = (E[]) Array.newInstance(a.getClass().getComponentType(), element_count);
		} else if (a.length > element_count) {
			  a[element_count] = null;
		}
		
		while (it.hasNext()) {
			a[i] = (E) it.next();
			i++;
		}
		
		return a;
	}

	
	@Override
	public boolean add(T e) {
		
		if (this.contains(e))
			return false;
		
		int i = max_height - 1;
		ArrayList<SkipListSetItem> temp = new ArrayList<SkipListSetItem>(); // temp pointer
		
		SkipListSetItem item = new SkipListSetItem(e);
		
		temp = head;
		
		while (i >= 0) { // i will represent the height we are at in the skiplist
			
			// If the element to the right is null, check to see if we can add at that height
			if (temp.get(i) == null) {
				if (item.height - 1 >= i) {
					item.array.set(i, temp.get(i));
					temp.set(i, item);
				}
				
				i--;
				continue;
			}
			
			// If the element to the right is greater than the value passed, check to see if we can add at that height
			else if (temp.get(i).value.compareTo(e) > 0 || temp.get(i).value == null) {
				if (item.height - 1 >= i) {
					item.array.set(i, temp.get(i));
					temp.set(i, item);
					i--;
					continue;
					
				} else {
					i--;
					continue;
				}
			} 
			
			// If the element to the right is less than the value passed, continue traversing to the right
			else if (temp.get(i).value.compareTo(e) < 0){
				temp = temp.get(i).array;
			}
		}
		
		// Increment the element_count since we've successfully added
		element_count++;
		
		// If the element_count reaches a power of 2 for max height, increment the max height and add to the head arraylist
		if (element_count >= (Math.pow(2, max_height))) {
			max_height++;
			head.add(null);
		}
		
		return true;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		
		int i = max_height - 1;
		int remove = 0; // tracker for checking if we actually change the skiplist
		ArrayList<SkipListSetItem> temp = new ArrayList<SkipListSetItem>(); // temp pointer
		
		temp = head;
		
		while (i >= 0) { // i will represent the height we are at in the skiplist
			
			// If the element to the right is null, traverse down
			if (temp.get(i) == null) {
				i--;
				continue;
			}
			
			// If the element to the right is the value we are looking to remove, remove it and re-link the nodes to it's left to point to the right of the element we are removing
			if (temp.get(i).value.compareTo((T)o) == 0) {
				remove = 1;
				temp.set(i, temp.get(i).array.get(i));
				i--;
				continue;
			} 
			
			// If the element to the right is greater than the value we are looking for, traverse down
			else if (temp.get(i).value.compareTo((T)o) > 0 || temp.get(i).value == null) {
				i--;
				continue;
			} 
			
			// If the element to the right is less than the value we are looking for, keep traversing right
			else if (temp.get(i).value.compareTo((T)o) < 0){
				temp = temp.get(i).array;
			}
		}
		
		// If we've ever changed the skiplist (successfully removed something), decrement element_count and return true
		if (remove == 1) {
			element_count--;
			
			// If the element_count is a power of 2 of the max_height - 1, that means we need to decrement max height and remove the last index of the head arraylist
			if (element_count <= Math.pow(2, max_height - 1) && max_height != 3) {
				max_height--;
				head.remove(max_height);
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		
		for (Object o : c) {
			if(!contains(o))
				return false;
		} 
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean track = false;
		
		for (T e : c) {
			if(add(e)) {
				track = true;
			}
		}
		return track;
	}
	
	

	@Override
	public boolean retainAll(Collection<?> c) {

		boolean track = false;
		Iterator<T> it = iterator();
		T value;
		
		while (it.hasNext()) {
			value = it.next();
			if (!c.contains(value)) {
				remove(value);
				track = true;
			}
		}
		
		return track;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
	boolean track = false;
		
		for (Object o : c) {
			if(remove(o)) {
				track = true;
			}
		}
		return track;
	}

	@Override
	public void clear() {
		int i;
		while (max_height - 1 >= 0) {
			head.remove(max_height - 1);
			max_height--;
		}
		
		// Reset the initial values of the skiplist
		max_height = 3;
		element_count = 0;
		
		for (i = 0; i < max_height; i++) 
			head.add(null);
	}
	
	@Override
	public boolean equals(Object o) {
		return this.hashCode() == o.hashCode() ? true : false;
	}
	
	@Override
	public int hashCode() {
		Iterator<T> it = iterator();
		int sum = 0;
		
		while (it.hasNext()) {
			sum += it.next().hashCode();
		}
		return sum;
	}

	@Override
	public Comparator<? super T> comparator() {
		return null;
	}

	@Override
	public SortedSet<T> subSet(T fromElement, T toElement) {
		try {
	        throw new UnsupportedOperationException("Invalid operation for sorted list.");
	    } catch (java.lang.UnsupportedOperationException e) {
	        System.out.println("Invalid operation for sorted list.");
	    }
		return null;
	}

	@Override
	public SortedSet<T> headSet(T toElement) {
		try {
	        throw new UnsupportedOperationException("Invalid operation for sorted list.");
	    } catch (java.lang.UnsupportedOperationException e) {
	        System.out.println("Invalid operation for sorted list.");
	    }
		return null;
	}

	@Override
	public SortedSet<T> tailSet(T fromElement) {
		try {
	        throw new UnsupportedOperationException("Invalid operation for sorted list.");
	    } catch (java.lang.UnsupportedOperationException e) {
	        System.out.println("Invalid operation for sorted list.");
	    }
		return null;
	}

	@Override
	public T first() {
		
		return head.get(0) == null ? null : head.get(0).value;
	}

	@Override
	public T last() {
		int i = max_height - 1;
		T value = null;
		ArrayList<SkipListSetItem> temp = new ArrayList<SkipListSetItem>(); // temp pointer
		temp = head;
		
		while (i >= 0) { // i will represent the height we are at in the skiplist
			
			if (temp.get(i) == null) {
				i--;
				continue;
			}
			else {
				value = temp.get(i).value;
				temp = temp.get(i).array;
			}
		}
		return value;
	}
	
	// Generates a height for a new item/node, 50% chance to be 1, 25% chance to be 2, etc.
	public int generate_height () {
		int height = 1;
		Random rand = new Random();
		boolean tf = rand.nextBoolean();
		
		while (tf == true && height < max_height) {
			height++;
			tf = rand.nextBoolean();
		}
		return height;
	}
	
	public void reBalance() {

		Iterator<T> it = iterator();
		T value;
		
		while (it.hasNext()) {
			value = it.next();
			this.remove(value);
			this.add(value);
		}
	}
	
	private class SkipListSetIterator<E extends Comparable<T>> implements Iterator<T> {

		SkipListSetItem temp;
		T value;
		
		public SkipListSetIterator () {
			temp = head.get(0);
		}
		
		@Override
		public boolean hasNext() {
			if (temp == null)
				return false;
			
			return true;
		}

		@Override
		public T next() {
			
			value = temp.value;
			temp = temp.array.get(0);
			return value;
		}
		
		@Override
		public void remove() {
			if (value == null)
				return;
			else {
				SkipListSet.this.remove(value);
				value = null;
			}
		}
	}
	
	class SkipListSetItem {
		
		ArrayList<SkipListSetItem> array = new ArrayList<SkipListSetItem>();
		
		private T value;
		private int height; // How many elements are in the item, last index would be height - 1
		
		public SkipListSetItem (T e) {
			int i;
			
			value = e;
			height = generate_height();
			
			for (i = 0; i < height; i++) 
				array.add(null);
			
		}
	}

}
