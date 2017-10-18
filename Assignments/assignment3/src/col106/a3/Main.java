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
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}