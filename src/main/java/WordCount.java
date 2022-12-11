import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.BufferedReader;
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

	@Parameters( description= "The files to count")
	public List<String> filenames;


	@Option(names = {"-c", "--countBytes"}, description = "Count the number of Bytes in the file")
	private Boolean countBytes= false;

	@Option(names= {"-w", "countWords"}, description = "Count the number of Words in the file")
	private Boolean countWords = false;

	@Option(names = {"-l", "--countLines"}, description = "Count the number of lines in the file")
	private Boolean countLines =false;

	@Option(names = {"-m", "--countCharacters"},  description = "Count the number of characters in the file")
	private Boolean countCharacters =false;

	public static long countBytes(Path path) throws IOException {
		return Files.size(path);
	}

	public static long countWords(Path path) throws IOException{

		/* not in a stream try with resource block because tokenizer doesn't work with streams
		  check out other ways to read words
		 */
		long words = 0;
		try (Stream<String> stream = Files.lines(path)) {
			words = stream.map(StringTokenizer::new)
					.mapToInt(StringTokenizer::countTokens).sum();
		} catch (IOException e) {
			System.out.println("Something went wrong, please try again");

		}

		return words;
	}

	public static long countLines(Path path) throws IOException {
		long result = 0;
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			for (; ; ) {
				String line = reader.readLine();
				if (line == null)
					break;
				result +=1;

			}
			return result;
		}

	}

	public static long countCharacters(Path path) throws IOException{
		//I think it's off by 5/7 review later
		long Characters = 0;
		try (Stream<String> stream = Files.lines(path)) {
			Stream<Object> chars = stream.flatMap(s -> s.chars().mapToObj(c->(char) c));
			Characters = chars.count();
		}


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
						System.out.println(countLines(path) + " " +countWords(path)  + " "+ countBytes(path)+ " " + " " + countCharacters(path)+ " " +filename);

					}else{

						System.out.println( (countLines ? countLines(path): "" ) +" "+(countWords ? countWords(path) +" ": "" ) + " "+(countBytes ? countBytes(path): "" ) + " "+(countCharacters? countCharacters(path):"")+ " "+filename);
					}
				} catch (NoSuchFileException e) {
					//put in real error messages
					System.out.println("File not found");
				}catch (IOException e){
					System.out.println("Something went wrong, please try again");
				}
			});




	}

}
