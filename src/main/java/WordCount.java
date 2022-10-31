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
	public String filename;


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
			//not in a try-resource block because path is not auto-closable
			Path path = Paths.get(filename);
			//to print all details if no command is specified
			if (!countBytes & !countLines & !countWords){
				System.out.println(countLines(path) + " " +countBytes(path) + " "+ countWords(path) + " " + filename);

			}else{
				System.out.println( (countLines ? countLines(path): "" ) +(countWords ? countWords(path) +" ": "" ) + (countBytes ? countBytes(path): "" ) + " " +filename);
			}

		} catch (IOException e) {
			//put in real error messages
			System.out.println(e.toString());
		}
	}

}
