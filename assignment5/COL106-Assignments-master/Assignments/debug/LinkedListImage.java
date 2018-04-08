import java.util.Scanner;import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LinkedListImage implements CompressedImageInterface {

public int row;
public int col;
public Node[] arrayOfLists; 

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
            int len = tempString.length();
            for (int j=0; j<len; j++) 
            {   // if the jth character is 0
                if (tempString.substring(j, j+1).equals("0")) {
                    if(prev==1 || j==len-1) 
                    {
                        Node newNode = new Node(j);

                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                        }
                        else
                        {
                            prevNode = tempNode;
                            while(tempNode.val!=-1)
                            {
                                prevNode = tempNode;
                                tempNode = tempNode.next;
                            }
                            prevNode.next = newNode;
                            newNode.next = tempNode;
                        }
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
                        else
                        {
                            prevNode = tempNode;
                            while(tempNode.val!=-1)
                            {
                                prevNode = tempNode;
                                tempNode = tempNode.next;
                            }
                            prevNode.next = newNode;
                            newNode.next = tempNode;
                        }
                    }
                    prev=1;
                }
            }
        }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    public LinkedListImage(boolean[][] grid, int width, int height)
    {
        int prev = -1;
        row = height;
        col = width;
        Node tempNode, prevNode;
        arrayOfLists = new Node[row];

        for(int i=0; i<row; i++)
        {
            tempNode = new Node(-1);
            arrayOfLists[i] = tempNode;
        }

        for(int i=0; i<height; i++)
        {
            for (int j=0; j<width; j++) 
            {
                if (grid[i][j] == false) 
                {
                    if(prev==1 || j==width-1) 
                    {
                        Node newNode = new Node(j);

                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                        }
                        else 
                        {
                            prevNode = tempNode;
                            while(tempNode.val!=-1)
                            {
                                prevNode = tempNode;
                                tempNode = tempNode.next;
                            }
                            prevNode.next = newNode;
                            newNode.next = tempNode;
                        }
                    
                    }
                    prev=0;
                }
                else if (grid[i][j]==true) {
                    if (prev==0) 
                    {
                        Node newNode = new Node(j-1);
                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                        }
                        else 
                        {
                            prevNode = tempNode;
                            while(tempNode.val!=-1)
                            {
                                prevNode = tempNode;
                                tempNode = tempNode.next;
                            }
                            prevNode.next = newNode;
                            newNode.next = tempNode;
                        }
                    }
                    prev=1;
                }
            }
        }
    }

    public boolean getPixelValue(int x, int y)
    {
        // FALSE STANDS FOR ZERO AND TRUE STANDS FOR ONE 

        Node firstNode = arrayOfLists[x];
        Node secondNode;
        while(firstNode.val!=-1)
        {
            secondNode = firstNode.next;

            if(y >= firstNode.val && y<=secondNode.val)
            {
                return false;
            }

            firstNode = secondNode.next;
            if(firstNode.val!=-1)
            {
                // maybe I don't need this because second node is updated after the while loop 
                // Or maybe I do what if the while loop quits
                // let's leave it here for now
                secondNode = secondNode.next.next;
            }
        }

        return true;
    }

    public void setPixelValue(int x, int y, boolean val)
    {
        boolean black = getPixelValue(x, y);
        Node rowHead = arrayOfLists[x];
        Node temp = rowHead;
        Node prevNode;
        //if the pixel is black
        if(!black)
        {
            // Do i want ot set it black or white 
            // I have to change only when 
            // I want to set it as white
            if(val==true)
            {
                //lonely black
                if((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==true))
                {
                    //occurs at the start then update the head
                    if(rowHead.val == y)
                    {
                        arrayOfLists[x] = rowHead.next.next;
                    }
                    else
                    {   
    
                        prevNode = temp;
                        while(temp.val!=y)
                        {
                            prevNode = temp;
                            temp = temp.next;
                        }
                        prevNode.next = temp.next.next;
                    }
                }
                //corner black
                if((getPixelValue(x,y-1) == true && getPixelValue(x,y+1) == false) || (getPixelValue(x,y-1) == false && getPixelValue(x,y+1) == true))
                {
                    //first occuring
                    if((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false))
                    {
                        //Get to the node 
                        while(temp.val!=y)
                        {
                            System.out.println(2);
                            temp = temp.next;
                        }
                        // Change the value in the node
                        temp.val = y+1;
                    }
                    else
                    {
                        while(temp.val!=y)
                        {
                            temp = temp.next;
                        }
                        temp.val = y-1;
                    }
                }
    
                //middle black
                if((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==false))
                {
                    Node firstNode, secondNode;
                    firstNode = rowHead;
                    secondNode = firstNode.next;
                    boolean found = false;
                    while(!found)
                    {
                        if(y >= firstNode.val && y<=secondNode.val)
                        {
                            //put y-1 y+1 nodes in between
                            Node afterFirst = new Node(y-1);
                            Node beforeSecond = new Node(y+1);
                            firstNode.next = afterFirst;
                            afterFirst.next = beforeSecond;
                            beforeSecond.next = secondNode;
                        }
                        else // traverse until you find
                        {
                            firstNode = secondNode.next;    
                            secondNode = secondNode.next.next;                
                        }
                    }
                }
            }
        }
        // otherwise it would be white
        else 
        {
            if(val==false)
            {
                // No surrounding black
                if((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==true))
                {
                    // check for the next node value
                    if(rowHead.val > y)
                    {
                        //create two nodes for the y and insert this would work since there are no surrounding blacks
                        Node firstNode = new Node(y);
                        Node secondNode = new Node(y);
                        secondNode.next = arrayOfLists[x];
                        firstNode.next = secondNode;
                        arrayOfLists[x] = firstNode;
                    }
                    else
                    {
                        temp = rowHead;
                        prevNode = temp;
                        while(!(temp.val > y))
                        {
                            if (temp.val==-1)
                            {
                                break;
                            }
                            prevNode = temp;
                            temp = temp.next;
                        }
                        // put two nodes after prevNode and before temp
                        Node firstNode = new Node(y);
                        Node secondNode = new Node(y);
    
                        prevNode.next = firstNode;
                        firstNode.next = secondNode;
                        secondNode.next = temp;
                    }
    
                }
    
                ///FALSE => 0 => Black
    
                // One neighbour is black 
                else if( ((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==true))|| ((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false)))
                {
                    //if the left neighbour is black then increment the value in the left neighbour
                    if(((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==true)))
                    {
                        while(temp.val!=y-1)
                        {
                            System.out.println(8);
                            temp = temp.next;
                        }
                        temp.val = y+1;
                    }
                    // otherwise right neighbour is black
                    else if(((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false)))
                    {
                        // find the right neighbour and decrement it's value
                        temp = rowHead;
                        while(temp.val!=y+1)
                        {
                            System.out.println("Temp: "+temp.val);
                            System.out.println("y+1: " + y+1 );
                            System.out.println(9);
                            temp = temp.next;
                        }
                        temp.val = y-1;
                    }
                }
            }
        }  
    }

    public int[] numberOfBlackPixels()
    {
        int count[] = new int[row];
        for( int i=0; i<row; i++)
        {
            for (int j=0; j<col; j++) 
            {
                if(getPixelValue(i,j) == false)
                {
                    count[i] += 1;
                }    
            }
        }
        return count;
    }
    
    public void invert()
    {
        for (int i=0; i<row; i++) 
        {
            for (int j=0; j<col; j++) 
            {
                setPixelValue(i, j, !(getPixelValue(i,j)));    
            }
            
        }
    }
    
    public void performAnd(CompressedImageInterface img)
    {
        for (int i=0; i<row; i++) 
        {
            for (int j=0; j<col; j++) 
            {
                if((getPixelValue(i,j)==true) && (((LinkedListImage)img).getPixelValue(i,j)==true))
                {
                    setPixelValue(i,j, true);
                }
                else 
                {
                    setPixelValue(i, j, false);    
                }
            }
            
        }
    }
    
    // typecast to use methods https://stackoverflow.com/questions/5306835/casting-objects-in-java
    // ((B)a).printFromB()
    public void performOr(CompressedImageInterface img)
    {
        for (int i=0; i<row; i++) 
        {
            for (int j=0; j<col; j++) 
            {
                if((getPixelValue(i,j)==true) || (((LinkedListImage)img).getPixelValue(i,j)==true))
                {
                    setPixelValue(i,j, true);
                }
                else
                {
                    setPixelValue(i, j, false);
                }
            }    
        }
    }
    
    public void performXor(CompressedImageInterface img)
    {
        for (int i=0; i<row; i++) 
        {
            for (int j=0; j<col; j++) 
            {
                if((getPixelValue(i,j)==true) && (((LinkedListImage)img).getPixelValue(i,j)==false))
                {
                    setPixelValue(i,j, true);
                }  
                else if(getPixelValue(i,j)==false && ((LinkedListImage)img).getPixelValue(i,j)==true)
                {
                    setPixelValue(i,j, true);
                }
                else
                {
                    setPixelValue(i,j,false);
                }                    
            }    
        }
    }
    
    public String toStringUnCompressed()
    {
        StringBuilder str = new StringBuilder(""+row+" "+ col+",");
        boolean finished;
        Node rowHead;
        for(int i=0; i<row; i++)
        {
            finished = false;
            rowHead = arrayOfLists[i];
            for (int j=0; j<col; j++) 
            {
                if(getPixelValue(i,j)==false)
                {
                    str.append("0");
                }
                else
                {
                    str.append("1");
                }
                str.append("\n");
            }
        }
        return str.toString();
    }
    

    
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