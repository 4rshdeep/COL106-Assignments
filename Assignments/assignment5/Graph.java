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

	
	public Graph(int n) {
		adjMap   = new HashMap<String, ArrayList<String>>();
		previous = new HashMap<String, String>();
		heap     = new Heap();
		weights  = new HashMap<Character, Integer>();
		distance = new HashMap<String, Integer>();
		cost	 = new HashMap<String, Integer>();
	}
	
	void makeGraph(String str) {
		Path path = Paths.get(str);
		try {
			InputStream in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			vertices = Integer.parseInt(reader.readLine());
			String line;
			StringBuilder sb;
			ArrayList<String> arraylist;
			for (int i = 0; i < vertices; i++) {
				line = reader.readLine();
				sb = new StringBuilder(line);
				arraylist = new ArrayList<String>();
				if (sb.charAt(0)=='G') {//swap 0 and 1
					arraylist.add(swap(line, 0, 1));
					arraylist.add(swap(line, 0, 3)); 
				}
				else if(sb.charAt(1)=='G') {
					arraylist.add(swap(line, 0, 1));
					arraylist.add(swap(line, 4, 1));
					arraylist.add(swap(line, 2, 1));
				}
				else if(sb.charAt(2)=='G') {
					arraylist.add(swap(line, 1, 2));
					arraylist.add(swap(line, 2, 5));
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
					arraylist.add(swap(line, 6, 7));
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
				if (line.equals(startState)) {
					heap.insert(new Node(0, startState));
					cost.put(startState, 0);
					distance.put(startState, 0);
				}
				else {
					heap.insert(new Node(MAX_VALUE, line));
					cost.put(line, MAX_VALUE);
					distance.put(line, MAX_VALUE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static String swap(String str, int a, int b) {
		StringBuilder sb = new StringBuilder(str);
		char temp = sb.charAt(a);
		sb.setCharAt(a, sb.charAt(b));
		sb.setCharAt(b, temp);
		return sb.toString();
	}
	
// 	public static void printMap(Map mp) {
// 	    Iterator it = mp.entrySet().iterator();
// 	    while (it.hasNext()) {
// 	        Map.Entry pair = (Map.Entry)it.next();
// 	        System.out.println(pair.getKey() + " = " + pair.getValue());
// //	        it.remove(); // avoids a ConcurrentModificationException
// 	    }
// 	}

	int ITERATIONS = 10;
	int num_iter = 0;
	void dijkstra() {
		int w, d1, d2;
		Node min_distance_node;
		while (!heap.isEmpty()) {
			min_distance_node = heap.extractMin();
			int cost_extracted_node = cost.get(min_distance_node.str)+1;
			ArrayList<String> list = adjMap.get(min_distance_node.str);
			for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
			    String nStr = iter.next();
			    w = weight(min_distance_node.str, nStr);
			    d1 = distance.get(min_distance_node.str) + w;
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












