import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	public static String parse(String keyName, String filePath) throws IOException{
		
		String content = "";
		if(keyName.endsWith(".txt") || keyName.endsWith(".json")){
			content = readFile(filePath, StandardCharsets.UTF_8).trim();
		}
		else if(keyName.endsWith(".xml")){
			//parseXML(filePath);
		}
		if(!content.startsWith("{")){
			content = content.replaceAll("[^a-zA-Z0-9]+"," ");
			content = content.replaceAll("\n", " ");
			content = "{content:" + content + "}";
		}
		return content;
				
	}
	
	static String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	private static String normalize(String value) {
		String result = value;
		Pattern regex = Pattern.compile("[a-zA-Z]");
		/**Trim leading**/
		for(int i = 0; i < result.length(); i++) {
			char c = result.charAt(i);
			Matcher matcher = regex.matcher(Character.toString(c));
			if (matcher.find()){
			    result = result.substring(i);
			    break;
			}
			if(i == result.length()-1){
				result = "";
			}
		}
		/**Trim trailing**/
		for(int i = result.length()-1; i >= 0; i--) {
			char c = result.charAt(i);
			Matcher matcher = regex.matcher(Character.toString(c));
			if (matcher.find()){
			    result = result.substring(0, i+1);
			    break;
			}
		}		
		result = result.toLowerCase();
		
		return result;
	}


}
