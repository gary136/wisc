# The default target should compile and run the program.
# It achieves this be depending on the target run
default: run

# The target runs the Java compiler for Front.java
# to create Front.class. Adding the file Front.java
# makes sure that this target is only run if Front.java
# was changed since the last compilation or if the Front.class
# does not exist.
Front.class: Front.java
	javac Front.java

# The target run runs the Java program in Front.class.
# Adding the file Front.class as a dependency makes sure that
# the compiler is run (through the previous rule) if Front.class
# does not exist or Front.java has been changes since the last
# compilation.
run: Front.class
	java Front

# Add a target to compile and a target to run the test for our program
# in the file ApplicationTest.java. Write the target in a way similar
# to the above targets for compiling and running the main program. Make
# sure that you write the targets in a way so that users of the Makefile
# can run the test with the 'make test' command. Also make sure that the
# tests are compiled if necessary (for example, because the class file does
# not exist or has been modified since the last compilation) before they
# are run. Also make sure that the latest version of Front.class
# exists when the tests are run, since the test class depends on it.
# For this you will have to create a rule with two dependencies. You can
# list those after the target, separated by whitespace. For example, the
# following rule with target *doit* has 2 dependencies, *prepare* and
# *maintask*:
# doit: prepare maintask
ApplicationTest.class: ApplicationTest.java Front.class # dependency
	javac ApplicationTest.java

test: ApplicationTest.class
	java -jar junit5.jar --class-path . --scan-classpath

# The target clean removes all of the compiled files for the program.
# For our Java programs, it removes all the *.class files in the directory.
# Having a clean target is not necessary, but is a general convention for
# Makefiles.
clean:
	rm *.class
