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

		String [] flagArgs = new String[]{"-c", "-l","\\Trial", "-m", "-w", "\\Test"};
		WordCount wc = new WordCount();
		new CommandLine(wc).parseArgs(flagArgs);
		System.out.println(wc.filenames);
		assert wc.filenames.equals(Arrays.asList("\\Trial", "\\Test" ));

	}


	 @Test
	public void NotFoundErrorWorks() throws Exception {
		// dodgy code here

		String [] flagArgs = new String[]{"-c", "-l", "-m", "-w", "\\FileThatDoesn'tExist"};
		WordCount.main(flagArgs);
		 Assert.assertSame(systemOutRule.getLog().trim(), containsString("File not found"));


	}

}
