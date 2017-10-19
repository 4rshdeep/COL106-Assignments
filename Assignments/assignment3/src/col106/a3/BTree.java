package col106.a3;

import java.util.List;
import java.util.Vector;
import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

    Node root;
    static int b;
    static int t;
    int num_nodes;
    // ToDo make a height attribute of a node which increases only when it splits and keep the lowest one to be zero
    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        if (b%2 != 0) {
            throw new bNotEvenException();
        }
        int m;
        this.b  	= b;
        m       	= b;
        t      	 	= m/2;
        root    	= new Node<Key, Value>(m-1);
        num_nodes	= 0;
    }

    public Node getRoot() {
    	return root;
    }

    @Override
    public boolean isEmpty() {        
        return (num_nodes == 0);
    }

    @Override
    public int size() {
        // better method
        return num_nodes;
        // return getSize(root);
    }

    public int getSize(Node node) {
    	// if node is null or empty return 0;
    	if ((node == null)||(node.num_keys==0)) {
            return 0;
        }

        int sum = node.num_keys;
        
        for (int i=0; i<node.num_keys+1; i++) {
            sum = sum + getSize(node.children[i]);
        }
        return sum;
    }


    @Override
    public int height() {
        int h = -1;
        Node tempNode = root;
        while((tempNode != null) && (tempNode.num_keys > 0)) {
            h        = h+1;
            tempNode = tempNode.children[0];
        }
        return h;
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        List<Value> list = getSearch(key, root);
        //list.removeAll(Collections.singleton(null));
        Value[] vals = (Value[]) (list.toArray());
        // Collections.reverse(list);
		return list;
    }

    /* return list can have null values */
    public List<Value> getSearch(Key key, Node node) {
        List<Value> list = new Vector<Value>();
        // if (node == null) {
        // 	return null;
        // }
        int i = 0;
        int n = node.num_keys;
        if (node.isLeaf) {
            while (i<n) {
            	Key temp = (Key) node.key[i];
            	if (temp.compareTo(key)==0) {
            		list.add((Value)node.value[i]);
            	}

            	i = i+1;
            }
            return list;
        }

        while(i<n) {
            Key temp = (Key) node.key[i];
            if((temp).compareTo(key)==0) {
                int j=i;
                
                // add left subtree
                list.addAll(getSearch(key, node.children[i]));

           
           
                // add element and then search in right subtree
                while(j<n) {
                    list.add((Value)(node.value[j]));
                    list.addAll(getSearch(key, node.children[j+1]));
              
                    j = j+1;
            // if the next key is different we need to stop
                    if (j!=n) {
                    	if ((((Key)node.key[j]).compareTo((key))!=0)) {
                        	return list;
                    	}
                    }
                }
                return list;
            }
            else if (((Key)node.key[i]).compareTo(key) > 0) {
                list.addAll(getSearch(key, node.children[i]));
                return list;
            }

            i = i+1;
        }
        // if neither the key is equal nor less than and not the leaf node then search in the last child
    	return getSearch(key, node.children[n]);
    }


    @Override
    public void insert(Key key, Value val) {
        Node r = root;
        num_nodes += 1;
        // if the root is full put an empty root and call split child
        if (r.num_keys==b-1) {
            Node tempRoot        = new Node(b-1);
            root                 = tempRoot;
            tempRoot.num_keys    = 0;
            tempRoot.isLeaf      = false;
            tempRoot.children[0] = r;
            split_child(tempRoot, 0);
        }
        insert_non_full(key, val, root);
    }

    public void insert_non_full(Key key, Value val, Node node) {
        if (node.isLeaf) {
            int i = 0;
            int num_k = node.num_keys;
            while((i<num_k)&&(key.compareTo((Key)node.key[i])>0)) {
                i = i + 1;
            }
            // now key to be inserted is smaller than the ith key

            // shift all key, values from i onwards including i
            for (int j=num_k; j>i; j--) {
                node.key[j]   = node.key[j-1];
                node.value[j] = node.value[j-1];
            }

            // insert at ith and increment num_keys
            node.key[i]   = key;
            node.value[i] = val;
            node.num_keys = num_k + 1;
        }
        else {
            int i = 0;
            int num_k = node.num_keys;
            Key temp = (Key)node.key[i];
            
            while((i<num_k)&&(key.compareTo(temp)>0)) {
                i = i + 1;
            }

    // it has to go to the ith children - since ith key greater than the key        
    // ensure that node where the key belongs to is not full 
            if (node.children[i].num_keys == b-1) {
                split_child(node, i);
    // now it has to go to i+1th child if "our key" is greater than the key brought up
                temp = (Key)node.key[i];
                if (key.compareTo(temp)>0) {
                    i = i+1;
                }
            }
            insert_non_full(key, val, node.children[i]);
        }
    }

    // code to split ith child which is not full
    public void split_child(Node node, int s) {
        Node y = node.children[s];
        Node z = new Node(b-1);
        z.isLeaf = y.isLeaf;

        Node temp   =  node.children[s];
        Key xkey    = (Key)temp.key[t-1];
        Value xVal  = (Value)temp.value[t-1];

        // copy from y to the new node z
        for (int i=0; i<t-1; i++) {
            z.key[i]        = y.key[t+i];
            z.value[i]      = y.value[t+i];
        }

        if (!y.isLeaf) {
            for (int i=0; i<t-1; i++) {
                z.children[i+1] = y.children[t+1+i];
            }            
        }

        // put the first one left out
        z.children[0] = y.children[t];
        

        // shift from node and add x into it
        int num_k = node.num_keys;
        for (int i=num_k; i>s; i--) {
            node.key[i] = node.key[i-1];
            node.value[i] = node.value[i-1];
            node.children[i+1] = node.children[i];
        }

        node.key[s]        = xkey;
        node.value[s]      = xVal;
        node.children[s]   = y;
        node.children[s+1] = z;
        
        node.num_keys      = node.num_keys + 1;
        z.num_keys    = t-1;
        y.num_keys    = t-1;
        
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
    	if ((root == null)||(root.num_keys==0)) {
    	    return;
		}
        else if (root.isLeaf) {
            Node node = root;
            
            int n = node.num_keys;
            int i = 0;
            int j = 0;
            
            while (i<n) {
            // find first occuurence of the key
                if (key.compareTo((Key)node.key[i])==0) {
                // copy i+1 in i and reduce num_keys
                // if i is not the last key
                    if (i!=n-1) {
                        if (((Key)node.key[n-1]).compareTo(key)==0){
                            node.num_keys = i;
                            num_nodes = i;
                            return;
                        }
                    //duplicates are not present from i to n-1 since n-1 is not same                
                        j = i;
                    // delete duplicates
                        while((j>=i)&&(j<n-1)) {
                            j = j+1;
                            if (((Key)node.key[j]).compareTo(key)!=0) {
                    // this means they are equal till j-1th index
                                j = j-1;
                                break;
                            }
                        }
                    // i to j indices contain duplicates 
                    // replace i by j+1
                        node.num_keys = node.num_keys - (j-i+1);
                        for (int k=j+1; k<n; k++) {
                            node.value[i] = node.value[k];
                            node.key[i]   = node.key[k];
                            i = i+1;
                        }
                    }
                    // if i is the last key
                    else if (i == n-1) {
                        node.num_keys = node.num_keys -1;
                    }
                    num_nodes = node.num_keys;
                    return;
                }
                i = i+1;
            }
            return;
        }
		else {
            int k = search(key).size();
            num_nodes = num_nodes - k;
			delete_key(key, root, k);
		}
    }


    public void delete_key(Key key, Node node, int iter) {
        System.out.println("At start: " + toString());
        int check = contains(key, node);
        int i = 0;
        int idx = 0;
        KeyValue k;
        Key tempKey;
        if(iter==0) {
            return ;
        }
        if (check != -1) {
            if (node.isLeaf) {
                System.out.println("leafy");
                idx = check;
                for (int j=idx; j<node.num_keys-1; j++) {
                    node.key[j]   = node.key[j+1];
                    node.value[j] = node.value[j+1];
                }
                node.num_keys = node.num_keys - 1;
                delete_key(key, root, iter-1);
                return;
            }
            else {
                System.out.println("internal");
                idx = check;
                if (node.children[idx].num_keys > t-1) {
                    System.out.println("getpred");
                    k = getPredecessor(node, idx);
                    node.key[idx]    = k.key;
                    node.value[idx]  = k.value;
                    tempKey        = (Key)k.key;
                    delete_key(tempKey, node.children[idx], 1);
                    delete_key(key, root, iter-1);
                    return;
                }
                if (idx!=node.num_keys) {
                    if (node.children[idx+1].num_keys > t-1) {
                        System.out.println("get succ");
                        k = getSuccessor(node, idx);
                        node.key[idx]     = k.key;
                        node.value[idx]   = k.value;
                        tempKey         = (Key)k.key;
                        delete_key(tempKey, node.children[idx+1], 1);
                        delete_key(key, root, iter-1); 
                        return;
                    }
                }
                System.out.println("merge 'em");
                int l = merge_down(node, idx);
                delete_key(key, node.children[l], iter);
                return;
            }
        }
        else {
            i = 0;
            while ((i<node.num_keys)&&(key.compareTo((Key)node.key[i])>0)) {
                System.out.println("finding");
                i = i + 1;
            }
            
            // now we know key is in the ith subtree
            int keys = node.children[i].num_keys;
            System.out.println("in the else");
            if (keys == t-1) {
                if ((i!=0)) {
                    if ((node.children[i-1].num_keys > t-1)) {
                        System.out.println("Filling from the previous");
                        fill_from_prev(node, i);
                        delete_key(key, node.children[i], iter);
                         
                        return;
                    }
                }
                if (i<node.num_keys) {
                    if (node.children[i+1].num_keys > t-1) {
                        fill_from_next(node, i);
                        System.out.println("Filling from the next");
                        delete_key(key, node.children[i], iter);
                         
                        return;
                    }
                }
                //Todo check if merge works for i = 0 and n
                System.out.println("Time to merge nodes");
                int j = merge_down(node, i);
                delete_key(key, node.children[j], iter);
                
                return;
            }

            delete_key(key, node.children[i], iter);
            return;
            
        }

    }

    public int contains(Key key, Node node) {
        int i = 0;
        int n = node.num_keys;
        while (i<n) {
            if (key.compareTo((Key)node.key[i])==0) {
                return i;
            }
            i = i+1;
        }
        return -1;
    }

    /*  
        node into which it merges can be get by
        int k = merge_down(node , i);
        targetNode = node.children[k] 
    */
    public int merge_down(Node node, int i) {
        // if it has a next node then merge with the next node
        if (i != node.num_keys) {
            // System.out.println(1);
            Node targetNode = node.children[i];
            Node nextNode   = node.children[i+1];
            int num_curr    = targetNode.num_keys;
            int num_next    = nextNode.num_keys;

        // merging i+1 th child with i th child

            // if we merge down the root we need to make targetNode root
            if (root.equals(node) && (node.num_keys==1)){
                root = targetNode;
            }

            targetNode.key[num_curr] = node.key[i];
            targetNode.value[num_curr] = node.value[i];
            targetNode.children[num_curr+1] = nextNode.children[0];

            num_curr = num_curr + 1;

            for (int l=0; l<num_next; l++) {
                targetNode.key[num_curr+l]   = nextNode.key[l];
                targetNode.value[num_curr+l] = nextNode.value[l];
                targetNode.children[num_curr+1+l] = nextNode.children[l+1];
            }

            int num_head = node.num_keys;
            for (int k=i; k<num_head-1; k++) {
                node.key[k] = node.key[k+1];
                node.value[k] = node.value[k+1];
                node.children[k+1] = node.children[k+2];
            }

            node.num_keys       = node.num_keys - 1;
            targetNode.num_keys = targetNode.num_keys + nextNode.num_keys + 1;
            return i;
        }
        else {
            // System.out.println(2);
            Node targetNode = node.children[i-1];
            Node nextNode   = node.children[i];
            int num_curr    = targetNode.num_keys;
            int num_next    = nextNode.num_keys;

        // merging i+1 th child with i th child

            // if we merge down the root we need to make targetNode root
            if (root.equals(node) && (node.num_keys==1)){
                root = targetNode;
            }

            targetNode.key[num_curr] = node.key[i-1];
            targetNode.value[num_curr] = node.value[i-1];
            targetNode.children[num_curr+1] = nextNode.children[0];

            num_curr = num_curr + 1;

            for (int l=0; l<num_next; l++) {
                targetNode.key[num_curr+l]   = nextNode.key[l];
                targetNode.value[num_curr+l] = nextNode.value[l];
                targetNode.children[num_curr+1+l] = nextNode.children[l+1];
            }

            int num_head = node.num_keys;
            for (int k=i-1; k<num_head-1; k++) {
                node.key[k] = node.key[k+1];
                node.value[k] = node.value[k+1];
                node.children[k+1] = node.children[k+2];
            }

            node.num_keys       = node.num_keys - 1;
            targetNode.num_keys = targetNode.num_keys + nextNode.num_keys + 1;
            return i-1; 
        } 

    }

    // fill from i-1 th child into i
    public void fill_from_prev(Node node, int i) {
     // add node.key[i] in front
        Node prevNode = node.children[i-1];
        Node targetNode = node.children[i];

        int num_left = prevNode.num_keys;
        int num_curr = targetNode.num_keys;
        
        targetNode.children[num_curr+1] = targetNode.children[num_curr];

        for (int l=num_curr; l>0; l--) {
            targetNode.key[l] = targetNode.key[l-1];
            targetNode.children[l] = targetNode.children[l-1];
        }
        
        targetNode.key[0]   = node.key[i-1];
        targetNode.value[0] = node.value[i-1];
        targetNode.children[0] = prevNode.children[num_left];
        
        node.value[i-1]     = prevNode.value[num_left-1];
        node.key[i-1]       = prevNode.key[num_left-1];
       
        // increment keys in target node
        targetNode.num_keys = targetNode.num_keys + 1;
        // decrement keys in prevNode
        prevNode.num_keys   = prevNode.num_keys -1;

    }

    public void fill_from_next(Node node, int i) {
        Node targetNode = node.children[i];
        Node nextNode   = node.children[i+1];

        int num_curr    = targetNode.num_keys;
        int num_next    = nextNode.num_keys;

        targetNode.key[num_curr] = node.key[i];
        targetNode.value[num_curr] = node.value[i];
        targetNode.children[num_curr+1] = nextNode.children[0];

        node.key[i]     = nextNode.key[0];
        node.value[i]   = nextNode.value[0];

        nextNode.children[0] = nextNode.children[1];

        for (int l=1; l<num_next; l++) {
            nextNode.value[l-1] = nextNode.value[l];
            nextNode.key[l-1] = nextNode.key[l];
            nextNode.children[l] = nextNode.children[l+1];
        }

        nextNode.num_keys = nextNode.num_keys - 1;
        targetNode.num_keys = targetNode.num_keys + 1;

    }


    /*gets the greatest predecessor of the ith key in the node*/
    public KeyValue getPredecessor(Node node, int i) {
        // to get the largest element in the node.children[i] tree
        node = node.children[i];
        while(!node.isLeaf) {
            node = node.children[node.num_keys];
        }
        return new KeyValue(node.key[node.num_keys-1], node.value[node.num_keys-1]);
    }

    /* gets the least successor of the node.key[i] -- least greatest element */
    public KeyValue getSuccessor(Node node, int i) {
        node = node.children[i+1];
        while (!node.isLeaf) {
            node = node.children[0];
        }
        return new KeyValue(node.key[0], node.value[0]);

    }


    // public void delete_key(Key key, Node node, Node parentNode, int idx) throws IllegalKeyException {
    //     int n = node.num_keys;
    //     int i = 0;
    //     int j = 0;

    //     // check if node contains the key
    //     int check = contains(key, node);
    //     if (check != -1) {
    //     // two cases if internal node or leaf node maintain t-1 keys after deletion assume t initially
    //         if (node.isLeaf) {
    //             int b = count(key, node);
    //             int num_keys = node.num_keys;

    //             if (num_keys>=(t+b-1)) {
    //                 System.out.println("suff keys in leaf node");
    //                 delete_from_leaf(key, node);
    //                 return;   
    //             }
    //             else {
    //                 boolean first = false;
    //                 boolean second = false;
    //                 boolean third = false;

    //                 System.out.println("insuff keys in leaf node");
    //                 delete_from_leaf(key, node);
    //                 while(node.num_keys < t-1) {
    //                 // Todo whenever fill from prev check whether idx not equal to zero
    //                 // since parent has t keys therefore merging will always fill the void left due to delete
    //                     if ((idx!=0)) {
    //                         if ((parentNode.children[idx-1].num_keys > t-1)) {
    //                             System.out.println("fill from prev");
    //                             fill_from_prev(parentNode, idx);
    //                             first = true;
    //                        }
    //                        else {
    //                            first = false;
    //                        }
    //                     }
    //                     if ((first==false)) {
    //                         if ((parentNode.children[idx+1].num_keys > t-1)) {
    //                             System.out.println("fill from next");
    //                             fill_from_next(parentNode, idx);
    //                             second = true;
    //                         }
    //                         else {
    //                             second = false;
    //                         }
    //                     }
    //                     if ((first==false)&&(second==false)) {
    //                         System.out.println("merge");
    //                         merge_down(parentNode, idx);
    //                         third = true;
    //                         return;
    //                     }
    //                 }
    //                 return;
    //             }
    //         }
    //         else {
    //             System.out.println("I am thinking what to do!");   
    //             // k = merge_down then node.children[k] gives the node where they are merge_down
    //             int total_keys = count(key, node);
    //             if (total_keys==1) {
    //                 i = 0;
    //                 while (i<n) {
    //                     if (key.compareTo((Key)node.key[i])!=0) {
    //                         i = i+1;
    //                     }
    //                     else {
    //                         break;  
    //                     }
    //                     // key occurs at i key == node.key[i]
    //                 }
    //                 // remove keys from aaju baaju children
    //                 int pred = getSearch(key, node.children[i]).size();
    //                 int succ = getSearch(key, node.children[i+1]).size();
    //                 if (pred==0) {
    //                     if (succ==0) {
    //                         // single occurence
    //                         if (node.children[i].num_keys > t-1) {
    //                             KeyValue replacer = getRightmost(i, node);
    //                             node.key[i] = replacer.key;
    //                             node.value[i] = replacer.key;
    //                             remove(replacer.key, node.children[i]);
    //                         }
    //                         else if (node.children[i+1] > t-1) {
    //                             KeyValue replacer = getLeftmost(i+1, node);
    //                             node.key[i] = replacer.key;
    //                             node.value[i] = replacer.value;
    //                             remove(replacer.key, node.children[i+1]);
    //                         }
    //                         else {
    //                             int k = merge_down(node, i);
    //                             delete_key(key, node.children[k], node, k);
    //                         }
    //                     }
    //                     else {
    //                         delete_key(key, node.children[i+1], node, i+1);

    //                         delete_key(key, node, parentNode, node_occurence(node, parentNode));
    //                     }
    //                 }
    //                 else {
    //                     if (succ == 0) {
    //                         delete_key(key, node.children[i], node, i);
    //                         delete_key(key, node, parentNode, node_occurence(node, parentNode));   
    //                     }
    //                     else {
    //                         delete_key(key, node.children[i], node, i);
    //                         delete_key(key, node.children[i+1], node, i+1);
    //                         delete_key(key, node, parentNode, node_occurence(node, parentNode));
    //                     }
    //                 }
                    
    //             }
    //             else {
    //                 int first_occ = contains(key, node);
    //                 int b = count(key, node);
    //                 if (node.num_keys - b >= t-2) {
    //                     // remove all copies and call delete_keys on node
    //                     for (int k=first_occ+1; k<n; k++) {
    //                         if (key.compareTo((Key)node.key[k])!=0) {
    //                             node.key[k-1] = node.key[k];
    //                             node.value[k-1] = node.value[k];
    //                             node.children[k-1] = node.children[k];
    //                         }
    //                         node.children[n-1] = node.children[n];
    //                     }
    //                     delete_key(key, node, parentNode, idx);
    //                 }
    //                 // else if (num_keys>t-1) {
    //                 //     int x = node.num_keys - t + 1;
    //                 //     int b = last_occurence(key, node);

    //                 //     for (int k=b; k<n; k++) {
    //                 //         node.value[k] = node.value[k-x];
    //                 //         node.key[k] = node.key[k-x];
    //                 //         node.key[k+1] = node.key[k-x+1];
    //                 //     }
                        
    //                 //     for (int k=first_occ+2; (k<n)&&(node.num_keys>t-1); k++) {
                            
    //                 //     }
    //                 // }
    //                 else {
    //                     int b = last_occurence(key, node);
    //                     int a = contains(key, node);
    //                     j = a;
    //                     while((j>=a)&&(j<=b)) {
    //                         delete_key(key, node.children[j]);
    //                         j = j+1;
    //                     }
    //                     delete_key(node.children[b+1]);
    //                     delete_key(node);
    //                 }
    //             }
    //         }
    //     }
        // else {
        //     while ((i<n)&&(key.compareTo((Key)node.key[i])>0)) {
        //         System.out.println("finding");
        //         i = i + 1;
        //     }
        //     // now we know key is in the ith subtree
        //     int keys = node.children[i].num_keys;
        //     System.out.println("in the else");
            
        //     if (keys == t-1) {
        //         if ((i!=0)) {
        //             if ((node.children[i-1].num_keys > t-1)) {
        //                 System.out.println("Filling from the previous");
        //                 fill_from_prev(node, i);
        //             }
        //         }
        //         else if (i<node.num_keys) {
        //             if (node.children[i+1].num_keys > t-1) {
        //                 fill_from_next(node, i);
        //                 System.out.println("Filling from the next");
        //             }
        //         }
        //         else {
        //             if ((i>=0)&&(i<n)) {
        //                 System.out.println("Time to merge nodes");
        //                 merge_down(node, i);
        //             }
        //         }
        //     }
        //     delete_key(key, node.children[i], node, i);
        //     return;
        // }

    // }

    // public void remove(Key key, Node node) {
    //     int i = count(key, node);
    //     if (node.isLeaf) {
    //         for (int j=i; j<node.num_keys-1; j++) {
    //             node.key[i] = node.key[i+1];
    //             node.value[i] = node.value[i+1];
    //         }
    //         node.num_keys = node.num_keys -1;
    //         return;
    //     }
    //     else {
    //         if (node.children[i].num_keys>t-1) {
    //             KeyValue k = getRightmost(i, node);
    //             node.key[i] = k.key;
    //             node.value[i] = k.value;
    //             remove(k.key, node.children[i]);
    //             return;   
    //         }
    //         else if (node.children[i+1].num_keys>t-1) {
    //             KeyValue k = getLeftmost(i+1, node);
    //             node.key[i] = k.key;
    //             node.value[i] = k.value;
    //             remove(k.key, node.children[i+1]);
    //             return;
    //         }
    //         else {
    //             int k = merge_down(node, key);
    //             remove(key, node.children[i]);
    //         }
    //     }
    // }

    // public int node_occurence(Node node, Node parentNode) {
    //     int num = parentNode.num_keys;
    //     for (int i=0; i<=num_keys; i++) {
    //         if (parentNode.children[i].equals(node)) {
    //             return i;
    //         }
    //     }
    // }

    public int count(Key key, Node node) {
        int i = 0;
        int n = node.num_keys;
        int sum = 0;
        while (i<n) {
            if (key.compareTo((Key)node.key[i])==0) {
                sum = sum+1;
            }
            i = i+1;
        }
        return sum;
    }

    

    public int last_occurence(Key key, Node node) {
        int i = contains(key, node);
        int n = node.num_keys;
        while (i<n) {
            if (key.compareTo((Key)node.key[i])!=0) {
                return i-1;
            }
            i = i+1;
        }
        return -1;
    }

    

    // public KeyValue getRightmost(int i, Node node) { // righmost key in ith children of node
    //     Node tempNode =  node.children[i];
    //     KeyValue k = KeyValue((Key)tempNode.key[n-1], (Value)tempNode.value[n-1]);
    //     return k;
    // }

    // public KeyValue getLeftmost(int i, Node node) {
    //     Node tempNode =  node.children[i+1];
    //     KeyValue k = KeyValue((Key)tempNode.key[0], (Value)tempNode.value[0]);
    //     return k;
    // }



    public void delete_from_leaf(Key key, Node node) throws IllegalKeyException {
    	int n = node.num_keys;
    	int i = 0;
   		int j = 0;
   		while (i<n) {
   			// find first occuurence of the key
   			if (key.compareTo((Key)node.key[i])==0) {
   				// copy i+1 in i and reduce num_keys
   				// if i is not the last key
   				if (i!=n-1) {
   					if (((Key)node.key[n-1]).compareTo(key)==0){
   						node.num_keys = i;
   						return;
   					}
   					//duplicates are not present from i to n-1 since n-1 is not same				
   					j = i;
   					// delete duplicates
   					while((j>=i)&&(j<n-1)) {
   						j = j+1;
   						if (((Key)node.key[j]).compareTo(key)!=0) {
   					// this means they are equal till j-1th index
   							j = j-1;
   							break;
   						}
   					}
   					// i to j indices contain duplicates 
   					// replace i by j+1
   					node.num_keys = node.num_keys - (j-i+1);
   					for (int k=j+1; k<n; k++) {
   						node.value[i] = node.value[k];
   						node.key[i]	  = node.key[k];
   						i = i+1;
   					}
   				}
   				// if i is the last key
   				else if (i == n-1) {
   					node.num_keys = node.num_keys -1;
   				}
   				return;
   			}
   			i = i+1;
   		}
   		return;
    }

    @Override
    public String toString() {
        return getString(root);
    }

    public String getString(Node node) {
        StringBuilder str = new StringBuilder("[");
        if (node.isLeaf) {
            int num_k = node.num_keys;
            for (int i=0; i<num_k; i++) {
                str.append(node.key[i].toString()+ "=" + node.value[i].toString());
                if (i!=num_k-1) {
                    str.append(", ");
                }
            }
            str.append("]");
            return str.toString();
        }
        else {
            int num_k = node.num_keys;
            for (int i=0; i<num_k; i++) {
                // if (node.children[i].num_keys != 0) {
                    str.append(getString(node.children[i]) + ", ");
                    str.append(node.key[i].toString() + "=" + node.value[i].toString() + ", ");
                // }
            }
            str.append(getString(node.children[num_k])+"]");
            return str.toString();
        }
    }

// package col106.a3;

// import java.util.Scanner;

// public class Main {


//     public static void main(String[] args) throws Exception {
//         Scanner s = new Scanner(System.in);
//         System.out.println("B tree size");
//         int n = s.nextInt();
//         // DuplicateBTree<String, String> tree = new BTree<>(n);
//         BTree<String, String> tree = new BTree<>(n);
//         while (true) {
//             String command = s.next();
//             try {
//                 switch (command) {
//                     case "exit":
//                         System.exit(0);
//                     case "reset" :
//                         int nt = s.nextInt();
//                         tree = new BTree<>(nt);
//                         break; 
//                     case "insert":
//                         tree.insert(s.next(), s.next());
//                         System.out.println(tree);
//                         break;
//                     case "delete":
//                         tree.delete(s.next());
//                         System.out.println(tree);
//                         break;
//                     case "print":
//                         System.out.println(tree);
//                         break;
//                     case "height":
//                         System.out.println(tree.height());
//                         break;
//                     case "empty":
//                         System.out.println(tree.isEmpty());
//                         break;
//                     case "search":
//                         System.out.println(tree.search(s.next()));
//                         break;
//                     case "print_num_keys":
//                         Node tempNode = tree.getRoot();
//                         System.out.println(tempNode.num_keys);
//                         break;
//                     case "size":
//                         System.out.println(tree.size());
//                         break;
//                     case "get":
//                         System.out.println(tree.getPredecessor(tree.getRoot(), s.nextInt()).key);
//                         break;
//                     case "gets":
//                         KeyValue k = tree.getSuccessor(tree.getRoot(), s.nextInt());
//                         System.out.println(k.key);
//                         System.out.println(k.value);
//                         break;
//                 }
//             } catch (Exception e) {
//                 System.out.println(e);
//                 StackTraceElement[] elements = e.getStackTrace();  
//                 for (int iterator=1; iterator<=elements.length; iterator++)  
//                 System.out.println("Class Name:"+elements[iterator-1].getClassName()+" Method Name:"+elements[iterator-1].getMethodName()+" Line Number:"+elements[iterator-1].getLineNumber());
//             }   
//         }
//     }
// }

}

class KeyValue<Key, Value> {
    Key key;
    Value value;
    KeyValue(Key k, Value v) {
        key = k;
        value = v;
    }
}

class Node <Key, Value> {
    int num_keys;
    Node[] children;
    Value[] value;
    Key[] key;
    boolean isLeaf;
    // int level;

    Node(int n) {
        num_keys = 0;
        children = new Node[n+1];
        // level	 = 0;

        @SuppressWarnings("unchecked")
        Key[] key 	= (Key[]) new Object[n];
        this.key 	= key;

        @SuppressWarnings("unchecked")
        Value[] val = (Value[]) new Object[n];
        this.value 	= val;

        isLeaf   	= true;

        @SuppressWarnings("unchecked")
        Node[] children = new Node[n+1];
        this.children = children;
    }
}