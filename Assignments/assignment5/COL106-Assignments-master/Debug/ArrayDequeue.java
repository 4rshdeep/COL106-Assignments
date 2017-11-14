import java.util.Scanner;


public class ArrayDequeue implements DequeInterface 
{
  /* Using array based deque
  * headIndex : index of the first element
  * rearIndex : index next to the last element
  */
  Object[] arrayDeq;
  int headIndex;
  int rearIndex;
  int size;

  public ArrayDequeue()
  {
    arrayDeq = new Object[1];
    headIndex = -1;
    rearIndex = 0;  
    size = 0;
  }
  // Maintaining gap of one between headIndex and rearIndex
  public void insertFirst(Object o)
  {
    int arrLen = arrayDeq.length;

    if((arrLen-size) == 1)
    {    
      int front  = headIndex;
      // Creating an array of size two times the previous one
      Object[] temp = new Object[2*arrLen];
      temp[0] = o;
      // After putting the new value at front copy the rest
      for(int i=1; i<=size; i++)
      {
        temp[i] = arrayDeq[front];
        front = (front+1)%arrLen;
      }
      // Assign the array and increment the size
      arrayDeq = temp;
      size += 1;
      // Change the head and the rear indexes
      headIndex = 0;
      rearIndex = size;
    }
    else
    {
      // Decrement the headIndex, put at head, increase the size
      int N = arrayDeq.length;
      headIndex = (headIndex+N-1)%N;
      arrayDeq[headIndex] = o;
      size+=1;
    }
  }
  
  public void insertLast(Object o)
  {
    //Insert at last and update the size and the indices
    int arrLen = arrayDeq.length;
    if((arrLen-size)==1)
    {
      //Double the array copy and put o at last and decrement size
      int frontIndex = headIndex;
      Object[] temp = new Object[2*arrLen];

      for(int i=0; i<size; i++)
      {
        temp[i] = arrayDeq[frontIndex];
        frontIndex = (frontIndex+1)%arrLen;
      }
      
      temp[size] = o;
      arrayDeq = temp;

      size += 1;
      headIndex = 0;
      rearIndex = size;

    }
    else
    {
      int N = arrayDeq.length;
      arrayDeq[rearIndex] = o;
      rearIndex = (rearIndex+1)%N;
      size += 1;
    }
  }
  
  public Object removeFirst() throws EmptyDequeException
  {
    if(size==0)
    {
      throw new EmptyDequeException("");
    }
    else
    {
      int temp = headIndex;
      headIndex = (headIndex+1)%arrayDeq.length;
      size -= 1;
      return arrayDeq[temp];
    }
  }
  
  public Object removeLast() throws EmptyDequeException
  {
    if(size==0)
    {
      throw new EmptyDequeException("");
    }
    else
    {
      int arrLen = arrayDeq.length;
      rearIndex = (rearIndex+arrLen-1)%arrLen;
      size -= 1;
      return arrayDeq[rearIndex];
    }
  }
  public Object first() throws EmptyDequeException
  {
    if(size==0)
    {
      throw new EmptyDequeException("");
    }
    else
    {
      return arrayDeq[headIndex];
    }
  }
  
  public Object last() throws EmptyDequeException
  {
    int last = (rearIndex+arrayDeq.length-1)%arrayDeq.length;
    if(size==0)
    {
      throw new EmptyDequeException("");    
    }
    else
    {
      return arrayDeq[last]; 
    }
  }
  
  public int size()
  {
    int N = arrayDeq.length;
    return ((rearIndex-headIndex+N)%N);
  }
  public boolean isEmpty()
  {
    return (size==0);
  }
  public String toString()
  {
    if(size==-1)
    {
      return "[]";
    }
    else
    {
      StringBuilder str = new StringBuilder("[");
      int f = headIndex;
      for(int i=0; i<size; i++)
      {
        str.append(arrayDeq[f]);
        if(i==size-1)
        {
          str.append("]");
        }      
        else
        {
          str.append(",");
        }
        f = (f+arrayDeq.length+1)%arrayDeq.length;
      }
      if(size==0)
      {
        str.append("]");  
      }
      return str.toString();
    }
  }
    
  
  public static void main(String[] args){
    DequeInterface myDeque = new ArrayDequeue();
    Scanner input = new Scanner(System.in);
    int quit = 0;
    int in = -1;
    int val = 0;
    while(quit!=1)
    {
      System.out.println("Enter 1 for insertFirst,\n2 for insertLast,\n3 for removeFirst,\n4 for removeLast,\n5 for first,\n6 for last,\n7 for size,\n8 for isEmpty, \n9 for toString,\n10 to end operations of the dequeue\n\n\n");
      in = input.nextInt();
      System.out.println("Processing\n");
      switch(in)
      {
        case 1:
        System.out.println("Enter Number to Insert First: ");
        val = input.nextInt();
        myDeque.insertFirst(val);
        System.out.println("To String \n");
        System.out.println(myDeque.toString());
        break;

        case 2:
        System.out.println("Enter integer to Insert Last: ");
        in = input.nextInt();
        System.out.println("\n");
        myDeque.insertLast(in);
        System.out.println("Printing Dequeue\n");
        System.out.println(myDeque.toString());
        break;

        case 3:
        try{
          System.out.println("Removing From Head\n ");
          myDeque.removeFirst();
          System.out.println("Printing Dequeue\n");
          System.out.println(myDeque.toString());
        }
        catch(EmptyDequeException e)
        {
          System.out.println("Deque is empty");
        }
        break;

        case 4:
        try{
          System.out.println("Removing From Tail\n ");
          myDeque.removeLast();
          System.out.println("Printing Dequeue\n");
          System.out.println(myDeque.toString());
        }
        catch(EmptyDequeException e)
        {
          System.out.println("Deque is empty");
        }
        break;

        case 5:
        try
        {
        System.out.println("Top element of the stack is: "+myDeque.first());
        }
        catch(EmptyDequeException e)
        {
          System.out.println("Deque is Empty");
        }
        break;

        case 6:
        try
        {
        System.out.println("Last element of the stack is: " + myDeque.last());
        }
        catch(EmptyDequeException e)
        {
          System.out.println("Deque is Empty");
        }
        break;

        case 7:
        System.out.println("Size of the deque is " + myDeque.size());
        break;

        case 8:
        if(myDeque.isEmpty())
        {
          System.out.println("Deque is Empty");
        }
        break;

        case 9:
        System.out.println("Printing out the deque");
        System.out.println(myDeque.toString());
        break;

        case 10:
        quit=1;
        break;
      }
      System.out.println("-------------------------------------\n\n\n");
    }

  }
  
}