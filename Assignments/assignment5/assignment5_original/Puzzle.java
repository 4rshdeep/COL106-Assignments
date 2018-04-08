import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
//TODO store only the graph needed by using a queue
//TODO Stop when final state is reached in djikstra's

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
					graph.charMoved 	 = prevGraph.charMoved;
					graph.strMoved 		 = prevGraph.strMoved;
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
				former = graph.startState;
				done = false;
				int count = 0;
				cost = 0;
				sb = new StringBuilder();
				char movedChar;
				while(!done) {
					latter = next.get(former);
					sb.append(graph.strMoved.get(former.concat(latter)));
					movedChar = graph.charMoved.get(former.concat(latter));
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


















