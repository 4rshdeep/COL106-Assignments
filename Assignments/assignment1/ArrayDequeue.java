
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
      
      temp[frontIndex] = o;
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
      return arrayDeq[rearIndex];
    }
  }
  public Object first()
  {
    return arrayDeq[headIndex];
  }
  
  public Object last()
  {
    int last = (rearIndex+arrayDeq.length-1)%arrayDeq.length;
    return arrayDeq[last];    
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
      return str.toString();
    }
  }
  
  
  public static void main(String[] args){
    int  N = 10;
    DequeInterface myDeque = new ArrayDequeue();
    for(int i = 0; i < N; i++) {
      myDeque.insertFirst(i);
      myDeque.insertLast(-1*i);
    }
   
    int size1 = myDeque.size();
    System.out.println("Size: " + size1);
    System.out.println(myDeque.toString());
    
    if(size1 != 2*N){
      System.err.println("Incorrect size of the queue.");
    }
    
    //Test first() operation
    try{
      int first = (int)myDeque.first();
      int size2 = myDeque.size(); //Should be same as size1
      if(size1 != size2) {
        System.err.println("Error. Size modified after first()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //Remove first N elements
    for(int i = 0; i < N; i++) {
      try{
        int first = (Integer)myDeque.removeFirst();
      }
      catch (EmptyDequeException e) {
        System.out.println("Cant remove from empty queue");
      }
      
    }
    
    
    int size3 = myDeque.size();
    System.out.println("Size: " + myDeque.size());
    System.out.println(myDeque.toString());
    
    if(size3 != N){
      System.err.println("Incorrect size of the queue.");
    }
    
    try{
      int last = (int)myDeque.last();
      int size4 = myDeque.size(); //Should be same as size3
      if(size3 != size4) {
        System.err.println("Error. Size modified after last()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }
    
    //empty the queue  - test removeLast() operation as well
    while(!myDeque.isEmpty()){
        try{
          int last = (int)myDeque.removeLast();
        }
        catch (EmptyDequeException e) {
          System.out.println("Cant remove from empty queue");
        }
    }
    
    int size5 = myDeque.size();
    if(size5 != 0){
      System.err.println("Incorrect size of the queue.");
    }
    
  }
  
}