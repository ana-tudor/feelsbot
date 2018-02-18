package curriculum;

import java.util.ArrayList;

public class Curriculum extends ArrayList {
	private ArrayList<Trial> list = new ArrayList<Trial>();
	
	public Curriculum() {
		list = generateTrialList();
	}
			
	public int getSize() {
		return this.list.size();
	}
	
	public ArrayList<Trial> getTrialList() {
		return list;
	}
	
	private ArrayList<Trial> generateTrialList() {
		
		return null;
	}
}
