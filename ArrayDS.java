/** A class that holds the functions of the 2D array used to store the sequence.
 * @author Insiah Kizilbash
 * Contains the functions for the the 2D array as well as the sequence such adding and removing elements
 * from the array and changing the sequence around as well. It also utilizes the SequenceInterface<T> and 
 * ReorderInterface to work with the sequence.
 * @version  JDK 17.0.8
 */
public class ArrayDS<T> implements SequenceInterface<T>, ReorderInterface {
    
    private BagInterface<Integer>[][] array; //the underlying 2-D array 
    private int size; //the number of items in the sequence
    private T[] alphabet; //the possible item values (e.g., the decimal digits)
    private T firstItem; //the first item in the sequence
    private T lastItem; //the last item in the sequence

	/** Creates an empty 2D array given the possible item values in the seqence. 
	 * 
	 * @param alphabet The possible item values in the sequence
	 */
	public ArrayDS(T[] alphabet){ 
		this.alphabet = alphabet;
		@SuppressWarnings("unchecked")
		BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length];
		array = temp;
		size = 0;
		firstItem = lastItem = null;
	}

	/** Creates an empty 2D array having given an intial sequence. 
	 *  Uses a loop to copy everything in the other parameter into the array.
	 * 
	 * @param other The desired sequence.
	 */
	public ArrayDS(ArrayDS<T> other){ 
		this.alphabet = other.alphabet;
		size = 0;
		@SuppressWarnings("unchecked")
		BagInterface<Integer>[][] temp = new ResizableArrayBag[alphabet.length][alphabet.length];
		array = temp;
		for(int i=0; i<other.size; i++){
			append(other.itemAt(i)); 
		}
	}

    /** Add a new Object to the logical end of the SequenceInterface<T>. 
	 * Checks to see whether there is anything in the sequence or not. 
	 * If there is something in the seqeunce, then go to position in 2D
	 * array based on the lastItem in the sequence and the item given
	 * and then adds to a bag if it already exists or creates a new bag 
	 * if there is nothing in that spot.
	 * @param item the item to be added.
	 */
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

	

	/** Creates a string which contains all the items in the sequence.
	 * Loops through the sequence and adds each item to a string using
	 * itemAt(position).
	 * @return a string containing the sequence.
	 */
	@Override
	public String toString(){
		String str = new String();
		for(int i=0; i<size; i++){
			str += itemAt(i);
		}
		return str;
	}

	/** Add a new Object to the logical start of the SequenceInterface<T>.
	 * Creates a temporary array and adds the item first and then loops through
	 * the sequence to add all the other items after the item using append().
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

	/** Delete the item at the logical start of the SequenceInterface<T>.
	 * Creates a temporary array which then appends all the items of the 
	 * sequence except the original first item and then everything is copied
	 * into the original 2D array.
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
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

	/** Delete the item at the logical end of the SequenceInterface<T>.
	 * Creates a temporary array which then appends all the items of the 
	 * sequence except the original last item and then everything is copied
	 * into the original 2D array.
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

	/** Checks to see if the sequence is empty.
	 * @return true if the SequenceInterface is empty, and false otherwise
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public boolean isEmpty(){
		if(size==0)
			return true;
		return false;
    }

	/** Returns the sequence size.
	 * @return the number of items currently in the SequenceInterface 
	 */ 
	public int size(){
		return size;
    }

	/** Returns the sequence's first item.
	 * @return the the logically first item in the sequence
	 */ 
	public T first(){
		return firstItem;
    }

	/** Returns the sequence's last item.
	 * @return the the logically last item in the sequence
	 */ 
	public T last(){
		return lastItem;
    }

	/** Checks if a given sequence is a subsequence of this sequence.
	 * Loops through the sequence and if the item is the same as the subsequence's
	 * first item, then you loop through the subsequence and compare the two 
	 * sequences. There is a counter to track if the subsequence exists within the 
	 * sequence.
	 * @param another the sequence to check
	 * @return true if another is a subsequence of this sequence or false otherwise
	 */ 
	public boolean hasSubsequence(SequenceInterface<T> another){
		int counter = 0;
		for(int i=0; i<size; i++){
			if(itemAt(i).equals(another.first())){
				for(int j=0; j<another.size(); j++){
					/** When comparing the sequence, since we can't start at the first index again, 
					 * we have to add j or the subsequences index to the sequence index to make sure
					 * the sequence is being comparec correctly. 
					 */
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

	/** Check if a given item comes right before another given item in the sequence.
	 * @param first an item
	 * @param second another item
	 * @return true if first comes right before second in the sequence, or false otherwise
	 */
	public boolean predecessor(T first, T second){
		int firstIndex = firstOccurrenceOf(first);
		int secIndex = firstOccurrenceOf(second);
		if(firstIndex+1==secIndex)
			return true;
		return false;
    }

	/** Return the number of occurences in the sequence of a given item.
	 * Loops through the sequence and if it is the given item, a counter is 
	 * increased to keep track of the number of occurances.
	 * @param item an item
	 * @return the number of occurences in the sequence of item
	 */
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

	/** Reset the SequenceInterface to empty status by reinitializing the variables.
	 *  appropriately. Loops through the 2D array and clears out all the bags.
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

	/** Return the item at a given logical position.
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
			/** loops through the 2D array and when the bag is not empty and contains the
			 * position, then the row item of that position is set to return.
			*/
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

	/** Return an array containing the items in the sequence in their logical order.
	 * Loops through the sequence and puts all the items of the sequence in a temporary
	 * array which is later returned.
	 * @return the an array containing the items in the sequence in their logical order
	 * or null if the sequence is empty
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

	/** Return the logical position in the sequence of the first occurrence of a given item.
	 * Loops through the logical positions in the sequence to see where the item is first found.  
	 * @param item an item
	 * @return the logical position in the sequence of the first occurrence of item
	 * or -1 if the item doesn't exist.
	 */ 
	public int firstOccurrenceOf(T item){
		for(int i=0; i<alphabet.length; i++){
			if(itemAt(i).equals(item))
				return i;
		}
		return -1;
    }

	/** Return the index of a given item in the alphabet of the sequence. Loops through
	 * the alphabet array to check to see if the item exists within the possible given item values.
	 * @param item an item
	 * @return the index of item in the alphabet of the sequence
	 * or -1 if the item doesn't exist.
	 */
	public int indexInAlphabet(T item){
		int result = -1;
		for(int i=0; i<alphabet.length; i++){
			if(alphabet[i].equals(item))
				result = i;
		}
		return result;
    }


	/** Return the index in the alphabet of the next item in the sequence
	 * after the occurrence of item at position.
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item after the occurrence of item
	 * at position or -1 if the next item doesn't exist.
	 */
	public int nextIndex(T item, int position){
		if(position+1<alphabet.length)
			return indexInAlphabet(itemAt(position+1));
		return -1;
    }

	/** Return the index in the alphabet of the previous item in the sequence
	 *  before the occurrence of item at position.
	 * @param item an item
	 * @param position item's position
	 * @return the index in the alphabet of the item before the occurrence of item
	 * at position or -1 if the previous item doesn't exist.
	 */
	public int prevIndex(T item, int position){
		if(position-1>0)
			return indexInAlphabet(itemAt(position-1));
		return -1;
    }

    /** Logically reverse the data in the ReorderInterface object so that the item
	 * that was logically first will now be logically last and vice versa. Loops
	 * through the sequence backwards and appends each item to a new temporary 
	 * array which is then copied into the current (this) object.
	 */
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
	 * front. 
	 */
	public void rotateRight(){
		prefix(lastItem);
		deleteTail();
    }

	/** Remove the logically first item and put it at the
	 * 	end.
	 */
	public void rotateLeft(){
        T temp = firstItem;
		deleteHead();
		append(temp);
    }
}
