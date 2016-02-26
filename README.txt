Join Up Words
By Jill Mirandilla


DESCRIPTION
This program will find a shortest sequence of joined up words that link a beginning word to an end word.
Then it produce two lines where:
 - First line should consist of singly join sequences between two words given, followed by such a sequence. 

 - Second line should be similar to first line, but report on doubly joined chains.

Example:
suffix and read in singly joined will produce:
 - suffix fixture read

suffix and read in doubly joined will produce:
 - suffix fixage agent entire ire read

TECHNICAL TERMS
* common word - suffix of first word is the same as the prefix of the common word. While the common word's prefix is the same as the suffix of the second word.

* Singly joined - the common word is at least half as long as one of the two words

* Doubly joined - the common word is at least half as long as both words.\

HOW TO EXECUTE THE PROGRAM

Compile the program first by typing the command:
javac *.java

Run the program by typing the command:
java JoinUp [firstword] [secondword] < [filename in txt]

example: java JoinUp suffix read < testA.txt
