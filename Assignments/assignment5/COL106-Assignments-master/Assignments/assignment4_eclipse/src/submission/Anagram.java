import java.io.*;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Anagram {
	static int num_buckets;
	static Hashtable hashtable;

	public Anagram(int a) {
		num_buckets = a;
		hashtable = new Hashtable(a);
	}

	private static int[] PRIMES = new int[] { 2,  3,  5,  7,  11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
	        107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};

	private static long calculate_hash(String str) {
		long result = 1L;
		char[] letters = str.toCharArray();
		for (char c : letters) {
			int pos;
			// System.out.println(c);
			if (c == 39) {
				pos = 37;
				// System.out.println(1);
			} 
			else if(c == 32) {
				continue;
			}
			else if (c < 97) {
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
	static void load(String str) {
		try {
			//File file = new File(str);
			// removed scanner for more effiecient reading from vocabulary of 25000 lines
			//Refer to https://stackoverflow.com/questions/11664393/java-nio-read-line-by-line
			Path path = Paths.get(str);
			InputStream in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			int bucket;
			String nextStr;
			String sorted;
			int count = Integer.parseInt(reader.readLine());
			for (int i = 0; i < count; i++) {
				nextStr = reader.readLine();
				sorted  = sortString(nextStr);
				bucket = (int) calculate_hash(sorted);
				if (hashtable.table[bucket] == null) {
					hashtable.table[bucket] = new Node(nextStr, sorted);
				} else {
					hashtable.table[bucket] = new Node(hashtable.table[bucket], nextStr, sorted);
				}
			}
			Node tempNode;
			// max elements hashed to a particular bucket is 11 which can further be decreased by increasing num_b
			for (int i = 0; i < num_buckets; i++) {
				tempNode = hashtable.table[i];
				// System.out.println("here");
				// System.out.print(i + ": ");
				while (tempNode != null) {
					// System.out.print(tempNode.str + "(" + tempNode.sortedStr + ") ");
					tempNode = tempNode.next;
				}
				// System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// static void show_hashtable(int idx) {
	// 	Node tempNode = hashtable.table[idx];
	// 	System.out.print(String.valueOf(idx) + ": ");
	// 	while (tempNode != null) {
	// 		System.out.print(tempNode.str + "(" + tempNode.sortedStr + ") ");
	// 		tempNode = tempNode.next;
	// 	}
	// 	System.out.println();
	// }

	

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

	/*break into 4 and 5*/
	static List<String> split_four_and_five(String str, String first) {
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
		StringBuilder secondStr = new StringBuilder();
		StringBuilder thirdStr  = new StringBuilder(str);
		StringBuilder sbStr     = new StringBuilder(str);
		List<String> list       = new Vector<String>();
		List<String> tempList   = new Vector<String>();
		int len = str.length();

		if ((len < 3)) {
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
					else if (len==12) {
						list.addAll(split_four_and_five(thirdStr.toString(), secondStr.toString()));
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
								list.addAll(split_three_and_three(thirdStr.toString(), secondStr.toString()));
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


	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 54001 41 secs on dictionary
		// 4001 46 secs
		// 10007 45 secs
		// 53003 41 secs
		// 93001 42 secs
		// 99991 
		// 25033 43.8

		Anagram a = new Anagram(20011);
		load(args[0]);

		BufferedOutputStream bout = new BufferedOutputStream(System.out);//TODO
		PrintWriter p = new PrintWriter(bout);
		
		try {
			Path path = Paths.get(args[1]);
			InputStream in = null;
			in = Files.newInputStream(path);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			int count = Integer.parseInt(reader.readLine());
			List<String> list;
			String str, tempStr, sortedTempStr;
			String[] strArr;
			int bucket;
			Vector<String> vec = new Vector<String>();
			Vector<String> v1 = new Vector<String>();
			Vector<String> v2 = new Vector<String>();
			Vector<String> v3 = new Vector<String>();
	
			Node tempNode;
			String tempString;
			for (int i = 0; i < count; i++) {
				str = reader.readLine();
				str = str.replaceAll("\\s","");
				if (str.length()>12) {
					continue;
				}
				list = generate_strings(str);
				for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
					tempStr = iter.next();
					strArr  = tempStr.split("_");
					if (strArr.length == 1) {
						tempStr = strArr[0];
						sortedTempStr = sortString(tempStr);
						bucket 		  = (int) calculate_hash(sortedTempStr);
						tempNode 	  = hashtable.table[bucket];
						while (tempNode != null) {
							if ((tempNode.sortedStr).equals(sortedTempStr)) {
								vec.add(tempNode.str);
							}
							tempNode = tempNode.next;
						}
					} else if (strArr.length == 2) {
						v1 = get_anagram(strArr[0]);
						v2 = get_anagram(strArr[1]);
				
						String s1, s2; //TODO try stringbuilder
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
				for (ListIterator<String> iter_ = vec.listIterator(); iter_.hasNext(); ) {
					p.println(iter_.next());
				}

				/* If using Hashsets is not allowed */
				// Collections.sort(vec);
				// for (ListIterator<String> iter_ = vec.listIterator(); iter_.hasNext(); ) {
				// 	currString = iter_.next();
				// 	if (prevString==null) {
				// 		p.println(currString);
				// 		prevString = currString;
				// 	}
				// 	else {
				// 		if (!currString.equals(prevString)) {
				// 			p.println(currString);
				// 			prevString = currString;
				// 		}		
				// 	}
				// }
				// define at top
				// String prevString = null;
			
				p.println("-1");
				vec = new Vector<String>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long time = System.currentTimeMillis() - startTime;
		p.println("time: " + time + " millis");
		p.close();
		System.out.println("time: " + time + " millis");
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