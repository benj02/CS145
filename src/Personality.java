import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CS145 Spring 2016 Programming Assignment 3 Ben Johnson
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
				//System.out.println(name + " : " + computePersonality(personality));
			}
		}

		// Now parse our data and link it up to names
		// System.out.println(new
		for(int x : computePersonality("BABAAAABAAAAAAABAAAABBAAAAAABAAAABABAABAAABABABAABAAAAAABAAAAAABAAAAAA"))
			System.out.println(x);
	}

	static int[] computePersonality(String s) {
		IntStream EIIndicies = getIndexStream(new int[] {0});
		IntStream SNIndicies = getIndexStream(new int[] {1, 2});
		IntStream TFIndicies = getIndexStream(new int[] {3, 4});
		IntStream JPIndicies = getIndexStream(new int[] {5, 6});
		
		List<Character> EIAnswers = getAnswerList(EIIndicies, s);
		List<Character> SNAnswers = getAnswerList(SNIndicies, s);
		List<Character> TFAnswers = getAnswerList(TFIndicies, s);
		List<Character> JPAnswers = getAnswerList(JPIndicies, s);
		
		int EITotal = EIAnswers.size();
		int SNTotal = SNAnswers.size();
		int TFTotal = TFAnswers.size();
		int JPTotal = JPAnswers.size();
		
		long EICountB = EIAnswers.stream().filter(c -> c == 'b' || c == 'B').count();
		long SNCountB = SNAnswers.stream().filter(c -> c == 'b' || c == 'B').count();
		long TFCountB = TFAnswers.stream().filter(c -> c == 'b' || c == 'B').count();
		long JPCountB = JPAnswers.stream().filter(c -> c == 'b' || c == 'B').count();
		
		int EIPercentB = Math.round(((float) EICountB / EITotal) * 100);
		int SNPercentB = Math.round(((float) SNCountB / SNTotal) * 100);
		int TFPercentB = Math.round(((float) TFCountB / TFTotal) * 100);
		int JPPercentB = Math.round(((float) JPCountB / JPTotal) * 100);
		
		
		return new int[] {
			EIPercentB, SNPercentB, TFPercentB, JPPercentB
		};
	}

	static IntStream getIndexStream(int[] offsets) {
		IntPredicate predicate = n -> {
			int mod = n % 7;
			for (int x : offsets)
				if (mod == x)
					return true;
			return false;
		};
		return IntStream.range(0, 69).filter(predicate);
	}
	
	static List<Character> getAnswerList(IntStream indicies, String s) {
		return indicies.mapToObj(n -> s.charAt(n)).filter(c -> c != '-').collect(Collectors.toList());
	}
}
