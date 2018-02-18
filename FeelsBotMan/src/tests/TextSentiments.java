package tests;

import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

import java.io.IOException;
import java.lang.Exception;

public class TextSentiments {
	public static void main(String... args) throws Exception {
		// Instantiates a client
		try (LanguageServiceClient language = LanguageServiceClient.create()) {

			// The text to analyze
			// String text = "That's";
			// Document doc = Document.newBuilder()
			// .setContent(text).setType(Type.PLAIN_TEXT).build();
			//
			// // Detects the sentiment of the text
			// Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();\

			String[] texts = { "This is a cake", "This is a good cake", "This is a damn good cake",
					"This is a damned cake", ":)", ":(", "Yes", "No" };

//			for (String text : texts) {
//				Sentiment sentiment = parseText(text);
//				AnalyzeEntitiesResponse keywords = parseKeywords(text);
//				System.out.printf("Text: %s%n", text);
//				System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
//				System.out.println(keywords.getEntitiesList());
//				System.out.println(keywords.getEntitiesList().get(0).getName());
//			}

			
//			String[] stringtexts = new String[texts.length];
//			
//			for(int i = 0; i < texts.length; i++) {
//				stringtexts[i] = texts.get(i).gettexts().get(0).getAlternatives().get(0).getTranscript();
//			}
			
			double totalScore = 0;
			double totalMag = 0;
			String keyWord = "";
			
			for (String text: texts) {
			      Document doc = Document.newBuilder()
			          .setContent(text).setType(Type.PLAIN_TEXT).build();

			      // Detects the sentiment of the text
			      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
			      
			      AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
			    		  .setDocument(doc)
			    		  .build();
			      AnalyzeEntitiesResponse response = language.analyzeEntities(request);
			      

			      totalScore += sentiment.getScore();
			      totalMag += sentiment.getMagnitude();
			      keyWord = response.getEntitiesList().get(0).getName();
			      System.out.println(totalScore + " " +  totalMag + " " +  keyWord);
			}
		}
	}

	public static Sentiment parseText(String text) throws Exception {
		// Instantiates a client
		try (LanguageServiceClient language = LanguageServiceClient.create()) {

			Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

			// Detects the sentiment of the text
			Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
			return sentiment;
		}
	}
	
	public static AnalyzeEntitiesResponse parseKeywords(String text) throws IOException, Exception {
		try (LanguageServiceClient language = LanguageServiceClient.create()) {

			Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

			
			 AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
				     .setDocument(doc)
				     .build();
			 AnalyzeEntitiesResponse response = language.analyzeEntities(request);
			 return response;
				  
		}
	}
	
}
