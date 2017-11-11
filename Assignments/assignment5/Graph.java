import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.*;

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
	int MAX_VALUE = 47483647;
	// boolean done;
	HashMap<String, String> parent;
	HashSet<String> set;
	public Graph(int n) {
		adjMap   = new HashMap<String, ArrayList<String>>();
		previous = new HashMap<String, String>();
		heap     = new Heap();
		weights  = new HashMap<Character, Integer>();
		distance = new HashMap<String, Integer>();
		cost	 = new HashMap<String, Integer>();
		parent 	 = new HashMap<String, String>();
		set 	 = new HashSet<String>();
	}
	
//	void makeGraph(String str) {
//		Path path = Paths.get(str);
//		try {
//			InputStream in = Files.newInputStream(path);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			vertices = Integer.parseInt(reader.readLine());
//			String line;
//			StringBuilder sb;
//			ArrayList<String> arraylist;
//			for (int i = 0; i < vertices; i++) {
//				line = reader.readLine();
//				sb = new StringBuilder(line);
//				arraylist = new ArrayList<String>();
//				if (sb.charAt(0)=='G') {//swap 0 and 1
//					arraylist.add(swap(line, 0, 1));
//					arraylist.add(swap(line, 0, 3)); 
//				}
//				else if(sb.charAt(1)=='G') {
//					arraylist.add(swap(line, 0, 1));
//					arraylist.add(swap(line, 4, 1));
//					arraylist.add(swap(line, 2, 1));
//				}
//				else if(sb.charAt(2)=='G') {
//					arraylist.add(swap(line, 1, 2));
//					arraylist.add(swap(line, 2, 5));
//				}
//				else if(sb.charAt(3)=='G') {
//					arraylist.add(swap(line, 0, 3));
//					arraylist.add(swap(line, 4, 3));
//					arraylist.add(swap(line, 6, 3));
//				}
//				else if(sb.charAt(4)=='G') {
//					arraylist.add(swap(line, 1, 4));
//					arraylist.add(swap(line, 3, 4));
//					arraylist.add(swap(line, 5, 4));
//					arraylist.add(swap(line, 7, 4));
//				}
//				else if(sb.charAt(5)=='G') {
//					arraylist.add(swap(line, 2, 5));
//					arraylist.add(swap(line, 4, 5));
//					arraylist.add(swap(line, 8, 5));
//				}
//				else if(sb.charAt(6)=='G') {
//					arraylist.add(swap(line, 3, 6));
//					arraylist.add(swap(line, 6, 7));
//				}
//				else if(sb.charAt(7)=='G') {
//					arraylist.add(swap(line, 4, 7));
//					arraylist.add(swap(line, 6, 7));
//					arraylist.add(swap(line, 8, 7));
//				}
//				else if(sb.charAt(8)=='G') {
//					arraylist.add(swap(line, 5, 8));
//					arraylist.add(swap(line, 7, 8));
//				}
//				adjMap.put(line, arraylist);
//				if (line.equals(startState)) {
//					heap.insert(new Node(0, startState));
//					cost.put(startState, 0);
//					distance.put(startState, 0);
//				}
//				else {
//					heap.insert(new Node(MAX_VALUE, line));
//					cost.put(line, MAX_VALUE);
//					distance.put(line, MAX_VALUE);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}


	void makeGraph() {
		ArrayList<String> arraylist;
		LinkedList<String> queue = new LinkedList<String>();
		boolean done = false;
		String str;
		queue.add(startState);
		int count =0;
		while (!done) {
			// System.out.println("while");
			str = queue.remove();
			// System.out.print(count++);
			// System.out.println("--"+str);
			set.add(str);
			arraylist = getNextStates(str);
			// System.out.println(arraylist);
			// System.out.println(nextHasTarget(str));
			// if (nextToNextHasTarget(str)) {
			// 	arraylist = new ArrayList<String>();
			// 	arraylist = getNextStates(str);

				// arraylist = getNextStates(str);
			// 	tempList = getMarkedNeighbours(str);
			// 	for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
			// 		String tempStr = iter.next();
			// 		if (nextHasTarget(tempStr)) {
			// 			tempList.add(tempStr);
			// 			queue.add(tempStr);
			// 		}
			// 	}
			// 	adjMap.put(str, tempList);
			// }
			if (str.equals(finishState)) {
				set.add(finishState);
				System.out.println(set);
				done = true; 
				break;
				// return;
			}
			else {
				for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
					String tempStr = iter.next();
					queue.add(tempStr);
				}
			}


		}
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String temp = (String)it.next();
			arraylist = getNextStates(temp);
			adjMap.put(temp, arraylist);
			if (temp.equals(startState)) {
				distance.put(startState, 0);
				cost.put(startState, 0);
				heap.insert(new Node(0, startState));
			}
			else {
				distance.put(temp, MAX_VALUE);
				cost.put(temp, MAX_VALUE);
				heap.insert(new Node(MAX_VALUE, temp));
			}
		}
		// printMap(adjMap);

	}

	boolean hasFinish(ArrayList<String> list) {
		for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
			String tempStr1 = iter.next();
			ArrayList<String> arraylist = new ArrayList<String>();
			for (ListIterator<String> iter2 = arraylist.listIterator(); iter2.hasNext(); ) {
				String tempStr2 = iter2.next();
				ArrayList<String> arraylist2 = new ArrayList<String>();
				for (ListIterator<String> iter3 = arraylist2.listIterator(); iter3.hasNext(); ) {
					String tempStr3 = iter3.next();
					if (tempStr3.equals(finishState)) {
						System.out.println("this");
						return true;
					}
				}
			}
		}
		return false;
	}
	
// 	void makeGraph() {
// 		System.out.println("hs");
// 		if(startState.equals(finishState)) {
// 			return;
// 		}
// 		ArrayList<String> arraylist;
// 		boolean check = false;
// 		arraylist = getNextStates(startState);
// 		String str;
// 		String tempStr;
		
		// for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
		// 	tempStr = iter.next();
		// 	if (tempStr.equals(finishState)) {
		// 		check = true;
		// 	}
		// }
// 		if (check == true) {
// 			arraylist.clear();
// 			arraylist.add(finishState);
// 			adjMap.put(startState, arraylist);
// 			heap.insert(new Node(0, startState));
// 			cost.put(startState, 0);
// 			distance.put(startState, 0);

// 			arraylist = new ArrayList<String>();
// 			arraylist.add(startState);
// 			adjMap.put(finishState, arraylist);
// 			heap.insert(new Node(MAX_VALUE, finishState));
// 			cost.put(finishState, MAX_VALUE);
// 			distance.put(finishState, MAX_VALUE);
// //			printMap(adjMap);
// 			return;
// 		}
			


// 		//book keeping
// 		Queue<String> queue = new LinkedList<String>();
// 		ArrayList<String> tempList = new ArrayList<String>();
// //		HashMap<String, String> mp = new HashMap<String, String>();
// 		HashSet<String> set = new HashSet<String>(); 
// 		boolean done = false;
		
// 		//start
// 		queue.add(startState);
// //		set.add(startState);
// //		parent.put(startState , "");
// //		heap.insert(new Node(0, startState));
// //		cost.put(startState, 0);
// //		distance.put(startState, 0);
// //		int count = 0;
// 		while(true){
// 			str = queue.remove();
// 			System.out.println("jah");
// 			System.out.println(str);
// 			set.add(str);
// 			arraylist = getNextStates(str);
// 			adjMap.put(str, arraylist);
			 

// 			if(nextToNextHasTarget(str)) {
// 				System.out.println("next");
// 				for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
// 					tempStr = iter.next();
// 					if (!adjMap.contains(tempStr)) {
// 						tempList = new ArrayList<>();
// 						tempList.add(str);
// 						adjMap.put(tempStr, tempList);//TODO add all those next states which are not marked
// 					}
// 					if(nextHasTarget(tempStr)) {
// 						tempList = new ArrayList<String>();
// 						tempList.add(finishState);
// 						adjMap.put(tempStr, tempList);
// 						tempList = new ArrayList<String>();
// 						tempList.add(tempStr);
// 						adjMap.put(finishState, tempList);
// 					}
// 				}
// 				printMap(adjMap);
// 				return;
// 			}
// 			else {
// 				for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
// 					tempStr = iter.next();
// 					if (!set.contains(tempStr)) {
// 						queue.add(tempStr);
// 					}
// 				}
// 				System.out.println("si");
// 				System.out.println(queue);
// 			}
// 		}

// //		
// 	}
	
	boolean nextHasTarget(String s) {
		ArrayList<String> arraylist;
		boolean check = false;
		arraylist = getNextStates(s);
		String tempStr;
		
		for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
			tempStr = iter.next();
			if (tempStr.equals(finishState)) {
				check = true;
			}
		}
		return check;
	}

	boolean nextToNextHasTarget(String str) {
		ArrayList<String> arraylist;
		boolean check = false;
		arraylist = getNextStates(str);
		String tempStr;
		for (ListIterator<String> iter = arraylist.listIterator(); iter.hasNext(); ) {
			tempStr = iter.next();
			if (nextHasTarget(tempStr)) {
				check = true;
			}
		}
		return check;
	}
	
	ArrayList<String> getNextStates(String str) {
		ArrayList<String> arraylist = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(str);
		
		if (sb.charAt(0)=='G') {//swap 0 and 1
			arraylist.add(swap(str, 0, 1));
			arraylist.add(swap(str, 0, 3)); 
		}
		else if(sb.charAt(1)=='G') {
			arraylist.add(swap(str, 0, 1));
			arraylist.add(swap(str, 4, 1));
			arraylist.add(swap(str, 2, 1));
		}
		else if(sb.charAt(2)=='G') {
			arraylist.add(swap(str, 1, 2));
			arraylist.add(swap(str, 2, 5));
		}
		else if(sb.charAt(3)=='G') {
			arraylist.add(swap(str, 0, 3));
			arraylist.add(swap(str, 4, 3));
			arraylist.add(swap(str, 6, 3));
		}
		else if(sb.charAt(4)=='G') {
			arraylist.add(swap(str, 1, 4));
			arraylist.add(swap(str, 3, 4));
			arraylist.add(swap(str, 5, 4));
			arraylist.add(swap(str, 7, 4));
		}
		else if(sb.charAt(5)=='G') {
			arraylist.add(swap(str, 2, 5));
			arraylist.add(swap(str, 4, 5));
			arraylist.add(swap(str, 8, 5));
		}
		else if(sb.charAt(6)=='G') {
			arraylist.add(swap(str, 3, 6));
			arraylist.add(swap(str, 6, 7));
		}
		else if(sb.charAt(7)=='G') {
			arraylist.add(swap(str, 4, 7));
			arraylist.add(swap(str, 6, 7));
			arraylist.add(swap(str, 8, 7));
		}
		else if(sb.charAt(8)=='G') {
			arraylist.add(swap(str, 5, 8));
			arraylist.add(swap(str, 7, 8));
		}
		return arraylist;
	}

	ArrayList<String> getMarkedNeighbours(String str) {
		ArrayList<String> arraylist = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(str);
		String tempStr;
		if (sb.charAt(0)=='G') {//swap 0 and 1
			arraylist.add(swapIfMarked(str, 0, 1));
			arraylist.add(swapIfMarked(str, 0, 3)); 
		}
		else if(sb.charAt(1)=='G') {
			arraylist.add(swapIfMarked(str, 0, 1));
			arraylist.add(swapIfMarked(str, 4, 1));
			arraylist.add(swapIfMarked(str, 2, 1));
		}
		else if(sb.charAt(2)=='G') {
			arraylist.add(swapIfMarked(str, 1, 2));
			arraylist.add(swapIfMarked(str, 2, 5));
		}
		else if(sb.charAt(3)=='G') {
			arraylist.add(swapIfMarked(str, 0, 3));
			arraylist.add(swapIfMarked(str, 4, 3));
			arraylist.add(swapIfMarked(str, 6, 3));
		}
		else if(sb.charAt(4)=='G') {
			arraylist.add(swapIfMarked(str, 1, 4));
			arraylist.add(swapIfMarked(str, 3, 4));
			arraylist.add(swapIfMarked(str, 5, 4));
			arraylist.add(swapIfMarked(str, 7, 4));
		}
		else if(sb.charAt(5)=='G') {
			arraylist.add(swapIfMarked(str, 2, 5));
			arraylist.add(swapIfMarked(str, 4, 5));
			arraylist.add(swapIfMarked(str, 8, 5));
		}
		else if(sb.charAt(6)=='G') {
			arraylist.add(swapIfMarked(str, 3, 6));
			arraylist.add(swapIfMarked(str, 6, 7));
		}
		else if(sb.charAt(7)=='G') {
			arraylist.add(swapIfMarked(str, 4, 7));
			arraylist.add(swapIfMarked(str, 6, 7));
			arraylist.add(swapIfMarked(str, 8, 7));
		}
		else if(sb.charAt(8)=='G') {
			arraylist.add(swapIfMarked(str, 5, 8));
			arraylist.add(swapIfMarked(str, 7, 8));
		}
		arraylist.removeAll(Collections.singleton(null));
		return arraylist;
	}
	
	String swap(String str, int a, int b) {
		StringBuilder sb = new StringBuilder(str);
		char temp = sb.charAt(a);
		sb.setCharAt(a, sb.charAt(b));
		sb.setCharAt(b, temp);
		return sb.toString();
	}

	String swapIfMarked(String str, int a, int b) {
		StringBuilder sb = new StringBuilder(str);
		char temp = sb.charAt(a);
		sb.setCharAt(a, sb.charAt(b));
		sb.setCharAt(b, temp);
		String tempStr = sb.toString();
		if (set.contains(tempStr)) {
			return null;
		}
		return tempStr;
	}
	
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    int count = 0;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.print(count++);
	        System.out.println("--" + pair.getKey() + " = " + pair.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	int ITERATIONS = 10;
	int num_iter = 0;
	void dijkstra() {
		int w, d1, d2;
		Node min_distance_node;
		while (!heap.isEmpty()) {
			min_distance_node = heap.extractMin();
			int cost_extracted_node = cost.get(min_distance_node.str)+1;
			ArrayList<String> list = adjMap.get(min_distance_node.str);
			// System.out.println(min_distance_node.str);
			// System.out.println(list);
			printMap(distance);
			for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
			    String nStr = iter.next();
			    w = weight(min_distance_node.str, nStr);
			    d1 = distance.get(min_distance_node.str) + w;
			    // System.out.println(nStr);
			    d2 = distance.get(nStr);
			    if(d2 > d1) {
			    	distance.put(nStr, d1);
			    	heap.decreaseKey(nStr, d1);
			    	//set the previous
			    	// System.out.println(nStr + " " +  min_distance_node.str);
			    	previous.put(nStr, min_distance_node.str);//Can put the movement here
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
		// System.out.println(previous);
	}

	private int weight(String former, String latter) {
		if(former.charAt(0)=='G') {
			if(latter.charAt(1)=='G' || latter.charAt(3)=='G') {
				return weights.get(latter.charAt(0));
			}
		}
		else if(former.charAt(1)=='G') {
			if(latter.charAt(0)=='G' || latter.charAt(2)=='G' || latter.charAt(4)=='G') {
				return weights.get(latter.charAt(1));
			}
		}
		else if(former.charAt(2)=='G') {
			if(latter.charAt(1)=='G' || latter.charAt(5)=='G') {
				return weights.get(latter.charAt(2));
			}
		}
		else if(former.charAt(3)=='G') {
			if(latter.charAt(0)=='G' || latter.charAt(4)=='G' || latter.charAt(6)=='G') {
				return weights.get(latter.charAt(3));
			}
		}
		else if(former.charAt(4)=='G') {
			if(latter.charAt(1)=='G' || latter.charAt(3)=='G' || latter.charAt(5)=='G' || latter.charAt(7)=='G') {
				return weights.get(latter.charAt(4));
			}
		}
		else if(former.charAt(5)=='G') {
			if(latter.charAt(2)=='G' || latter.charAt(4)=='G' || latter.charAt(8)=='G') {
				return weights.get(latter.charAt(5));
			}
		}
		else if(former.charAt(6)=='G') {
			if(latter.charAt(7)=='G' || latter.charAt(3)=='G') {
				return weights.get(latter.charAt(6));
			}
		}
		else if(former.charAt(7)=='G') {
			if(latter.charAt(4)=='G' || latter.charAt(6)=='G' || latter.charAt(8)=='G') {
				return weights.get(latter.charAt(7));
			}
		}
		else if(former.charAt(8)=='G') {
			if(latter.charAt(7)=='G' || latter.charAt(5)=='G') {
				return weights.get(latter.charAt(8));
			}
		}
		else {
			System.out.println("somethings wrong with the edge weights");
		}
		return 1;
	}

}












