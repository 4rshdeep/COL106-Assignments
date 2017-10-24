import java.util.Scanner;

public class Anagram {
	int num_buckets;

	public Anagram(int a) {
		num_buckets = a;
	}


	long hash(String s) {
		return sfold(s, num_buckets);
	}

	// Use folding on a string, summed 4 bytes at a time
	// taken from http://research.cs.vt.edu/AVresearch/hashing/strings.php
	long sfold(String s, int M) {
		int intLength = s.length() / 4;
		long sum = 0;
		for (int j = 0; j < intLength; j++) {
			char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
			long mult = 1;
			for (int k = 0; k < c.length; k++) {
				sum += c[k] * mult;
				mult *= 256;
			}
		}
		char c[] = s.substring(intLength * 4).toCharArray();
		long mult = 1;
		for (int k = 0; k < c.length; k++) {
			sum += c[k] * mult;
			mult *= 256;
		}
		return ((sum& 0x7fffffff)%M); //  ((code & 0x7fffffff) % M) use instead
	}

	void load(File file) {
		Scanner in = new Scanner(file);
	}

	public static void main(String[] args) {
		boolean exit = false;
		Scanner s = new Scanner(System.in);
		String input;
		Anagram a = new Anagram(100);
		while (!exit) {
			input = s.next();
			if (input.equals("hash")) {
				System.out.println(a.hash(s.next()));
			} else if (input.equals("exit")) {
				exit = true;
			}
		}
	}	
}

class Node {
	Node next;
	String str;
	Node(String s) {
		this(s, null);
	}
	Node(String s, Node n) {
		this.str = s;
		this.next = n;
	}
}

class Hashtable {
	Node[] table;
	Hashtable(int n){
		table = new Node[n];
	}
}