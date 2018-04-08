import java.util.ArrayList;
import java.util.*;

public class Heap implements Cloneable{
	ArrayList<Node> heapList = new ArrayList<>();
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	

	public Heap clone() throws CloneNotSupportedException {
		Heap clone;
		clone = (Heap) super.clone();
		clone.heapList = (ArrayList<Node>)this.heapList.clone();
		clone.map 	   = (HashMap<String, Integer>)this.map.clone();
		return clone;
    }

	// index starts from one instead of zero
	int left(int i) {
		return (2*i)+1;
	}
	
	int right(int i) {
		return (2*i)+2;
	}
	
	int parent (int i) {
		if((i%2)==1) {
			return i/2;
		}
		return (i-1)/2;
	}
	
	boolean isEmpty() {
		return (heapList.size()==0);
	}
	
	void swap(int i, int j) {
		Node n1 = heapList.get(j);
		Node n2 = heapList.get(i);
		heapList.set(j, n2);
		heapList.set(i, n1);
		map.put(n1.str, i);
		map.put(n2.str, j);
	}
	
	void insert(Node n) {
		heapList.add(n);
		int i = heapList.size()-1;
		map.put(n.str, i);
		int parent = parent(i);
		while((parent!=i) && (heapList.get(i).key < heapList.get(parent).key)) {
			swap(i, parent); // also changes indexes in the map
			i = parent;
			parent = parent(i);
		}
	}
	// we need to know the index where the key needs to be decreased
	void decreaseKey(Node node, int key) {
		decreaseKey(map.get(node.str), key);
	}
	
	void decreaseKey(String str, int key) {
		decreaseKey(map.get(str), key);
	}
	
	void decreaseKey(int i, int key) {
		Node tempNode = heapList.get(i);
		if (key>tempNode.key) {
			System.out.println("why are you increasing key by calling function decrease key");
			return;
		}
		tempNode.key = key;
		int parent = parent(i);
		while(i>0 && heapList.get(parent).key > key) {
			swap(i, parent);
			i = parent;
			parent = parent(i);
		}
	}
	
	Node extractMin() {
		int j = 0;
		if(heapList.size()==0) {
			throw new IllegalStateException("");
		}
		else if(heapList.size()==1) {
			Node minNode = heapList.remove(0);
			map.remove(minNode.str);
			return minNode;
		}
		Node minNode = heapList.get(0);
		Node lastNode = heapList.remove(heapList.size()-1);
		heapList.set(0, lastNode);

		//bubble down
		j = bubbleDown(0);
		map.remove(minNode.str);
		map.put(lastNode.str, j);
		return minNode;
	}
	
	int bubbleDown(int i) {
		int left = left(i);
		int right = right(i);
		int smallest = -1;
		
		if(left <= heapList.size()-1 && heapList.get(left).key < heapList.get(i).key) {
			smallest = left;
		}
		else {
			smallest = i;
		}
		
		if(right<=heapList.size()-1 && heapList.get(right).key < heapList.get(i).key && heapList.get(right).key <  heapList.get(left).key) {
			smallest = right;
		}
		
		if(smallest!=i) {//smallest is the index which contain the smaller value
			//amongst left and right children
			swap(i, smallest);
			smallest = bubbleDown(smallest);
		}
		return smallest;
	}

	// public static void main(String[] args) {
		
	// 	Scanner input = new Scanner(System.in);
	// 	boolean done = false;
	// 	Heap hp = new Heap();
	// 	while (!done) {
	// 		System.out.println("insert, getMin, decreaseKey, done");
	// 		String i = input.next();
	// 		switch (i) {
	// 			case "insert":
	// 				hp.insert(new Node(input.nextInt(), input.next()));
	// 				break;
	// 			case "getMin":
	// 				Node temp = hp.extractMin();
	// 				System.out.println(temp.str);
	// 				break;
	// 			case "decreaseKey": 
	// 				hp.decreaseKey(input.next(), input.nextInt());
	// 				break;
	// 			case "done":
	// 				done = true;
	// 		}
	// 		System.out.print("[");
	// 		for (Node data : hp.heapList) {
	// 			System.out.print(String.valueOf(data.key) +  " ("+ data.str +") ");
	// 		}
	// 		System.out.println("]");
	// 	}
	// }
	
}




























