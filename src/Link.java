
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
}
