import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class TestPrintMessage {
	
	// store the standard output stream to redirect the output for testing
	private PrintStream standardOut = System.out;
	// the new output stream to output to a string for testing
	private ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	public static void main(String[] args) {
		(new TestPrintMessage()).runTests();
	}

	public void runTests() {
		// run the test and output results
		if (this.testEmail()) {
			System.out.println("Test email: PASSED");
		} else {
			System.out.println("Test email: FAILED");
		}
	}

	// This test fails if the output stream does not contain the string "@wisc.edu"
	// or if the @-sign is preceded by a whitespace. It passes in all other cases.
	public boolean testEmail() {
		// redirect output stream to byte array captor
		System.setOut(new PrintStream(outputStreamCaptor));
		// call main method from PrintMessage and print output to captor
		PrintMessage.main(new String[] { });
		// direct output to terminal again
		System.setOut(standardOut);
		// test output we received
		String programOutput = outputStreamCaptor.toString();
		// test fails if there is no "@wisc.edu" in the output
		if (!programOutput.contains("@wisc.edu")) {
			return false;
		}
		String stringBeforeAtSymbol = programOutput.split("@wisc.edu")[0];
		// We test if there is a space character before the @ symbol
		// in the output string; the test fails if it is and succeeds
		// if there is any other character.
		if (stringBeforeAtSymbol.endsWith(" ")) {
			return false;
		} else {
			return true;
		}
	}

}
