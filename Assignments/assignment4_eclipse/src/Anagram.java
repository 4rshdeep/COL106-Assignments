import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Anagram {
	int num_buckets;
	Hashtable hashtable;
	
	public Anagram(int a) {
		num_buckets = a;
		hashtable = new Hashtable(a);
	}

	/* computes hash using the sfold method */
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
	
	private int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
        37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
        107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199 };

 	private long calculate_hash(char[] letters) {
	    long result = 1L;
	    for (char c : letters) {
	        int pos;
			if (c < 97) {
	        	System.out.println(c);
	        	pos = c-21;
	        }
	        else {
	        	pos = c - 97;
	        }
	        result *= PRIMES[pos];
	    }
	    return (result& 0x7fffffff)%num_buckets;
	}
	
	
	/* Loads dictionary into the hashtable */
	void load(File file, int count) {
		try {
			Scanner in = new Scanner(file);
			int bucket;
			String nextStr;
			in.nextLine();
			for(int i=0; i<count; i++) {
				nextStr = in.nextLine();
				// System.out.println(nextStr);
				bucket = (int) calculate_hash(nextStr.toCharArray());
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
	
	/* Checks whether string is present in the hashtable */
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
	
	/* Utility function to sort String */ 
	public static String sortString(String str){
        // convert input string to char array
        char tempArray[] = str.toCharArray();
        
	    // sort tempArray
	    Arrays.sort(tempArray);
	         
	    // return new sorted string
	    return new String(tempArray);
	}



	public static void main(String[] args) {
		boolean exit = false;
		Scanner s = new Scanner(System.in);
		String input;
		
		File vocab_file = new File(args[0]);
		Scanner in = null;
		try {
			in = new Scanner(vocab_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count = Integer.parseInt(in.nextLine());
		
		Anagram a = new Anagram(5);	
		a.load(file, count);
			
		File input_file = new File(args[1]);
		Scanner test_input = new Scanner(input_file);
		
		int num = test_input.nextInt();
		for(int)

		// while (!exit) {
		// 	input = s.next();
		// 	switch (input) {
		// 	case "hash":
		// 		System.out.println(a.hash(s.next()));
		// 	break;
		// 	case "contains" :
		// 		System.out.println(a.contains(s.next()));
		// 	break;
		// 	case "exit" :
		// 		exit = true;
		// 	break;
		// 	case "check_contains":
		// 		try {
		// 			System.out.println(1);
		// 			File f = new File(args[0]);
		// 			Scanner scan = new Scanner(f);
		// 			int c = scan.nextInt();
		// 			String tempStr = "";
		// 			for (int i=0; i<c; i++) {
		// 				System.out.println(i);
		// 				tempStr = a.hashtable.table[i].str;
		// 				if (a.contains(tempStr)==false) {
		// 					System.out.println("Contains has bugs");
		// 				}
		// 			}
		// 		} catch (Exception e) {
		// 			e.printStackTrace();
		// 		}
				
		// 		System.out.println("Seems to be correct");
		// 	break;
		// 	}
		// }
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