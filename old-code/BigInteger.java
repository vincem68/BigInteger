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
		int digit = 0;
		
		if (integer.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		BigInteger biginteger = new BigInteger();
		if (integer.charAt(0) == ' ' && integer.length() >= 2) {
			boolean allSpaces = true;
			for (int i = 0; i < integer.length(); i++) {
				if (integer.charAt(i) != ' ') {
					allSpaces = false;
				}
			}
			if (allSpaces == false) {
				while (integer.length() >= 1 && integer.charAt(0) == ' ') {
				integer = integer.substring(1, integer.length());
			    }
				
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		if (integer.length() >= 2 && integer.charAt(integer.length()-1) == ' ') {
			while (integer.charAt(integer.length()-1) == ' ' && integer.length() >= 2) {
				integer = integer.substring(0, integer.length()-1);
			}
			}
			
		if (integer.length() >= 2 && integer.charAt(0) == '-' && integer.charAt(1) != '+') {
			biginteger.negative = true; 
			integer = integer.substring(1, integer.length());
			
		}
		
		if (integer.charAt(0) == '+' && integer.length() >= 2) {
			integer = integer.substring(1, integer.length());
			
		}
		
		if (integer.charAt(0) == '0' && integer.length() >= 2) {
			
			while (integer.length() >= 1 && integer.charAt(0) == '0') {
				integer = integer.substring(1, integer.length());
				
			}
			
		}
		
		if (integer.length() == 1 && integer.charAt(0) == '0') {
			biginteger.negative = false;
		}
		
		
		
		for (int i = 0; i < integer.length(); i++) {
			//next = null;
			boolean isDigit = Character.isDigit(integer.charAt(i));
			digit = Character.getNumericValue(integer.charAt(i));
			if (isDigit == true) {
				DigitNode new_node = new DigitNode(digit, biginteger.front); 
				biginteger.front = new_node;
					
					// add new node and switch positions
					
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		
		// following line is a placeholder for compilation
		biginteger.numDigits = integer.length();
		//System.out.println(biginteger.numDigits);
		return biginteger;
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
		
		// following line is a placeholder for compilation
		
		BigInteger biginteger = new BigInteger();
		int digit = 0;
		boolean rem = false;
	//	boolean firstBigger = false; boolean secondBigger = false;
		int holder = 0;
		int secondHolder = 0;
		//int lastDigit1, lastDigit2;
		DigitNode crnt1 = first.front; DigitNode crnt2 = second.front; 
	//	DigitNode prev1 = null; DigitNode prev2 = null;
		
		// maybe put the negative statements over here
		
		
		if (first.negative == true && second.negative == true) {
			biginteger.negative = true;
		}
		if (first.negative == false && second.negative == false) {
			biginteger.negative = false;
		}
		if (first.negative == true && second.negative == false) {
			if (first.numDigits > second.numDigits) {
				biginteger.negative = true;
			}
			if (first.numDigits < second.numDigits) {
				biginteger.negative = false;
			}  
		}
		if (first.negative == false && second.negative == true) {
			if (first.numDigits > second.numDigits) {
				biginteger.negative = false;
			}
			if (first.numDigits < second.numDigits) {
				biginteger.negative = true;
			} 
		}
		
		while (crnt1 != null && crnt2 != null) {
			if ((first.negative == true && second.negative == true) || (first.negative == false && second.negative == false)) {
				digit = crnt1.digit + crnt2.digit;
				if (rem == true) {
					digit += 1;
				}
				if (digit > 9) {
					rem = true;
					digit = digit % 10;
				} else {
					rem = false;
				}
				DigitNode new_node = new DigitNode(digit, null);
				if (biginteger.front == null) {
					biginteger.front = new_node;
					biginteger.numDigits++;
					
				} else {
					DigitNode current = biginteger.front;
					while (current.next != null) {
						current = current.next;
					}
					current.next = new_node;
					biginteger.numDigits++;
				}
				crnt1 = crnt1.next; crnt2 = crnt2.next;
			} 
			if ((first.negative == true && second.negative == false) || (first.negative == false && second.negative == true)) {
				if (first.numDigits >= second.numDigits) {
					digit = crnt1.digit - crnt2.digit;
					
                    if (rem == true) {
                    	secondHolder = crnt1.digit;
						secondHolder -= 1;
						digit = secondHolder - crnt2.digit;
						
					}
					if (digit < 0) {
						if (rem == true) {
							digit = (secondHolder+10) - crnt2.digit;
							
						}
						else {
							holder = crnt1.digit;
							holder += 10;
							digit = holder - crnt2.digit;
							
						}
						rem = true;
					}
					else {
						rem = false;
					}
					DigitNode new_node = new DigitNode(digit, null);
					if (biginteger.front == null) {
						biginteger.front = new_node;
						biginteger.numDigits++;
						
						
					} else {
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
						biginteger.numDigits++;
						
					}
				}
				if (first.numDigits < second.numDigits) {
					
					digit = crnt2.digit - crnt1.digit;
					if (rem == true) {
						secondHolder = crnt2.digit;
						secondHolder -= 1;
						digit = secondHolder - crnt1.digit;
					}
					if (digit < 0) {
						if (rem == true) {
							digit = (secondHolder+10) - crnt1.digit;
						}
						else {
							holder = crnt2.digit;
							holder += 10;
							digit = holder - crnt1.digit;
						}
						rem = true;
					} else {
						rem = false;
					}
					DigitNode new_node = new DigitNode(digit, null);
					if (biginteger.front == null) {
						biginteger.front = new_node;
						biginteger.numDigits++;
						
					} else {
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
						biginteger.numDigits++;
					}
				}
				/*if (first.numDigits == second.numDigits) {
					
				} */
				
				crnt1 = crnt1.next; crnt2 = crnt2.next;
			}
		
		}
		    

		
		// check to see if we need a remainder if both are equal
		
		if (crnt1 == null && crnt2 == null) {
			if ((first.negative == true && second.negative == true) || (first.negative == false && second.negative == false)) {
				if (rem == true) {
					DigitNode new_node = new DigitNode(1, null);
					DigitNode current = biginteger.front;
					while (current.next != null) {
						current = current.next;
					}
					current.next = new_node;
					biginteger.numDigits++;
				}
			}
			
		  }
		
		if (crnt1 == null && crnt2 != null) {
			while (crnt2 != null) {
				digit = crnt2.digit;
				if ((first.negative == false && second.negative == true) || (first.negative == true && second.negative == false)) {
						if (rem == true) {
							if (digit == 0) {
								digit = 9;
								rem = true;
							} else {
							digit -= 1;
							rem = false;
							}
						}
						DigitNode new_node = new DigitNode(digit, null);
						if (biginteger.front == null) {
							biginteger.front = new_node;
							biginteger.numDigits++;
							
						} else {
							DigitNode current = biginteger.front;
							while (current.next != null) {
								current = current.next;
							}
							current.next = new_node;
							biginteger.numDigits++;
						}
						crnt2 = crnt2.next;
					}
					
			
				
		    if ((first.negative == false && second.negative == false) || (first.negative == true && second.negative == true)) {
			    	if (rem == true) {
			    		digit += 1;
			    		if (digit > 9) {
			    			digit = digit % 10;
			    			rem = true;
			    		} else {
			    			rem = false;
			    		}
			    	}
			    	DigitNode new_node = new DigitNode(digit, null);
					if (biginteger.front == null) {
						biginteger.front = new_node;
						
					} else {
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
					}
					crnt2 = crnt2.next;
			    }
		    
			}
					
			  if (crnt2 == null) {
				if (rem == true) {
					digit = 1;
					DigitNode new_node = new DigitNode(digit, null);
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
						biginteger.numDigits++;
					}
				}
			}
		

		if (crnt1 != null && crnt2 == null) {
			while (crnt1 != null) {
				digit = crnt1.digit;
				if ((first.negative == false && second.negative == true) || (first.negative == true && second.negative == false)) {
						if (rem == true) {
							if (digit == 0) {
								digit = 9;
								rem = true;
							} else {
							digit -= 1;
							rem = false;
							}
						}
						DigitNode new_node = new DigitNode(digit, null);
						if (biginteger.front == null) {
							biginteger.front = new_node;
							biginteger.numDigits++;
							
						} else {
							DigitNode current = biginteger.front;
							while (current.next != null) {
								current = current.next;
							}
							current.next = new_node;
							biginteger.numDigits++;
						}
						crnt1 = crnt1.next;
					}
					
				
		    if ((first.negative == false && second.negative == false) || (first.negative = true && second.negative == true)) {
			    	if (rem == true) {
			    		digit += 1;
			    		if (digit > 9) {
			    			digit = digit % 10;
			    			rem = true;
			    		} else {
			    			rem = false;
			    		}
			    	}
			    	DigitNode new_node = new DigitNode(digit, null);
					if (biginteger.front == null) {
						biginteger.front = new_node;
						biginteger.numDigits++;
						
					} else {
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
						biginteger.numDigits++;
					}
					crnt1 = crnt1.next;
			    }
			}
			  if (crnt1 == null) {
				if (rem == true) {
					digit = 1;
					DigitNode new_node = new DigitNode(digit, null);
						DigitNode current = biginteger.front;
						while (current.next != null) {
							current = current.next;
						}
						current.next = new_node;
						biginteger.numDigits++;
					}
				}

		  } 
		
		return parse(biginteger.toString()); 
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
		BigInteger firstInteger = new BigInteger(); BigInteger finalInteger = new BigInteger();
		int digit = 0; boolean rem = false; int remainder = 0; int zeros = 0;
		
		if (first.negative == true && second.negative == false) {
			finalInteger.negative = true;
		}
		if (first.negative == false && second.negative == true) {
			finalInteger.negative = true;
		}
		if (first.negative == true && second.negative == true) {
			finalInteger.negative = false;
		}
		if (first.negative == false && second.negative == false) {
			finalInteger.negative = false;
		}
		System.out.println(first.negative); 
		System.out.println(second.negative);
		
		// following line is a placeholder for compilation
	 // this creates our base linked list so we can keep adding
		for (DigitNode firstCurrent = first.front; firstCurrent != null; firstCurrent = firstCurrent.next) {
				digit = second.front.digit * firstCurrent.digit;
				if (rem == true) {
					digit += remainder;
					
				}
				if (digit > 9) {
					rem = true;
					remainder = digit / 10;
					digit = digit % 10;
					
					
				} else {
					rem = false;
				}
				DigitNode new_node = new DigitNode(digit, null);
				if (firstInteger.front == null) {
					firstInteger.front = new_node;
					firstInteger.numDigits++;
					
				} else {
					DigitNode current = firstInteger.front;
					while (current.next != null ) {
						current = current.next;
					}
					current.next = new_node;
					firstInteger.numDigits++;
					
				}
			}
			if (rem == true) {
				DigitNode new_node = new DigitNode(remainder, null);
				if (firstInteger.front == null) {
					firstInteger.front = new_node;
					firstInteger.numDigits++;
					
				} else {
					DigitNode current = firstInteger.front;
					while (current.next != null) {
						current = current.next;
					}
					current.next = new_node;
					firstInteger.numDigits++;
					
				}
				
				rem = false;
			
			}
		
			
			if (second.front.next == null) {
				if (first.negative == true && second.negative == false) {
					firstInteger.negative = true;
				}
				if (first.negative == false && second.negative == true) {
					firstInteger.negative = true;
				}
				if (first.negative == true && second.negative == true) {
					firstInteger.negative = false;
				}
				else {
					firstInteger.negative = false;
				}
				return firstInteger;
			}
		
		
		// cycle through the lists
		
		for (DigitNode crnt1 = second.front.next; crnt1 != null; crnt1 = crnt1.next){
			
			zeros++;
			BigInteger secondInteger = new BigInteger();
			for (int counter = zeros; counter >= 1; counter--) {
				DigitNode new_node = new DigitNode(0, null);
				if (secondInteger.front == null) {
					secondInteger.front = new_node;
					secondInteger.numDigits++;
					
				} else {
					DigitNode current = secondInteger.front;
					while (current.next != null) {
						current = current.next;
					}
					current.next = new_node;
					secondInteger.numDigits++;
				}
			}
			
			for (DigitNode crnt2 = first.front; crnt2 != null; crnt2 = crnt2.next) {
				digit = crnt1.digit * crnt2.digit;
				if (rem == true) {
					digit += remainder;
					
				}
				if (digit > 9) {
					rem = true;
					remainder = digit / 10;
					digit = digit % 10;
					
				} else {
					rem = false;
				}
				DigitNode new_node = new DigitNode(digit, null);
				if (secondInteger.front == null) {
					secondInteger.front = new_node;
					secondInteger.numDigits++;
					
				} else {
					DigitNode current = secondInteger.front;
					while (current.next != null ) {
						current = current.next;
					}
					current.next = new_node;
					secondInteger.numDigits++;
					
				}
			}
			if (rem == true) {
				DigitNode new_node = new DigitNode(remainder, null);
				if (secondInteger.front == null) {
					secondInteger.front = new_node;
					secondInteger.numDigits++;
					
				} else {
					DigitNode current = secondInteger.front;
					while (current.next != null) {
						current = current.next;
					}
					current.next = new_node;
					secondInteger.numDigits++;
					
				}
				rem = false;
			}
			
			if (zeros == 1) {
				finalInteger = add(firstInteger, secondInteger);
			} else {
				finalInteger = add(finalInteger, secondInteger);
			}

		  }
		
		
		
		return finalInteger;
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
}
