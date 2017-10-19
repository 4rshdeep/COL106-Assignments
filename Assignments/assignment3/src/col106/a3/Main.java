package col106.a3;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        System.out.println("B tree size");
        int n = s.nextInt();
        // DuplicateBTree<String, String> tree = new BTree<>(n);
        BTree<String, String> tree = new BTree<>(n);
        while (true) {
            String command = s.next();
            try {
                switch (command) {
                    case "exit":
                        System.exit(0);
                    case "reset" :
                        int nt = s.nextInt();
                        tree = new BTree<>(nt);
                        break; 
                    case "insert":
                        tree.insert(s.next(), s.next());
                        System.out.println(tree);
                        break;
                    case "delete":
                        tree.delete(s.next());
                        System.out.println(tree);
                        break;
                    case "print":
                        System.out.println(tree);
                        break;
                    case "height":
                        System.out.println(tree.height());
                        break;
                    case "empty":
                        System.out.println(tree.isEmpty());
                        break;
                    case "search":
                        System.out.println(tree.search(s.next()));
                        break;
                    case "print_num_keys":
                        Node tempNode = tree.getRoot();
                        System.out.println(tempNode.num_keys);
                        break;
                    case "size":
                        System.out.println(tree.size());
                        break;
                    case "get":
                        System.out.println(tree.getPredecessor(tree.getRoot(), s.nextInt()).key);
                        break;
                    case "gets":
                        KeyValue k = tree.getSuccessor(tree.getRoot(), s.nextInt());
                        System.out.println(k.key);
                        System.out.println(k.value);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
                StackTraceElement[] elements = e.getStackTrace();  
                for (int iterator=1; iterator<=elements.length; iterator++)  
                System.out.println("Class Name:"+elements[iterator-1].getClassName()+" Method Name:"+elements[iterator-1].getMethodName()+" Line Number:"+elements[iterator-1].getLineNumber());
            }   
        }
    }
}
