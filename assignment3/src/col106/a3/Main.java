//package col106.a3;

// import col106.a3.StringFormatChecker;
// import col106.a3.ta.TA_BTree;


import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {

	public static void main1(String[] a) {
		LinkedList<Integer> l = new LinkedList<>();
		for (int i = 0; i < 10; i++)
			l.add(2 * i);
		ListIterator<Integer> it = l.listIterator();
		while (it.hasNext()) {
			int t = it.next();
			if (t > 5)
				it.add(5);
			System.out.println(t);
		}
		System.out.println(l);
	}

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		BTree<Integer, Integer> tree = new BTree<>(n);
		while (true) {
			String command = s.next();
				
				switch (command) {
				case "exit":
					System.exit(0);
				case "insert":
					tree.insert(s.nextInt(), s.nextInt());
					System.out.println(tree);
					break;
				case "delete":
					tree.delete(s.nextInt());
					break;
				case "print":
					System.out.println(tree);
					break;
				case "size":
					System.out.println(tree.size());
				case "height":
					System.out.println(tree.height());
					break;
				case "empty":
					System.out.println(tree.isEmpty());
					break;
				case "search":
					System.out.println(tree.search(s.nextInt()));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}