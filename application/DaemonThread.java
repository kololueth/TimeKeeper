package application;

import java.util.concurrent.ThreadFactory;

public class DaemonThread implements ThreadFactory{
	
	
		
		@Override
		public Thread newThread(Runnable r) {
		
		
			Thread t = new Thread(r);
			
			t.setDaemon(true);
			
			return t;
		
		
		} // End of newThread	
		
		


} // End of Class
