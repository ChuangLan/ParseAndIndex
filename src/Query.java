

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.*;

/**
 * Servlet implementation class Query
 */
public class Query{
	private static final long serialVersionUID = 1L;
	final static String[] stopWords = { "a", "able", "about", "above", "abst", "accordance", "according", "accordingly",
			"across", "act", "actually", "added", "adj", "affected", "affecting", "affects", "after", "afterwards",
			"again", "against", "ah", "all", "almost", "alone", "along", "already", "also", "although", "always",
			"am", "among", "amongst", "an", "and", "announce", "another", "any", "anybody", "anyhow", "anymore",
			"anyone", "anything", "anyway", "anyways", "anywhere", "apparently", "approximately", "are", "aren",
			"arent", "arise", "around", "as", "aside", "ask", "asking", "at", "auth", "available", "away",
			"awfully", "b", "back", "be", "became", "because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "begin", "beginning", "beginnings", "begins", "behind", "being", "believe", "below",
			"beside", "besides", "between", "beyond", "biol", "both", "brief", "briefly", "but", "by", "c", "ca",
			"came", "can", "cannot", "can't", "cause", "causes", "certain", "certainly", "co", "com", "come",
			"comes", "contain", "containing", "contains", "could", "couldnt", "d", "date", "did", "didn't",
			"different", "do", "does", "doesn't", "doing", "done", "don't", "down", "downwards", "due", "during",
			"e", "each", "ed", "edu", "effect", "eg", "eight", "eighty", "either", "else", "elsewhere", "end",
			"ending", "enough", "especially", "et", "et-al", "etc", "even", "ever", "every", "everybody",
			"everyone", "everything", "everywhere", "ex", "except", "f", "far", "few", "ff", "fifth", "first",
			"five", "fix", "followed", "following", "follows", "for", "former", "formerly", "forth", "found",
			"four", "from", "further", "furthermore", "g", "gave", "get", "gets", "getting", "give", "given",
			"gives", "giving", "go", "goes", "gone", "got", "gotten", "h", "had", "happens", "hardly", "has",
			"hasn't", "have", "haven't", "having", "he", "hed", "hence", "her", "here", "hereafter", "hereby",
			"herein", "heres", "hereupon", "hers", "herself", "hes", "hi", "hid", "him", "himself", "his", "hither",
			"home", "how", "howbeit", "however", "hundred", "i", "id", "ie", "if", "i'll", "im", "immediate",
			"immediately", "importance", "important", "in", "inc", "indeed", "index", "information", "instead",
			"into", "invention", "inward", "is", "isn't", "it", "itd", "it'll", "its", "itself", "i've", "j",
			"just", "k", "keep", "keeps", "kept", "kg", "km", "know", "known", "knows", "l", "largely", "last",
			"lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked",
			"likely", "line", "little", "'ll", "look", "looking", "looks", "ltd", "m", "made", "mainly", "make",
			"makes", "many", "may", "maybe", "me", "mean", "means", "meantime", "meanwhile", "merely", "mg",
			"might", "million", "miss", "ml", "more", "moreover", "most", "mostly", "mr", "mrs", "much", "mug",
			"must", "my", "myself", "n", "na", "name", "namely", "nay", "nd", "near", "nearly", "necessarily",
			"necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "ninety", "no",
			"nobody", "non", "none", "nonetheless", "noone", "nor", "normally", "nos", "not", "noted", "nothing",
			"now", "nowhere", "o", "obtain", "obtained", "obviously", "of", "off", "often", "oh", "ok", "okay",
			"old", "omitted", "on", "once", "one", "ones", "only", "onto", "or", "ord", "other", "others",
			"otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "owing", "own",
			"p", "page", "pages", "part", "particular", "particularly", "past", "per", "perhaps", "placed",
			"please", "plus", "poorly", "possible", "possibly", "potentially", "pp", "predominantly", "present",
			"previously", "primarily", "probably", "promptly", "proud", "provides", "put", "q", "que", "quickly",
			"quite", "qv", "r", "ran", "rather", "rd", "re", "readily", "really", "recent", "recently", "ref",
			"refs", "regarding", "regardless", "regards", "related", "relatively", "research", "respectively",
			"resulted", "resulting", "results", "right", "run", "s", "said", "same", "saw", "say", "saying", "says",
			"sec", "section", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves",
			"sent", "seven", "several", "shall", "she", "shed", "she'll", "shes", "should", "shouldn't", "show",
			"showed", "shown", "showns", "shows", "significant", "significantly", "similar", "similarly", "since",
			"six", "slightly", "so", "some", "somebody", "somehow", "someone", "somethan", "something", "sometime",
			"sometimes", "somewhat", "somewhere", "soon", "sorry", "specifically", "specified", "specify",
			"specifying", "still", "stop", "strongly", "sub", "substantially", "successfully", "such",
			"sufficiently", "suggest", "sup", "sure", "t", "take", "taken", "taking", "tell", "tends", "th", "than",
			"thank", "thanks", "thanx", "that", "that'll", "thats", "that've", "the", "their", "theirs", "them",
			"themselves", "then", "thence", "there", "thereafter", "thereby", "thered", "therefore", "therein",
			"there'll", "thereof", "therere", "theres", "thereto", "thereupon", "there've", "these", "they",
			"theyd", "they'll", "theyre", "they've", "think", "this", "those", "thou", "though", "thoughh",
			"thousand", "throug", "through", "throughout", "thru", "thus", "til", "tip", "to", "together", "too",
			"took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "ts", "twice", "two", "u",
			"un", "under", "unfortunately", "unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups",
			"us", "use", "used", "useful", "usefully", "usefulness", "uses", "using", "usually", "v", "value",
			"various", "'ve", "very", "via", "viz", "vol", "vols", "vs", "w", "want", "wants", "was", "wasnt",
			"way", "we", "wed", "welcome", "we'll", "went", "were", "werent", "we've", "what", "whatever",
			"what'll", "whats", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby",
			"wherein", "wheres", "whereupon", "wherever", "whether", "which", "while", "whim", "whither", "who",
			"whod", "whoever", "whole", "who'll", "whom", "whomever", "whos", "whose", "why", "widely", "willing",
			"wish", "with", "within", "without", "wont", "words", "world", "would", "wouldnt", "www", "x", "y",
			"yes", "yet", "you", "youd", "you'll", "your", "youre", "yours", "yourself", "yourselves", "you've",
			"z", "zero" };
	private static int ArgsNum = 0;
	public static ArrayList<Integer> nodeIDListOne = new ArrayList<Integer>();
	public static ArrayList<Integer> nodeIDListTwo = new ArrayList<Integer>();
	public static ArrayList<Node> ParentTree = new ArrayList<Node>();
	public static ArrayList<Node> Children = new ArrayList<Node>();
	public static ArrayList<Link> LinkTable = new ArrayList<Link>();
	public static HashMap<Integer, Integer> FileIndex = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> SeedIDs = new HashMap<Integer, String>();
	public static HashMap<Integer, String> CrawledIDs = new HashMap<Integer, String>();
	public static HashMap<Integer, String> ID_Key = new HashMap<Integer, String>();
	public static HashMap<Integer, String> ID_Value = new HashMap<Integer, String>();
	public static HashMap<Integer, Integer> ID_ParentID = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> ID_Permission = new HashMap<Integer, String>();
	public static HashMap<Integer, String> ID_Children= new HashMap<Integer, String>();
	public static HashMap<Integer, Document> ID_Doc = new HashMap<Integer, Document>();
	public static HashMap<Integer, Document> File_Doc = new HashMap<Integer, Document>(); 
	
    MongoClientURI uri  = new MongoClientURI("mongodb://dingz:cis550@ds021731.mlab.com:21731/550project");        
    MongoClient client = new MongoClient(uri);
    MongoDatabase db = client.getDatabase(uri.getDatabase());
    
    MongoCollection nodes = db.getCollection("NodeTable");
    MongoCollection links = db.getCollection("LinkTable");
    MongoCollection words = db.getCollection("WordTable");
    MongoCollection files = db.getCollection("FileTable");
    MongoCollection queries = db.getCollection("QueryTable");
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doQuery(String key1, String key2) {
		// TODO Auto-generated method stub
		nodeIDListOne = new ArrayList<Integer>();
		nodeIDListTwo = new ArrayList<Integer>();
		ParentTree = new ArrayList<Node>();
		Children = new ArrayList<Node>();
		LinkTable = new ArrayList<Link>();
		FileIndex = new HashMap<Integer, Integer>();
		SeedIDs = new HashMap<Integer, String>();
		CrawledIDs = new HashMap<Integer, String>();
		ID_Key = new HashMap<Integer, String>();
		ID_Value = new HashMap<Integer, String>();
		ID_ParentID = new HashMap<Integer, Integer>();
		ID_Permission = new HashMap<Integer, String>();
		ID_Children= new HashMap<Integer, String>();
		ID_Doc = new HashMap<Integer, Document>();
		File_Doc = new HashMap<Integer, Document>(); 
		
//		String keyOne = request.getParameter("key_one");
//		String keyTwo = request.getParameter("key_two");
		String keyOne = StemWord(key1);
		String keyTwo = StemWord(key2);
		String nodeListOne = SearchNodeList(keyOne);
		String nodeListTwo = SearchNodeList(keyTwo);
		test("keyOne: " + keyOne);
		test("keyTwo: " + keyTwo);
//		String[] nodeIDListOne;
//		String[] nodeIDListTwo;
		
		if(nodeListOne.equals("")){
			if(nodeListTwo.equals("")){
				//Nothing to show
				ArgsNum = 0;
			}
			else {
				nodeIDListOne = ParseToArray(nodeListTwo);
				ArgsNum = 1;
			}
		}
		else{
			nodeIDListOne = ParseToArray(nodeListOne);
			if(nodeListTwo.equals("")){
				ArgsNum = 1;
			}
			else {
				nodeIDListTwo = ParseToArray(nodeListTwo);
				ArgsNum = 2;
			}
		}
		test("ArgsNum: " + ArgsNum);
		test("nodeIDListOne: " + nodeIDListOne.size());
		test("nodeIDListTwo: " + nodeIDListTwo.size());
		//Go to FileTable
		MongoCursor<Document> cursor = files.find().iterator();
        while(cursor.hasNext()){
        	Document docNode = cursor.next();
        	int rootID = Integer.parseInt(docNode.get("rootID").toString());
        	int currentID = Integer.parseInt(docNode.get("currentID").toString());
        	String permission =  docNode.get("permission").toString();
        	FileIndex.put(rootID, currentID);
        }
        cursor.close();
        //test("FileIndex: " + FileIndex.get(0));
        //Seed Id classify
        for(int fi: FileIndex.keySet()){
        	int i = 0;
        	for(int id: nodeIDListOne){
        		if(id < FileIndex.get(fi) && id >= fi){
        			if(!SeedIDs.containsKey(fi)){
        				SeedIDs.put(fi, "" + id);
        			}
        			else {
        				SeedIDs.put(fi, SeedIDs.get(fi) + "&" + id);
        			}
        			nodeIDListOne.set(i, -1);
        		}
        		i++;
        	}
        	if(ArgsNum == 2){
        		
        		int j = 0;
        		for(int id: nodeIDListTwo){
            		if(id < FileIndex.get(fi) && id > -1){
            			if(SeedIDs.containsKey(fi)){
            				SeedIDs.put(fi, SeedIDs.get(fi) + "&" + id);
            			}
            			nodeIDListTwo.set(j, -1);
            		}
            		j++;
            	}
        	}
        }
        //test("SeedIDs: " + SeedIDs.get(0));
        //Process NodeTable
        MongoCursor<Document> nodeCursor = nodes.find().iterator();
        String userName = "admin";
        while(nodeCursor.hasNext()){
        	Document docNode = nodeCursor.next();
        	int id = Integer.parseInt(docNode.get("id").toString());
        	int parentID = Integer.parseInt(docNode.get("parentID").toString());
        	int rootID = Integer.parseInt(docNode.get("rootID").toString());
        	String key = docNode.get("key").toString();
        	String value = docNode.get("value").toString();
        	String permission = docNode.get("permission").toString();
        	String children = docNode.get("children").toString();
        	if(FileIndex.containsKey(rootID) && (permission.equals("")||permission.equals(userName))){
        		ID_ParentID.put(id, parentID);
        		ID_Key.put(id, key);
        		ID_Value.put(id, value);
        		ID_Permission.put(id, permission);
        		ID_Children.put(id, children);      		
        	}
        }
        nodeCursor.close();        
        //Crawl all the IDs
        for(int key: SeedIDs.keySet()){
        	CrawledIDs.put(key, SeedIDs.get(key));
        	CrawlAllIDs(key, SeedIDs.get(key));
        }
        
        //Display as JSON
        for(int fID: CrawledIDs.keySet()){
        	//each file
        	ArrayList<Integer> nodeIds = ParseToArray(CrawledIDs.get(fID));
        	int i = 0;
        	while(i != nodeIds.size()){        		
        		for(int id: nodeIds){
            		if(CreateNewDoc(id, nodeIds))
            			i++;
            		test("i: " + i);
            	}
        	}
        	File_Doc.put(fID, ID_Doc.get(fID));
        }
        Document realDoc = new Document();
        test("File_Doc: " + File_Doc.size());
        for(int fID: File_Doc.keySet()){
        	test("Doc: " + File_Doc.get(fID));
        	String key = ID_Key.get(fID);
        	realDoc = realDoc.append(subStr(key), ID_Doc.get(fID).get(subStr(key)));
        }
        //Write to DB
        queries.insertOne(realDoc);
        String outputID = realDoc.get("_id").toString();
        
        test(realDoc);
        test(outputID);
        
        
        
        
        
        test(CrawledIDs.size());
        for(int id: CrawledIDs.keySet()){
        	test("-----------------");
        	test(CrawledIDs.get(id));
        }
		
	}
	
	private boolean CreateNewDoc(int id, ArrayList<Integer> nodeIds){
		if(ID_Doc.containsKey(id)) {
			test("false2");
			return false;
		}
		
		if(ID_Children.get(id)==null || ID_Children.get(id).equals("")){
			//Leaf node
			Document doc = new Document(subStr(ID_Key.get(id)), ID_Value.get(id));
			ID_Doc.put(id, doc);
		}
		else{
			ArrayList<Integer> chil = ParseToArray(ID_Children.get(id));
			Document tmp = new Document();
			if(!chil.isEmpty()){
				for(int i: chil){
					if(nodeIds.contains(i)){//this is in the crawled list
						if(!ID_Doc.containsKey(i)) {
							test("false3");
							return false;
						}
						String key = ID_Key.get(i);
						tmp = tmp.append(subStr(key), ID_Doc.get(i).get(subStr(key)));	
					}					
				}
			}
			Document doc = new Document(subStr(ID_Key.get(id)), tmp);
			ID_Doc.put(id, doc);
		}
		return true;
	}
	
	private String subStr(String text){
		if(text.indexOf(".") == -1) return text;
		return text.substring(0, text.indexOf("."));
	}

	private void CrawlAllIDs(int key, String seeds) {
		ArrayList<Integer> seedIds = ParseToArray(seeds);
		for(int id: seedIds){
			FindParent(key, id);
			FindChildren(key, id);
		}
	}
	
	
	
	
	private void FindChildren(int key, int id) {
		// TODO Auto-generated method stub
		if(ID_Children.get(id)==null || ID_Children.get(id).equals("")) return;
		ArrayList<Integer> chil = ParseToArray(ID_Children.get(id));
		for(int ch: chil){
			if(CrawledIDs.containsKey(key)){
				ArrayList<Integer> crawl = ParseToArray(CrawledIDs.get(key));
				if(!crawl.contains(ch)){
					CrawledIDs.put(key, CrawledIDs.get(key) + "&" + ch);
				}			
			}
			else {
				CrawledIDs.put(key, ""+ch);
			}
			FindChildren(key, ch);
		}
		
		
	}
	private void FindParent(int key, int id) {
		// TODO Auto-generated method stub
		if(id==-1) return;
		int par = ID_ParentID.get(id);
		if(par==-1) return;
		if(CrawledIDs.containsKey(key)){
			ArrayList<Integer> crawl = ParseToArray(CrawledIDs.get(key));
			if(!crawl.contains(par)){
				CrawledIDs.put(key, CrawledIDs.get(key) + "&" + par);
			}
		}
		else {
			CrawledIDs.put(key, ""+par);
		}
		FindParent(key, par);
	}
	private ArrayList<Integer> ParseToArray(String text) {
		// TODO Auto-generated method stub
		if(text.equals("")) return new ArrayList<Integer>();
		String[] list = text.split("&");
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(String s: list){
			if(!s.equals(""))
				result.add(Integer.parseInt(s));
		}
		return result;
	}

	private String SearchNodeList(String text) {
        
        MongoCursor<Document> cursor = words.find().iterator();
        String result = "";
        while(cursor.hasNext()){
        	Document docNode = cursor.next();
        	String word = docNode.get("word").toString();
        	if(word.equals(text)){
        		result = docNode.get("index").toString();
        		break;
        	}
        }
        cursor.close();
		return result;
	}
	
	
	public static String StemWord(String text) {
		/**TODO: Remove Digits**/
		//SPECIAL CHARACTERS
		if(isStopWord(text)) return "";
		//return text;
		for (char c : text.toCharArray()) {
			if (!Character.isLetter(c)) { return ""; }
		}
		
		Stemmer st = new Stemmer();
		
		for (char ch : text.toCharArray()) {
			st.add(ch);
		}
		st.stem();		
		return st.toString();
	}

	public static boolean isStopWord(String word) {
		String wordLower = word.toLowerCase();
		
		//STOP WORDS
		for (String stop : stopWords) {
			if (wordLower.equals(stop)) { return true; }
		}
		return false;
	}

	public static void setPermission(ArrayList<Node> nodeTable, String userName) {
		for(Node n: nodeTable){
			n.setPermission(userName);
		}		
	}
	
	public static void test(Object text) {
		System.out.println(text.toString());		
	}

}
