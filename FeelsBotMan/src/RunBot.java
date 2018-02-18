import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

import javax.sound.sampled.LineUnavailableException;

import com.google.cloud.language.v1beta2.Sentiment;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

import curriculum.Curriculum;
import curriculum.Trial;

// [ START RUNBOT CLASS ]
public class RunBot {
	
	// Set file path for bot speech text file
	public static final String FILENAME = "bot_speech.txt";
	public static final String face_analysis_txt = "face_analysis.txt";
	
	// [ START MAIN() ]
	public static void main(String[] args) {
		/* Write actual description here at some point */
		
		// Instantiate Curriculum object and get list of Trials
		Curriculum curriculum = new Curriculum();
		ArrayList<Trial> trials = curriculum.getTrialList();
		
		// set t_0 as the absolute time in seconds when the program is first initiated
		long t_0 = time(); 
		
		// Loop through all the trials in the ArrayList
		for (Trial trial:trials) {
			// First, call run_prompt() and write returned string to bot_speech.txt
			String prompt = trial.run_prompt();
			writeBotSpeech(prompt);
			
			// Now that prompt has been shown to user, make call to audio transcription
			// and video transcription
			
			ArrayList<SpeechResults> speech_results = null;
			try {
				speech_results = UserResponseStage.collectUserSpeechResponse(time());

			} catch (LineUnavailableException line) {
				line.getStackTrace();
			} catch (InterruptedException i) {
				i.getStackTrace();
			}
			
			// get analysis of user's sentiments (based on text)
			ArrayList<Object> user_response = null;
			try {
				user_response = UserResponseStage.evaluateUserSpeechResponse(speech_results);
			} catch (Exception e) {
				e.getStackTrace();
			}
			
			// get analysis of user's facial expressions
			float[] expressions = readFacialAnalysisScores(face_analysis_txt);
			
			// determine whether normal protocol can continue, or if special protocols (anger, anxiety) must be activated
			String response_type = whichProtocol(expressions, speech_results);
			
			// take weighted average of facial expression scores and text analysis scores to get overall sentiment score
			float score = reconcileScore(expressions, textSentiments(user_response));
			
			
			
			
			
			// determine bot's appropriate response
			String response = BotResponseStage.determineBotResponse(score, response_type, correct, keywords);
			
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
	public static ArrayList<Float> facialAnalysis() {
		/* Will eventually call vision.py detect_face() method; for now,
		 * just returns something that will let the rest of the program run */
		ArrayList<Float> values = new ArrayList<Float>();
		values.add((float) 0.5);
		values.add((float) 0.2);
		values.add((float) 0.6);
		return values;
	}
	
	// [ START TEXTSENTIMENTS ]
	public static ArrayList<Float> textSentiments(ArrayList<Object> user_response) {
		ArrayList<Float> scores = new ArrayList<Float>();
		user_response.get(1);
		scores.add((float) user_response.get(1));
		scores.add((float) user_response.get(0));
		return scores;
	}
	// [ /END TEXTSENTIMENTS ]
	
	
	// [ START RECONCILESCORES ]
	public static Float reconcileScore(float[] facial_scores, ArrayList<Float> textSentiments) {
		float anger = (float) (facial_scores[0] * -1.8);
		float happiness = facial_scores[1];
		float surprise = (float) (facial_scores[2] * -1.5);
		
		float text_score = textSentiments.get(1);
		
		float score = (anger + happiness + surprise + text_score) / 4;
		
		if (Math.abs(score) > 1) {
			return (float) 0.99;
		}
		return score;
		
		
		
		
	}
	// [ /END RECONCILESCORES ]
	
	// [ START READ FACIAL ANALYSIS ]
	public static float[] readFacialAnalysisScores(String pathName) {
		/* Read in the floats from last line of facial_analysis.txt */
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
		String[] parts = last_line.substring(1,last_line.length()).split(",");
		float[] scores = new float[parts.length-1];
		for (int i = 0; i < parts.length-1; i++) {
			float number = Float.parseFloat(parts[i]);
			float rounded = (int) Math.round(number*1000) / 1000f;
			scores[i] = rounded;
		}
		return scores;
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
		String[] words = last_line.substring(1,last_line.length()).split(",");
		return words[words.length];
	}
	// [ /END READ FACIAL ANALYSIS KEYWORD ]
	
	// [ START WHICH PROTOCOL ]
	public static String whichProtocol(float[] expressions, ArrayList<SpeechResults> speech_results) {
		float anger = expressions[0];
		float anxiety = expressions[2];
		float threshold = (float) 0.6;
		if (speech_results.length < 3) {
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
