package curriculum;

import java.util.ArrayList;

interface DiscreteTrialInterface {
	ArrayList<String> responseBank = new ArrayList<String>();
	ArrayList<String> promptBank = new ArrayList<String>();
	ArrayList<String> improvBank = new ArrayList<String>();
	
	public void run_antecedent();
	
	public void run_prompt();
	
	public void run_response();
	
	public default void pause() {
		
	}
}
