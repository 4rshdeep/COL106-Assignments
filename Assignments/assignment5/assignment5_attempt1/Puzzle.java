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
	static StringBuilder sb;
	static int cost;
	static char movedChar;

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
		Path path = Paths.get(args[0]);
		InputStream in = null;
		try {
			in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int numInput = Integer.parseInt(reader.readLine());
			// assume numInput to be 1
			
			boolean done = false;
			for (int i=0; i<numInput; i++) {
				Graph graph = new Graph(362880);
				String nextLine = reader.readLine();
				String[] strings = nextLine.split("\\s+");
				graph.startState = strings[0];
				graph.finishState = strings[1];

				nextLine = reader.readLine();

				if (strings[0].equals(strings[1])) {
					System.out.println("0 0");
					System.out.println();
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
			
				// if (done = false) {
					graph.makeGraph("perms.txt");
					// done = true;
				// }
			
				// graph.previous
				graph.dijkstra();
				String latter = graph.finishState;
				
				String former;
				// StringBuilder sb = new StringBuilder();
				// System.out.println(graph.previous);
				HashMap<String, String> next = new HashMap<String, String>();
				if (graph.previous.get(latter) == null) {
						System.out.println("-1 -1");
						System.out.println();
						continue;		
				}
				done = false;
				while(!latter.equals(graph.startState)) {
					// System.out.println("here");
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
				System.out.print(count);
				System.out.print(" ");
				System.out.println(cost);
				System.out.println(sb.toString());
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


















