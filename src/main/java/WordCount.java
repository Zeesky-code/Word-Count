import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.* ;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

@Command(
		name = "Word Count",
		description = "It prints the counts of different things in the file"
)

public class WordCount implements Runnable{

	@Parameters(index="*", description= "The file to count")
	public List<String> filenames;


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

		/* not in a stream try with resource block because tokenizer doesn't work with streams
		  check out other ways to read words
		 */
			String sentence = Files.readString(path);
			StringTokenizer tokens = new StringTokenizer(sentence);
			return  tokens.countTokens();
	}

	public static long countLines(Path path) {
		long lines = 0;
		try (Stream<String> stream = Files.lines(path)) {
			lines = stream.map(StringTokenizer::new)
					.mapToInt(StringTokenizer::countTokens).sum();
		} catch (IOException e) {
			System.out.println("Something went wrong, please try again");

		}

		return lines;

	}

	public static long countCharacters(Path path){
		long Characters = 0;

		return Characters;
	}

	public static void main(String []args){
		new CommandLine(new WordCount()).execute(args);
	}
	@Override
	public void run(){

			filenames.forEach((filename)->{
				//not in a try-resource block because path is not auto-closable
				try {
					Path path = Paths.get(filename);
					//to print all details if no command is specified
					if (!countBytes & !countLines & !countWords){
						System.out.println(countLines(path) + " " +countBytes(path) + " "+ countWords(path) + " " + filename);

					}else{

						System.out.println( (countLines ? countLines(path): "" ) +" "+(countWords ? countWords(path) +" ": "" ) + " "+(countBytes ? countBytes(path): "" ) + " " +filename);
					}
				} catch (Exception e) {
					//put in real error messages
					System.out.println("File not found");
				}
			});




	}

}
