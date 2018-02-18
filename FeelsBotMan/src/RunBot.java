import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// [ START RUNBOT CLASS ]
public class RunBot {
	
	// Set file path for bot speech text file
	public static final String FILENAME = "../bot_speech.txt";
	
	// [ START MAIN() ]
	public static void main(String[] args) {
		//Monitor UI changes and start running camera
		String s = "dank memes!";
		writeBotSpeech(s);
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

}
// [ /END RUNBOT CLASS ]
