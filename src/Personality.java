import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * CS145 Spring 2016
 * Programming Assignment 3
 * Ben Johnson
 */
public class Personality {
	static final Path INPUT_PATH = Paths.get("personality.txt");
	
	public static void main(String[] args) throws IOException {
		// In the event of a file error we simply bail out, hence the throws
		// declaration, the spec says to assume a present file
		
		try (Stream<String> stream = Files.lines(INPUT_PATH)) {
			// Wrapped in try block to close file handle when we're done. RAII!
			Iterator<String> it = stream.iterator();
			while (it.hasNext()) {
				// Of course, this will crash on a file with an odd number of
				// lines. That's okay, the spec says to assume a correct file
				
				String name = it.next();
				String personality = it.next();
				System.out.println(name + " : " + personality);
			}
		}
		
		// Now parse our data and link it up to names
		Map<String, PersonalityType> personalities = new HashMap<>();
//		System.out.println(new PersonalityType("BABAAAABAAAAAAABAAAABBAAAAAABAAAABABAABAAABABABAABAAAAAABAAAAAABAAAAAA"));
	}
}

class PersonalityType {
	int bcount;
	
	public PersonalityType(String line) {
		// Parses out answers from line and calculates personality
		Stream<Character> chars = line.chars().mapToObj(c -> (char)c)
				                              .map(Character::toLowerCase);
		long acount = chars.filter(c -> c == 'a').count();
		long bcount = chars.filter(c -> c == 'b').count();
		long total = acount + bcount;
		int percentage = (int) Math.round(bcount / total);
	}
}