package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import mold.RecordWrite;

public class HashReset implements Runnable {
	
	
	

	@Override
	public void run() {
		
		
		String date = new SimpleDateFormat("MMMM.d.Y").format(new Date()); // Example January.21.2019	
		
		
		Calendar c = Calendar.getInstance();
		long now = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long passed = now - c.getTimeInMillis();
		long secondsPassed = passed / 1000;
		long nextDay = 86400 - secondsPassed;
		
		
			try {
		
			
					TimeUnit.SECONDS.sleep(nextDay - 1); // 11:59:59PM
					
					
					String date2 = new SimpleDateFormat("MMMM.d.Y").format(new Date()); // Example January.21.2019
					Thread t = new Thread(new RecordWrite());
					
					
						if(date.equals(date2)) {
											
							t.setDaemon(true);
							t.start();					
						
						}  // End of if
					
						if(t.isAlive()) {t.join();}  // Wait for today's record to be completed if record was written
					
			
							synchronized (Hash.lock) {  // Take control of the Hash and wait, ensure day change, clear Hash
							
								do {  // Ensure the change of date before moving on!
								
									TimeUnit.MILLISECONDS.sleep(100);
								
									date2 = new SimpleDateFormat("MMMM.d.Y").format(new Date()); // Example January.21.2019
								
								} while (date.equals(date2)); // Example January.21.2019
							
					
								Hash.hash.clear();
								
									if (Hash.start != 0) { // if start has a value other than zero (project is recording), reset the start time
										
										Hash.start = (System.currentTimeMillis()/1000); 
										
									}  
														
							
						} //End of synchronized lock
						
						
					
			
			} catch (InterruptedException ie) {
			
				
				Thread t = new Thread(new HashReset());
				t.setDaemon(true);
				t.start();
			
			}  
		
		
			Thread t = new Thread(new HashReset());
			t.setDaemon(true);
			t.start();
		
	} // End of run
	
	

} // End of Class
