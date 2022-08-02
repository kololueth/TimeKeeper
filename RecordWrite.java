package mold;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.DocLoad;
import application.Hash;
import application.Main;

public class RecordWrite implements Runnable {
	
	
	{


		
	}
	
	
	private void addProjectNode(Document doc, Element dateNode) {
		
		
		for(int i = 0; i < Main.projects.length; i++) {
			
			
			if(Hash.hash.containsKey(Main.projects[i])){
					
				Element projectNode = doc.createElement("project");
			
				Attr attr2 = doc.createAttribute("name");
				attr2.setValue(Main.projects[i]);						
				projectNode.setAttributeNode(attr2);
			
				dateNode.appendChild(projectNode);	
				
				Element time1 = doc.createElement("time1");
				time1.appendChild(doc.createTextNode(Integer.toString(Hash.hash.get(Main.projects[i]))));
						
				Element time2 = doc.createElement("time2");
				time2.appendChild(doc.createTextNode(Hash.valueString(Hash.hash.get(Main.projects[i]))));
			
				projectNode.appendChild(time1);
				projectNode.appendChild(time2);

			
			} // End of If
	
		} // End of for
		
		
	}  // End of addProjectNode
	
	
	public void updateProjectNodes(Document doc, Element dateListItem, String date) {
		

					  									  
					 /* The following iterator processes every key within the Hashmap.  All the Keys of the Hashmap are entered into a list which the
					  * iterator goes through.  For each key in the iterator, the date node in time file is checked for a previous entry of that key.
					  * If the time file contains the project key, that key is updated with the value in the current Hashmap for that project key.  If the 
					  * Hashmap key was not found in the document, the node for the new project key is created, given values, and appended.   */
					  
					  NodeList projectList = dateListItem.getElementsByTagName("project");  // Gets number of projects for date	
					  
					  Iterator<String> keyList = Hash.hash.keySet().iterator(); //Gets all keys of hashmap for iteration * maybe needs to be synchronized
					  															  	  
					  	
					  
					  while (keyList.hasNext()) {  // seached through all keys
						  
						  String keyString = keyList.next().toString();  // it.next() will not equal projectname string so must be converted to string
						  																			
						  boolean entered = false;  // entry indicator												  
						  

									for(int a = 0; a < projectList.getLength(); a++) {  // Scans through all projects for given date									  										  	
		
										
											String projectName = ((Element) projectList.item(a)).getAttribute("name").trim();  // Get's project name from file and changes to string for comparison with hashmap key name 
					  
									
												if(projectName.equals(keyString)){  // If one of the previous projects is entered modify that entry
																							  
													NodeList time1 = ((Element) projectList.item(a)).getElementsByTagName("time1");
													NodeList time2 = ((Element) projectList.item(a)).getElementsByTagName("time2");			
													
														synchronized(Hash.lock) {
						 
															((Element) time1.item(0)).setTextContent(Integer.toString(Hash.hash.get(keyString)));										  
															((Element) time2.item(0)).setTextContent(Hash.valueString(Hash.hash.get(keyString)));
													
														}  // End of synchronized
						 
													entered = true;  // Blocks second entry of same project for today's date.  Possibly could use a break statement when modifying code.
													
								
													
													break;
													
												}  // end of if match
					  
									} // end of project scan
					  
					  
												if(entered == false) { // project has not been entered
													
						
													Element project = doc.createElement("project");
																																								
													Attr nameattr = doc.createAttribute("name");
													nameattr.setValue(keyString);																																			
													project.setAttributeNode(nameattr);
													dateListItem.appendChild(project);		
													
													Element time1 = doc.createElement("time1");
													Element time2 = doc.createElement("time2");	
														
														synchronized(Hash.lock) {
																																						 
															time1.setTextContent(Integer.toString(Hash.hash.get(keyString)));																						 										 
															time2.setTextContent(Hash.valueString(Hash.hash.get(keyString)));
													
														} // End of synchronized
													
													project.appendChild(time1);
													project.appendChild(time2);
							 
											
												} // end of entered if
						
					
					  } // End of iterator
					  	


	} // End of update project Nodes
	
	
	
	@Override
	public synchronized void run() {   // Writes time file named currentmonth.xml.  (There should be a new timefile.xml for the first entry of each month.)

		
		String date = new SimpleDateFormat("MMMM.d.Y").format(new Date()); // Example January.21.2019	
		File record = new File(new SimpleDateFormat("MMMM(Y)").format(new Date()) + ".xml");  // Example (January(2019))
		
		record.setWritable(true);
				

		
			if(!record.exists()) {  // If file does not Exist, create a new document and add first entry. 
		
						
				try {
					
					DocumentBuilderFactory foundry = DocumentBuilderFactory.newInstance();
					DocumentBuilder build = foundry.newDocumentBuilder();
					Document doc = build.newDocument();
							    
					doc.appendChild(doc.createElement("TimeCreeper"));
					
					Element dateNode = doc.createElement("date");
					
					Attr attr2 = doc.createAttribute("name"); 
					attr2.setValue(new SimpleDateFormat("EEEE").format(new Date()));  // "EEEE" is full name of day lowerCase	
					dateNode.setAttributeNode(attr2);  // Give date element date attribute
					
					
					Attr attr = doc.createAttribute("id");
					attr.setValue(date);	
					dateNode.setAttributeNode(attr);  // Give date element date attribute
					
					doc.getFirstChild().appendChild(dateNode);				
					
						addProjectNode(doc,dateNode);
				  
						new DocLoad().write(doc, record);
				
				} catch (ParserConfigurationException e) {
				
		    		System.out.println(getClass().getName() + " - Error");
		    		System.out.println(e.toString());
		    		e.printStackTrace();
				
				} 
				
			} // End of if record file does not exist 
			
			

			
			if(record.exists()) {  // If record file (currentmonth(Year).xml) already exists.  
			
			
				DocLoad Create = new DocLoad();
				
				Document doc = Create.load(record);  
						
					NodeList dateList = doc.getElementsByTagName("date");
						
			
						boolean noDate = true;  // indicator for entry of date node
						
						
							for (int i = 0; i < dateList.getLength(); i++) {  // Iterates through each date node 			
								
								if(((Element) dateList.item(i)).getAttribute("id").trim().equals(date)) {  
								   
									noDate = false;  
								  
									updateProjectNodes(doc, (Element) dateList.item(i), date);  // Updates current entries w
								  
								}  // End of if
								
							 }  // End of For
						
							  
						
							if(noDate == true) { // If there has not been an entry for date, add date node and projects
							  
							  	Element dateNode = doc.createElement("date");
							  	
							  	Attr attr2 = doc.createAttribute("name");
								attr2.setValue(new SimpleDateFormat("EEEE").format(new Date()));	
								dateNode.setAttributeNode(attr2);  // Give date element date attribute
							  	
								Attr attr = doc.createAttribute("id");
								attr.setValue(date);	
								dateNode.setAttributeNode(attr);  // Give date element date attribute
								
								doc.getFirstChild().appendChild(dateNode);
								
								addProjectNode(doc,dateNode);
								
							 }  // End of noDate
							

				Create.write(doc, record);
							
			
			}  // End of if
			
						
				
		record.setReadOnly();
		
	
		
	} // end of run

}
