import org.bson.Document;

public class Link {
	private int nodeID1 = -1;
	private int nodeID2 = -1;
	Link(int n1, int n2){
		nodeID1 = n1;
		nodeID2 = n2;
	}
	public int getNodeID1(){
		return nodeID1;
	}
	public int getNodeID2(){
		return nodeID2;
	}
	public Object toJson(){
		Document doc = new Document().append("nodeID1", nodeID1).append("nodeID2", nodeID2);
		return doc;
	}
}
