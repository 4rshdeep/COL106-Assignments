import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Puzzle {
	static int vertices;
	static HashMap<String, ArrayList<String>> adjMap;
	static HashMap<Integer, String> distance;
	static HashMap<String, String> previous;
	static String startState;
	static String finishState;
	static MinHeap heap;

	public Puzzle(int n) {
		adjMap = new HashMap<String, ArrayList<String>>();
		distance = new HashMap<Integer, String>();
		previous = new HashMap<String, String>();
		heap = new MinHeap();
	}
	
	static void generatePermutations(StringBuilder sb, int size) {
		if (size==1) {
			System.out.println(sb);
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
	}
	
	static String swap(String str, int a, int b) {
		StringBuilder sb = new StringBuilder(str);
		char temp = sb.charAt(a);
		sb.setCharAt(a, sb.charAt(b));
		sb.setCharAt(b, temp);
		return sb.toString();
	}
	
	static void makeGraph(String str) {
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
				if (sb.charAt(0)=='G') {
					//swap 0 and 1
					arraylist.add(swap(line, 0, 1));
					// swap 0 and 3
					arraylist.add(swap(line, 0, 3)); 
				}
				else if(sb.charAt(1)=='G') {
					arraylist.add(swap(line, 0, 1));
					arraylist.add(swap(line, 4, 1));
					arraylist.add(swap(line, 2, 1));
				}
				else if(sb.charAt(2)=='G') {
					//swap 2 and 1
					arraylist.add(swap(line, 1, 2));
					//swap 2 and 5
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
				distance.put(-1, line);
				heap.insert(-1);
			}
			distance.put(0, startState);
			heap.insert(0);
			System.out.println(distance.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Puzzle pz = new Puzzle(362880);
//		StringBuilder sb = new StringBuilder("12345678G");
//		generatePermutations(sb, 9);
		Path path = Paths.get(args[0]);
		InputStream in = null;
		try {
			in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int numInput = Integer.parseInt(reader.readLine());
			// assume numInput to be 1
			
			String nextLine = reader.readLine();
			String[] strings = nextLine.split("\\s+");
			startState = strings[0];
			finishState = strings[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		makeGraph("perms.txt");
		Set<String> set   = adjMap.keySet();
		Set<String> cloud = new HashSet<String>();
		int min_distance;
		String min_vertex;
		while (!heap.isEmpty()) {
			min_distance = extractMin();
			distance.get(min_distance);
			if () {
				
			}
			cloud.add()
		}


//		for (Entry<String, ArrayList<String>> entry : adjMap.entrySet())
//		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
//		}
		
		long time = System.currentTimeMillis() - startTime;
		System.out.println(time);
	}
}



//
//class Graph {
//	
//}







