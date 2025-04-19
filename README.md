This is one of the first programming projects I worked on during school. I decided to redo this project to practice more efficient coding and fix any bugs I left in the first
time I coded it. 

The purpose of this project was to add and multiply large integers that were too big to store in an integer variable. To get around this, the numbers would be converted into
BigInteger objects, which stored the head of a linked list, the number of digits (nodes) in the number, and a boolean if the number was negative or not. The linked lists were
made of objects called DigitNodes. These objects would represent each individual digit in each number entered in, and stored an integer of values 0-9 and a reference to the next
DigitNode in the list. The DigitNodes were stored in reverse order to make arithmetic easier, so for example "123" would be represented as "3 -> 2 -> 1" in the list. 

The project outline was made by Rutgers, specifically DigitNode.java and BigTest.java. My job was to fill in the parse(), add(), and multiply() methods in BigInteger.java. 

parse() would take in a string representing a number and format it to a BigInteger. It would detect non digit characters in the string, trim out whitespace in the start and end,
and get rid of needless 0's in the front of the number (000123 would become 123 for example). The '-' or '+' symbols could appear in the start to make the number positive or 
negative.

add() takes two BigInteger objects and adds them together. It would also subtract if one number was positive and the other negative. 

multiply() would multiply the two BigIntegers and return the product.
