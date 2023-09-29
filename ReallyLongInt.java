/** A partial implementation of the ReallyLongInt class.
 * @author Sherif Khattab (Adapted from Dr. John Ramirez's Spring 2017 CS 0445 Assignment 2 code)
 * You need to complete the implementation of the remaining methods.  Also, for this class
 *  to work, you must complete the implementation of the ArrayDS class. See additional comments below.
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
		// an int.  Then add at the end.  Note that
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

	// You must implement the methods below. See the descriptions in the
	// assignment sheet
	// prefix zeros until its the same size (compare the lengths)
	// digits sum
	// remainder = 12-10 = 
	//this.itemAt(first) + rightOp.itemAt(second) + remainder
	// append to teh new ReallyLongInt
	// if remainder at end, append again

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

	// substracting this for the second parameter (this-rightOp) = this object is being decreased as well
	public ReallyLongInt subtract(ReallyLongInt rightOp){
		if(compareTo(rightOp)==-1)
			throw new ArithmeticException("Invalid Difference -- Negative Number");

		int maxSize = Math.max(rightOp.size(), this.size());
		ReallyLongInt answer = new ReallyLongInt(""); 

		int minSize = Math.min(rightOp.size(), this.size());
		ReallyLongInt min = rightOp;
		ReallyLongInt max = this;

		// if(rightOp.size()<size()){
		// 	min = rightOp;
		// 	max = this;
		// }
		// else if(rightOp.size()>size()){
		// 	min = this;
		// 	max = rightOp;
		// }
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

	// first-second (should not be changing this) - actually a very good candidate for a static metgid
	public ReallyLongInt subtract(ReallyLongInt first, ReallyLongInt second){
		return first.subtract(second);
	}
// for loop to go through each item --> check individual sequeunce and when one is greater than the other boolean becomes false
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

	public boolean equals(Object rightOp){
		if(this.compareTo((ReallyLongInt)rightOp)==0)
			return true;
		return false;
	}

	// add zeros o the end
	public ReallyLongInt multTenToThe(int num){
		ReallyLongInt temp = new ReallyLongInt(this.size());
		temp = this;
		for(int i=0; i<num; i++){
			temp.append(0);
		}
		return temp;
	}

	// divide
	public ReallyLongInt divTenToThe(int num){
		ReallyLongInt temp = new ReallyLongInt(this.size());
		temp = this;
		for(int i=0; i<num; i++){
			temp.deleteTail();
		}
		return temp;
	}

	/** Remove leading zeros.
	 *
	 */
	private void removeZeros() 	{
		int i = 0;
		while(i<size() && itemAt(i)==0 && size()>1){
			deleteHead();
		}
	}
}
