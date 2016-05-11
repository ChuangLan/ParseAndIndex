import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
//import org.apache.commons.io.*;
/**
 * @author naveen.k
 */
public class XMLtoJsonConverter {
    private URL url = null;
    private InputStream inputStream = null;   
    public String getXMLfromJson(String file) {
        try{
        	System.out.println(file); 
            url = XMLtoJsonConverter.class.getClassLoader().getResource(file);
            System.out.println(url); 
            inputStream = url.openStream();
            String xml = inputStream.toString();
            JSON objJson = new XMLSerializer().read(xml);
            System.out.println("JSON data : " + objJson);  
            return objJson.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }finally{
     try {
                if (inputStream != null) {
                    inputStream.close();
                }
                url = null;
            } catch (IOException ex) {}
        }
        
    }
}