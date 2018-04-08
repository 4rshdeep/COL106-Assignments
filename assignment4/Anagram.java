import java.util.*;
import java.io.File;

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
		// Scanner in = new Scanner(file);
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
                    secondStr.deleteCharAt(secondStr.length()-1);
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

        if (len < 3) {
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

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		boolean exit = false;
		Scanner s = new Scanner(System.in);
		String input;
		Anagram a = new Anagram(100);
		while (!exit) {
			input = s.next();
			switch (input) {
				case "hash":
					System.out.println(a.hash(s.next()));
					break;
				case "exit":
					exit = true;
					break;
				case "check":
					List<String> list = generate_strings("abcdefghijk");
        			System.out.println(list.size());
        			break;	
				
			}
		}


		long time = System.currentTimeMillis() - startTime;
        System.out.println("time: " + time + " millis");
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