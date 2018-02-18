package curriculum;

import java.util.ArrayList;

public class Trial {
	private String correctResponse;
	private ArrayList<String> optionsDisplayed;
	private String prompt;
	
	private ArrayList<String> responseBank = new ArrayList<String>();
	
	/**
	 * 
	 * @param expectedResponse - String of what the response should resemble
	 * @param options - 3 suggestions for the user to see
	 */
	public Trial(String prompt, String expectedResponse, ArrayList<String> options) {
		this.prompt = prompt;
		this.correctResponse = expectedResponse;
		this.optionsDisplayed = options;
	}
	
	/**
	 * Displays response options, sets up the trial 
	 */
	public ArrayList<String> get_antecedent() {
		return this.optionsDisplayed;
	}
	
	/**
	 * Returns the bot output for the trial
	 */
	public String run_prompt() {
		return this.prompt;
	}
	
	
	/**
	 * @param keyWord - key word drawn from response
	 * @param response - full list of user response
	 * @return whether the response was what was expected
	 */
	public boolean determine_response_correctness(String keyWord, ArrayList<String> response) {
		System.out.println("Response from Trial.java: " + response);
		
		if(response.size() == 0) {
			return false;
		} else if (keyWord == this.correctResponse) {
			return true;
		} else if (this.optionsDisplayed != null && this.optionsDisplayed.contains(keyWord)) {
			return true;
		} else if (response.contains(this.correctResponse)) {
			return true;
		} else if(in(this.correctResponse, response)) {
			return true;
		} else if (this.correctResponse == "vague") {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Pauses thread for some amount of time before the next trial begins
	 * @throws InterruptedException 
	 */
	public void pause(double time) throws InterruptedException {
		Thread.sleep(10 * 1000);
	}
	
	private boolean in(String element, ArrayList<String> set) {
		for(String str:set) {
			if (str.contains(element)) {
				return true;
			}
		}
		return false;
	}

}
