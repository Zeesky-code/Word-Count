import org.junit.*;
import org.junit.Assert;
import picocli.CommandLine;

import java.io.*;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;


public class WordCountTest {
	// setting up streams to get console output
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void TestArgsAreParsedCorrectly() {
		//test if arguments are parsed correctly
		String [] flagArgs = new String[]{"-c", "-l","\\Trial", "-m", "-w", "\\Test"};
		WordCount wc = new WordCount();
		new CommandLine(wc).parseArgs(flagArgs);
		assert wc.filenames.equals(Arrays.asList("\\Trial", "\\Test" ));

	}

	@Test
	public void NotFoundErrorWorks() throws Exception {
		// test if the program can handle invalid inputs

		String [] flagArgs = new String[]{"-c", "-l", "-m", "-w", "\\FileThatDoesn'tExist"};
		WordCount.main(flagArgs);
		 Assert.assertThat(outContent.toString(), containsString("File not found"));

	}

	@Test
	public void TestNumberOfLines() {
		// test number of lines are calculated properly for large files.

		String [] flagArgs = new String[]{"-l", "src\\test\\Trial"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("27536"));


	}

	@Test
	public void TestNumberOfWords() {
		// test number of lines are calculated properly for large files.

		String [] flagArgs = new String[]{"-w", "src\\test\\Test"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("34"));


	}

	@Test
	public void TestNumberOfWordsLargeFiles() {
		// test number of lines are calculated properly for large files.
		//Note: This test fails. I got the expected from MS Word. review later
		String [] flagArgs = new String[]{"-w", "src\\test\\Trial"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("120368"));


	}

	@Test
	public void TestNumberOfCharacters(){
		String [] flagArgs = new String[]{"-m", "src\\test\\Test"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("185"));
	}

	@Test
	public void TestNumberOfCharactersLargeFile(){
		String [] flagArgs = new String[]{"-m", "src\\test\\Trial"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("1602078"));
	}

	@Test
	public void TestSize(){
		String [] flagArgs = new String[]{"-c", "src\\test\\Test"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("185"));
	}

	@Test
	public void TestSizeLargeFile(){
		String [] flagArgs = new String[]{"-c", "src\\test\\Trial"};
		WordCount.main(flagArgs);
		Assert.assertThat(outContent.toString(),  containsString("1629670"));
	}


}
