import java.util.ArrayList;
import java.util.HashMap;

public class Heap{
	ArrayList<Node> heap = new ArrayList<>();
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	
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
		return (heap.size()==0);
	}
	
	void swap(int i, int j) {
		Node temp = heap.get(j);
		heap.set(j, heap.get(i));
		heap.set(i, temp);
	}
	
	void insert(Node n) {
		heap.add(n);
		int i = heap.size()-1;
		int parent = parent(i);
		while((parent!=i) && (heap.get(i).key < heap.get(parent).key)) {
			swap(i, parent);
			i = parent;
			parent = parent(i);
		}
		map.put(n.str, i);
	}
	// we need to know the index where the key needs to be decreased
	void decreaseKey(Node node, int key) {
		decreaseKey(map.get(node.str), key);
	}
	
	void decreaseKey(String str, int key) {
		decreaseKey(map.get(str), key);
	}
	
	void decreaseKey(int i, int key) {
		Node tempNode = heap.get(i);
		if (key>tempNode.key) {
			System.out.println("why are you increasing key by calling function decrease key");
			return;
		}
		tempNode.key = key;
		int parent = parent(i);
		while(i>0 && heap.get(parent).key > key) {
			swap(i, parent);
			i = parent;
			parent = parent(i);
		}
	}
	
	Node extractMin() {
		if(heap.size()==0) {
			throw new IllegalStateException("");
		}
		else if(heap.size()==1) {
			Node minNode = heap.remove(0);
			map.remove(minNode.str);
			return minNode;
		}
		Node minNode = heap.get(0);
		Node lastNode = heap.remove(heap.size()-1);
		heap.set(0, lastNode);
		
		//bubble down
		bubbleDown(0);
		map.remove(minNode.str);
		return minNode;
	}
	
	void bubbleDown(int i) {
		int left = left(i);
		int right = right(i);
		int smallest = -1;
		
		if(left <= heap.size()-1 && heap.get(left).key < heap.get(i).key) {
			smallest = left;
		}
		else {
			smallest = i;
		}
		
		if(right<=heap.size()-1 && heap.get(right).key < heap.get(i).key) {
			smallest = right;
		}
		
		if(smallest!=i) {//smallest is the index which contain the smaller value
			//amongst left and right children
			swap(i, smallest);
			bubbleDown(smallest);
		}
	}
	
}




























