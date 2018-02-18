import java.util.ArrayList;
import java.util.Arrays;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.Document.Type;

public class BotResponseStage {
	

	private static String[] anxietyResponses = {
			"You don't need it right now. Play a game with me instead :)", 
			"Let's not think about that right now.",
			"It's okay. It's okay. You will be okay.",
			"Hey it's okay, look at this cute thing I can do!",
			"Look at me. You are going to be okay. :)",
			"I'm worried about you. Are you feeling okay?",
			"Can you focus on me, please? Let's try again.",
			"That is not okay. Pay attention, please."};
	
	private static String[] angerResponses = {
			"You don't need it right now. Play a game with me instead :)", 
			"Let's not think about that right now.",
			"It's okay. It's okay. You will be okay.",
			"Hey it's okay, look at this cute thing I can do!",
			"Look at me. You are going to be okay. :)",
			"I'm worried about you. Are you feeling okay?",
			"Can you focus on me, please? Let's try again.",
			"That is not okay. Pay attention, please."};
	
	private static String[] normalCorrectResponses = {
			"Good. :) Let's move on to the next exercise.", 
			"Great job. We have a few more left to go.",
			"Nice! Time for the next one",
			"Perfect. On to the next one! :)"};
	
	private static String[] normalWrongResponses = {
			"That's not right. Let's move on to the next exercise.",
			"Try again.",
			"Try again, focus on me."};
	
	private static String[] noResponses = {
			"What was that?", 
			"Can you say that again?",
			"Try again please.",
			"Try using your words :)",
			"I couldn't hear you"};
	
	private static double[] anxietyRespWeights = new double[anxietyResponses.length];
	private static double[] angerRespWeights = new double[angerResponses.length];
	private static double[] normalCorrectRespWeights = new double[normalCorrectResponses.length];
	private static double[] normalWrongRespWeights = new double[normalWrongResponses.length];
	private static double[] noRespWeights = new double[noResponses.length];
	
	public static void initializeResponseStage() throws Exception {
		
		double totalScore = 0;
		double totalMag = 0;
		
		for (int i = 0; i < anxietyRespWeights.length; i++) {
			String text = anxietyResponses[i];
			Sentiment sentiment = parseText(text);
			anxietyRespWeights[i] = sentiment.getScore();		      
	    }
		for (int i = 0; i < angerRespWeights.length; i++) {
			String text = angerResponses[i];
			Sentiment sentiment = parseText(text);
			angerRespWeights[i] = sentiment.getScore();		      
	    }
		for (int i = 0; i < normalCorrectRespWeights.length; i++) {
			String text = normalCorrectResponses[i];
			Sentiment sentiment = parseText(text);
			normalCorrectRespWeights[i] = sentiment.getScore();		      
	    }
		for (int i = 0; i < normalWrongRespWeights.length; i++) {
			String text = normalWrongResponses[i];
			Sentiment sentiment = parseText(text);
			normalWrongRespWeights[i] = sentiment.getScore();		      
	    }
		for (int i = 0; i < noRespWeights.length; i++) {
			String text = noResponses[i];
			Sentiment sentiment = parseText(text);
			noRespWeights[i] = sentiment.getScore();		      
	    }
	}
	
	private static Sentiment parseText(String text) throws Exception {
		// Instantiates a client
		try (LanguageServiceClient language = LanguageServiceClient.create()) {

			Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

			// Detects the sentiment of the text
			Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
			return sentiment;
		}
	}
	
	public static String determineBotResponse(double score, String type, boolean correct, String[] keywords) {
		//Bot should have bank of responses in emergency
		//Bot should respond happily when human happy
		if(type == "anxiety") {
			return anxietyResponses[matchScoreToIndex(score, anxietyRespWeights)];
		} else if (type == "anger") {
			return angerResponses[matchScoreToIndex(score, angerRespWeights)];
		} else if (type == "normal") {
			if (correct) {
				return normalCorrectResponses[matchScoreToIndex(score, normalCorrectRespWeights)];
			} else {
				return normalWrongResponses[matchScoreToIndex(score, normalWrongRespWeights)];
			}
		} else if (type == "no") {
			return noResponses[matchScoreToIndex(score, noRespWeights)];
		}
		
		return null;
	}
	
	private static int matchScoreToIndex(double score, double[] scores) {
		double smallestDiff = 2;
		int index = 0;
		for (int i = 0; i < scores.length; i++) {
			if(Math.abs(scores[i]-score) < smallestDiff) {
				index = i;
				smallestDiff = scores[i]-score;
			}
		}
		
		return index;
	}
}
