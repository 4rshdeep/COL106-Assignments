import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LinkedListImage implements CompressedImageInterface {

public int row;
public int col;
public Node[] arrayOfLists;
public bool invert = false;

    public LinkedListImage(String filename)
    {
    try{
        // does not put the node if the first column is 0
        File file = new File(filename);
        Scanner input = new Scanner(file);
        String templ = input.nextLine();
        String[] firstLine = templ.split("\\s+");
        row = Integer.parseInt(firstLine[0]);
        col = Integer.parseInt(firstLine[1]);
        
        arrayOfLists = new Node[row];

        int temp=0;
        int prev;
        Node[] tailPtrs = new Node[row];
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
                    if(prev==1 || j==len-1 ||j==0)  
                    {  //We need to insert if we had a one before or
                       // if it is the first column or the last one
                        Node newNode = new Node(j);

                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                            // update the tailptr
                            tailPtrs[i] = newNode;
                        }
                        else
                        {
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
                        }
                        if(j==len-1 && prev==1)
                        {
                            //Insert NewNode at last
                            newNode = new Node(j);
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
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
                            System.out.println("This occurs");
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                            tailPtrs[i] = newNode;
                        }
                        else
                        {
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
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
        Node[] tailPtrs = new Node[row];
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
                    if(prev==1 || j==width-1 ||j==0)  
                    {  //We need to insert if we had a one before or
                       // if it is the first column or the last one
                        Node newNode = new Node(j);

                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                            tailPtrs[i] = newNode;
                        }
                        else
                        {
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
                            //Insert NewNode at last
                            
                        }
                        if(j==width-1 && prev==1)
                        {
                            //Insert NewNode at last
                            newNode = new Node(j);
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
                        }
                    }
                    prev=0;
                }

                else if (grid[i][j]==true) {
                    if (prev==0) {
                        Node newNode = new Node(j-1);
                        tempNode = arrayOfLists[i];
                        if(tempNode.val==-1)
                        {
                            //System.out.println("This occurs");
                            newNode.next = arrayOfLists[i];
                            arrayOfLists[i] = newNode;
                            tailPtrs[i] = newNode;
                        }
                        else
                        {
                            newNode.next = tailPtrs[i].next;
                            tailPtrs[i].next = newNode;
                            tailPtrs[i] = newNode;
                        }
                    }
                    prev=1;
                }
            }
        }
    }


    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        // FALSE STANDS FOR ZERO AND TRUE STANDS FOR ONE 

        if(x<0 ||x > row)
        {
            throw new PixelOutOfBoundException("");
        }
        else if (y<0 || y>col) 
        {
            throw new PixelOutOfBoundException("");   
        }
        Node firstNode = arrayOfLists[x];
        Node secondNode;
        while(firstNode.val!=-1)
        {
            secondNode = firstNode.next;
            //System.out.println(y + " " + firstNode.val + " " + secondNode.val);
            if(y >= firstNode.val && y<=secondNode.val)
            {   
                 //System.out.println(false);
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

   public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        if (x > row-1 || y > col-1) {
            throw new PixelOutOfBoundException("");
        }
        try
        {    
            boolean isBlack = !getPixelValue(x, y);
            boolean black = false;
            Node rowHead = arrayOfLists[x];
            Node temp = rowHead;
            Node prevNode, tempNode;
            if(isBlack)
            {
                if(val!=false)
                {
                    // Change a zero(black) to a one(white)
                   //System.out.println("Okay you want to make it one");

                    // has one neighbour 
                    if((y==0)||(y==row-1))
                    {
                        //System.out.println("You have one neighbour");
                        //alone at start
                        if(y==0 && getPixelValue(x,y+1)==true)
                        {
                            temp = arrayOfLists[x];
                            arrayOfLists[x] = temp.next.next;
                        }
                        // has a black neighbour
                        if(y==0 && getPixelValue(x,y+1)==false)
                        {
                            arrayOfLists[x].val = y+1;
                        }
                        // alone in the end
                        if(y==row-1 && getPixelValue(x,y-1)==true)
                        {
                            temp = rowHead;
                            //if occurs at first
                            if(temp.val==y)
                            {
                                arrayOfLists[x] = new Node(-1);
                            }
                            if(temp.val!=y)
                            {
                                //System.out.println(toStringCompressed());
                                prevNode = rowHead;
                                tempNode = rowHead;
                                while(tempNode.val!=y)
                                {
                                    prevNode = tempNode;
                                    tempNode = tempNode.next;
                                }
                                prevNode.next = new Node(-1);
                                //System.out.println(toStringCompressed());
                                
                            }

                        }
                        // has a black predecessor
                        if(y==row-1 && getPixelValue(x,y-1)==false)
                        {
                            tempNode = rowHead;
                            while(tempNode.val!=y)
                            {
                                tempNode = tempNode.next;
                            }
                            tempNode.val -= 1;
                        }
                    }
                    else // has two neighbours
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
                        // corner black in a group of blacks
                        if((getPixelValue(x,y-1) == true && getPixelValue(x,y+1) == false) || (getPixelValue(x,y-1) == false && getPixelValue(x,y+1) == true))
                        {
                            //first occuring
                            if((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false))
                            {
                                //Get to the node 
                                while(temp.val!=y)
                                {
                                    //System.out.println(2);
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
                                    found = true;
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
            }
            else
            {
                if(val!=true)
                {
                    //System.out.println("So yo want to make it zero");
                    // has one neighbour 
                    if((y==0)||(y==row-1))
                    {
                        //System.out.println("You have one neighbour");
                        if(y==0)
                        {
                            if(getPixelValue(x, y+1)==true)
                            {
                                Node firstNode  = new Node(y);
                                Node secondNode = new Node(y);
                                firstNode.next  = secondNode;
                                secondNode.next = arrayOfLists[x];
                                arrayOfLists[x] = firstNode;
                            }
                            else if(getPixelValue(x, y+1)==false)
                            {
                                arrayOfLists[x].val = y;
                            }
                        }
                        else if(y==row-1)
                        {
                            if(getPixelValue(x, y-1)==false)
                            {
                                temp = rowHead;
                                while(temp.val!=y-1)
                                {
                                    temp = temp.next;
                                }
                                // In case out of the pair if there is only a single dot then the first value will change therefore we need to change the second occurence in case it is same.
                                if (temp.val == temp.next.val) 
                                {
                                    temp.next.val = y;   
                                }
                                else
                                {
                                    temp.val = y;
                                }
                                
                            }
                            // BUG changing the last node from 1 to zero when the second last is 0
                            if(getPixelValue(x, y-1)==true)
                            {
                                if(rowHead.val==-1)
                                {
                                    Node firstNode = new Node(y);
                                    Node secondNode = new Node(y);
                                    firstNode.next = secondNode;
                                    secondNode.next = arrayOfLists[x];
                                    arrayOfLists[x] = firstNode;
                                }
                                else
                                {
                                    prevNode = rowHead;
                                    tempNode = rowHead;
                                    while(tempNode.val!=-1)
                                    {
                                        prevNode = tempNode;
                                        tempNode = tempNode.next;
                                    }
                                    Node firstNode = new Node(y);
                                    Node secondNode = new Node(y);
                                    firstNode.next = secondNode;
                                    secondNode.next = tempNode;
                                    prevNode.next = firstNode;
                                }
                            }
                        }
                    }
                    else // has two neighbours
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
                            else if(rowHead.val==-1)
                            {
                                Node firstNode = new Node(y);
                                Node secondNode = new Node(y);
                                firstNode.next = secondNode;
                                secondNode.next = rowHead;
                                arrayOfLists[x] = firstNode;
                            }
                            else if(rowHead.val!=-1)
                            {
                                temp = rowHead;
                                prevNode = temp;
                                while((temp.val < y)&&(temp.val!=-1))
                                {
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
                        //If both neighbours are black


                        // If only one neighbour is black 
                        else if( ((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==true))|| ((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false)))
                        {
                            //if the left neighbour is black then increment the value in the left neighbour
                            // make 011 -> 001
                            if(((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==true)))
                            {
                                String str = toStringCompressed();
                                while(temp.val!=y-1)
                                {
                                    ////System.out.println(8);
                                    temp = temp.next;
                                }
                                //System.out.println(temp.val + " " + y);
                                // Now if there is single zero temp points to the first one
                                // otherwise it pounts to the right one
   // Thing is we would want to update the value where we have y = y-1 and we want to increment that to y since in case of unequal pair (note that every group of zero forms a pair) we have only single occurence of y so we need not check for the later value where y occurs but that is not the case with single zero since we have to take the later value we have to make an additional check on that
                                if(temp.val == temp.next.val)
                                {
                                    temp.next.val = y;
                                }
                                else
                                {
                                    temp.val = y;    
                                }
                                //System.out.println(temp.val + " " + y);
                                
                            }
                            // otherwise right neighbour is black
                            else if(((getPixelValue(x,y-1)==true) && (getPixelValue(x,y+1)==false)))
                            {
                                // find the right neighbour and decrement it's value
                                temp = rowHead;
                                while(temp.val!=y+1)
                                {
                                    //System.out.println("Temp: "+temp.val);
                                    //System.out.println("y+1: " + y+1 );
                                    //System.out.println(9);
                                    temp = temp.next;
                                }
                                // Since in any case whether there is only single 0 we donot need to make case since in any case we would want to update the first value i.e the smaller one
                                temp.val = y;
                            }
                        }

                        // Between two blacks
                        else if((getPixelValue(x,y-1)==false) && (getPixelValue(x,y+1)==false))
                        {
                            Node fptr = rowHead.next;
                            Node tptr = rowHead;
                            Node sptr = rowHead.next.next;
                            boolean found = false;
                            while(!found)
                            {
                                if((fptr.val<y)&&(sptr.val>y))
                                {
                                    tptr.next = sptr.next;
                                    found = true;
                                }
                                else
                                {
                                    tptr = sptr;
                                    fptr = sptr.next;
                                    sptr = fptr.next;
                                }
                            } 
                        }

                            //System.out.println("2 neighbours");
                    }
                }
            }
        }
        catch (PixelOutOfBoundException e) 
        {
            System.out.println("PixelOutOfBoundException");
        }
    }

    public int[] numberOfBlackPixels()
    {
        int count[] = new int[row];
    try
    {   
        
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
        
    }
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    return count;
    }
    
    public void invert()
    {
     try
     { 
        for (int i=0; i<row; i++) 
        {
            for (int j=0; j<col; j++) 
            {
                setPixelValue(i, j, !(getPixelValue(i,j)));    
            }
            
        }
    }
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
        int rowImg = ((LinkedListImage)img).row;
        int colImg = ((LinkedListImage)img).col;
        if(row!=rowImg || col!=colImg)
        {   
            throw new BoundsMismatchException("");          
        }
    try
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
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    }
    
    // typecast to use methods https://stackoverflow.com/questions/5306835/casting-objects-in-java
    // ((B)a).printFromB()
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        int rowImg = ((LinkedListImage)img).row;
        int colImg = ((LinkedListImage)img).col;
        if(row!=rowImg || col!=colImg)
        {   
            throw new BoundsMismatchException("");          
        }
    try{
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
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    }
    
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
        int rowImg = ((LinkedListImage)img).row;
        int colImg = ((LinkedListImage)img).col;
        if(row!=rowImg || col!=colImg)
        {   
            throw new BoundsMismatchException("");          
        }
    try
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
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    }
     
    public String toStringUnCompressed()
    {
        StringBuilder str = new StringBuilder(""+row+" "+ col+", \n");
        // Identified Bug when the first column contains zero in 
        // some row.
    try
    {
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
                if (j==col-1 && i!=row-1) 
                {
                    str.append(", ");    
                }
                else if((i!=row-1) || (j!=col-1))
                {    
                    str.append(" ");
                }    
                           
            }
            str.append('\n');   
            
            
        }
        
    }
    catch (PixelOutOfBoundException e) {
        System.out.println("PixelOutOfBoundException");
    }
    return str.toString();
    }
    

    
    public String toStringCompressed()
    {
        StringBuilder str = new StringBuilder(""+row+" "+col+",");

        int val = 0;
        str.append("\n");
        
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
                        str.append(", ");
                    }
                }
                else{
                    temp = temp.next;
                }
                
            }
            str.append("\n");
        
        }
        return str.toString();    
    }

public static void main(String[] args) {
    CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");
    int quit = 0;
    int in = -1;
    int x, y,z = 0;
    Scanner input = new Scanner(System.in);

    try
    {
        //System.out.println(img1.toStringCompressed());
        System.out.println(img1.toStringUnCompressed());

    while(quit != -1)
    {
        
        System.out.println("1 for getpixel, 2 for setpixel , 3 to invert");
        in = input.nextInt();
        switch(in)
        {
            case 1:
            System.out.println(img1.toStringUnCompressed());
            System.out.print("Enter row value: ");
            x = input.nextInt();
            System.out.print("Enter column value: ");
            y = input.nextInt();
            if(img1.getPixelValue(x,y)==true){System.out.println("ONE");}
            else System.out.println("ZERO");

            System.out.println();
            break;

            case 2:
            String str = img1.toStringUnCompressed();
            System.out.println("Enter row number: ");
            x = input.nextInt();
            System.out.println("Enter column number");
            y = input.nextInt();
            System.out.println("What value you want to set to 0/1");
            z = input.nextInt();
            if(z==0)
            {
                img1.setPixelValue(x, y, false);
            }
            else if(z==1)
            {
                img1.setPixelValue(x, y, true);

            }
            else
            {
                System.out.println("Invalid choice");
            }
            System.out.println("Before: \n" + str);
            System.out.println("After: \n"+img1.toStringUnCompressed());
            
            break;
            case 3:
            img1.invert();
            System.out.println(img1.toStringUnCompressed());
        

    }
    }
    }
    catch (PixelOutOfBoundException e) 
    {
     System.out.println("this");   
    }
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