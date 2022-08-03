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

public class RecordEdit {
	
	
	// Called from itemEdit
	
	
	public void updateProjectNodes(Document doc, Element dateListItem, String origin, String product) {
		

		 
		  NodeList projectList = dateListItem.getElementsByTagName("project");  // Gets number of projects for date			

		  															  	  
					for(int a = 0; a < projectList.getLength(); a++) {  // Scans through all projects for given date									  										  	

							
							String projectName = ((Element) projectList.item(a)).getAttribute("name").trim();  // Get's project name from file and changes to string for comparison with hashmap key name 
		  
						
								if(origin.equals(projectName)){  // If one of the previous projects is entered modify that entry
																				  
									((Element) projectList.item(a)).setAttribute("name", product);
			 
									break;
										
								}  // end of if match
									
		  
					} // end of project scan
		  

	} // End of update project Nodes
	
	
	
	

	
	
	public void edit(String origin, String product) {   // Writes time file named currentmonth.xml.  (There should be a new timefile.xml for the first entry of each month.)

	
		
		String date = new SimpleDateFormat("MMMM.d.Y").format(new Date()); // Example January.21.2019
				
		File record = new File(new SimpleDateFormat("MMMM(Y)").format(new Date()) + ".xml");  // Example (January(2019))
		
	
			
			
			if(record.exists()) {  // If record file (currentmonth(Year).xml) already exists.  
				record.setWritable(true);
			
			
					DocLoad Create = new DocLoad();
					
					Document doc = Create.load(record);  
						
						NodeList dateList = doc.getElementsByTagName("date");
						
						
											
							for (int i = 0; i < dateList.getLength(); i++) {  // Iterates through each date node 		
								
								if(((Element) dateList.item(i)).getAttribute("id").trim().equals(date)) {    // If there is an entry for today's date
								  
									updateProjectNodes(doc, (Element) dateList.item(i), origin, product);  // Updates current entries 
								  
								}  // End of if
								
							 }  // End of For
							
								  
							  

					Create.write(doc, record);
					record.setReadOnly();
					
			}  // End of Else
			
						
			
	
	} // end of edit
	
	

} // End of Class
