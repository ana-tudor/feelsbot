import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Process;


import javax.sound.sampled.LineUnavailableException;

import curriculum.CurriculumExpressWant;
import curriculum.Trial;

// [ START RUNBOT CLASS ]
public class RunBot {
	
	// Set file path for bot speech text file
	public static final String FILENAME = "bot_speech.txt";
	public static final String face_analysis_txt = "target/face_analysis.txt";
	public static final int response_time = 10;
	
	
	// [ START MAIN() ]
	public static void main(String[] args) throws IOException {
		/* Write actual description here at some point */
		
		// Instantiate Curriculum object and get list of Trials
		CurriculumExpressWant curriculum = new CurriculumExpressWant();
		ArrayList<Trial> trials = curriculum.getTrialList();
		
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("python ui.py");
		
		
		
		// set t_0 as the absolute time in seconds when the program is first initiated
		long t_0 = time(); 
		
		// Loop through all the trials in the ArrayList
		for (Trial trial:trials) {
			// First, call run_prompt() and write returned string to bot_speech.txt
			String prompt = trial.run_prompt();
			writeBotSpeech(prompt);
			

			
			ArrayList<String> options = trial.get_antecedent();
			//!!!write options to file for button access
			System.out.println(options + "\n");
			
			// Now that prompt has been shown to user, make call to audio transcription
			// and video transcription
			
			ArrayList<String> speech_results = null;
			try {
				speech_results = UserResponseStage.collectUserSpeechResponse(response_time);

			} catch (LineUnavailableException line) {
				line.getStackTrace();
			} catch (InterruptedException i) {
				i.getStackTrace();
			}
			
			// get analysis of user's sentiments (based on text)
			ArrayList<Object> user_sentiment = null;
			try {
				user_sentiment = UserResponseStage.evaluateUserSpeechResponse(speech_results);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
			
			// get analysis of user's facial expressions
			double[] expressions = readFacialAnalysisScores(face_analysis_txt);
			
			// determine whether normal protocol can continue, or if special protocols (anger, anxiety) must be activated
			String response_type = whichProtocol(expressions, speech_results);
			
			System.out.println(user_sentiment);
			
			// take weighted average of facial expression scores and text analysis scores to get overall sentiment score
			double score = reconcileScore(expressions, (double)user_sentiment.get(0));
			
			
			boolean correct = trial.determine_response_correctness((String)user_sentiment.get(2), speech_results);
			
			
			
			
			// determine bot's appropriate response
			String response = BotResponseStage.determineBotResponse(score, response_type, correct);
			System.out.println(response);
			
		}
		
		
	}
	// [ /END MAIN() ]
	
	// [ START WRITEBOTSPEECH ]
	public static void writeBotSpeech(String speech) {
		/* Write speech to .txt file, bot_speech.txt, for ui.py to read */
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(speech);

			System.out.println("Logged input: " + speech + " to bot_speech.txt");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
	}
	// [ /END WRITEBOTSPEECH ]
	
	// [ START FACIALANALYSIS ]
	public static ArrayList<Double> facialAnalysis() {
		/* Will eventually call vision.py detect_face() method; for now,
		 * just returns something that will let the rest of the program run */
		ArrayList<Double> values = new ArrayList<Double>();
		values.add((double) 0.5);
		values.add((double) 0.2);
		values.add((double) 0.6);
		return values;
	}
	
	// [ START TEXTSENTIMENTS ]
	public static ArrayList<Double> textSentiments(ArrayList<Object> user_response) {
		ArrayList<Double> scores = new ArrayList<Double>();
		if (user_response.size() == 0) {
			return null;
		}
		user_response.get(1);
		scores.add((double) user_response.get(1));
		scores.add((double) user_response.get(0));
		return scores;
	}
	// [ /END TEXTSENTIMENTS ]
	
	
	// [ START RECONCILESCORES ]
	public static Double reconcileScore(double[] facial_scores, double text_score) {
		if (facial_scores == null) {
			return text_score;
		}
		double anger = (double) (facial_scores[0] * -1.8);
		double happiness = facial_scores[1];
		double surprise = (double) (facial_scores[2] * -1.5);
		
		double score = (anger + happiness + surprise + text_score) / 4;
		
		if (Math.abs(score) > 1) {
			return (double) 0.99;
		}
		return score;
		
		
		
		
	}
	// [ /END RECONCILESCORES ]
	
	// [ START READ FACIAL ANALYSIS ]
	public static double[] readFacialAnalysisScores(String pathName) {
		/* Read in the doubles from last line of facial_analysis.txt */
		String line = null;
		String last_line = null;
		FileReader filereader;
		BufferedReader bufferedReader;
		
		
		try {
			filereader = new FileReader(pathName);
			System.out.println("read file");
			
			bufferedReader = new BufferedReader(filereader);
			
			while ((line = bufferedReader.readLine()) != null) {
				last_line = line;
			}
			
			bufferedReader.close();
			
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		if (last_line != null) {
			String[] parts = last_line.substring(1,last_line.length()).split(",");
			double[] scores = new double[parts.length-1];
			for (int i = 0; i < parts.length-1; i++) {
				double number = Double.parseDouble(parts[i]);
				double rounded = (int) Math.round(number*1000) / 1000f;
				scores[i] = rounded;
				
				return scores;
			}
		} else {
			System.out.println("rip");
			return null;
		}
		return null;

	}
	// [ /END READ FACIAL ANALYSIS ]
	
	// [ START READ FACIAL ANALYSIS KEYWORD ]
	public static String readFacialAnalysisKey(String pathName) {
		/* Read in the keyword from last line of facial_analysis.txt */
		String line = null;
		String last_line = null;
		
		try {
			FileReader filereader = new FileReader(pathName);
			
			BufferedReader bufferedReader = new BufferedReader(filereader);
			
			while ((line = bufferedReader.readLine()) != null) {
				last_line = line;
			}
			
			bufferedReader.close();
			
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		if (last_line != null) {
			String[] words = last_line.substring(1,last_line.length()).split(",");
			return words[words.length];
		} else {
			return "";
		}
	}
	// [ /END READ FACIAL ANALYSIS KEYWORD ]
	
	// [ START WHICH PROTOCOL ]
	public static String whichProtocol(double[] expressions, ArrayList<String> speech_results) {
		if (expressions == null) {
			if (speech_results.size() == 0) {
				return "no";
			}
			return "normal"; 
		}
		double anger = expressions[0];
		double anxiety = expressions[2];
		double threshold = (double) 0.6;
		if (speech_results.size() == 0) {
			return "no";
		} else if (anger > threshold && anger > anxiety) {
			return "anger";
		} else if (anger > threshold && anxiety >= anger) {
			return "anxiety";
		} else if (anxiety > threshold) {
			return "anxiety";
		} else if ((anger + anxiety + expressions[1])/3 < 0.1) {
			// low enough average that the person may not have actually replied
			return "no";
		} else {
			return "normal";
		}
	}
	
	// [ /END WHICH PROTOCOL ]
	
	// [ START TIME ]
	public static long time() {
		return System.currentTimeMillis()/1000;
	}
	// [ /END TIME ]
}
// [ /END RUNBOT CLASS ]
