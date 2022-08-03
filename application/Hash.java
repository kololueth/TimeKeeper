package application;

import java.util.HashMap;

public class Hash {
	
	
	public static HashMap<String, Integer> hash = new HashMap<String, Integer>();
	
	public static final Object lock = new Object();
	
	public static long start;
	

	
	
	
	public static synchronized int getElapse() {
		
		long end = (System.currentTimeMillis()/1000);
		
		int elapse = (int) (end - start); 
		
	return elapse;
	
		
	}  // End of getElapse
	
	
	public static synchronized void hashEntry(String project, int elapse) {
		

		if(hash.containsKey(project)){
			
			elapse = hash.get(project) + elapse;
			hash.put(project, elapse);  // Update new total elapsed time into map
     					
		}
		
		if(!hash.containsKey(project)){
			
			hash.put(project, elapse);  // Update new total elapsed time into map
     					
		}
		
		
	} // End of hashEntry
	
	
	public static synchronized void startReset() {
		
		start = 0;
		
	} // End of startReset

	
	public static synchronized String valueString(int value) {
		
		String text = "";
		
		if(value < 60) {
			
			if(value == 1) {
				
				text = Integer.toString(value) + " second";
				
			} else {

			text = Integer.toString(value) + " seconds";
			
			}
	
		}  // End of if
		
		
		
		if(value >= 60 && value < 3600 ) {
			
			if(value < 120) { 
				
				value = value/60;
				
				text = Integer.toString(value) + " minute"; 
				
			}
			
			if(value >= 120) {
				
				value = value/60;
				
				text = Integer.toString(value) + " minutes";
			}
	
		}  // End of if
		
		
		
		if(value >= 3600) {
			
			if(value == 3600) { text = Integer.toString(value) + " hour"; }
			
			if(value > 3600) {
				
				Double value2 = (double) value/3600;
				
				text = Double.toString(value2);

				if(text.length() >= 4) {
					
					text = Double.toString(value2).substring(0, 4) + " hours";
					
				} else {
					
					text = Double.toString(value2).substring(0, 4) + " hours";
					
				}
				
			}
	
		}  // End of if
		
		return text;
		
	}  // End of value String
	

}
