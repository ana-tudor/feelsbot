import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.github.sarxos.webcam.Webcam;

public class UserResponseStage {

	public static void main(String[] args) {

	}

	public static void collectUserSpeechResponse() {
		/* Collect audio response and convert to text string */
		
	}
	
	public static void collectUserVisualResponse() {
		/* Collect video data (one image frame) and run analysis on it to return sentiment interpretation.
		 * --> Capture picture every time this function is called
		 * --> Save picture as file
		 * --> Pass picture filename into vision.py
		 * --> Get sentiment values
		 * --> Delete picture file so we don't use up the computer's entire storage
		 * --> Return sentiment values (particularly, anger and surprise) */
		
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		
		BufferedImage image = webcam.getImage();
		
		try {
			ImageIO.write(image,  "jpg", new File("test.jpg"));
		
		}
		catch (IOException e) {
			// idk really what to do so we can just print the shrug emoji and hope this never gets executed
			System.out.println("whoops \\u00AF\\\\_(\\u30C4)_/\\u00AF");
		}
		
		try {
			Process p = Runtime.getRuntime().exec("python vision.py");
		}
		
		
		
		
	}
	
	public static void evaluateUserSpeechResponse() {
		
	}
	
	public static void evaluateUserVisualResponse() {
		
	}
	
	public static void determineBotResponse() {
		
	}
}
