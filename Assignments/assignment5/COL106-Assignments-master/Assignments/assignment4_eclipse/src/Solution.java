import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
		Solution a = new Solution(15);
		Scanner input = new Scanner(System.in);
		int bucket;
		String nextStr;
		String sorted;
		int count = input.nextInt();
        input.nextLine();
        for (int i = 0; i < count; i++) {
			nextStr = input.nextLine();
			// System.out.println(String.valueOf(i) + " " + nextStr);
			sorted  = sortString(nextStr);
			bucket = (int) calculate_hash(sorted);
			if (hashtable.table[bucket] == null) {
				hashtable.table[bucket] = new Node(nextStr, sorted);
			} else {
				hashtable.table[bucket] = new Node(hashtable.table[bucket], nextStr, sorted);
			}
		}

		Node tempNode;
		for (int i=0; i<hashtable.table.length; i++) {
			// System.out.print(String.valueOf(i) + ": " );
			tempNode = hashtable.table[i];
			while (tempNode!=null) {
				// System.out.print(tempNode.str+", ");
				tempNode = tempNode.next;
			}
			// System.out.println();
		}
        count = Integer.parseInt(input.nextLine());
        List<String> list;
		String str, tempStr, sortedTempStr;
		String[] strArr;
		Vector<String> vec = new Vector<String>();
		Vector<String> v1 = new Vector<String>();
		Vector<String> v2 = new Vector<String>();
		Vector<String> v3 = new Vector<String>();

		for (int i = 0; i < count; i++) {
			str = input.nextLine();
            list 	= generate_strings(str);
            // System.out.println(list);
			for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
				tempStr = iter.next();
				strArr  = tempStr.split("_");
				if (strArr.length == 1) {
					tempStr = strArr[0];
					sortedTempStr = sortString(tempStr);
					bucket 		  = (int) a.calculate_hash(sortedTempStr);
					tempNode 	  = a.hashtable.table[bucket];
					while (tempNode != null) {
						if ((tempNode.sortedStr).equals(sortedTempStr)) {
							vec.add(tempNode.str);
                            // System.out.println(tempNode.str);
						}
						tempNode = tempNode.next;
					}
				} else if (strArr.length == 2) {
					v1 = get_anagram(strArr[0]);
					v2 = get_anagram(strArr[1]);
					String s1, s2; //todo try stringbuilder
					for (ListIterator<String> iter1 = v1.listIterator(); iter1.hasNext(); ) {
						s1 = iter1.next();
						for (ListIterator<String> iter2 = v2.listIterator(); iter2.hasNext(); ) {
							s2 = iter2.next();
							vec.add(s1 + " " + s2);
							if (!s1.equals(s2)) {
								vec.add(s2 + " " + s1);
							}
						}
					}
				} else if (strArr.length == 3) {
					v1 = get_anagram(strArr[0]);
                    
					v2 = get_anagram(strArr[1]);
					v3 = get_anagram(strArr[2]);
					String s1, s2, s3;
					for (ListIterator<String> iter1 = v1.listIterator(); iter1.hasNext(); ) {
						s1 = iter1.next();
						for (ListIterator<String> iter2 = v2.listIterator(); iter2.hasNext(); ) {
							s2 = iter2.next();
							for (ListIterator<String> iter3 = v3.listIterator(); iter3.hasNext(); ) {
								s3 = iter3.next();
								if ((s1.equals(s2)) && (!s2.equals(s3)) && (!s1.equals(s3))) {
									vec.add(s1 + " " + s2 + " " + s3);
									vec.add(s2 + " " + s3 + " " + s1);
									vec.add(s3 + " " + s1 + " " + s2);
								} else if ((s2.equals(s3)) && (!s1.equals(s3)) && (!s2.equals(s1))) {
									vec.add(s1 + " " + s2 + " " + s3);
									vec.add(s2 + " " + s3 + " " + s1);
									vec.add(s3 + " " + s1 + " " + s2);
								} else if ((s1.equals(s3)) && (!s2.equals(s3)) && (!s2.equals(s1))) {
									vec.add(s1 + " " + s2 + " " + s3);
									vec.add(s2 + " " + s3 + " " + s1);
									vec.add(s3 + " " + s1 + " " + s2);
								} else if ((!(s1.equals(s3)) && (!s2.equals(s3)) && (!s2.equals(s1)))) {
									vec.add(s1 + " " + s2 + " " + s3);
									vec.add(s2 + " " + s3 + " " + s1);
									vec.add(s3 + " " + s1 + " " + s2);
									vec.add(s2 + " " + s1 + " " + s3);
									vec.add(s1 + " " + s3 + " " + s2);
									vec.add(s3 + " " + s2 + " " + s1);
								} else if (((s1.equals(s3)) && (s2.equals(s3)) && (s2.equals(s1)))) {
									vec.add(s3 + " " + s2 + " " + s1);
								}
								else {
									System.out.println("you should not be here");
								}
							}
						}
					}

				}
			}
			Set<String> set = new HashSet<String>();
			set.addAll(vec);
			vec.clear();
			vec.addAll(set);
			Collections.sort(vec);
			vec.add("-1");
			// System.out.println(vec);
				for (ListIterator<String> iter_ = vec.listIterator(); iter_.hasNext(); ) {
				System.out.println(iter_.next());
			}
			vec = new Vector<String>();
		}
    }
// }

// class Anagram {
	static int num_buckets;
	static Hashtable hashtable;

	public Solution(int a) {
		num_buckets = a;
		hashtable = new Hashtable(a);
	}

	/* computes hash using the sfold method */
	static long hash(String s) {
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
		return ((sum & 0x7fffffff) % M); //  ((code & 0x7fffffff) % M) use instead
	}

	public static int[] PRIMES = new int[] { 2,  3,  5,  7,  11, 13, 17, 19, 23, 29, 31,
	        37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
	        107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199
	                                        };

	public static long calculate_hash(String str) {
		long result = 1L;
		char[] letters = str.toCharArray();
		for (char c : letters) {
			int pos;
			// System.out.println(c);
			if (c == 39) {
				pos = 37;
				// System.out.println(1);
			} else if (c < 97) {
				// System.out.println(c);
				pos = c - 22;
				// System.out.println(2);
			} else {
				pos = c - 97;
				// System.out.println(3);
			}
			result *= PRIMES[pos];

		}
		return (result & 0x7fffffff) % num_buckets;
	}
	/* Loads dictionary into the hashtable */
	static void load() {
		Scanner in = new Scanner(System.in);
		int bucket;
		String nextStr;
		String sorted;
		int count = in.nextInt();
           in.nextLine();
           for (int i = 0; i < count; i++) {
			nextStr = in.nextLine();
			System.out.println(String.valueOf(i) +" " +nextStr);
               sorted  = sortString(nextStr);
			bucket = (int) calculate_hash(sorted);
			if (hashtable.table[bucket] == null) {
				hashtable.table[bucket] = new Node(nextStr, sorted);
			} else {
				hashtable.table[bucket] = new Node(hashtable.table[bucket], nextStr, sorted);
			}
		  }
        return;
	}

	static void show_hashtable(int idx) {
		Node tempNode = hashtable.table[idx];
		System.out.print(String.valueOf(idx) + ": ");
		while (tempNode != null) {
			System.out.print(tempNode.str + "(" + tempNode.sortedStr + ") ");
			tempNode = tempNode.next;
		}
		System.out.println();
	}

	/* Checks whether string is present in the hashtable */
	static boolean contains(String s) {
		int h = (int) hash(s);
		Node tempNode = hashtable.table[h];
		if (tempNode == null) {
			return false;
		} else {
			while (tempNode != null) {
				if ((tempNode.str).equals(s)) {
					return true;
				}
				tempNode = tempNode.next;
			}
			return false;
		}
	}

	/* breaks str into 3*3 and returns a List after concatenating first+3*3 */
	static List<String> split_three_and_three(String str, String first) {
		int len = str.length();
		List<String> list       = new Vector<String>();
		StringBuilder secondStr = new StringBuilder();
		StringBuilder thirdStr  = new StringBuilder(str);
		StringBuilder sbStr     = new StringBuilder(str);
		int count = 0;

		// secondstr of length 3 and thirdstr of len-3
		for (int i = 0; i < len - 2; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 1; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					thirdStr.deleteCharAt(k);
					thirdStr.deleteCharAt(j);
					thirdStr.deleteCharAt(i);
					if (first.equals("")) {
						list.add(secondStr + "_" + thirdStr);
					} else {
						list.add(first + "_" + secondStr + "_" + thirdStr);
					}
					thirdStr = new StringBuilder(str);
					secondStr.deleteCharAt(secondStr.length() - 1);
					count++;
					if ((count == 10) && (len == 6)) {
						return list;
					}
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}
		return list;
	}

	/* breaks str into 4*4 and returns a List after concatenating first+3*3 */
	static List<String> split_four_and_four(String str, String first) {
		int len = str.length();
		List<String> list       = new Vector<String>();
		StringBuilder secondStr = new StringBuilder();
		StringBuilder thirdStr  = new StringBuilder(str);
		StringBuilder sbStr     = new StringBuilder(str);
		int count = 0;
		// 4 and len-4
		for (int i = 0; i < len - 3; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 2; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len - 1; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					for (int l = k + 1; l < len; l++) {
						secondStr.append(sbStr.charAt(l));
						thirdStr.deleteCharAt(l);
						thirdStr.deleteCharAt(k);
						thirdStr.deleteCharAt(j);
						thirdStr.deleteCharAt(i);
						count++;
						if (first.equals("")) {
							list.add(secondStr + "_" + thirdStr);
						} else {
							list.add(first + "_" + secondStr + "_" + thirdStr);
						}
						if (len == 8) {
							if (count == 35) {
								//repitions occur after this in case of same partitions
								return list;
							}
						}
						thirdStr = new StringBuilder(str);
						secondStr.deleteCharAt(secondStr.length() - 1);
					}
					secondStr.deleteCharAt(secondStr.length() - 1);
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}
		return list;
	}

	static List<String> generate_strings(String str) {
		StringBuilder firstStr  = new StringBuilder();
		StringBuilder secondStr = new StringBuilder();
		StringBuilder thirdStr  = new StringBuilder(str);
		StringBuilder sbStr     = new StringBuilder(str);
		StringBuilder tempStr   = new StringBuilder();
		List<String> list       = new Vector<String>();
		List<String> tempList   = new Vector<String>();
		List<String> fList      = new Vector<String>();
		int len = str.length();

		if ((len < 3)||(len>12)) {
			return list;
		}
		list.add(thirdStr + "_");
		if ((len == 3) || (len == 4) || (len == 5)) {
			return list;
		}

		int count = 0;
		// secondstr of length 3 and thirdstr of len-3
		for (int i = 0; i < len - 2; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 1; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					thirdStr.deleteCharAt(k);
					thirdStr.deleteCharAt(j);
					thirdStr.deleteCharAt(i);
					list.add(secondStr + "_" + thirdStr);
					count++;
					if ((count == 10) && (len == 6)) {
						return list;
					} else if (len == 9) {
						list.addAll(split_three_and_three(thirdStr.toString(), secondStr.toString()));//Check if overcount
					} else if (len == 11) {
						// 3*4*4
						list.addAll(split_four_and_four(thirdStr.toString(), secondStr.toString()));
					}
					thirdStr = new StringBuilder(str);
					secondStr.deleteCharAt(secondStr.length() - 1);
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}

		if (len == 7) {
			return list;
		}

		count = 0;
		// 4 and len-4
		for (int i = 0; i < len - 3; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 2; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len - 1; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					for (int l = k + 1; l < len; l++) {
						secondStr.append(sbStr.charAt(l));
						thirdStr.deleteCharAt(l);
						thirdStr.deleteCharAt(k);
						thirdStr.deleteCharAt(j);
						thirdStr.deleteCharAt(i);
						list.add(secondStr + "_" + thirdStr);
						// thirdStr is of 6 chars here
						if (len == 10) {
							list.addAll(split_three_and_three(thirdStr.toString(), secondStr.toString()));
						} else if (len == 12) {
							list.addAll(split_four_and_four(thirdStr.toString(), secondStr.toString()));//6 times extra counting
						}
						count++;
						if ((len == 8) && (count == 35)) {
							return list;
						}
						thirdStr = new StringBuilder(str);
						secondStr.deleteCharAt(secondStr.length() - 1);
					}
					secondStr.deleteCharAt(secondStr.length() - 1);
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}

		if (len == 8) {
			return list;
		}

		if (len == 9) {
			return list;
		}

		count = 0;
		// 5 len-5
		for (int i = 0; i < len - 4; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 3; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len - 2; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					for (int l = k + 1; l < len - 1; l++) {
						secondStr.append(sbStr.charAt(l));
						for (int m = l + 1; m < len; m++) {
							secondStr.append(sbStr.charAt(m));
							thirdStr.deleteCharAt(m);
							thirdStr.deleteCharAt(l);
							thirdStr.deleteCharAt(k);
							thirdStr.deleteCharAt(j);
							thirdStr.deleteCharAt(i);
							list.add(secondStr + "_" + thirdStr);
							// System.out.println(secondStr + "_" + thirdStr);
							count++;
							// System.out.println(count);
							if (len == 10) {
								if (count == 126) {
									return list;
								}
							} else if (len == 11) {
								//5*3*3
								list.addAll(split_three_and_three(thirdStr.toString(), secondStr.toString()));
							}

							thirdStr = new StringBuilder(str);
							secondStr.deleteCharAt(secondStr.length() - 1);
						}
						secondStr.deleteCharAt(secondStr.length() - 1);
					}
					secondStr.deleteCharAt(secondStr.length() - 1);
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}

		if (len == 10) {
			return list;
		}

		if (len == 11) {
			return list;
		}

		count = 0;
		// 6 len-6
		tempList = new Vector<String>();
		for (int i = 0; i < len - 5; i++) {
			secondStr.append(sbStr.charAt(i));
			for (int j = i + 1; j < len - 4; j++) {
				secondStr.append(sbStr.charAt(j));
				for (int k = j + 1; k < len - 3; k++) {
					secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
					for (int l = k + 1; l < len - 2; l++) {
						secondStr.append(sbStr.charAt(l));
						for (int m = l + 1; m < len - 1; m++) {
							secondStr.append(sbStr.charAt(m));
							for (int n = m + 1; n < len; n++) {
								secondStr.append(sbStr.charAt(n));
								thirdStr.deleteCharAt(n);
								thirdStr.deleteCharAt(m);
								thirdStr.deleteCharAt(l);
								thirdStr.deleteCharAt(k);
								thirdStr.deleteCharAt(j);
								thirdStr.deleteCharAt(i);
								count++;
								tempList.addAll(split_three_and_three(thirdStr.toString(), secondStr.toString()));
								if (count <= 462) {
									// in case both partitions are equal after this we will have water image and mirror image
									list.add(secondStr + "_" + thirdStr);
								}
								thirdStr = new StringBuilder(str);
								secondStr.deleteCharAt(secondStr.length() - 1);
							}
							secondStr.deleteCharAt(secondStr.length() - 1);
						}
						secondStr.deleteCharAt(secondStr.length() - 1);
					}
					secondStr.deleteCharAt(secondStr.length() - 1);
				}
				secondStr.deleteCharAt(secondStr.length() - 1);
			}
			secondStr.deleteCharAt(secondStr.length() - 1);
		}
		return list;
	}


	/* Utility function to sort String */
	static String sortString(String str) {
		// convert input string to char array
		char tempArray[] = str.toCharArray();
		// sort tempArray
		Arrays.sort(tempArray);
		// return new sorted string
		return new String(tempArray);
	}

	static Vector<String> get_anagram(String str) {
		String sorted = sortString(str);
		int bucket 	  = (int) calculate_hash(sorted);
		Vector<String> vec = new Vector<String>();

		Node tempNode = hashtable.table[bucket];
		while (tempNode != null) {
			if ((tempNode.sortedStr).equals(sorted)) {
				vec.add(tempNode.str);
			}
			tempNode = tempNode.next;
		}
		return vec;
	}


	
}

class Node {
	Node next;
	String str;
	String sortedStr;

	Node(String s, String sorted) {
		this(null, s, sorted);
	}

	Node(Node node, String s, String sorted) {
		this.str 	= s;
		this.sortedStr = sorted;
		this.next 	= node;
	}
}

class Hashtable {
	Node[] table;
	Hashtable(int n) {
		table = new Node[n];
	}
}
	