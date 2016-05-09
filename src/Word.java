import org.bson.Document;

public class Word{
	private String word = "";
	private String index = "";
	Word(String word, String index){
		this.word = word;
		this.index = index;
	}
	public String getWord(){
		return word;
	}
	public String getIndex(){
		return index;
	}
	public Object toJson(){
		Document doc = new Document().append("word", word).append("index", index);
		return doc;
	}
}
