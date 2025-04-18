package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		
		/* IMPLEMENT THIS METHOD */
		BigInteger result = new BigInteger();

		integer.trim(); //get rid of any whitespace before and after string right off bat

		boolean numStart = false;
		DigitNode front = null;

		if (integer.charAt(0) == '+' || integer.charAt(0) == '-'){
			result.negative = (integer.charAt(0) == '-') ? true : false;
			integer = integer.substring(1, integer.length());
		}

		for (int i = 0; i < integer.length(); i++){

			if (!Character.isDigit(integer.charAt(i))){
				front = null;
				break;
			}

			if (integer.charAt(i) == '0' && !numStart){
				continue;
			}
			
			numStart = true;
			DigitNode node = new DigitNode(Character.getNumericValue(integer.charAt(i)), null);
			node.next = front;
			front = node;
			result.numDigits++;
		}


		result.front = front;
		return result;
    }
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		
		/* IMPLEMENT THIS METHOD */
		
		if (!isFirstLargerInteger(first, second)){ //see if second is larger by absolute value
			BigInteger temp = first;
			first = second;
			second = temp;
		}

		//if first and second are both + or -, we add. If not, we subtract
		BigInteger result = (first.negative == second.negative) ? addNums(first, second) 
			: subtractNums(first, second);

		result.negative = (first.negative) ? true : false; //since first is always larger num

		return result;
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		/* IMPLEMENT THIS METHOD */
		BigInteger finalProduct = new BigInteger(); //final BigInteger to return
		int zeroCounter = 0; //counter that tracks how many 0s we need to add to next product

		if (!isFirstLargerInteger(first, second)){ //see if second is larger by absolute value
			BigInteger temp = first;
			first = second;
			second = temp;
		} 

		DigitNode secondCrnt = second.front;
		while (secondCrnt != null){ //cycle through digits in smaller number

			int carryNum = 0; //holds number to carry into next operation
			DigitNode firstCrnt = first.front; //dummy node to cycle through digits in first to multiply
			BigInteger productTemp = new BigInteger(); //holds temp products that we add to finalProduct
			DigitNode productCrntDigit = null; 

			for (int i = 0; i < zeroCounter; i++){ //add 0s to end of next product
				DigitNode zero = new DigitNode(0, null);
				if (i == 0){
					productTemp.front = zero;
					productCrntDigit = productTemp.front;
				} else {
					productCrntDigit.next = zero;
					productCrntDigit = productCrntDigit.next;
				}
			}

			while (firstCrnt != null){ //multiply each digit in first number with current digit in second

				int total = secondCrnt.digit * firstCrnt.digit + carryNum;
				if (total > 9){ //see if we need to carry
					carryNum = total / 10; //get amount to carry to next digit
					total %= 10;
				} else { carryNum = 0; }

				DigitNode digit = new DigitNode(total, null); //create new node to add to current product
				if (productTemp.front == null){ 
					productTemp.front = digit;
					productCrntDigit = digit;
				} else {
					productCrntDigit.next = digit;
					productCrntDigit = productCrntDigit.next;
				}
				
				firstCrnt = firstCrnt.next;
			}

			if (carryNum > 0){
				DigitNode digit = new DigitNode(carryNum, null);
				productCrntDigit.next = digit;
				productCrntDigit = productCrntDigit.next;
			}
			//now add current product to final Product
			if (zeroCounter == 0){ //if second is only 1 digit long, just assign finalProduct to temp
				finalProduct = productTemp;
			} else {
				finalProduct = add(finalProduct, productTemp); //add temp product to final
			}
			zeroCounter++; //increment counter so we add a 0 to next temp product
			secondCrnt = secondCrnt.next;
		}
		//check if our product will be negative or not
		finalProduct.negative = (first.negative == second.negative) ? false : true;
		return finalProduct;
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}

	/**
	 * Helper method to see which BigInteger has more digits or by absolute value if same num of digits.
	 * Swaps references of first and second if second has more digits or larger absolute value if both have same
	 * num of digits.
	 * @param first
	 * @param second
	 */
	private static boolean isFirstLargerInteger(BigInteger first, BigInteger second){

		if (second.numDigits > first.numDigits) { //have the one with more digits be the number on top
			return false;
		}
		else if (first.numDigits == second.numDigits) { //if same amount of digits, check which number is larger
			DigitNode dummy1 = first.front;
			DigitNode dummy2 = second.front;
			boolean firstIsGreater = true;
			while (dummy1 != null){ //cycle through digit by digit to see which of first and second is larger
				if (dummy1.digit > dummy2.digit){
					firstIsGreater = true;
				}
				else if (dummy2.digit > dummy1.digit){
					firstIsGreater = false;
				}
				dummy1 = dummy1.next;
				dummy2 = dummy2.next;
			}
			return firstIsGreater;
		}
		
		return true;
	}

	/**
	 * private static method that focuses on adding BigIntegers. 
	 */
	private static BigInteger addNums(BigInteger first, BigInteger second){

		BigInteger sum = new BigInteger();

		DigitNode crnt = null; //dummy node that will iteravely add nums
		int carryNum = 0;

		while (first.front != null){ 

			int total = 0; //var to hold each addition

			if (second.front == null){ //if we just have the first number left
				total = first.front.digit + carryNum;
				if (total <= 9){
					carryNum = 0;
				}
				crnt.next = new DigitNode(total, null);
				crnt = crnt.next;
				sum.numDigits++;
				first.front = first.front.next;
				continue;
			}

			total = first.front.digit + second.front.digit + carryNum; //add the two together

			if (total > 9){ //carry to next operation
				carryNum = 1;
				total %= 10;
			} else { carryNum = 0; } //else reset to 0

			if (crnt == null){ //if this is first node in result
				crnt = new DigitNode(total, null);
				sum.front = crnt;
			} else { //else add node like normal
				crnt.next = new DigitNode(total, null);
				crnt = crnt.next;
			}

			sum.numDigits++;
			first.front = first.front.next;
			second.front = second.front.next;
		}
		if (carryNum == 1){ //if we have leftover carry
			crnt.next = new DigitNode(1, null);
			sum.numDigits++;
		}
		return sum;
	}

	/**
	 * Helper method to subtract BigIntegers
	 */
	private static BigInteger subtractNums(BigInteger first, BigInteger second){

		BigInteger difference = new BigInteger();

		DigitNode crnt = null; //dummy node that will iteravely add nums
		boolean borrow = false; //lets us know when to borrow from next digit in first
		DigitNode lastNonZero = null;

		while (first.front != null){ 

			int total = 0; //var to hold each difference

			if (second.front == null){ //if we just have the first number left

				if (borrow){ //if we need to borrow
					if (first.front.digit == 0){ //if we have a 0, set it to 9
						total = 9;
					} else {
						borrow = false;
						total = first.front.digit - 1;
					}
				} else {
					total = first.front.digit;
				}

				crnt.next = new DigitNode(total, null);
				crnt = crnt.next;
				if (total != 0){
					lastNonZero = crnt;
				}
				difference.numDigits++;
				first.front = first.front.next;
				continue;
			}

			if (borrow){ //if the previous digit had to borrow, subtract 1
				first.front.digit -= 1;
			}
			//if first digit larger than ssecond, add 10 to first digit and borrow from next digit in first
			if (first.front.digit < second.front.digit){ 
				borrow = true;
				first.front.digit += 10;
			} else { borrow = false; } //else set it to false, no borrowing

			total = first.front.digit - second.front.digit;

			if (crnt == null){ //if this is first node in result
				crnt = new DigitNode(total, null);
				difference.front = crnt;
				lastNonZero = crnt;
			} else { //else add node like normal
				crnt.next = new DigitNode(total, null);
				crnt = crnt.next;
				if (total != 0){
					lastNonZero = crnt;
				}
			}

			difference.numDigits++;
			first.front = first.front.next;
			second.front = second.front.next;
		}
		lastNonZero.next = null;
		if (difference.front.next == null && difference.front.digit == 0){
			difference.negative = false;
		}
		return difference;
	}
}
