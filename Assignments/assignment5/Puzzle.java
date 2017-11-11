import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
//TODO store only the graph needed by using a queue
//TODO Stop when final state is reached in djikstra's

public class Puzzle {
	
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
	
	public static void main(String[] args) {
		// System.out.println((Integer.MAX_VALUE+10)& 0x7fffffff);
		long startTime = System.currentTimeMillis();
		Graph graph = new Graph(362880);
		Path path = Paths.get(args[0]);
		InputStream in = null;
		try {
			in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int numInput = Integer.parseInt(reader.readLine());
			// assume numInput to be 1
			for (int i=0; i<numInput; i++) {
					
				String nextLine = reader.readLine();
				String[] strings = nextLine.split("\\s+");
				graph.startState = strings[0];
				graph.finishState = strings[1];

				nextLine = reader.readLine();

				if (strings[0].equals(strings[1])) {
					System.out.println("strings equal");
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
				
				
				graph.makeGraph();

				graph.dijkstra();
				String latter = graph.finishState;
				
				String former;
				StringBuilder sb = new StringBuilder();
				
				HashMap<String, String> next = new HashMap<String, String>();
				if (graph.previous.get(latter) == null) {
						System.out.println("no path exist");
						continue;		
				}
				boolean done = false;
				while(!latter.equals(graph.startState)) {
					// System.out.println(latter);
					former = graph.previous.get(latter);
					// System.out.println(former);
					next.put(former, latter);
					latter = former;
				}
				if (latter == null) {
					continue;
				}
				former = graph.startState;
				done = false;
				while(!done) {
					latter = next.get(former);
					move(former, latter);
					former = latter;
					if (former.equals(graph.finishState)) {
						done = true;
						break;
					}
					System.out.print(" ");	
				}
				System.out.println();
			}
		} 
		catch (IOException e) {
				e.printStackTrace();
		}
		long time = System.currentTimeMillis() - startTime;
		
		System.out.println(time);
	}

	private static void move(String former, String latter) {
		if (former.charAt(0)=='G') {
			System.out.print(latter.charAt(0));
			if (latter.charAt(1)=='G') {
				System.out.print("L");
			}
			else if (latter.charAt(3)=='G') {
				System.out.print("U");
			}
		}
		else if (former.charAt(1)=='G') {
			System.out.print(latter.charAt(1));
			if (latter.charAt(0)=='G') {
				System.out.print("R");
			}
			else if (latter.charAt(2)=='G'){
				System.out.print("L");
			}
			else if (latter.charAt(4)=='G') {
				System.out.print("U");
			}
		}
		else if(former.charAt(2)=='G') {
			System.out.print(latter.charAt(2));
			if (latter.charAt(1)=='G') {
				System.out.print("R");
			}
			else if (latter.charAt(5)=='G'){
				System.out.print("U");
			}
		}
		else if(former.charAt(3)=='G') {
			System.out.print(latter.charAt(3));
			if (latter.charAt(0)=='G') {
				System.out.print("D");
			}
			else if (latter.charAt(4)=='G'){
				System.out.print("L");
			}
			else if (latter.charAt(6)=='G') {
				System.out.print("U");
			}
		}
		else if(former.charAt(4)=='G') {
			System.out.print(latter.charAt(4));
			if (latter.charAt(1)=='G') {
				System.out.print("D");
			}
			else if (latter.charAt(3)=='G'){
				System.out.print("R");
			}
			else if (latter.charAt(5)=='G') {
				System.out.print("L");
			}
			else if (latter.charAt(7)=='G') {
				System.out.print("U");
			}
		}
		else if(former.charAt(5)=='G') {
			System.out.print(latter.charAt(5));
			if (latter.charAt(2)=='G') {
				System.out.print("D");
			}
			else if (latter.charAt(4)=='G'){
				System.out.print("R");
			}
			else if (latter.charAt(8)=='G') {
				System.out.print("U");
			}
		}
		else if(former.charAt(6)=='G') {
			System.out.print(latter.charAt(6));
			if (latter.charAt(7)=='G') {
				System.out.print("L");
			}
			else if (latter.charAt(3)=='G'){
				System.out.print("D");
			}
		}
		else if(former.charAt(7)=='G') {
			System.out.print(latter.charAt(7));
			if (latter.charAt(4)=='G') {
				System.out.print("D");
			}
			else if (latter.charAt(6)=='G'){
				System.out.print("R");
			}
			else if (latter.charAt(8)=='G') {
				System.out.print("L");
			}
		}
		else if(former.charAt(8)=='G') {
			System.out.print(latter.charAt(8));
			if (latter.charAt(7)=='G') {
				System.out.print("R");
			}
			else if (latter.charAt(5)=='G'){
				System.out.print("D");
			}
		}
		else {
			System.out.println("somethings wrong with the moves");
		}
	}
}


















