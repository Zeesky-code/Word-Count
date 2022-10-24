import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.* ;
import java.util.StringTokenizer;
import java.util.stream.Stream;

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

	public static long countBytes(Path path) throws IOException {
		return Files.size(path);
	}

	public static long countWords(Path path) throws IOException{
		String sentence = Files.readString(path);
		StringTokenizer tokens = new StringTokenizer(sentence);
		return  tokens.countTokens();
	}

	public static long countLines(Path path) {
		long lines = 0;
		try (Stream<String> stream =Files.lines(path) ){
			lines = stream.count();

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
			Path path = Paths.get(filename);
			System.out.println( (countBytes ? "Number of Bytes:" + countBytes(path): "" ) +(countLines ? " Number of Lines:" + countLines(path): "" ) +(countWords ? " Number of Words:" + countWords(path) +" ": "" ) + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
