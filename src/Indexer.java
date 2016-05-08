import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.json.*;

//@author Troy (Chuang Lan)

public class Indexer {
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
	public static int currentNodeId;
	public static String userName = "";
	public static String status = "Ready";
	
	public static ArrayList<Node> NodeTable = new ArrayList<Node>();
	public static ArrayList<Link> LinkTable = new ArrayList<Link>();
	public static HashMap<String, String> WordTable = new HashMap<String, String>();
	//public HashMap<Integer, String> RootFile = new HashMap<Integer, String>();
	
	public static void Index(){
		//Initialize the input
		//Still needed since the program might be terminated
//		int currentID = 0;
//		setCurrentNodeID(currentID);
		String filePath = "path/BigBang.txt";
		//Go to Parser
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
			test(fileName);		//From parser
		String jsonString = "";	//From parser
		
		String testInput =  "{\"Verbs\":[{\n" +
                "    \"develop\":\"30d\", \"type\":\"ed\", \"rel\":1.0, \"id\":\"80\", \"hate\":\"en\", \"ct\":\"on\", \"sps\":null\n" +
                "},{\n" +
                "    \"development\":\"31\", \"type\":\"cc\", \"rel\":3.0, \"id\":\"10\", \"hated\":\"en\", \"ct\":\"off\", \"sps\":null\n" +
                "},{\n" +
                "    \"of\":\"81\", \"type\":\"nn\", \"rel\":3.0, \"id\":\"60\", \"and\":\"en\", \"ct\":\"on\", \"sps\":null\n" +
                "}]}";
		jsonString = testInput;
		JSONObject root = new JSONObject(jsonString.trim());
		//Add root node
		Node rootNode = new Node(fileName, "");
		NodeTable.add(rootNode);
		//Recursively add node and link
		handleObject(root, rootNode.getId(), rootNode.getId());
		//Set permission for the file
		setPermission(NodeTable, userName);
		//Add inverted index
		for(Node node: NodeTable){
			if(node.isRoot()){
				//Root node added into WordTable
				String rawKey = node.getKey().trim().toLowerCase();
				/**Stemmed!**/					
				String word = StemWord(rawKey.trim());
				if(word.equals("")) continue;
				if(WordTable.containsKey(word)) {
					WordTable.put(word, WordTable.get(word) + "&" + node.getId());
				}
				else {
					WordTable.put(word, "&" + node.getId());
				}		
			}
			else if(!node.getValue().equals("")){
				//Only Leaf Node
				String[] rawValue = node.getValue().trim().split(" ");
				String[] rawKey = node.getKey().trim().split(" ");
				for(String rt: rawKey){
					//Leaf Node's key
					/**Stemmed!**/	
					String word = StemWord(rt.trim().toLowerCase());
					if(word.equals("")) continue;
					if(WordTable.containsKey(word)) {
						WordTable.put(word, WordTable.get(word) + "&" + node.getId());
					}
					else {
						WordTable.put(word, "&" + node.getId());
					}						
				}
				for(String rt: rawValue){
					//Leaf Node's value
					/**Stemmed!**/						
					String word = StemWord(rt.trim().toLowerCase());
					if(word.equals("")) continue;
					if(WordTable.containsKey(word)) {
						WordTable.put(word, WordTable.get(word) + "&" + node.getId());
					}
					else {
						WordTable.put(word, "&" + node.getId());
					}					
				}				
			}
		}
		//Update link table
//		for(String word: WordTable.keySet()){
//			//test(WordTable.get(word));
//			String[] nodeList = WordTable.get(word).split("&");
//			LinkedList<Integer> nodeIdList = new LinkedList<Integer>();
//			for(String nodeId: nodeList){
//				if(!nodeId.equals("")){
//					//test("id: " + nodeId);
//					int id = Integer.parseInt(nodeId);
//					nodeIdList.add(id);
//				}
//			}
//			while(nodeIdList.size() > 1){
//				int first = nodeIdList.pop();
//				for(int n: nodeIdList){
//					Link link = new Link(first, n);
//					LinkTable.add(link);
//				}
//			}
//		}
		/**TODO: UpdateRootFile with startID and endID**/
		
		/**TODO: Add all the data to database**/

		/**TEST the output table**/
		for(Node node: NodeTable){
			test("----------------------------");
			test("NodeID: " + node.getId());
			test("Key: " + node.getKey());
			test("Value: " + node.getValue());
			test("ParentID: " + node.getParentID());
			test("Root: " + node.getRootID());
			test("Permission: " + node.gerPermission());
		}
		for(Link link: LinkTable){
			test("----------------------------");
			test("Node1: " + link.getNodeID1());
			test("Node2: " + link.getNodeID2());
		}
		for(String word: WordTable.keySet()){
			test("----------------------------");
			test("Word: " + word);
			test("NodeList: " + WordTable.get(word));			
		}	
		
		//Clean the indexer after indexed
		clean();		
	}

	public static String StemWord(String text) {
		/**TODO: Remove Digits**/
		//SPECIAL CHARACTERS
		if(isStopWord(text)) return "";
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

	public static void handleObject(JSONObject obj, int rootID, int parentID) {
		Iterator<?> keys = obj.keys();
		while( keys.hasNext() ) {
		    String key = (String)keys.next();
		    if ( obj.get(key) instanceof JSONObject  ) {
		    	JSONObject child = obj.getJSONObject(key);
		    	Node node = new Node(key, "", rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    	handleObject(child, rootID, node.getId());
		    }
		    else if(obj.get(key) instanceof JSONArray){
		    	JSONArray array = obj.getJSONArray(key);
		    	Node node = new Node(key, "", rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    	handleArray(array, key, rootID, node.getId());
		    }
		    else if(obj.get(key) instanceof String){
		    	String value = obj.getString(key);
		    	Node node = new Node(key, value, rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    }
		    else if(obj.get(key) instanceof Integer){
		    	int value = obj.getInt(key);
		    	Node node = new Node(key, Integer.toString(value), rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    }
		    else if(obj.get(key) instanceof Double){
		    	double value = obj.getDouble(key);
		    	Node node = new Node(key, Double.toString(value), rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    }
		    else if(obj.get(key) instanceof Boolean){
		    	boolean value = obj.getBoolean(key);
		    	Node node = new Node(key, Boolean.toString(value), rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    }
		    else if(obj.get(key) == null){
		    	Node node = new Node(key, null, rootID, parentID);
		    	NodeTable.add(node);
		    	AddLink2Table(parentID, node.getId());
		    }
		}
	}

	public static void handleArray(JSONArray array, String key, int rootID, int parentID) {
		for(int i = 0; i < array.length(); i++){
			JSONObject obj = array.getJSONObject(i);
			String newKey = key + "_member_" + i;
			Node node = new Node(newKey, "", rootID, parentID);
			NodeTable.add(node);
	    	AddLink2Table(parentID, node.getId());
			handleObject(obj, rootID, node.getId());
		}		
	}

	public static void UpdateInvertedIndex(String word, int nodeID) {
		//Fetch the original nodeIDList of word
		String nodeIdList = "".trim();
		if(nodeIdList.equals("")) 
			nodeIdList += Integer.toString(nodeID);
		else 
			nodeIdList += "&" + nodeID;
		//Update the table	
	}
	
	public static void AddLink2Table(int n1, int n2){
		Link link = new Link(n1, n2);
		/**TODO: Add to the list**/
		LinkTable.add(link);
	}
	
	public static void AddNode2Table(Node node){
		
		/**TODO: Add to the list**/
	}
	
	public static void AddRootFile2Table(int rootID, String filePath){
		
		/**TODO: Add to the list**/
	}

	public static void test(Object text) {
		System.out.println(text.toString());		
	}

	public static void setCurrentNodeID(int cID) {
		currentNodeId = cID;
		
	}
	public static void clean(){
		NodeTable = new ArrayList<Node>();
		LinkTable = new ArrayList<Link>();
		WordTable = new HashMap<String, String>();
		userName = "";
		status = "Ready";
		/**TODO: setCurrentNodeID**/
		//setCurrentNodeID(0);
	}	
}
