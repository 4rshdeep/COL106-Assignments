//package assignment4;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Anagram {
	int num_buckets;
	Hashtable hashtable;
	
	public Anagram(int a) {
		num_buckets = a;
		hashtable = new Hashtable(a);
	}


	long hash(String s) {
		return sfold(s, num_buckets);
	}

	// Use folding on a string, summed 4 bytes at a time
	// taken from http://research.cs.vt.edu/AVresearch/hashing/strings.php
	static long sfold(String s, int M) {
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

	void load(String s) {
		try {
			File file = new File(s);
			Scanner in = new Scanner(file);
			int count = Integer.parseInt(in.nextLine());
			int bucket;
			String nextStr;
			for(int i=0; i<count; i++) {
				nextStr = in.nextLine();
				bucket = (int) hash(nextStr);
				if (hashtable.table[bucket] == null) {
					hashtable.table[bucket] = new Node(nextStr);
				}
				else {
					hashtable.table[bucket] = new Node(nextStr, hashtable.table[bucket]);
				}
			}
			Node tempNode;
			// max elements hashed to a particular bucket is 11 which can further be decreased by increasing num_b
			for(int i=0; i<num_buckets; i++) {
				tempNode = hashtable.table[i];
				System.out.print(i+": ");
				while(tempNode!=null) {
					System.out.println(tempNode.str);
					tempNode = tempNode.next;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	boolean contains(String s) {
		int h = (int) hash(s);
		Node tempNode = hashtable.table[h];
		if (tempNode == null) {
			return false;
		}
		else {
			while(tempNode != null) {
				if ((tempNode.str).equals(s)) {
					return true;
				}
				tempNode = tempNode.next;
			}
			return false;
		}
	}
	

	public static void main(String[] args) {
		boolean exit = false;
		Scanner s = new Scanner(System.in);
		String input;
		Anagram a = new Anagram(10000);
		a.load(args[0]);
		while (!exit) {
			input = s.next();
			switch (input) {
			case "hash":
				System.out.println(a.hash(s.next()));
			break;
			case "contains" :
				System.out.println(a.contains(s.next()));
			break;
			case "exit" :
				exit = true;
			break;
			case "check_contains":
				try {
					System.out.println(1);
					File f = new File(args[0]);
					Scanner scan = new Scanner(f);
					int c = scan.nextInt();
					String tempStr = "";
					for (int i=0; i<c; i++) {
						System.out.println(i);
						tempStr = a.hashtable.table[i].str;
						if (a.contains(tempStr)==false) {
							System.out.println("Contains has bugs");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("Seems to be correct");
			break;
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