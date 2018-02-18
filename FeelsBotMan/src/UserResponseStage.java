import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.github.sarxos.webcam.Webcam;
import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.Document.Type;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class UserResponseStage {

	
	/**
	 * Collect the text results of the user's speech
	 * @param time - hard timeout on collecting response
	 * @return - list of sentences spoken
	 */
	public static ArrayList<String> collectUserSpeechResponse(long time)
			throws LineUnavailableException, InterruptedException {
		/* Collect audio response and convert to text string */
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("45f69b5b-36c5-4e0c-849a-5961ee969f70", "wvBvrJL3q58T");

		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Line not supported");
			System.exit(0);
		}

		TargetDataLine line = null;
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		AudioInputStream audio = new AudioInputStream(line);
		ArrayList<SpeechResults> results = new ArrayList();

		RecognizeOptions options = new RecognizeOptions.Builder().interimResults(true).inactivityTimeout(2)
				.contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate).build();

		service.recognizeUsingWebSocket(audio, options, new BaseRecognizeCallback() {
			@Override
			public void onTranscription(SpeechResults speechResults) {
				results.add(speechResults);
//				 System.out.println(speechResults);
			}
		});

		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// closing the WebSockets underlying InputStream will close the WebSocket
		// itself.
		line.stop();
		line.close();

		for (int i = 0; i < results.size(); i++) {
			if ((i != results.size() - 1) && (results.get(i + 1).getResultIndex() == results.get(i).getResultIndex())) {
				results.remove(i);
				results.trimToSize();
				i--;
			}
		}
		
		ArrayList<String> stringResults = new ArrayList<String>(results.size());

		for (int i = 0; i < results.size(); i++) {
			stringResults.add(results.get(i).getResults().get(0).getAlternatives().get(0).getTranscript());
		}

		return stringResults;

	}

	
	/**
	 * Evaluates the speech-based sentiment and keywords from the speakers response
	 * 
	 * @param results - results from collecting the user response
	 * @return average response score, magnitude, and most recent keyword
	 */
	public static ArrayList<Object> evaluateUserSpeechResponse(ArrayList<String> results) throws Exception {
		double totalScore = 0;
		double totalMag = 0;
		String keyWord = "";

		for (String text : results) {
			try {
				LanguageServiceClient language = LanguageServiceClient.create();
				Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

				// Detects the sentiment of the text
				Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

				AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder().setDocument(doc).build();
				AnalyzeEntitiesResponse response = language.analyzeEntities(request);

				totalScore += sentiment.getScore();
				totalMag += sentiment.getMagnitude();
				
				if(response.getEntitiesCount() == 0) {
					keyWord = "";
				} else {
					keyWord = response.getEntitiesList().get(0).getName();

				}
				
			} catch (Exception e) {
				System.out.println(results);
				System.out.println(e + "\n");
			}
		}
		ArrayList<Object> ret = new ArrayList<Object>();
		ret.add(totalScore / ((double) results.size()));
		ret.add(totalMag / ((double) results.size()));
		ret.add(keyWord);
		return ret;
	}
	
	/**
	 * Collect video data (one image frame) and run analysis on it to return
	 * sentiment interpretation. --> Capture picture every time this function is
	 * called --> Save picture as file --> Pass picture filename into vision.py -->
	 * Get sentiment values --> Delete picture file so we don't use up the
	 * computer's entire storage --> Return sentiment values (particularly, anger
	 * and surprise)
	 */
	public static void collectUserVisualResponse() {
		

		Webcam webcam = Webcam.getDefault();
		webcam.open();

		BufferedImage image = webcam.getImage();

		try {
			ImageIO.write(image, "jpg", new File("test.jpg"));

		} catch (IOException e) {
			// idk really what to do so we can just print the shrug emoji and hope this
			// never gets executed
			System.out.println("whoops \\u00AF\\\\_(\\u30C4)_/\\u00AF");
		}

		try {
			Process p = Runtime.getRuntime().exec("python vision.py");
		} catch (Exception e) {
			System.out.println("oh noes");
		}

	}

	public static void evaluateUserVisualResponse() {

	}

}
