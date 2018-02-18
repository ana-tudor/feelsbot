package tests;


import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class WatsonRecognize {
	public static void main(String[] args) throws LineUnavailableException, InterruptedException {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("45f69b5b-36c5-4e0c-849a-5961ee969f70",
				"wvBvrJL3q58T");

		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
		  System.out.println("Line not supported");
		  System.exit(0);
		}

		TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();

		AudioInputStream audio = new AudioInputStream(line);
		ArrayList<SpeechResults> results = new ArrayList();
		
		RecognizeOptions options = new RecognizeOptions.Builder()
		  .interimResults(true)
		  .inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
		  .contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
		  .build();

		service.recognizeUsingWebSocket(audio, options, new BaseRecognizeCallback() {
		  @Override
		  public void onTranscription(SpeechResults speechResults) {
			results.add(speechResults);
		    System.out.println(speechResults);
		  }
		});

		System.out.println("Listening to your voice for the next 30s...");
		Thread.sleep(30 * 1000);

		// closing the WebSockets underlying InputStream will close the WebSocket itself.
		line.stop();
		line.close();
		int prevIndex = 0;
		for(int i = 0; i < results.size(); i++) {
			if ((i != results.size() - 1) && (results.get(i + 1).getResultIndex() == results.get(i).getResultIndex())) {
				results.remove(i);
				results.trimToSize();
				i--;
			}
		}
		for( int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).getResults());
		}
		
		String[] stringResults = new String[results.size()];
		
		for(int i = 0; i < results.size(); i++) {
			stringResults[i] = results.get(i).getResults().get(0).getAlternatives().get(0).getTranscript();
			System.out.println(stringResults[i]);
		}

		System.out.println("Fin.");
	}
}
