import java.util.List;
import java.util.Vector;
import java.util.Collections;
import java.util.Arrays;

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
        list.removeAll(Collections.singleton(null));
        Value[] vals = (Value[]) (list.toArray());
        Collections.reverse(list);
		return list;
    }

    /* return list can have null values */
    public List<Value> getSearch(Key key, Node node) {
        List<Value> list = new Vector<Value>();
        if (node == null) {
        	return null;
        }
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
    	return getSearch(key, node.children[n+1]);
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
		else {
			single_pass_delete(key, root);
		}
    }

 //    public void single_pass_delete(Key key, Node node) throws IllegalKeyException {
 //    	if (node.isLeaf) {
 //    		int n = node.num_keys;
	//     	int i = 0;
	//    		int j = 0;
	//    		while (i<n) {
	//    			// find first occuurence of the key
	//    			if (key.compareTo((Key)node.key[i])==0) {
	//    				// copy i+1 in i and reduce num_keys
	//    				// if i is not the last key
	//    				if (i!=n-1) {
	//    					if (((Key)node.key[n-1]).compareTo(key)==0){
	//    						node.num_keys = i;
	//    						return;
	//    					}
	//    					//duplicates are not present from i to n-1 since n-1 is not same				
	//    					j = i;
	//    					// delete duplicates
	//    					while((j>=i)&&(j<n-1)) {
	//    						j = j+1;
	//    						if (((Key)node.key[j]).compareTo(key)!=0) {
	//    					// this means they are equal till j-1th index
	//    							j = j-1;
	//    							break;
	//    						}
	//    					}
	//    					// i to j indices contain duplicates 
	//    					// replace i by j+1
	//    					node.num_keys = node.num_keys - (j-i+1);
	//    					for (int k=j+1; k<n; k++) {
	//    						node.value[i] = node.value[k];
	//    						node.key[i]	  = node.key[k];
	//    						i = i+1;
	//    					}
	//    				}
	//    				// if i is the last key
	//    				else if (i == n-1) {
	//    					node.num_keys = node.num_keys -1;
	//    				}
	//    				return;
	//    			}
	//    			i = i+1;
	//    		}
	//    		return;
    		
 //    	}
 //    	else {
 //    		int j = 0;
 //    		int i = 0;
 //    		int n = node.num_keys;
 //    		Key tempKey = (Key) new Object();
 //    		while(i<n) {
 //    			// if key is in the internal node		
 //    			if (key.compareTo((Key)node.key[i])==0) {
	// // Between any two duplicates anything would be duplicate     				
 //    				// if the last key is same 
 //    				if (((Key)node.key[n-1]).compareTo(key)==0){
    					
 //    					if (node.children[i].num_keys>t-1) {
 //    						tempKey = getPredecessor(i, node);    						
 //    					}
 //    					else if (node.children[i+1].num_keys>t-1) {
 //    						tempKey = getSuccessor(i, node);
 //    					}
 //    					// delete current keys
 //    					//delete key in node.children[i];
 //    					//delete key in	node.children[n];
 //    				}

 //    				j = i;
 //    				// delete duplicates
 //    				while((j>=i)&&(j<n-1)) {
 //    					j = j+1;
 //    					if (((Key)node.key[j]).compareTo(key)!=0) {
 //    				// this means they are equal till j-1th index
 //    						j = j-1;
 //    						break;
 //    					}
 //    				}
 //    				// everything from index i to j-1 are duplicates of the keys including the children

 //    			}
 //    			//or it is not in the internal node
 //    			else {
    				
 //    			}
 //    		}
    		
 //    	}
 //    }

    // public void delete_from_leaf(Key key, Node node) throws IllegalKeyException {
    // 	int n = node.num_keys;
    // 	int i = 0;
   	// 	int j = 0;
   	// 	while (i<n) {
   	// 		// find first occuurence of the key
   	// 		if (key.compareTo((Key)node.key[i])==0) {
   	// 			// copy i+1 in i and reduce num_keys
   	// 			// if i is not the last key
   	// 			if (i!=n-1) {
   	// 				if (((Key)node.key[n-1]).compareTo(key)==0){
   	// 					node.num_keys = i;
   	// 					return;
   	// 				}
   	// 				//duplicates are not present from i to n-1 since n-1 is not same				
   	// 				j = i;
   	// 				// delete duplicates
   	// 				while((j>=i)&&(j<n-1)) {
   	// 					j = j+1;
   	// 					if (((Key)node.key[j]).compareTo(key)!=0) {
   	// 				// this means they are equal till j-1th index
   	// 						j = j-1;
   	// 						break;
   	// 					}
   	// 				}
   	// 				// i to j indices contain duplicates 
   	// 				// replace i by j+1
   	// 				node.num_keys = node.num_keys - (j-i+1);
   	// 				for (int k=j+1; k<n; k++) {
   	// 					node.value[i] = node.value[k];
   	// 					node.key[i]	  = node.key[k];
   	// 					i = i+1;
   	// 				}
   	// 			}
   	// 			// if i is the last key
   	// 			else if (i == n-1) {
   	// 				node.num_keys = node.num_keys -1;
   	// 			}
   	// 			return;
   	// 		}
   	// 		i = i+1;
   	// 	}
   	// 	return;
    // }

    // public void delete_from_internal(Key key, Node node) {

    // }

    // public Key getPredecessor(int i, Node node) {
    // 	Node tempNode =  node.children[i];
    // 	Node n 		  = tempNode.num_keys;
    // 	return tempNode.key[n-1];
    // }

    // public Key getSuccessor(int i, Node node) {
    // 	Node tempNode =  node.children[i+1];
    // 	return tempNode.key[0];
    // }

    // public void fill_from_prev(Key key, Node node) {

    // }

    // public void fill_from_next(Key key, Node node) {

    // }

    // public void merge_down(Key key, Node node) {

    // }


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
                str.append(getString(node.children[i]) + ", ");
                str.append(node.key[i].toString() + "=" + node.value[i].toString() + ", ");
            }
            str.append(getString(node.children[num_k])+"]");
            return str.toString();
        }
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