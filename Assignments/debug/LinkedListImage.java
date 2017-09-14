import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LinkedListImage implements CompressedImageInterface {

public static int row;
public static int col;
public static Node[] arrayOfLists; 

	public LinkedListImage(String filename)
	{
    try{
        File file = new File(filename);
        Scanner input = new Scanner(file);
        String templ = input.nextLine();
        String[] firstLine = templ.split("\\s+");
        row = Integer.parseInt(firstLine[0]);
        col = Integer.parseInt(firstLine[1]);
        
        arrayOfLists = new Node[row];

        int temp=0;
        int prev;
        int black=0;

        // Insert a -1 first to denote end of the row
        for(int i=0; i<row; i++)
        {
            Node tempNode = new Node(-1);
            arrayOfLists[i] = tempNode;
        }

         String tempString;

        // System.out.println("Next Line  " + tempString);
        Node tempNode, prevNode;
        for(int i=0;  i<row; i++)
        {
            //stack st = new stack(row+col);
            tempString = input.nextLine();
            //System.out.print(i + ": ");
            tempString = tempString.replaceAll("\\s+","");
            //System.out.println(tempString);

            prev = -1;
            for (int j=0; j<tempString.length(); j++) 
            {
                if (tempString.substring(j, j+1).equals("0")) {
                    if(prev==1) 
                    {
                        Node newNode = new Node(j);

                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                        }
                        prevNode = tempNode;
                        while(tempNode.val!=-1)
                        {
                            prevNode = tempNode;
                            tempNode = tempNode.next;
                        }
                        prevNode.next = newNode;
                        newNode.next = tempNode;
                    }
                    prev=0;
                }
                else if (tempString.substring(j, j+1).equals("1")) {
                    if (prev==0) {
                        Node newNode = new Node(j-1);
                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                        }
                        prevNode = tempNode;
                        while(tempNode.val!=-1)
                        {
                            prevNode = tempNode;
                            tempNode = tempNode.next;
                        }
                        prevNode.next = newNode;
                        newNode.next = tempNode;
                    
                    }
                    prev=1;
                }

            }
               // if the input is 0
               /*System.out.print(temp);
               if(temp == 0){
                   Node newNode = new Node(i);
                   newNode.next = arrayOfLists[i];
                   arrayOfLists[i] = newNode;
                   prev = i;
                   black=1;
               }
               // if the input is 1 and the prev element was 0(black)
               else if (temp == 1 && black == 1) {
                   Node newNode = new Node(prev);
                   newNode.next = arrayOfLists[i];
                   arrayOfLists[i] = newNode;
                   black = 0;
               }*/
               
        }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    public LinkedListImage(boolean[][] grid, int width, int height)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public boolean getPixelValue(int x, int y)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setPixelValue(int x, int y, boolean val)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int[] numberOfBlackPixels()
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    
    public void invert()
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performAnd(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performOr(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performXor(CompressedImageInterface img)
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    
    public String toStringUnCompressed()
    {
		//you need to implement this
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    //"16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1,
    // 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1"
    public String toStringCompressed()
    {
        StringBuilder str = new StringBuilder(""+row+" "+col+",");
        int val = 0;
        for (int i=0; i<row; i++) {
            Node temp = arrayOfLists[i];
            val = 0;
            while(val != -1)
            {
                str.append(" " + temp.val);
                if(temp.val==-1)
                {
                    val = -1;
                    if(i!=row-1)
                    {
                        str.append(",");
                    }
                }
                else{
                    temp = temp.next;
                }
                
            }
        }
        return str.toString();    
    }


public static void main(String[] args) {
    CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");
    System.out.println(img1.toStringCompressed());

}

}

class Node{
    int val;
    Node next;
    public Node(int i)
    {
        val = i;
        next = null;
    }
    public Node()
    {
        this(0);
    }
}