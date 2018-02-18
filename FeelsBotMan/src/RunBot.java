import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;

import com.google.cloud.language.v1beta2.Sentiment;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

import curriculum.Curriculum;
import curriculum.Trial;

// [ START RUNBOT CLASS ]
public class RunBot {
	
	// Set file path for bot speech text file
	public static final String FILENAME = "bot_speech.txt";
	
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
			ArrayList<Float> expressions = facialAnalysis();
			
			
			
			
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
	
	public static ArrayList<Float> textSentiments(ArrayList<Object> user_response) {
		ArrayList<Float> scores = new ArrayList<Float>();
		user_response.get(1);
		scores.add((float) user_response.get(1));
		scores.add((float) user_response.get(0));
		return scores;
	}
	
	// [ START TIME ]
	public static long time() {
		return System.currentTimeMillis()/1000;
	}
	// [ /END TIME ]
}
// [ /END RUNBOT CLASS ]
