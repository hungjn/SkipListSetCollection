
/**
 * Test cases for SkipListSet. These tests are still in progress and 
 * are not guaranteed to accurately reflect what the assignment requires
 * 
 * To run, save this document in the same folder as your SkipListSet.java file
 * In your terminal, go to the folder location and run
 * 		javac Main.java SkipListSet.java
 * If there are no errors run
 * 		java Main SkipListSet
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
public class TestCases {
		public static void main(String[] a) {
			SkipListSet<Integer> header = new SkipListSet<Integer>();
			// Test the constructor returns a SkipListSet
			if (header != null && header instanceof SkipListSet ){
				System.out.println("PASSED!! The no argument constructor works");
			} else{
				System.out.println("FAILED!! The no argument constructor did not work");
			}

			// Test the second constructor returns a SkipList Set
			ArrayList<Integer> set= new ArrayList<Integer>();
			set.add(7);
			set.add(2);
			set.add(5);
			set.add(3);
			header = new SkipListSet<Integer>(set);
			if (header != null && header instanceof SkipListSet) {
				System.out.println("PASSED!! The Collection argument constructor works");
			} else {
				System.out.println("FAILED!! The Collection argument constructor did not work");
			}
			if(header.size() == 4){
				System.out.println("PASSED!! The SkipList has the correct number of items");
			} else {
				System.out.println("FAILED!! The skiplist does not have the correct number of items");
			}

			// Test that iterator() makes an iterator 
			Iterator<Integer> i = header.iterator();
			try{
				if (i != null && i instanceof Iterator){
					System.out.println("PASSED!! The Iterator was created!");
				} else {
					System.out.println("FAILED!! The iterator was not created. You'll need to make this to get everything else to work");
				}

				// test iterator.hasNext() on a SkipList
				if (i.hasNext())
					System.out.println("PASSED!! iterator.hasNext() passed true");
				else
					System.out.println("FAILED!! iterator.hasNext() did not pass true");

				// Test iterator.next() works correctly and that values were added in order
				if (i.next() == 2)
					System.out.println("PASSED!! iterator.next() returned 2");
				else
					System.out.println("FAILED!! iterator.next() did not return 2");
				if(i.next() == 3 && i.next() ==5 && i.next() == 7){
					System.out.println("PASSED!! The constructor set all values into the correct order");
				}
				else
					System.out.println("FAILED!! The constructor did not set the values in the correct order");

				// Test that iterator.remove() removes whatever iterator.next() last returned
				i = header.iterator();
				i.next();
				i.remove();
				i = header.iterator();
				if (i.next() == 3)
					System.out.println("PASSED!! iterator.remove() removed 2");
				else
					System.out.println("FAILED!! iterator.remove() did not remove 2");
			}catch (Exception e){
				System.out.println("WARNING!! Iterator is not implemented. Some tests will not work properly");
			}
			// Test isEmpty() against an empty set
			header = new SkipListSet<Integer>();
			if (header.isEmpty())
				System.out.println("PASSED!! isEmpty() returned true on an empty set");
			else
				System.out.println("FAILED!! isEmpty() did not return true on an empty set");

			// Test add() returns correct values
			if (header.add(43))
				System.out.println("PASSED!! add() returned true after adding");
			else
				System.out.println("FAILED!! add() did not return true after adding");
			// Test add() does not save repeat values
			if (!header.add(43))
				System.out.println("PASSED!! add() returned false after trying to add 43 again");
			else
				System.out.println("FAILED!! add() did not return false after trying to add 43 again");


			// Test isEmpty() on a non-empty set
			if (!header.isEmpty())
				System.out.println("PASSED!! isEmpty() returned false on a non-empty set");
			else
				System.out.println("FAILED!! isEmpty() did not return false on a non-empty set");
			// Test that add does not isert duplicates
			header.add(47);
			try{
				i = header.iterator();
				i.next();
				if (i.next() == 47)
					System.out.println("PASSED!! iterator.next() only returned 43 once");
				else
					System.out.println("FAILED!! iterator.next() did not return 47");
				// Test iterator.hasNext() returns false at end of list
				// THis will not work correctly if the previous tests failed
				if (!i.hasNext())
					System.out.println("PASSED!! iterator.hasNext() returned false");
				else
					System.out.println("FAILED!! iterator.hasNext did not return false");
			}catch(Exception e){
				System.out.println("WARNING!! Iterator is not implemented. Some tests will not work properly");	
			}
				// Test addAll() returns correct value
			if (header.addAll(set))
				System.out.println("PASSED!! addAll() returned true");
			else
				System.out.println("FAILED!! addAll() did not return true");
			// Test all values were added and are in order	
			try{
				i = header.iterator();
				if (i.next() == 2 && i.next() == 3 && i.next() == 5 && i.next() == 7 && i.next() == 43)
					System.out.println("PASSED!! All added values are in expected order");
				else
					System.out.println("FAILED!! Values are not in expected order");
				// Test clear()
				header.clear();
				i = header.iterator();
				if (!i.hasNext())
					System.out.println("PASSED!! iterator.hasNext() returned false after using clear()");
				else
					System.out.println("FAILED!! iterator.hasNext() did not return false after using clear()");
			} catch (Exception e){
				System.out.println("WARNING!! Iterator is not implemented. Some tests will not work properly");
			}
				// Test contains()
			header.add(43);
			if (header.contains(43))
				System.out.println("PASSED!! contains() returned true when searching for 43");
			else
				System.out.println("FAILED!! contains() returned false when searching for 43");
			
			// Test contains()	
			if (!header.contains(47))
				System.out.println("PASSED!! contains() returned false when searching for 47");
			else
				System.out.println("FAILED!! contains() returned true when searching for 47");
			// Test containsAll()
			header.add(47);
			set.clear();
			set.add(43);
			set.add(47);
			if (header.containsAll(set))
				System.out.println("PASSED!! containsAll() returned true when searching for 43 and 47");
			else
				System.out.println("FAILED!! contains() returned false when searching for 43 and 47");
			// Test containsAll()
			set.add(52);
			if (!header.containsAll(set))
				System.out.println("PASSED!! containsAll() returned false when searching for 43,47, and 52");
			else
				System.out.println("FAILED!! contains() returned true when searching for 43, 47, and 52");
			// Test equals()
			if (header.equals(header))
				System.out.println("PASSED!! equals() returned true when comparing a SkipList set to itself");
			else
				System.out.println("FAILED!! equals() returned false when comparing a SkipListSet to itself");
			SkipListSet<Integer> x = new SkipListSet<Integer>();
			// Test equals()
			if (!header.equals(x))
				System.out.println("PASSED!! equals() returned false when comparing two different SkipListsSets");
			else
				System.out.println("FAILED!! equals() returned true when comparing two different SkipListsSets");
			// Test hashCode()
			header.clear();
			if (header.hashCode() == 0)
				System.out.println("PASSED!! hashCode() returned 0 for an empty set");
			else
				System.out.println("FAILED!! hashCode() did not return 0 for an empty set");
			// Test hashCode()	
			header.addAll(set);
			if (header.hashCode()!= 0)
				System.out.println("PASSED!! hashCode() returned a non-zero value for a filled set");
			else
				System.out.println("FAILED!! hashCode() returned 0 for a filled set");
			// Test hashCode()
			if (header.hashCode() == header.hashCode())
				System.out.println("PASSED!! hashCode() returned the same value for the same set");
			else
				System.out.println("FAILED!! hashCode() returned different values for the same sets");

			if (header.remove(52))
				System.out.println("PASSED!! remove() returned true after being passed a value in the set");
			else
				System.out.println("FAILED!! remove() returned false after being passed a value in the set");
			if (!header.remove(1))
				System.out.println("PASSED!! remove() returned false after being passed a value that is not in the set");
			else
				System.out.println("FAILED!! remove() returned true after being passed a value that is not in the set");

			i = header.iterator();
			if (!header.contains(52))
				System.out.println("PASSED!! remove() successfully removed 52");
			else
				System.out.println("FAILED!! remove() did not remove 52");
			set.add(5);
			set.add(12);
			set.add(1000);
			set.add(34);
			set.add(1176581);
			set.add(55);
			header.addAll(set);
			set.clear();
			set.add(47);
			set.add(43);
			if (header.removeAll(set))
				System.out.println("PASSED!! removeAll() returned true when passed a set where all values are in the SkipListSet");
			else
				System.out.println("FAILED!! removeAll() returned false when passed a set where all values are in the SkipListSet");
			set.add(52);
			set.add(5);
			if (header.removeAll(set))
				System.out.println("PASSED!! removeAll() returned true when passed a set where some of the values are in the SkipList");
			else
				System.out.println("FAILED!! removeAll() returned false when passed a set where some of the values are in the skiplist");
			if (!header.removeAll(set))
				System.out.println("PASSED!! removeAll() returned false when passed a set where none of the values are in the Skiplist");
			else
				System.out.println("FAILED!! removeAll() returned true when passed a set where none of the values are in the SkipList");
			if (!(header.contains(5) || header.contains(43) || header.contains(47)))
				System.out.println("PASSED!! All desired values were removed with removeAll()");
			else
				System.out.println("FAILED!! removeAll() did not remove every desired value");
			header.addAll(set);
			set.clear();
			set.add(47);
			set.add(5);
			if (header.retainAll(set))
				System.out.println("PASSED!! retainAll() returned true when passed a set that changes the SkipListSet");
			else
				System.out.println("FAILED!! retainAll() returned false when passed a set that changes the SkipListSet");
			if (!header.retainAll(set))
				System.out.println("PASSED!! retainAll() returned false when passed a set that doesn't change the SkipListSet");
			else
				System.out.println("FAILED!! retainAll() returned true when passed a set that doesn't change the SkipListSet");
			if (header.containsAll(set))
				System.out.println("PASSED!! The skiplist contains all elements that should be in the set after retainsAll()");
			else
				System.out.println("FAILED!! The SkipListSet is missing some values that should still be in the set after using retainsAll()");

			if(!(header.contains(12) || header.contains(34) || header.contains(34) || header.contains(43)
					|| header.contains(52) || header.contains(55) || header.contains(1000) || header.contains(1176681))){
						System.out.println("PASSED!! The skiplist contains no elements that shouldn't be in the set after retainsAll()");
					}
					else
						System.out.println(
								"FAILED!! The SkipListSet has some values that should not still be in the set after using retainsAll()");
			if (header.size() == 2){
				System.out.println("PASSED!! size() correctly returned 2");

			}else{
				System.out.println("FAILED!! size() did not return 2");

			}
			header.add(22);
			header.add(33);
			header.add(42);
			header.add(12);
			if (header.first() == 5) {
				System.out.println("PASSED!! first() returned the first element 5");

			} else {
				System.out.println("FAILED!! first() did not return the first element 5");

			}
			if (header.last() == 47) {
				System.out.println("PASSED!! last() returned the last element 47");

			} else {
				System.out.println("FAILED!! last() did not return the last element 47");

			}

			Object[] arr = header.toArray();
			if (arr.length == header.size()) {
				System.out.println("PASSED!! toArray() returned an array with length = size()");

			} else {
				System.out.println("FAILED!! toArray() returned an array with length != size()");

			}
			if ((Integer)arr[0] ==5 && (Integer)arr[1] ==12 && (Integer)arr[2] == 22 && (Integer)arr[3] == 33 && (Integer)arr[4] == 42 && (Integer)arr[5] == 47)  {
				System.out.println("PASSED!! The array has all the elements");

			} else {
				System.out.println("FAILED!! The array does not have all the elements");

			}
			arr = header.toArray(new Integer[3]);
			if (arr.length == header.size()) {
				System.out.println("PASSED!! toArray(T[]arg0) increased a smaller array into the right size");

			} else {
				System.out.println("FAILED!! toArray(T[]arg0) did not increase the smaller array to the right size");

			}
			if ((Integer) arr[0] == 5 && (Integer) arr[1] == 12 && (Integer) arr[2] == 22 && (Integer) arr[3] == 33
					&& (Integer) arr[4] == 42 && (Integer) arr[5] == 47) {
				System.out.println("PASSED!! The array has all the elements");

			} else {
				System.out.println("FAILED!! The array does not have all the elements");

			}
			arr = header.toArray(new Integer[7]);
			if (arr.length == 7) {
				System.out.println("PASSED!! toArray(T[]arg0) did not change the length of a larger array");

			} else {
				System.out.println("FAILED!! toArray(T[]arg0) changed the length of the larger array");

			}
			if (arr.length == 7 && arr[6] == null) {
				System.out.println("PASSED!! Excess indices are null");

			} else {
				System.out.println("FAILED!! Excess indices are either not null or do not exist");
			}
			Random r = new Random();
			for (int j = 0; j < 10; j++){
				header.add(r.nextInt() % 100);
			}
			header.print();

	}
}
			
