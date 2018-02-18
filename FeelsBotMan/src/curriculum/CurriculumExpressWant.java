package curriculum;

import java.util.ArrayList;
import java.util.Arrays;

public class CurriculumExpressWant {
	private ArrayList<Trial> list = new ArrayList<Trial>();
	
	public CurriculumExpressWant() {
		list = generateTrialList();
	}
			
	public int getSize() {
		return this.list.size();
	}
	
	public ArrayList<Trial> getTrialList() {
		return list;
	}
	
	private ArrayList<Trial> generateTrialList() {
		
		Trial trial1 = new Trial("Say the word want.", 
				"want", 
				null);
		
		Trial trial2 = new Trial("Do you like food?", 
				"vague", 
				new ArrayList<String>(Arrays.asList("yes", "no")));
		
		Trial trial3 = new Trial("Are these foods?", 
				"yes", 
				new ArrayList<String>(Arrays.asList("hot dogs", "potatos", "broccoli")));
		
		Trial trial4 = new Trial("Are these foods?", 
				"no", 
				new ArrayList<String>(Arrays.asList("pillow", "toothpaste", "cardboard")));
		
		Trial trial5 = new Trial("What kind of food do you like?", 
				"vague", 
				new ArrayList<String>(Arrays.asList("pizza", "carrots", "pasta")));
		
		Trial trial6 = new Trial("Tell me what food you want", 
				"want", 
				new ArrayList<String>(Arrays.asList("I want pizza", "I want carrots", "I want pasta")));
		
		
		return new ArrayList<Trial>(Arrays.asList(trial1, trial2, trial3, trial4, trial5, trial6));
	}
}
