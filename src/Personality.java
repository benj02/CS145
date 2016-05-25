import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CS145 Spring 2016 Programming Assignment 3 Ben Johnson
 */
public class Personality {
	static final char[][] CATEGORIES = {
		{ 'E', 'I', 'X' },
		{ 'S', 'N', 'X' },
		{ 'T', 'F', 'X' },
		{ 'J', 'P', 'X' },
	};
	
	static final String WELCOME_MSG =
			"This program processes a file of answers to the Keirsey Temperament Sorter. It converts the various A\n" +
			"and B answers for each person into a sequence of B-percentages and then into a four-letter personality\n" +
			"type.\n";

	public static void main(String[] args) throws IOException {
		// IOException will be thrown if file is not present
		Scanner scanner = new Scanner(System.in);
		
		System.out.println(WELCOME_MSG);
		Path inputPath  = Paths.get(askString("input file? ",  scanner));
		Path outputPath = Paths.get(askString("output file? ", scanner));
		
		StringBuilder acc = new StringBuilder();
		
		try (Stream<String> stream = Files.lines(inputPath)) {
			Iterator<String> it = stream.iterator();
			while (it.hasNext()) {
				// Of course, this will crash on a file with an odd number of
				// lines. That's okay, the spec says to assume a correct file

				String name = it.next();
				String personality = it.next();
				acc.append(name + ": " + computePersonality(personality) + "\n");
			}
		}
		
		Files.write(outputPath, acc.toString().getBytes());
	}
	
	static String askString(String msg, Scanner s) {
		System.out.print(msg);
		return s.nextLine();
	}
	
	static String computePersonality(String s) {
		int[] percentages = {
			computePercentB(new int[] {0},    s),
			computePercentB(new int[] {1, 2}, s),
			computePercentB(new int[] {3, 4}, s),
			computePercentB(new int[] {5, 6}, s),
		};
		
		StringBuilder acc = new StringBuilder();
		
		acc.append(Arrays.toString(percentages));
		acc.append(" = ");
		
		for (int i = 0; i < 4; i++) {
			char[] category = CATEGORIES[i];
			int j = overUnder(percentages[i]);
			
			acc.append(category[j]);
		}
		
		return acc.toString();
	}
	
	static int overUnder(int percent) {
		if (percent < 50) return 0;
		if (percent > 50) return 1;
		return 2;
	}

	static int computePercentB(int[] indicies, String s) {
		List<Character> answerList = getAnswerList(getIndexStream(indicies), s);
		
		int total = answerList.size();
		int bCount = (int) answerList.stream().filter(c -> c == 'b' || c == 'B').count();
		
		return Math.round(((float) bCount / total) * 100);
	}
	
	static List<Character> getAnswerList(IntStream indicies, String s) {
		return indicies.mapToObj(n -> s.charAt(n)).filter(c -> c != '-').collect(Collectors.toList());
	}

	static IntStream getIndexStream(int[] offsets) {
		return IntStream.range(0, 70).filter(n -> {
			for (int x : offsets)
				if (n % 7 == x)
					return true;
			return false;
		});
	}
}
