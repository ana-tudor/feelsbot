package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

//import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

public class WatsonSpeech {

	public static void main(String[] args) {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword(
				"2c1cbbe8-0b5f-45aa-851b-03a0601003c0",
		        "z00XpuS3xn7n");

//		List<Voice> voices = service.getVoices().execute();
//		System.out.println(voices);
		
		
//		Voice voice = service.getVoice("en-GB_KateVoice").execute();
//		System.out.println(voice);
		
		
		try {
			  String text = "<speak>This output speech uses SSML.</speak>";
			  Voice voice = service.getVoice("en-GB_KateVoice").execute();
			  InputStream stream = service.synthesize((String)text, 
					  Voice.EN_ALLISON, AudioFormat.WAV, null).execute();
			  InputStream in = WaveUtils.reWriteWaveHeader(stream);
			  OutputStream out = new FileOutputStream("hello_world.wav");
			  byte[] buffer = new byte[1024];
			  int length;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }
			  out.close();
			  in.close();
			  stream.close();
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
	
//			try {
//				File out = new File("hello_world.wav");
//			    AudioInputStream stream;
//			    AudioFormat format;
//			    DataLine.Info info;
//			    Clip clip;
//	
//			    stream = AudioSystem.getAudioInputStream(out);
//			    format = stream.getFormat();
//			    info = new DataLine.Info(Clip.class, format);
//			    clip = (Clip) AudioSystem.getLine(info);
//			    clip.open(stream);
//			    clip.start();
//			}
//			catch (Exception e) {
//			    //whatevers
//			}
		
		
		
		
	}

}
