package application;

import java.util.concurrent.TimeUnit;
import mold.RecordWrite;

public class Pulse implements Runnable {
	
	
	String project;
	
	
		
	public void setKey(String project) {
		
		this.project = project;
		
		Hash.start = (System.currentTimeMillis()/1000);
		
		
	}
	
	

	
	@Override
	public void run() {
		
		
		boolean exit = false;
		
		int seconds = 60;
		 
		int count = 0;
		
		
			while(!exit) {
				
		
					try { 	
						
						TimeUnit.SECONDS.sleep(seconds);
						
							synchronized(Hash.lock) {
						
								Hash.hashEntry(project, seconds);
								count++;
												
								Thread t = new Thread( new RecordWrite() );
								t.setDaemon(true);
								t.start();
						
							}  // End of Synchronized
						
					} // End of try
					
					
						catch (InterruptedException ie) { 
							
						
								synchronized(Hash.lock) {
								
									Hash.hashEntry(project, Hash.getElapse() - (count * seconds));
									Hash.startReset();
							
									Thread t = new Thread( new RecordWrite() );
									t.setDaemon(true);
									t.start();
							
								}  // End of Synchronized
							
							break;  // allows Thread to escape loop  !!!!!IMPORTANT!!!!


					}  // End of catch
					
				
			} // End of while
			

		
	} // End of run
	
	
} // End of class
