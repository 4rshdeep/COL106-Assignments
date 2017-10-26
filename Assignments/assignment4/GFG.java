import java.util.*;
public class GFG {


    static List<String> split_three(String str, String first) {
        int len = str.length();
        List<String> list = new Vector<String>();
        StringBuilder secondStr = new StringBuilder();
        StringBuilder thirdStr  = new StringBuilder(str);
        StringBuilder sbStr     = new StringBuilder(str);
        int count = 0;

        // secondstr of length 3 and thirdstr of len-3
        for (int i=0; i<len-2; i++) {
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
                    thirdStr = new StringBuilder(str);
                    secondStr.deleteCharAt(secondStr.length()-1);
                    count++;
                    if ((count==10)&&(len==6)) {
                        return list;
                    }
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            secondStr.deleteCharAt(secondStr.length()-1);
        }
        return list;
    }

    static List<String> split_four(String str, String first) {
        int len = str.length();
        List<String> list = new Vector<String>();
        StringBuilder secondStr = new StringBuilder();
        StringBuilder thirdStr  = new StringBuilder(str);
        StringBuilder sbStr     = new StringBuilder(str);
        int count = 0;
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
                        count++;
                        if (first.equals("")) {
                            list.add(secondStr + "_" + thirdStr);    
                        }
                        else {
                            list.add(first + "_" + secondStr + "_" + thirdStr);    
                        }
                        if (count==35) {
                            if (len==8) {
                                //repitions occur after this in case of same partitions
                                return list;
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
        List<String> fList = new Vector<String>();
        int len = str.length();
        if (len<3) {
            return;
        }
        list.add(thirdStr+"_");
        if ((len==3)||(len==4)||(len==5)) {
            return;
        }
        int count = 0;
        // secondstr of length 3 and thirdstr of len-3
        for (int i=0; i<len-2; i++) {
            secondStr.append(sbStr.charAt(i));
            for (int j=i+1; j<len-1; j++) {
                secondStr.append(sbStr.charAt(j));
                for (int k=j+1; k<len; k++) {
                    secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
                    thirdStr.deleteCharAt(k);
                    thirdStr.deleteCharAt(j);    
                    thirdStr.deleteCharAt(i);
                    list.add(secondStr + "_" + thirdStr);    
                    count++;
                    if ((count==10)&&(len==6)) {
                        return;
                    }
                    else if (len==9) {
                        list.addAll(split_three(thirdStr.toString(), secondStr.toString()));
                    }
                    else if (len==11) {
                        // 3*4*4
                        list.addAll(split_four(thirdStr.toString(), secondStr.toString()));
                        // // 3*3*5 inefficient here add in 5 split
                        // fList.addAll(split_three(thirdStr.toString(), secondStr.toString()));
                    }
                    thirdStr = new StringBuilder(str);
                    secondStr.deleteCharAt(secondStr.length()-1);
                }
                secondStr.deleteCharAt(secondStr.length()-1);
            }
            secondStr.deleteCharAt(secondStr.length()-1);
        }
        // System.out.println(tempList.size());
        // System.out.println(fList);
                    
        // System.out.println(list);
        if (len==7) {
            return;
        }

        count = 0;
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
                        // thirdStr is of 6 chars here
                        if (len==10) {
                            list.addAll(split_three(thirdStr.toString(), secondStr.toString()));
                        }
                        else if (len==12) {
                            tempList.addAll(split_four(thirdStr.toString(), secondStr.toString()));
                        }
                        count++;
                        if (len==8) {
                            if (count<=35) {
                                list.add(secondStr + "_" + thirdStr);
                            }
                        }
                        else {
                            list.add(secondStr + "_" + thirdStr);
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
        // System.out.println(tempList);
        // System.out.println(tempList.size());
        if (len==8) {
            return;
        }

        // List<String> tList = new Vector<String>();
        if (len==9) {
            // // should yield 9c3*6c3 combinations in total
            // // break in 3 3 3
            // count = 0;
            // tempList = new Vector<String>();
            // for (int i=0; i<len-2; i++) {
            //     secondStr.append(sbStr.charAt(i));
            //     for (int j=i+1; j<len-1; j++) {
            //         secondStr.append(sbStr.charAt(j));
            //         for (int k=j+1; k<len; k++) {
            //             secondStr.append(sbStr.charAt(k)); //Here I have a string of length 3
            //             thirdStr.deleteCharAt(k);
            //             thirdStr.deleteCharAt(j);    
            //             thirdStr.deleteCharAt(i);
            //             tempList.addAll(split_three(thirdStr.toString(), secondStr.toString()));
            //             thirdStr = new StringBuilder(str);
            //             secondStr.deleteCharAt(secondStr.length()-1);
            //             count++;
            //         }
            //         secondStr.deleteCharAt(secondStr.length()-1);
            //     }
            //     secondStr.deleteCharAt(secondStr.length()-1);
            // }
            // // System.out.println(tempList.size());
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
                            // System.out.println(secondStr + "_" + thirdStr);
                            count++;
                            
                            // System.out.println(count); 
                            if (len==10) {
                                if (count<=126) {
                                // in case both partitions are equal after this we will have water image and mirror image
                                    list.add(secondStr + "_" + thirdStr);
                                }
                            }
                            else {
                                list.add(secondStr + "_" + thirdStr);
                            }
                            if (len==11) {
                                //5*3*3
                                fList.addAll(split_three(thirdStr.toString(), secondStr.toString()));
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
        // System.out.println(fList.size());

        if (len == 10) {
            //make one for 3 3 4

            return;
        }

        if (len == 11) {
            // System.out.println(fList.size());
            //make one for 3 3 5 and one for 3 4 4
            return;
        }

        count = 0;
        // 6 len-6
        tempList = new Vector<String>();
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
                                // System.out.println(secondStr + "_" + thirdStr);
                                count++;
                                tempList.addAll(split_three(thirdStr.toString(), secondStr.toString()));
                                if (count<=462) {
                                // in case both partitions are equal after this we will have water image and mirror image
                                    list.add(secondStr + "_" + thirdStr);
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
            // System.out.println("--"+secondStr);
            secondStr.deleteCharAt(secondStr.length()-1);
        }
        // System.out.println(tempList);
        return;
        

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
        generate_strings("abcdefghijkl");
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