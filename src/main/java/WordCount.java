import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.* ;
import java.util.StringTokenizer;

@Command(
		name = "Word Count",
		description = "It prints the counts of different things in the file"
)

public class WordCount implements Runnable{

	@Parameters(index="0", description= "The file to count")
	private String filename;


	@Option(names = {"-c", "--countBytes"}, description = "Count the number of Bytes in the file")
	private Boolean countBytes= false;

	@Option(names= {"-w", "countWords"}, description = "Count the number of Words in the file")
	private Boolean countWords = false;

	@Option(names = {"-l", "--countLines"}, description = "Count the number of lines in the file")
	private Boolean countLines =false;

	public static long countBytes(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.size(path);
	}

	public static long countWords(String filename) throws IOException{
		Path path = Paths.get(filename);
		String sentence = Files.readString(path);
		StringTokenizer tokens = new StringTokenizer(sentence);
		return  tokens.countTokens();
	}

	public static long countLines(String fileName) {

		Path path = Paths.get(fileName);

		long lines = 0;
		try {
			lines = Files.lines(path).count();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;

	}

	public static void main(String []args){
		new CommandLine(new WordCount()).execute(args);
	}
	@Override
	public void run(){
		try {
			System.out.println( (countBytes ? "Number of Bytes:" + countBytes(filename): "" ) +(countLines ? " Number of Lines:" + countLines(filename): "" ) +(countWords ? " Number of Words:" + countWords(filename) +" ": "" ) + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
