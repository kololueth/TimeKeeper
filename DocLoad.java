package application;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class DocLoad  {
	
	
	
	public Document load(File file) {
		

		Document doc = null;	    
		
	
		try {
			
			DocumentBuilderFactory foundry = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = foundry.newDocumentBuilder();
	    
			doc = build.parse(file);		    
		

		
		} catch (IOException e) {
			
    		System.out.println(getClass().getName() + " - Error");
    		System.out.println(e.toString());
    		e.printStackTrace();
			
		} catch (SAXException e) {
			
    		System.out.println(getClass().getName() + " - Error");
    		System.out.println(e.toString());
    		e.printStackTrace();
			
		} catch (ParserConfigurationException e) {
			
    		System.out.println(getClass().getName() + " - Error");
    		System.out.println(e.toString());
    		e.printStackTrace();
		
		} 
		
		return doc;
		
		
		
	} // End of load
	
	
	
	
	
	public void write(Document doc, File file) {
		
		
		try {		
				
				
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
				
			Transformer transformer = transformerFactory.newTransformer();
				
			DOMSource domSource = new DOMSource(doc);
				
			StreamResult streamResult = new StreamResult(file);
			
			transformer.transform(domSource, streamResult);

		
		} catch (TransformerConfigurationException e) {
			
    		System.out.println(getClass().getName() + " - Error");
    		System.out.println(e.toString());
    		e.printStackTrace();
			
		} catch (TransformerException e) {
			
    		System.out.println(getClass().getName() + " - Error");
    		System.out.println(e.toString());
    		e.printStackTrace();
			
		} // End of try catch block
		
		
	} // End of Write
	
	

} // End of Class
