// import col106.a3.BTree;
// import col106.a3.DuplicateBTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StressTest {

	public static void main(String argv[]) throws Exception {
		long startTime = System.currentTimeMillis();
		BTree<Integer, Integer> graph = new BTree<>(4);
		int V = 10000;
		int E = 100000;
		int cor = 0;
		int inc = 0;
		ArrayList<ArrayList<Integer>> g = new ArrayList<>(V);
		Random r = new Random();
		for (int i = 0; i < V; i++)
			g.add(new ArrayList<>());
		for (int i = 0; i < E; i++) {
			int v1 = r.nextInt(V);
			int v2 = r.nextInt(V);
			if (v1 != v2) {
				g.get(v1).add(v2);
				graph.insert(v1, v2);
			}
		}
		for (int i = 0; i < V; i++) {
			List<Integer> neighbourhood = graph.search(i);
			neighbourhood.sort(Integer::compareTo);
			ArrayList<Integer> correctAnswer = g.get(i);
			correctAnswer.sort(Integer::compareTo);
			if (!neighbourhood.equals(correctAnswer)) {
				System.out.println("Incorrect search result for: " + i);
				System.out.println("correctAnswer: " + correctAnswer);
				System.out.println("neighbourhood: " + neighbourhood);
				inc = inc + 1;
			} else {
				cor = cor + 1;
			}

		}
		System.out.println("insertion and search correct");
		try{
			for (int i = 0; i < V; i++){
				if (g.get(i).size()!=0) {
					graph.delete(i);	
				}
			}

		if (!graph.isEmpty()) {
			System.out.println("deletion does not yield empty graph");
			System.out.println(graph);
		}
		System.out.println("deletion correct");
	
		}
		catch(Exception e) {
			System.out.println(graph);
		}
		
		long time = System.currentTimeMillis() - startTime;
		// System.out.println(graph);
		System.out.println("time: " + time + " millis");

		// System.out.println("Incorrect " + inc);
		// System.out.println("correct " + cor);
	}
}
