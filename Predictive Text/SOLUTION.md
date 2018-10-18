# Predictive Text System

### Insert
* To implement insert, I passed it to the insert which takes a popularity as a parameter. I then use a two helper functions. One is to insert the word into the list called insertIntoTree. Where it looks to see if the first letter of the word is in the map, if it is then it calls itself to insert the next letter into the dictionary tree from the letter recursively going through. If it isn't than the word is added one letter at a time. The next helper method i used was called dictOfLastLetter and this gets the DictionaryTree of the last letter of a word by recursively going down a branch. So i get the dictionary and i set the popularity of the word inserting to an optional value pop to symbolise this is the end of the word and to store the popularity. If the normal insert is used the popularity is stored as the lowest value.


### Remove
* To implement remove, it will first check that the word is in the tree by seeing if the dictOfLastLetter.isPresent as it returns an optional. Then I set the pop at the end of the word to optional.empty to symbolise its not a word in the map. Then I look at the last letter of the words dictionary. If it has multiple children I return false as I can't remove a letter. Else if it is a leaf then I remove it from the map and the word and repeat the process with the shorter word. This is continued till the word is all deleted or it hits a node with multiple children or another word is present on one of the nodes. Then it will return true as it was able to remove some of the characters of the word.


### Contains
* To implement contains I got the dictOfLastLetter and then checked if the optional pop was present and if so return true otherwise false.


### Predict
* To implement predict, I get all the words after the prefix by calling the all words method. If the prefix is a word I add it to the list of words. I then sort this list by value of there pop's. Then I pick the word at the top of the list and return. If there are no words then an empty optional is returned. There are a number of helper methods I used for this.
* To implement predict with the additional parameter n, I get the same sorted list of words back, and I return the first n words. If there are less than n words in the list then i return all elements in the list.


### Number Of Leaves
* I used fold for this. The function I pass to fold is if children for the dictionary has size of 0, then it adds 1 to the list. Then the number of leaves is calculated by adding the list together.


### Maximum Branching
* I used fold for this. The function gets the max children size of all nodes in the tree by keeping a value for the max then going through the list comparing to max. If its bigger than max it sets max to the new value. And then max and the size of the dictionary tree that called fold is also compared, then max is returned.


### Height
* I used fold for this. It stores max height in a variable. And i go through the list looking if theres a bigger value than the value I have for max. Max + 1 is then returned. And after the fold function is called I do -1 to account for the fact the root starts at 0 for height.


### Size
* I used fold for this. It has a total variable and goes through the list adding each element to the total. It then returns total + 1 as for each node in the tree I need to increment the total.


### Longest word
* I first calculate the child with the biggest height to know which branch to take. I add this character to a string variable. I then add recursively the child of that character which has the biggest height and so on.


### All words
* I call a helper function which takes a word as a parameter. I go through each child of the map. I look to see if a words pop is present to symbolise an end of a word, if it is I concatenate the parameter with that character and add it to an array. Then I look again at the children of each of those children but this time I send the character of the children as the parameter. I add the result of the recursion to the array also and then return it.


### Fold
I create an ArrayList for the collection type as its easy to add and traverse for values. I then look at all child nodes and for each one add recursively the value of its children when applying the function to the list. I call this when I state 'f.apply(this, cResults)'.


### Answer to question.
The good thing about using a tree to predict words with popularities is it is quick and easy to cut off other possible words as you traverse down the branches. And is relatively simple to then order the possible words by the popularity. However it can be inefficient as when i call predict I am getting all words in my tree that appear after the prefix. Therefore it would be better if the tree was able to order the nodes in such a way that the most popular word is always in the same place. Therefore I would not have to sort all the words. This can be inefficient as if the user only enters 'o' for example, it has to search through a very big section of the tree still.


### Git Lab Repo
https://git.cs.bham.ac.uk/axe772/PredictiveText.git
