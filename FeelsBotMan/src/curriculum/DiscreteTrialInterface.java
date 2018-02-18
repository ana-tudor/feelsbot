package curriculum;

import java.util.ArrayList;

interface DiscreteTrialInterface {
	ArrayList<String> responseBank = new ArrayList<String>();
	ArrayList<String> promptBank = new ArrayList<String>();
	ArrayList<String> improvBank = new ArrayList<String>();
	
	
	/**
	 * Displays response options, sets up the trial 
	 */
	public void run_antecedent();
	
	/**
	 * Returns the bot output for the trial
	 */
	public void run_prompt();
	
	
	/**
	 * 
	 * @return whether the response was what was expected
	 */
	public boolean determine_response_correctness();
	
	/**
	 * Pauses thread for some amount of time before the next trial begins
	 */
	public default void pause(long time) {
		
	}//ArrayList<Trial> contains multiple trials
	
	
	
}
