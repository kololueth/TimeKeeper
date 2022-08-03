package application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HashFill implements Runnable {
	
	
	
	public void run() {
		

		
		try {
			
			String date = new SimpleDateFormat("MMMM.d.Y").format(new Date());
			File record = new File(new SimpleDateFormat("MMMM(Y)").format(new Date()) + ".xml");
			
			DocumentBuilderFactory found = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = found.newDocumentBuilder();
		
			NodeList dateList = build.parse(record).getElementsByTagName("date");
			

				for (int i = 0; i < dateList.getLength(); i++) {  // Gets element according to attribute date
				  if(((Element) dateList.item(i)).getAttribute("id").trim().equals(date)) {  // If node is today's date


					  NodeList projectList = ((Element) dateList.item(i)).getElementsByTagName("project");  // Project NodeList for date			
					  
					  
					  		for (int a = 0; a < projectList .getLength(); a++) {  // Gets element according to attribute (project name).  Adds project name and time value in seconds to hashmap.  Sends back to TimerFrame
				  
					  			Element project = (Element) projectList .item(a);  // Convert project node to element for Attribute extraction
					  			NodeList timeList = project.getElementsByTagName("time1"); // time1 node for specific project
					  			
					  			synchronized(Hash.lock) {
					  			
					  				Hash.hash.put(project.getAttribute("name"), Integer.parseInt(timeList.item(0).getTextContent()));  // get's project name, and adds time value
					  			
					  			}

					  		} // End of for
					  	
			  
				  } // End of if
				} // End of outer for

				
		} catch (IOException e) {  // File Not Found
			
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
		
		} catch (ParserConfigurationException e) {
			
				System.out.println(getClass().getName() + " - Error");
				System.out.println(e.toString());
				e.printStackTrace();
			
		} catch (SAXException e) {
			
				System.out.println(getClass().getName() + " - Error");
				System.out.println(e.toString());
				e.printStackTrace();

		} // End of catch statements 
			
	
		
		Thread t = new Thread(new HashReset());
		t.setDaemon(true);
		t.start();
		

	}  // End of run()

}  // End of class
