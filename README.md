
Overview of the solution:

Read lines from input file
For each line:
	Add line to group of same number of words.

  Create another subtle groups that satisfies the following:
		Contains sentences that belong to the same group (same number of words)
		The sentences are similar (differ by only ONE word), and 
    have a unique key. The key indicates the position of the different word		 

The final result (actually, the subtle groups) are printed to the screen and written to file (at same directory of the project).

************************************************************************************************************************************

How to run?

1 Clone the project from master branch of https://github.com/odedpinchover/MyProject/new/master?readme=1

  After you open the project please verify that resource folder is marked as the resources folder for this project - 
  you should have this in your TestProject.iml file

  <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src" isTestSource="false" />
      <sourceFolder url="file://$MODULE_DIR$/src/resources" type="java-resource" />
  </content>
 
  if you dont have that - simply mark the folder under src/resources as resources folder within your IDE
  (i specify this as when the project is imported into your IDE the file may be overwrriten and the folder is not mark as resources folder
  and then the project cannot read its content )                                
  
2 This is a standard java project. Please run class Main 

************************************************************************************************************************************

1. What can you say about the complexity of your code?

Assuming that:
 a sentence length is bound to a reasonable length and 
there are n sentences,
the complexity is o(n^2)

2. How will your algorithm scale?

My current algorithm will work fine while the RAM memory is big enough to handle the size of all the input sentences.
When this is not the case, I will change the algorithm and use files instead of memory.


3. If you had two weeks to do this task, what would you have done differently?  What would be better?

1.	My algorithm separates the input file into groups according to the number of words in the sentence.
2.	After this grouping is done, I can improve by using a dedicated thread for each group according to hardware limitations.
3.	This improvement should take in account writing the output from many threads.


