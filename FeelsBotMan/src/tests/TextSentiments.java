package tests;


import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import java.lang.Exception;

public class TextSentiments {
	  public static void main(String... args) throws Exception {
	    // Instantiates a client
	    try (LanguageServiceClient language = LanguageServiceClient.create()) {

	      // The text to analyze
	      // String text = "That's";
//	      Document doc = Document.newBuilder()
//	          .setContent(text).setType(Type.PLAIN_TEXT).build();
//
//	      // Detects the sentiment of the text
//	      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();\
	    	
	      String[] texts = {"This is a cake", "This is a good cake", "This is a damn good cake", "This is a damned cake"};
	      
	      for (String text: texts) {
	    	  Sentiment sentiment = parseText(text);
	    	  System.out.printf("Text: %s%n", text);
		      System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
	      }

	    }
	  }
	  
	  public static Sentiment parseText(String text) throws Exception {
		// Instantiates a client
		    try (LanguageServiceClient language = LanguageServiceClient.create()) {

		      Document doc = Document.newBuilder()
		          .setContent(text).setType(Type.PLAIN_TEXT).build();

		      // Detects the sentiment of the text
		      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
		      return sentiment;
	  }
	}
}
