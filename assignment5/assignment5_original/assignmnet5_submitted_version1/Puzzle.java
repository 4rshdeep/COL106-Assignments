import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Puzzle {
	static StringBuilder sb;
	static int cost;
	static char movedChar;
	static Vector<String> vec = new Vector<String>();

	static Vector<String> generatePermutations(StringBuilder sb, int size) {
		if (size==1) {
			vec.add(sb.toString());
		}
		char fChar, lChar;
		for (int i = 0; i < size; i++) {
			generatePermutations(sb, size-1);
			if ((size%2)==1) {//if size is odd swap first and last
				fChar = sb.charAt(0);
				lChar = sb.charAt(size-1);
				sb.setCharAt(0, lChar);
				sb.setCharAt(size-1, fChar);
			}
			else {
				fChar = sb.charAt(i);
				lChar = sb.charAt(size-1);
				sb.setCharAt(i, lChar);
				sb.setCharAt(size-1, fChar);
			}
		}
		return vec;
	}

	// algorithm used from geeksforgeeks
	private static void permute(String str, int l, int r)
    {
        if (l == r)
            vec.add(str);
        else
        {
            for (int i = l; i <= r; i++)
            {
                str = swap(str,l,i);
                permute(str, l+1, r);
                str = swap(str,l,i);
            }
        }
    }
    public static String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i] ;
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

	
	public static void main(String[] args) {
		Path path = Paths.get(args[0]);
		InputStream in = null;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(args[1]);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedOutputStream bout = new BufferedOutputStream(fileOut);
		PrintWriter p = new PrintWriter(bout);
		try {
			in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int numInput = Integer.parseInt(reader.readLine());
			permute("12345678G", 0, 8);
			boolean done = false;
			Graph graph = null;
			for (int i=0; i<numInput; i++) {
				Graph prevGraph = graph;
				graph = new Graph(362880);
				String nextLine = reader.readLine();
				String[] strings = nextLine.split("\\s+");
				graph.startState = strings[0];
				graph.finishState = strings[1];

				nextLine = reader.readLine();

				if (i == 0) {
					graph.makeGraph(vec);
				}
				else {
					graph.adjMap         = (HashMap<String, ArrayList<String>>)prevGraph.adjMap;
					graph.cost 	         = (HashMap<String, Integer>)prevGraph.clonedCost.clone();
					graph.clonedCost     = (HashMap<String, Integer>)prevGraph.clonedCost.clone();
					graph.distance       = (HashMap<String, Integer>)prevGraph.clonedDistance.clone();
					graph.clonedDistance = (HashMap<String, Integer>)prevGraph.clonedDistance.clone();
				}

				if (strings[0].equals(strings[1])) {
					p.println("0 0");
					p.println();
					continue;
				}

				strings  = nextLine.split("\\s+");
				graph.weights.put('1', Integer.parseInt(strings[0]));
				graph.weights.put('2', Integer.parseInt(strings[1]));
				graph.weights.put('3', Integer.parseInt(strings[2]));
				graph.weights.put('4', Integer.parseInt(strings[3]));
				graph.weights.put('5', Integer.parseInt(strings[4]));
				graph.weights.put('6', Integer.parseInt(strings[5]));
				graph.weights.put('7', Integer.parseInt(strings[6]));
				graph.weights.put('8', Integer.parseInt(strings[7]));
				
				// graph.previous
				graph.dijkstra();
				String latter = graph.finishState;
				
				String former;
				HashMap<String, String> next = new HashMap<String, String>();
				if (graph.previous.get(latter) == null) {
						p.println("-1 -1");
						p.println();
						continue;		
				}
				while(!latter.equals(graph.startState)) {
					former = graph.previous.get(latter);
					next.put(former, latter);
					latter = former;
				}
				if (latter == null) {
					continue;
				}
				former = graph.startState;
				done = false;
				int count = 0;
				cost = 0;
				sb = new StringBuilder();
				while(!done) {
					latter = next.get(former);
					move(former, latter);
					cost += graph.weights.get(movedChar);
					former = latter;
					count++;
					if (former.equals(graph.finishState)) {
						done = true;
						break;
					}
					sb.append(" ");	
				}
				p.print(count);
				p.print(" ");
				p.println(cost);
				p.println(sb.toString());
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		p.close();
	}

	private static void move(String former, String latter) {
		if (former.charAt(0)=='G') {
			sb.append(latter.charAt(0));
			movedChar = latter.charAt(0);
			if (latter.charAt(1)=='G') {
				sb.append("L");
			}
			else if (latter.charAt(3)=='G') {
				sb.append("U");
			}
		}
		else if (former.charAt(1)=='G') {
			sb.append(latter.charAt(1));
			movedChar = latter.charAt(1);
			if (latter.charAt(0)=='G') {
				sb.append("R");
			}
			else if (latter.charAt(2)=='G'){
				sb.append("L");
			}
			else if (latter.charAt(4)=='G') {
				sb.append("U");
			}
		}
		else if(former.charAt(2)=='G') {
			movedChar = latter.charAt(2);
			sb.append(latter.charAt(2));
			if (latter.charAt(1)=='G') {
				sb.append("R");
			}
			else if (latter.charAt(5)=='G'){
				sb.append("U");
			}
		}
		else if(former.charAt(3)=='G') {
			sb.append(latter.charAt(3));
			movedChar = latter.charAt(3);
			if (latter.charAt(0)=='G') {
				sb.append("D");
			}
			else if (latter.charAt(4)=='G'){
				sb.append("L");
			}
			else if (latter.charAt(6)=='G') {
				sb.append("U");
			}
		}
		else if(former.charAt(4)=='G') {
			sb.append(latter.charAt(4));
			movedChar = latter.charAt(4);
			if (latter.charAt(1)=='G') {
				sb.append("D");
			}
			else if (latter.charAt(3)=='G'){
				sb.append("R");
			}
			else if (latter.charAt(5)=='G') {
				sb.append("L");
			}
			else if (latter.charAt(7)=='G') {
				sb.append("U");
			}
		}
		else if(former.charAt(5)=='G') {
			sb.append(latter.charAt(5));
			movedChar = latter.charAt(5);
			if (latter.charAt(2)=='G') {
				sb.append("D");
			}
			else if (latter.charAt(4)=='G'){
				sb.append("R");
			}
			else if (latter.charAt(8)=='G') {
				sb.append("U");
			}
		}
		else if(former.charAt(6)=='G') {
			sb.append(latter.charAt(6));
			movedChar =latter.charAt(6);
			if (latter.charAt(7)=='G') {
				sb.append("L");
			}
			else if (latter.charAt(3)=='G'){
				sb.append("D");
			}
		}
		else if(former.charAt(7)=='G') {
			sb.append(latter.charAt(7));
			movedChar = latter.charAt(7);
			if (latter.charAt(4)=='G') {
				sb.append("D");
			}
			else if (latter.charAt(6)=='G'){
				sb.append("R");
			}
			else if (latter.charAt(8)=='G') {
				sb.append("L");
			}
		}
		else if(former.charAt(8)=='G') {
			latter.charAt(8);
			sb.append(latter.charAt(8));
			movedChar = latter.charAt(8);
			if (latter.charAt(7)=='G') {
				sb.append("R");
			}
			else if (latter.charAt(5)=='G'){
				sb.append("D");
			}
		}
		else {
			System.out.println("somethings wrong with the moves");
		}
	}
}

class Node {
	String str;
	int key;

	Node(int d, String s) {
		str = s;
		key = d;
	}
}

class Graph {
	int vertices;
	HashMap<String, ArrayList<String>> adjMap;
	HashMap<String, Integer> distance;
	HashMap<String, Integer> cost;
	HashMap<String, String> previous;
	String startState;
	String finishState;
	Heap heap;
	HashMap<Character, Integer> weights;
	
	HashMap<String, Integer> clonedDistance;
	HashMap<String, Integer> clonedCost;
	Heap clonedHeap;

	Graph(int n) {
		adjMap   = new HashMap<String, ArrayList<String>>();
		previous = new HashMap<String, String>();
		heap     = new Heap();
		weights  = new HashMap<Character, Integer>();
		distance = new HashMap<String, Integer>();
		cost	 = new HashMap<String, Integer>();
	}
	int MAX_VALUE =  147483647;	
	
	void makeGraph(Vector<String> vec) {
		
		StringBuilder sb;
		ArrayList<String> arraylist;
		for (String line : vec) {
	    	sb = new StringBuilder(line);
			arraylist = new ArrayList<String>();
			if (sb.charAt(0)=='G') {//swap 0 and 1
				arraylist.add(swap(line, 1, 0));
				arraylist.add(swap(line, 3, 0)); 
			}
			else if(sb.charAt(1)=='G') {
				arraylist.add(swap(line, 0, 1));
				arraylist.add(swap(line, 4, 1));
				arraylist.add(swap(line, 2, 1));
			}
			else if(sb.charAt(2)=='G') {
				arraylist.add(swap(line, 1, 2));
				arraylist.add(swap(line, 5, 2));
			}
			else if(sb.charAt(3)=='G') {
				arraylist.add(swap(line, 0, 3));
				arraylist.add(swap(line, 4, 3));
				arraylist.add(swap(line, 6, 3));
			}
			else if(sb.charAt(4)=='G') {
				arraylist.add(swap(line, 1, 4));
				arraylist.add(swap(line, 3, 4));
				arraylist.add(swap(line, 5, 4));
				arraylist.add(swap(line, 7, 4));
			}
			else if(sb.charAt(5)=='G') {
				arraylist.add(swap(line, 2, 5));
				arraylist.add(swap(line, 4, 5));
				arraylist.add(swap(line, 8, 5));
			}
			else if(sb.charAt(6)=='G') {
				arraylist.add(swap(line, 3, 6));
				arraylist.add(swap(line, 7, 6));
			}
			else if(sb.charAt(7)=='G') {
				arraylist.add(swap(line, 4, 7));
				arraylist.add(swap(line, 6, 7));
				arraylist.add(swap(line, 8, 7));
			}
			else if(sb.charAt(8)=='G') {
				arraylist.add(swap(line, 5, 8));
				arraylist.add(swap(line, 7, 8));
			}
			adjMap.put(line, arraylist);
			cost.put(line, MAX_VALUE);
			distance.put(line, MAX_VALUE);
			
		}
		clonedCost = (HashMap<String, Integer>)cost.clone();
		clonedDistance = (HashMap<String, Integer>)distance.clone();
 	}
	
	static String swap(String str, int a, int b) {
		StringBuilder sb = new StringBuilder(str);
		char temp = sb.charAt(a);
		sb.setCharAt(a, sb.charAt(b));
		sb.setCharAt(b, temp);
		String ret = sb.toString();
		return ret;
	}

	void dijkstra() {
		int w, d1, d2;
		Node min_distance_node;
		
		heap.insert(new Node(0, startState));
		cost.put(startState, 0);
		distance.put(startState, 0);
		int temp1, temp2;
		char tempChar;
		int c=181442;
		while ((!heap.isEmpty())&&(c>=0)) {
			c--;
			min_distance_node = heap.extractMin();
			if ((min_distance_node.str).equals(finishState)) {
				break;
			}
			int cost_extracted_node = cost.get(min_distance_node.str)+1;
			ArrayList<String> list = adjMap.get(min_distance_node.str);
			for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {

			    String nStr = iter.next();
			    temp1 = min_distance_node.str.indexOf('G');
			    tempChar = nStr.charAt(temp1);

			    w = weights.get(tempChar);
			    d1 = distance.get(min_distance_node.str) + w;
			    d2 = distance.get(nStr);
			    if(d2 > d1) {
			    	if (heap.map.containsKey(nStr)) {
			    		heap.decreaseKey(nStr, d1);
			    	}
			    	else {
			    		heap.insert(new Node(d1, nStr));	
			    	}
			    	distance.put(nStr, d1);
			    	previous.put(nStr, min_distance_node.str);
			    	cost.put(nStr, cost_extracted_node);
			    }
			    if (d2 == d1) {
			    	int cost_current = cost.get(nStr);
			    	if (cost_current>cost_extracted_node) {
			    		previous.put(nStr, min_distance_node.str);
			    		cost.put(nStr, cost_extracted_node);
			    	}
			    }
			 }
		}
	}
}


class Heap implements Cloneable{
	ArrayList<Node> heapList = new ArrayList<>(362880);
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	
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
		
		if(smallest!=i) {
			swap(i, smallest);
			smallest = bubbleDown(smallest);
		}
		return smallest;
	}
}






















































