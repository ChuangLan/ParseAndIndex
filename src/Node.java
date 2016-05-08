
public class Node {
	private String key = "";
	private String value = "";
	//@PrimaryKey
	private int id = -1;
	private int parentID = -1;
	private int rootID = -1;
	private String permission = "";
	Node(String k, String v){
		this.key = k;
		this.value = v;
		this.id = Indexer.currentNodeId;
		this.rootID = id;
		Indexer.currentNodeId++;
	}
	Node(String k, String v, int rID, int pID){
		this.key = k;
		this.value = v;
		this.parentID = pID;
		this.rootID = rID;
		this.id = Indexer.currentNodeId;
		Indexer.currentNodeId++;
	}
	
	public void setPermission(String userName){
		if(userName.equals("")) return;
		this.permission = userName;
	}
	
	public String getKey(){
		return this.key;
	}
	public String getValue(){
		return this.value;
	}
	public int getId(){
		return this.id;
	}
	public int getParentID(){
		return this.parentID;
	}
	public int getRootID(){
		return this.rootID;
	}
	
	public boolean isRoot(){
		return (id == rootID);
	}
	
	public String gerPermission(){
		return this.permission;
	}
	
	public boolean isPublic(){
		return this.permission.equals("");
	}
	
}
