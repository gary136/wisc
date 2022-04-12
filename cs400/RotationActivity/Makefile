test: RedBlackTree.class
	java -jar junit5.jar -cp . --scan-classpath -n RedBlackTree

RedBlackTree.class: RedBlackTree.java SortedCollectionInterface.class
	javac -cp .:junit5.jar RedBlackTree.java

SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java

clean:
	rm *.class
