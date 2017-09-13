import java.util.Scanner;
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

	public static void main(String args[]) throws StackFullException, StackEmptyException
	{
		Scanner input = new Scanner(System.in);
		int quit = 0;
		int in = -1;
		stack st = new stack(100);
		while(quit!=1)
		{
			System.out.println("Enter 1 for pop,\n 2 for push,\n 3 for size,\n 4 for top,\n 5 for printing stack,\n 7 to end operations of the stack\n\n\n");
			in = input.nextInt();

			System.out.println("Processing\n");
			switch(in)
			{
				case 1: 
				try
				{
					System.out.println("Popping..\n");
					st.pop();
					System.out.println("Printing Stack\n");
					st.printstack();
				}
				catch(StackEmptyException e)
				{
					System.err.println(e.getMessage());
				}				
				break;

				case 2: 
				try
				{
					System.out.println("Enter integer to push: ");
					in = input.nextInt();
					System.out.println("\n");
					st.push(in);
					System.out.println("Printing Stack\n");
					st.printstack();
				}
				catch(StackFullException e)
				{
					System.err.println(e.getMessage());
				}
				
				break;

				case 3:
				System.out.println("Size is: " + st.size());
				break;

				case 4:
				System.out.println("Top element of the stack is: "+st.top());
				break;

				case 5:
				System.out.println("Printing Stack...");
				st.printstack();
				break;

				case 7:
				quit=1;
				break;
			}
			System.out.println("-------------------------------------\n\n\n");
		}
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

	public int top()
	{
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