import java.util.Scanner;
public class stackll 
{
	public static Node head = new Node();

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int quit = 0;
		int in = -1;
		stackll st = new stackll();
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
				System.out.println("Enter integer to push: ");
				in = input.nextInt();
				System.out.println("\n");
				st.push(in);
				System.out.println("Printing Stack\n");
				st.printstack();				
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


	public static int pop() throws StackEmptyException
	{
		if(head==null)
		{
			throw new StackEmptyException("Stack is Empty");
		}
		Node temp = head;
		int val = temp.val;
		head = temp.next;
		temp = null;
		return val;
	}

	public static void push(int a) 
	{
		Node first = new Node();
		first.val = a;
		first.next = head;
		head = first;
	}

	public static int size()
	{
		Node temp = head;
		int count=0;
		while(temp!=null)
		{
			count += 1;
			temp = temp.next;
		}
		return count;
	}

	public static int top()
	{
		return head.val;
	}

	public static void printstack()
	{	
		Node temp = head;
		while(temp!=null)
		{
			System.out.println(temp.val);
			temp = temp.next;

		}
	}

}

class Node 
{
	int val;
	Node next;
}


class StackEmptyException extends Exception
{
      public StackEmptyException() {}

      public StackEmptyException(String message)
      {
         super(message);
      }
 }