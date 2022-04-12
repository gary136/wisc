# The default target should compile and run the program.
# It achieves this be depending on the target runProgram
default: runProgram

# The target runs the Java compiler for PrintMessage.java
# to create PrintMessage.class. Adding the file PrintMessage.java
# makes sure that this target is only run if PrintMessage.java
# was changed since the last compilation or if the PrintMessage.class
# does not exist.
PrintMessage.class: PrintMessage.java
	javac PrintMessage.java

# The target runProgram runs the Java program in PrintMessage.class.
# Adding the file PrintMessage.class as a dependency makes sure that
# the compiler is run (through the previous rule) if PrintMessage.class
# does not exist or PrintMessage.java has been changes since the last
# compilation.
runProgram: PrintMessage.class
	java PrintMessage

# Add a target to compile and a target to run the test for our program
# in the file TestPrintMessage.java. Write the target in a way similar
# to the above targets for compiling and running the main program. Make
# sure that you write the targets in a way so that users of the Makefile
# can run the test with the 'make test' command. Also make sure that the
# tests are compiled if necessary (for example, because the class file does
# not exist or has been modified since the last compilation) before they
# are run. Also make sure that the latest version of PrintMessage.class
# exists when the tests are run, since the test class depends on it.
# For this you will have to create a rule with two dependencies. You can
# list those after the target, separated by whitespace. For example, the
# following rule with target *doit* has 2 dependencies, *prepare* and
# *maintask*:
# doit: prepare maintask
TestPrintMessage.class: TestPrintMessage.java PrintMessage.class # dependency
	javac TestPrintMessage.java
# TODO: Add you targets for the test class here.
test: TestPrintMessage.class
	java TestPrintMessage
# The target clean removes all of the compiled files for the program.
# For our Java programs, it removes all the *.class files in the directory.
# Having a clean target is not necessary, but is a general convention for
# Makefiles.
clean:
	rm *.class
