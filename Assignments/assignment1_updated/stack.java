public class stack implements stackinterface
{
	/**
	* Implement the following methods from the interface
	* public int size();
	* public bool isEmpty();
	* public int top() throws StackEmptyException;
	* public void push(int a) throws StackFullException;
	* public int pop() throws StackEmptyException;
	*/
	public static int lastindex;
	public static int[] stackarr;

	public stack(int n)
	{
		stackarr = new int[n];
		lastindex = -1;
	}

	public int pop() throws StackEmptyException
	{	
		if(lastindex==-1)
		{
			throw new StackEmptyException("Stack is Empty");
		}
		lastindex -= 1;
		return stackarr[lastindex+1];
	}

	public void push(int a) throws StackFullException
	{
		if(lastindex == stackarr.length-1)
		{
			throw new StackFullException("Stack is Full");
		}
		else
		{
			lastindex += 1;
			stackarr[lastindex] = a;
		}
	}

	public boolean isEmpty()
	{
		return (stackarr.length==lastindex+1); 
	}

	public int top() throws StackEmptyException
	{
		if(lastindex==-1)
		{
			throw new StackEmptyException("Stack is Empty");
		}	
		return stackarr[lastindex];
	}

	public void printstack()
	{
		for(int i=lastindex; i>=0; i--)
		{
			System.out.println(stackarr[i]);
		}
	}

	public int size()
	{
		return lastindex+1;
	}
}

class StackFullException extends Exception
{
      public StackFullException() {}

      public StackFullException(String message)
      {
         super(message);
      }
 }

class StackEmptyException extends Exception
{
      public StackEmptyException() {}

      public StackEmptyException(String message)
      {
         super(message);
      }
 }