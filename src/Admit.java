import java.util.Scanner;

/**
 * CS145 Spring 2016
 * Programming Assignment 2
 * Ben Johnson
 */

class Admit {
	static final String WELCOME_MSG = 
			"This program compares two applicants to\n" +
			"determine which one seems like the stronger\n" +
			"applicant. For each candidate I will need\n" +
			"either SAT or ACT scores plus a weighted GPA.";
	
	/**
	 * Helper method for getting input
	 */
	public static int askInt(Scanner scanner, String msg) {
		System.out.print(msg);
		return scanner.nextInt();
	}
	
	/**
	 * Helper method for getting input
	 */
	public static double askDouble(Scanner scanner, String msg) {
		System.out.print(msg);
		return scanner.nextDouble();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println(WELCOME_MSG);

		System.out.println();
		
		Applicant.ONE.getDetails(scanner);	
		Applicant.TWO.getDetails(scanner);
		
		System.out.println();
		
		System.out.printf("First applicant overall score  = %.1f\n",  Applicant.ONE.overallScore);
		System.out.printf("Second applicant overall score = %.1f\n", Applicant.TWO.overallScore);
		
		if (Applicant.ONE.overallScore > Applicant.TWO.overallScore)
			System.out.println("The first applicant seems to be better");
		else if (Applicant.TWO.overallScore > Applicant.ONE.overallScore)
			System.out.println("The second applicant seems to be better");
		else
			System.out.println("The two applicants seem to be equal");
	}

}

enum Applicant {
	ONE(1), TWO(2);
	
	public int id;
	public Exam exam;
	public double gpa, gpaMax, multiplier, gpaScore, overallScore;
	
	Applicant(int id) {
		this.id = id;
	}
	
	public void getDetails(Scanner scanner) {
		System.out.printf("Information for applicant #%d:\n", this.id);
		this.exam = Exam.getExam(scanner);
		System.out.printf("\texam score = %.1f\n", this.exam.getExamScore());
		
		this.gpa = Admit.askDouble(scanner, "\toverall GPA? ");
		this.gpaMax = Admit.askDouble(scanner, "\tmaximum GPA? ");
		this.multiplier = Admit.askDouble(scanner, "\ttranscript multipler? ");
		
		this.gpaScore = (this.gpa / this.gpaMax) * 100.0 * this.multiplier;
		System.out.printf("\tGPA score = %.1f\n", this.gpaScore);
		
		this.overallScore = this.exam.getExamScore() + this.gpaScore;
	}
}

abstract class Exam {
	public static Exam getExam(Scanner scanner) {
		int n = Admit.askInt(scanner, "\tdo you have 1) SAT scores or 2) ACT scores? ");
		switch (n) {
		case 1:
			return ExamSAT.fromInput(scanner);
		case 2:
			return ExamACT.fromInput(scanner);
		}
		return null;
	}
	
	public abstract double getExamScore();
}

class ExamSAT extends Exam {
	private int math, reading, writing;

	static ExamSAT fromInput(Scanner s) {
		ExamSAT exam = new ExamSAT();
		exam.math    = Admit.askInt(s, "\tSAT math? ");
		exam.reading = Admit.askInt(s, "\tSAT reading? ");
		exam.writing = Admit.askInt(s, "\tSAT writing? ");
		return exam;
	}
	
	public double getExamScore() {
		return ((2.0 * math) + reading + writing) / 32;
	}
}

class ExamACT extends Exam {
	private int english, math, reading, science;

	static ExamACT fromInput(Scanner s) {
		ExamACT exam = new ExamACT();
		exam.english = Admit.askInt(s, "\tACT english? ");
		exam.math    = Admit.askInt(s, "\tACT math? ");
		exam.reading = Admit.askInt(s, "\tACT reading? ");
		exam.science = Admit.askInt(s, "\tACT science? ");
		return exam;
	}
	
	public double getExamScore() {
		return ((2.0 * math) + reading + english + science) / 1.8;
	}
}