public class ArrayDS<T> implements SequenceInterface<T>, ReorderInterface {
    
    private BagInterface<Integer>[][] array; //the underlying 2-D array (DO WE NEED THE FINAL)
    private int size; //the number of items in the sequence
    private T[] alphabet; //the possible item values (e.g., the decimal digits)
    private T firstItem; //the first item in the sequence
    private T lastItem; //the last item in the sequence


	// USE BREAKPOINTS IN JAVA

	public ArrayDS(T[] alphabet){ //DONE
		this.alphabet = alphabet;
		@SuppressWarnings("unchecked")
		BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length];
		array = temp;
		size = 0;
		firstItem = lastItem = null;
	}

	public ArrayDS(ArrayDS<T> other){ //DONE
		this.alphabet = other.alphabet;
		size = 0;
		@SuppressWarnings("unchecked")
		BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length];
		array = temp;
		for(int i=0; i<other.size; i++){
			append(other.itemAt(i)); 
		}
	}

    /** Add a new Object to the logical end of the SequenceInterface<T>
	 * @param item the item to be added.
	 */

	 /* size is keeping track of the last items positions
	  * app size at the end 
	  */ // DONE
	public void append(T item){
		if(size==0){
			size = 1;
			firstItem = lastItem = item;
		}
		else {
			int row = indexInAlphabet(lastItem);
			int col = indexInAlphabet(item);
			if(array[row][col]==null)
				array[row][col] = new ResizableArrayBag<>();
			array[row][col].add(size);
			size++; 
			lastItem = item;
		}
    }

	// str = StringBuilder("") 
	// str.append(i)
	// return str.-- (this appends to the string) //DONE
	@Override
	public String toString(){
		String str = new String();
		for(int i=0; i<size; i++){
			str += itemAt(i);
		}
		return str;
	}

	/*
	 * int row = lastitem --> integer
	 * int col = item --> integer
	 * array[row][col] <--
	 */

	/** Add a new Object to the logical start of the SequenceInterface<T>
	 * @param item the item to be added.
	 */
	public void prefix(T item){
		ArrayDS<T> temp = new ArrayDS<>(alphabet);
		temp.append(item);
		for(int i=0; i<size; i++){
			temp.append(itemAt(i));
		}
		array = temp.array;
		size = temp.size;
		firstItem = temp.firstItem;
		lastItem = temp.lastItem;
    }

	/** Delete the item at the logical start of the SequenceInterface<T>
	* @return the deleted item
	* @throws EmptySequenceException if the sequence is empty
	// if sequence: abdc, delete a, then decrease all of the numbers in the array by 1, remove the 1 and make the new first item 
	*/
	public T deleteHead(){
		T original = firstItem;

		ArrayDS<T> temp = new ArrayDS<>(alphabet);
		for(int i=1; i<size; i++){
			temp.append(itemAt(i));
		}
		array = temp.array;
		firstItem = temp.firstItem;
		if(size==0){
			throw new EmptySequenceException("nothing");
		}
		else
			size--;
		return original;
    }

	/** Delete the item at the logical end of the SequenceInterface<T>
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public T deleteTail() {
		T original = lastItem;

		ArrayDS<T> temp = new ArrayDS<>(alphabet);
		for(int i=0; i<size-1; i++){
			temp.append(itemAt(i));
		}
		array = temp.array;
		if(size==0)
			throw new EmptySequenceException("nothing");
		else
			size--;
		firstItem = temp.firstItem;
		lastItem = temp.lastItem;
		return original;
    }

	/**
	 * @return true if the SequenceInterface is empty, and false otherwise
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public boolean isEmpty(){
		if(size==0)
			return true;
		return false;
    }

	/**
	 * @return the number of items currently in the SequenceInterface 
	 */ 
	public int size(){
		return size;
    }

	/**
	 * @return the the logically first item in the sequence
	 */ 
	public T first(){
		return firstItem;
    }

	/**
	 * @return the the logically last item in the sequence
	 */ 
	public T last(){
		return lastItem;
    }

	/** Checks if a given sequence is a subsequence of this sequence
	 * @param another the sequence to check
	 * @return true if another is a subsequence of this sequence or false otherwise
	 */ // substring --> find item of the first sequence 
	 // data structures are fun
	 // tures aref
	public boolean hasSubsequence(SequenceInterface<T> another){
		int counter = 0;
		for(int i=0; i<size; i++){
			if(itemAt(i).equals(another.first())){
				for(int j=0; j<another.size(); j++){
					if(itemAt(i+j).equals(another.itemAt(j)))
						counter++;
					else
						break;
				}
			}
		}

		if(counter==another.size())
			return true;
		return false;
    }

	/** Check if a given item comes right before another given item in the sequence
	 * @param first an item
	 * @param second another item
	 * @return true if first comes right before second in the sequence, or false otherwise
	 */
	 // easier to do it directly of (get index of alphabet in first and then second and how you would use the two indices in the arry)
	public boolean predecessor(T first, T second){
		int firstIndex = firstOccurrenceOf(first);
		int secIndex = firstOccurrenceOf(second);
		if(firstIndex+1==secIndex)
			return true;
		return false;
    }

	/** Return the number of occurences in the sequence of a given item
	 * @param item an item
	 * @return the number of occurences in the sequence of item
	 */
	 // have a similar loop as in toString
	public int getFrequencyOf(T item){
		int counter = 0;
		for(int i=0; i<size; i++){
			if(itemAt(i).equals(item))
				counter++;
		}
		if(counter==0)
			return -1;
		return counter;
    }

	/** Reset the SequenceInterface to empty status by reinitializing the variables
	 * appropriately
	 */ 
	public void clear(){
		size = 0;
		firstItem = lastItem = null;
		for(int row=0; row<alphabet.length; row++){
			for(int col=0; col<alphabet.length; col++){
				if(array[row][col] != null)
					array[row][col].clear();
			}
		}
    }

	/** Return the item at a given logical position
	 * @param position the logical position starting from 0
	 * @return the item at logical position position
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()-1
	 */ 
	public T itemAt(int position){
		T result = null;
		if(position==size-1){
			result = lastItem;
		}
		else if(position==0)
			result = firstItem;
		else{
			for(int row=0; row<alphabet.length; row++){
				for(int col=0; col<alphabet.length; col++){
					if(array[row][col]!=null && array[row][col].contains(position+1)){
						result = alphabet[row];
					}
				}
			}
		}
		return result;
    }	

	/** Return an array containing the items in the sequence in their logical order
	 * @return the an array containing the items in the sequence in their logical order
	 *         or null if the sequence is empty
	 */
	public T[] toArray(){
		T[] temp = null;
		if(size==0)
			return null;
		for(int i=0; i<size; i++){
			temp[i] = itemAt(i);
		}
		return temp;
    }

	/** Return the logical position in the sequence of the first occurrence of a given item
	 * @param item an item
	 * @return the logical position in the sequence of the first occurrence of item
	 *         or -1 if the item doesn't exist
	 */ 
	// go through all the rows until item, then you look through that row until you see the first item (return that number)
	public int firstOccurrenceOf(T item){
		for(int i=0; i<alphabet.length; i++){
			if(itemAt(i).equals(item))
				return i;
		}
		return -1;
    }

	/** Return the index of a given item in the alphabet of the sequence
	 * @param item an item
	 * @return the index of item in the alphabet of the sequence
	 *         or -1 if the item doesn't exist
	 */
	 // size is the sequence which is like (abdc) in a alphabet array of (abcd)
	public int indexInAlphabet(T item){
		int result = -1;
		for(int i=0; i<alphabet.length; i++){
			if(alphabet[i].equals(item))
				result = i;
		}
		return result;
    }


	/** Return the index in the alphabet of the next item in the sequence
	 * after the occurrence of item at position
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item after the occurrence of item
	 *         at position
	 *         or -1 if the next item doesn't exist
	 */
	public int nextIndex(T item, int position){
		if(position+1<alphabet.length)
			return indexInAlphabet(itemAt(position+1));
		return -1;
    }

	/** Return the index in the alphabet of the previous item in the sequence
	 *  before the occurrence of item at position
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item before the occurrence of item
	 *         at position
	 *         or -1 if the previous item doesn't exist
	 */
	public int prevIndex(T item, int position){
		if(position-1>0)
			return indexInAlphabet(itemAt(position-1));
		return -1;
    }

    /** Logically reverse the data in the ReorderInterface object so that the item
	 * that was logically first will now be logically last and vice
	 * versa.  The physical implementation of this can be done in
	 * many different ways, depending upon how you actually implemented
	 * your physical ArrayDS<T> class
	 */ // 321 goes to 123 --> initalize a new arrayDS object temp that contains nothing and then start removing items (remove head = 0 first and then add that 0 to the new arrayds and then remove 1 and then prefix the 1, remove the 2 prefix the 2)
	 // at the end switch array variable --> this.array = new.array and switch the first item (while this.object!empty) this.firstItem = ;
	public void reverse(){
		ArrayDS<T> temp = new ArrayDS<>(alphabet);
		for(int i=size-1; i>=0; i--){
			temp.append(itemAt(i));
		}
		array = temp.array;
		firstItem = temp.firstItem;
		lastItem = temp.lastItem;
    }

	/** Remove the logically last item and put it at the
	 * front.  As with reverse(), this can be done physically in
	 * different ways depending on the underlying implementation.
	 */
	public void rotateRight(){
		// ArrayDS<T> temp = new ArrayDS<>(alphabet);
		// temp.append(lastItem);
		// deleteTail();
		// for(int i=0; i<size; i++){
		// 	temp.append(itemAt(i));
		// }
		// array = temp.array;
		// size = temp.size;
		// firstItem = temp.firstItem;
		// lastItem = temp.lastItem;

		prefix(lastItem);
		deleteTail();
    }

	/** Remove the logically first item and put it at the
	 * end.  As above, this can be done in different ways.
	 */
	public void rotateLeft(){
        T temp = firstItem;
		deleteHead();
		append(temp);
    }
}
