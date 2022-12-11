import org.junit.*;
import org.junit.Assert;
import org.junit.contrib.java.lang.system.SystemOutRule;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

import static jdk.nashorn.internal.objects.Global.print;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;


public class WordCountTest {
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

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
		 Assert.assertEquals(systemOutRule.getLog().trim(), "File not found");

	}


	//Todo
	//Test that the program correctly counts the number of words in a file. This could be done by providing a test file with a known number of words and verifying that the program outputs the correct number.
	//Test that the program correctly counts the number of lines in a file. This could be done by providing a test file with a known number of lines and verifying that the program outputs the correct number.
	//Test that the program correctly counts the number of characters in a file. This could be done by providing a test file with a known number of characters and verifying that the program outputs the correct number.
	//Test that the program correctly counts the number of bytes in a file. This could be done by providing a test file with a known number of bytes and verifying that the program outputs the correct number.

	
	//Test that the program can handle files with very large numbers of words, lines, characters, or bytes without crashing or producing incorrect output.

}
