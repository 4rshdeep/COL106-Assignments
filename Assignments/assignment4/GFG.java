import java.util.*;
public class GFG {


    static List<String> split_in_three(String str, String first) {
        int len = str.length();
        List<String> list = new Vector<String>();
        int count = 0;
        // secondstr of length 3 and thirdstr of len-3
        for (int i=0; i<len-2; i++) {
            StringBuilder secondStr = new StringBuilder();
            StringBuilder thirdStr  = new StringBuilder(str);
            StringBuilder sbStr     = new StringBuilder(str);
            secondStr.append(sbStr.charAt(i));
            for (int j=i+1; j<len-1; j++) {
                secondStr.append(sbStr.charAt(j));
                for (int k=j+1; k<len; k++) {
                    secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                    thirdStr.deleteCharAt(k);
                    thirdStr.deleteCharAt(j);    
                    thirdStr.deleteCharAt(i);
                    if (first.equals("")) {
                        list.add(secondStr + "_" + thirdStr);    
                    }
                    else {
                        list.add(first + "_" + secondStr + "_" + thirdStr);    
                    }
                    // System.out.println(secondStr + "_" + thirdStr);
                    thirdStr = new StringBuilder(str);
                    secondStr.deleteCharAt(secondStr.length()-1);
                    count++;
                    if ((count==10)&&(len==6)) {
                        // System.out.println(list);
                        // System.out.println(list.size());
                        return list;
                    }
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            secondStr.deleteCharAt(secondStr.length()-1);
        }
        return list;
    }

    static void generate_strings(String str) {
        StringBuilder firstStr  = new StringBuilder();
        StringBuilder secondStr = new StringBuilder();
        StringBuilder thirdStr  = new StringBuilder(str);
        StringBuilder sbStr     = new StringBuilder(str);
        StringBuilder tempStr   = new StringBuilder();
        List<String> list = new Vector<String>();
        List<String> tempList = new Vector<String>();
        int len = str.length();
        if (len<3) {
            return;
        }
        list.add(thirdStr+"_");
        if ((len==3)||(len==4)||(len==5)) {
            return;
        }
        int count;
        list.addAll(split_in_three(str, ""));
        if (len==7) {
            return;
        }

        count = 0;
        System.out.println("4 in first");
        // 4 and len-4
        for (int i=0; i<len-3; i++) {
            secondStr.append(sbStr.charAt(i));
            for (int j=i+1; j<len-2; j++) {
                secondStr.append(sbStr.charAt(j));
                for (int k=j+1; k<len-1; k++) {
                    secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                    for (int l=k+1; l<len; l++) {
                        secondStr.append(sbStr.charAt(l));
                        thirdStr.deleteCharAt(l);                   
                        thirdStr.deleteCharAt(k);
                        thirdStr.deleteCharAt(j);    
                        thirdStr.deleteCharAt(i);
                        list.add(secondStr + "_" + thirdStr);
                        thirdStr = new StringBuilder(str);
                        secondStr.deleteCharAt(secondStr.length()-1);
                        count++;
                        if (count==35) {
                            if (len==8) {
                                //repitions occur after this in case of same partitions
                                return;
                            }
                        }
                    }
                    secondStr.deleteCharAt(secondStr.length()-1);
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            secondStr.deleteCharAt(secondStr.length()-1);
        }

        if (len==8) {
            return;
        }

        List<String> tList = new Vector<String>();
        if (len==9) {
            // break in 3 3 3
            count = 0;
            for (int i=0; i<len-2; i++) {
                secondStr.append(sbStr.charAt(i));
                for (int j=i+1; j<len-1; j++) {
                    secondStr.append(sbStr.charAt(j));
                    for (int k=j+1; k<len; k++) {
                        secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                        thirdStr.deleteCharAt(k);
                        thirdStr.deleteCharAt(j);    
                        thirdStr.deleteCharAt(i);
                        tList.addAll(split_in_three(thirdStr.toString(), secondStr.toString()));
                        thirdStr = new StringBuilder(str);
                        secondStr.deleteCharAt(secondStr.length()-1);
                        count++;
                        
                        tList = new Vector<String>();
                    }
                    secondStr.deleteCharAt(secondStr.length()-1);
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            System.out.println(count);
            
            return;
        }

        count = 0;
        // 5 len-5
        for (int i=0; i<len-4; i++) {
            secondStr.append(sbStr.charAt(i));
            for (int j=i+1; j<len-3; j++) {
                secondStr.append(sbStr.charAt(j));
                for (int k=j+1; k<len-2; k++) {
                    secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                    for (int l=k+1; l<len-1; l++) {
                        secondStr.append(sbStr.charAt(l));
                        for (int m=l+1; m<len; m++) {
                            secondStr.append(sbStr.charAt(m));
                            thirdStr.deleteCharAt(m);
                            thirdStr.deleteCharAt(l);                   
                            thirdStr.deleteCharAt(k);
                            thirdStr.deleteCharAt(j);    
                            thirdStr.deleteCharAt(i);
                            list.add(secondStr + "_" + thirdStr);
                            System.out.println(secondStr + "_" + thirdStr);
                            count++;
                            
                            // System.out.println(count); 
                            if (count==126) {
                                if (len==10) {
                    // in case both partitions are equal after this we will have water image and mirror image
                                    return;
                                }
                            }

                            thirdStr = new StringBuilder(str);
                            secondStr.deleteCharAt(secondStr.length()-1);    
                        }
                        secondStr.deleteCharAt(secondStr.length()-1);
                    }
                    secondStr.deleteCharAt(secondStr.length()-1);
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            System.out.println("--"+secondStr);
            secondStr.deleteCharAt(secondStr.length()-1);
        }

        if (len == 10) {
            //make one for 3 3 4
            return;
        }

        if (len == 11) {
            //make one for 3 3 5 and one for 3 4 4
            return;
        }

        count = 0;
        // 5 len-5
        for (int i=0; i<len-5; i++) {
            secondStr.append(sbStr.charAt(i));
            for (int j=i+1; j<len-4; j++) {
                secondStr.append(sbStr.charAt(j));
                for (int k=j+1; k<len-3; k++) {
                    secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                    for (int l=k+1; l<len-2; l++) {
                        secondStr.append(sbStr.charAt(l));
                        for (int m=l+1; m<len-1; m++) {
                            secondStr.append(sbStr.charAt(m));
                            for (int n=m+1; n<len; n++) {
                                secondStr.append(sbStr.charAt(n));
                                thirdStr.deleteCharAt(n);
                                thirdStr.deleteCharAt(m);
                                thirdStr.deleteCharAt(l);                   
                                thirdStr.deleteCharAt(k);
                                thirdStr.deleteCharAt(j);    
                                thirdStr.deleteCharAt(i);
                                list.add(secondStr + "_" + thirdStr);
                                System.out.println(secondStr + "_" + thirdStr);
                                count++;
                                // System.out.println(count); 
                                if (count==462) {
                                // in case both partitions are equal after this we will have water image and mirror image
                                    
                                    if (len==12) {
                                        // System.out.println("---------------------");
                                        return;
                                    }
                                }
                                thirdStr = new StringBuilder(str);
                                secondStr.deleteCharAt(secondStr.length()-1);
                            }
                            secondStr.deleteCharAt(secondStr.length()-1);    
                        }
                        secondStr.deleteCharAt(secondStr.length()-1);
                    }
                    secondStr.deleteCharAt(secondStr.length()-1);
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            System.out.println("--"+secondStr);
            secondStr.deleteCharAt(secondStr.length()-1);
        }
        System.out.println(count);

        // make one for 3,3,6  3,4,5  4,4,4
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
        long startTime = System.currentTimeMillis();
        Scanner input = new Scanner(System.in);
        generate_strings("abcdefghi");
        long time = System.currentTimeMillis() - startTime;
        System.out.println("time: " + time + " millis");
    }
}


class Strings {
    String str1;
    String str2;
    String str3;

    Strings(String s1, String s2, String s3) {
        this.str1 = s1;
        this.str2 = s2;
        this.str3 = s3;
    }
}