import java.util.*;
import java.io.*;

/* *******************************************************************************************************************************************
AUTHOR:Fabio Cataleta, 11/10/17,
Asking a random question from a quiz based on three choices, feeding back whether the answer is correct or not, and assigning a mark if it is.
The question can be attempted a total of three times.
The quiz is now stored as an abstract data type, and can only be accessed by specific methods.
The quiz now uses a bubblesort method to sort the user's scores by question, in descending order.
The quiz now stores all scores by the player, so that they can attempt to beat their high score.
******************************************************************************************************************************************* */

class quiz9
{
    public static void main (String[] param) throws IOException
    {
		BufferedReader inputStream = new BufferedReader(new FileReader("scores.txt"));
		PrintWriter outputStream = new PrintWriter(new FileWriter("scores.txt"), true);
		
		final int SIZE = 6;
		boolean quit = false;
		int score = 0;
		QuestionBank qb = createQuestionBank(SIZE);
		String answer = input("What is your name?");
		outputStream.println(answer + "\n");

		while (quit == false)
		{
			String choice = getChoice(answer);
			if (choice.equalsIgnoreCase("s") || choice.equalsIgnoreCase("start"))
			{
				startQuiz(qb, inputStream, outputStream);
				
				for (int i = 0; i < qb.quizarray.length; i++)
					setQuestionAsked(qb.quizarray[i], false);
			}
			else if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit"))
				quit = true;
		}
		
		System.exit(0);
		
    }// END main

    //A method to return ther user's choice.
    public static String getChoice (String answer)
    {
		return input("Hi " + answer + "! Would you like to start a quiz, or quit? (s/q)");

    }//END getChoice

    //A method to create a QuestionBank.
    public static QuestionBank createQuestionBank (int SIZE) 
    {
		QuestionBank qb = new QuestionBank();
		qb.quizarray = new Quiz[SIZE];

		for (int i = 0; i < qb.quizarray.length; i++)
		{
			qb.quizarray[i] = new Quiz ();
		}

		setQuestion(qb.quizarray[0], "Who was the lead singer of Queen?");
		setCorrectAnswer(qb.quizarray[0], "Freddie Mercury");
		setQuestionScore(qb.quizarray[0], 0);
		setQuestionAsked(qb.quizarray[0], false);

		setQuestion(qb.quizarray[1], "Who sang Uptown Girl?");
		setCorrectAnswer(qb.quizarray[1], "Billy Joel");
		setQuestionScore(qb.quizarray[1], 0);
		setQuestionAsked(qb.quizarray[1], false);


		setQuestion(qb.quizarray[2], "Which is Taylor Swift's highest selling single?");
		setCorrectAnswer(qb.quizarray[2], "Shake It Off");
		setQuestionScore(qb.quizarray[2], 0);
		setQuestionAsked(qb.quizarray[2], false);
		
		setQuestion(qb.quizarray[3], "What is the first name of the famous composer Vivaldi?");
		setCorrectAnswer(qb.quizarray[3], "Antonio");
		setQuestionScore(qb.quizarray[3], 0);
		setQuestionAsked(qb.quizarray[3], false);
		
		setQuestion(qb.quizarray[4], "Which year was the first Eurovision Song Contest");
		setCorrectAnswer(qb.quizarray[4], "1953");
		setQuestionScore(qb.quizarray[4], 0);
		setQuestionAsked(qb.quizarray[4], false);
		
		setQuestion(qb.quizarray[5], "What song was used as Spectre's Bond theme");
		setCorrectAnswer(qb.quizarray[5], "Writing's On the Wall");
		setQuestionScore(qb.quizarray[5], 0);
		setQuestionAsked(qb.quizarray[5], false);

		return qb;

    }//END createQuestionBank

    //A method to ask a series of questions from the QuestionBank array.
    public static void startQuiz (QuestionBank qb, BufferedReader inputStream, PrintWriter outputStream) throws IOException
    {
		int score = 0;
		int mark = 0;

		for (int j = 0; j < qb.quizarray.length; j++)
		{
			int question = questionOption();
			
			
			while (getQuestionAsked(qb.quizarray[question]))
				question = questionOption();
			
			setQuestionAsked(qb.quizarray[question], true);
			mark = askQuestion(qb, question, score);
			score += mark;
		}
		
		score = score / 2;
		
		sortScores(qb);
		seeScores(qb, inputStream, outputStream, score);
		
    }//END startQuiz

    //A method to ask a question from QuestionBank.
    public static int askQuestion (QuestionBank qb, int question, int score)
    {
		int i = 0;
		int j = 1;
		boolean correct = false;

		while ((correct == false) && (i < 3))
		{
			String answer = answer(qb, question);
			String correctanswer = getCorrectAnswer(qb.quizarray[question]);
			
			correct = isCorrect(answer, correctanswer);
			score = mark(correct);
			changeScore(score, question, qb);
			j = response(score, j);
			score += score;
			i++;
		}
		
		return score;

    }//END askQuestion

    //Randomly selects a number corresponding to a position in QuestionBank's array.
    public static int questionOption ()
    {
        int choice = rollDice(6);
		return (choice - 1);

    }//END questionOption

    //A method to ask a question stored in QuestionBank.
    public static String answer (QuestionBank qb, int question)
    {
		String answer = input(getQuestion(qb.quizarray[question]));
		return answer;

    }//END askQuestion

    //Test whether the answer given is correct, then pass the value of the variable correct.
    public static boolean isCorrect (String answer, String correctanswer)
    {
		boolean correct;

		if (answer.equalsIgnoreCase(correctanswer))
			correct = true;
		else
			correct = false;

		return correct;

    }//END isCorrect

    // Depending on value of variable correct, a score is calculated.
    public static int mark (boolean correct)
    {
		int score = 0;

		if (correct)
		{
			int mark = rollDice(6);
			score += mark;
		}

		return score;

    }//END mark
	
	//A method to record the score for each question.
	public static void changeScore (int score, int question, QuestionBank qb)
	{
		setQuestionScore(qb.quizarray[question], score);
		
	}//END changeScore
	
	//A method to write a response depending on whether the user's answer is correct.
    public static int response (int score, int j)
    {
		if (score != 0)
			print("Correct! Your score is " + score + "\n");
		else
		{
			print("Incorrect. You have " + (3 - j) + " chance(s) remaining.\n");
			j++;
		}

		return j;

    }//END response
	
	//A method to sort the scores by question
	public static void sortScores (QuestionBank qb)
	{
		for (int j = 0; j < qb.quizarray.length - 1; j++)
		{
			for (int i = 0; i < qb.quizarray.length - 1 - j; i++)
			{
				if (getQuestionScore(qb.quizarray[i]) < getQuestionScore(qb.quizarray[i + 1]))
					swap(qb, i);
			}
		}
		
	}//END sortScores
	
	//A method to swap values in the score array.
	public static void swap (QuestionBank qb, int i)
	{
		Quiz tmp = (qb.quizarray[i+1]);
		qb.quizarray[i+1] = qb.quizarray[i];
		qb.quizarray[i] = tmp;
	
	}//END swap
	
	//A method to ask the user if they want to see their scores.
	public static void seeScores(QuestionBank qb, BufferedReader inputStream, PrintWriter outputStream, int score) throws IOException
	{
		String answer = input("Would you like to see your scores by question for this quiz? (y/n)");
		
		if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
			showScores(qb, inputStream, outputStream, score);
		else
			outputScores(qb, inputStream, outputStream, score);
		
	}//END seeScores
	
	//A method to output the user's scores.
	public static void outputScores (QuestionBank qb, BufferedReader inputStream, PrintWriter outputStream, int score) throws IOException
	{
		for (int i = 0; i < qb.quizarray.length; i++)
			outputStream.println(getQuestion(qb.quizarray[i]) + ": " + getQuestionScore(qb.quizarray[i]) + "\n");
		
		print("Your total score for this quiz is " + score);
		outputStream.println("Total: " + score + "\n");
		
		seeRankingTable(qb, inputStream);

	}//END outputScores
	
	//A method to show the user their scores by question.
	public static void showScores (QuestionBank qb, BufferedReader inputStream, PrintWriter outputStream, int score) throws IOException
	{		
		for (int i = 0; i < qb.quizarray.length; i++)
		{
			print("\n" + getQuestion(qb.quizarray[i]) + ": " + getQuestionScore(qb.quizarray[i]));
			outputStream.println(getQuestion(qb.quizarray[i]) + ": " + getQuestionScore(qb.quizarray[i]) + "\n");
		}
		
		print("Your total score for this quiz is " + score);
		outputStream.println("Total: " + score + "\n");

		seeRankingTable(qb, inputStream);
		
	}//END showScores
	
	//A method to show the high score table of every player thus far.
	public static void seeRankingTable (QuestionBank qb, BufferedReader inputStream) throws IOException
	{
		print("Your high scores are:\n");
		
		inputStream.mark(10000);
		String score = inputStream.readLine();
		
		while (score != null)
		{
			print(score);
			score = inputStream.readLine();
		}

		inputStream.reset();
	
	}//END seeRankingTable
	
    //A method to input Strings
    public static String input(String message)
    {
		Scanner scanner = new Scanner(System.in);
		String answer;

		print(message);
		answer = scanner.nextLine();
		return answer;

    }//END input

    //A method to print Strings.
    public static void print (String message)
    {
		System.out.println(message);

    }//END print

    //Getter methods for Quiz record type
    //Return the question of a quiz
    public static String getQuestion (Quiz q)
    {
		return q.question;

    }//END getQuestion

    // Return the correct answer of a quiz.
    public static String getCorrectAnswer (Quiz q)
    {
        return q.correctanswer;

    }//END getCorrectAnswer
	
	//Return the score of a quiz.
	public static int getQuestionScore (Quiz q)
	{
		return q.questionscore;
		
	}//END getQuestionScore
	
	//Return whether the question has been asked.
	public static boolean getQuestionAsked (Quiz q)
	{
		return q.questionasked;
	
	}//END getQuestionAsked

    // Setter methods for Quiz record type
    // Return fields from record Quiz
    public static Quiz setQuestion (Quiz q, String quizquestion)
    {
       q.question = quizquestion;
       return q;

    }//END setQuestion

    public static Quiz setCorrectAnswer (Quiz q, String quizcorrectanswer)
    {
       q.correctanswer = quizcorrectanswer;
       return q;

    }//END setCorrectAnswer
	
	public static Quiz setQuestionScore (Quiz q, int quizscore)
	{
		q.questionscore = quizscore;
		return q;
		
	}//END setQuestionScore
	
	public static Quiz setQuestionAsked (Quiz q, boolean quizasked)
	{
		q.questionasked = quizasked;
		return q;
		
	}//END setQuestionAsked

    //Creates a mark for the user, using a diceroll, and returns its value.
    public static int rollDice(int sides)
    {
        Random dice = new Random();

        int mark = dice.nextInt(sides) + 1;

		return mark;

    }// END rollDice
	
}// END class quiz9

class Quiz
{
   String question;
   String correctanswer;
   int questionscore;
   boolean questionasked;

}//END class Quiz


class QuestionBank
{
   Quiz[] quizarray; //Where the questions and answers are stored.

}//END class QuestionBank