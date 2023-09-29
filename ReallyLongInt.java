/** A implementation of the ReallyLongInt class. 
 * @author Sherif Khattab (Adapted from Dr. John Ramirez's Spring 2017 CS 0445 Assignment 2 code) & Insiah Kizilbash
 * Contains the functions that can be done with a really long integer from adding to dividing by tens. Utilizes the 
 * ArrayDS<Integer> to use the ReallyLongInt as a sequence of digits in addition to the Comparable<T> interface.
 * @version 
 */
 public class ReallyLongInt extends ArrayDS<Integer> implements Comparable<ReallyLongInt> 
{
	// Instance variables are inherited.  You may not add any new instance variables
	private static final Integer[] DIGITS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	private ReallyLongInt(int size) {

		super(DIGITS);
		for (int i = 0; i < size; i++) {
			append(0);
		}
	}

	/**
	 * @param s a string representing the integer (e.g., "123456") with no leading
	 * zeros except for the special case "0".
	 * Note that we are adding the digits here to the END. This results in the
	 * MOST significant digit first. It is assumed that String s is a valid representation of an
	 * unsigned integer with no leading zeros.
	 * @throws NumberFormatException if s contains a non-digit
	 */
	public ReallyLongInt(String s)
	{
		super(DIGITS);
		char c;
		Integer digit;
		// Iterate through the String, getting each character and converting it into
		// an int. Then add at the end.  Note that
		// the append() method (from ArrayDS) adds at the end.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				append(digit);
			}
			else
				throw new NumberFormatException("Illegal digit " + c);
		}
	}



	/** Simple call to super to copy the items from the argument ReallyLongInt into a new one.
	 * @param rightOp the object to copy
	 */
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}

	/** Finds the sum between the current ReallyLongObject and the rightOp
	 *  including adding and then removing zeros to make them the same size.
	 * @param rightOp
	 * @return the sum between the current ReallyLongInt object and the rightOp.
	 */
	public ReallyLongInt add(ReallyLongInt rightOp) {
		int maxSize = Math.max(rightOp.size(), this.size());
		ReallyLongInt answer = new ReallyLongInt(""); 

		int minSize = Math.min(rightOp.size(), this.size());
		ReallyLongInt min = rightOp;
		ReallyLongInt max = this;

		if(rightOp.size()<size()){ 
			min = rightOp;
			max = this;
		}
		else if(rightOp.size()>size()){
			min = this;
			max = rightOp;
		}
		for(int i=0; i<maxSize-minSize; i++){
			min.prefix(0);
		}
		
		int remainder = 0;
		for(int i=maxSize-1; i>=0; i--){
			int ans = max.itemAt(i) + min.itemAt(i) + remainder;
			if(ans >= 10){
				remainder = 1;
				ans -= 10;
			}
			else {
				remainder = 0;
			}
			answer.append(ans);
		}
		if(remainder==1)
			answer.append(1);

		answer.reverse();
		min.removeZeros();

		return answer;
	}

	
	/** Subtracting the rightOp from the current ReallyLongObject including (should i use this?)
	 *  adding and then removing zeros to make them the same size to 
	 *  subtract correctly.
	 * @param rightOp The object given to subtract from this.
	 * @return the difference between the current ReallyLongInt object and the rightOp.
	 */
	public ReallyLongInt subtract(ReallyLongInt rightOp){
		if(compareTo(rightOp)==-1)
			throw new ArithmeticException("Invalid Difference -- Negative Number");

		int maxSize = Math.max(rightOp.size(), this.size());
		ReallyLongInt answer = new ReallyLongInt(""); 

		int minSize = Math.min(rightOp.size(), this.size());
		ReallyLongInt min = rightOp;
		ReallyLongInt max = this;

		for(int i=0; i<maxSize-minSize; i++){
			min.prefix(0);
		}

		int carry = 0;
		for(int i=maxSize-1; i>=0; i--){
			int first = max.itemAt(i) + carry;
			int second = min.itemAt(i);
			if(first<second){
				first += 10;
				carry = -1;
			}
			int ans = first - second;
			answer.append(ans);
		}

		answer.reverse();
		min.removeZeros();
		answer.removeZeros();
		return answer;
	}

	/** Subtracting the second parameter from the first parameter
	 *  adding and then removing zeros to make them the same size to 
	 *  subtract correctly. Utilizes the subtract(ReallyLongInt obj).
	 * @param first An integer given. 
	 * @param second An integer given to subtract from the first parameter.
	 * @return the difference between the first and second parameters.
	 */
	public ReallyLongInt subtract(ReallyLongInt first, ReallyLongInt second){
		return first.subtract(second);
	}

	/** Compares the given paramter with the current ReallyLongInt and then
	 * based on whether the paramter is less than (1), equal to (0), or 
	 * greater than (-1) the current object, a integer is returned.
	 * @param rOp Given ReallyLongInt object to be compared.
	 * @return an integer (-1, 0, 1) based on the comparison.
	 */
	public int compareTo(ReallyLongInt rOp){
		if(size()>rOp.size())
			return 1;
		else if(size()<rOp.size())
			return -1;
		else if(size()==rOp.size()){
			for(int i=0; i<size(); i++){
				if(itemAt(i)>rOp.itemAt(i))
					return 1;
				else if(itemAt(i)<rOp.itemAt(i))
					return -1;
			}
		}
		return 0;
	}

	/** Determines whether the current ReallyLongInt is equal to the given 
	 *  parameter object.
	 * @param rightOp The object given to compare to the current ReallyLongInt.
	 * @return Whether the values of the integers are equal or not.
	*/
	public boolean equals(Object rightOp){
		if(this.compareTo((ReallyLongInt)rightOp)==0)
			return true;
		return false;
	}

	/** Multiplies the current ReallyLongInt to 10 to the power of the number which 
	 * is the given parameter.
	 * @param num The power to 10 that is desired.
	 * @return A new ReallyLongInt which is the current ReallyLongInt
	 * multiplied by 10 to the power of the given parameter num.
	 */
	public ReallyLongInt multTenToThe(int num){
		ReallyLongInt temp = new ReallyLongInt(this.size());
		temp = this;
		for(int i=0; i<num; i++){
			temp.append(0);
		}
		return temp;
	}

	/** Divides the current ReallyLongInt by 10 to the power of the number which 
	 * is the given parameter.
	 * @param num The power to 10 that is desired.
	 * @return A new ReallyLongInt which is the current ReallyLongInt
	 * divided by 10 to the power of the given parameter num.
	 */
	public ReallyLongInt divTenToThe(int num){
		ReallyLongInt temp = new ReallyLongInt(this.size());
		temp = this;
		for(int i=0; i<num; i++){
			temp.deleteTail();
		}
		return temp;
	}

	/** Removes the leading zeros in the current ReallyLongInt object. 
	 * Utilizes the deleteHead() which is located in the parent ArrayDS class.
	 */
	private void removeZeros() 	{
		int i = 0;
		while(i<size() && itemAt(i)==0 && size()>1){
			deleteHead();
		}
	}
}
