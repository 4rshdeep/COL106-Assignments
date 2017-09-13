public class queue
{	
	public static Node head = new Node();
	public static Node tail = new Node();

	public static int size = 0;

	public void enqueue(int value)
	{
		Node temp = new Node(value);
		if(size==0)
		{
			tail = temp;
		}
		temp.next = head;
		head = temp;
		size += 1;
	}

	public int dequeue() throws EmptyQueueException
	{
		if(isEmpty())
		{
			throw new EmptyQueueException("Queue is Empty");
		}
		int temp = tail.val;
		tail = tail.prev;
		size -= 1;
		return temp;
	}

	public int qsize()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return (size==0);
	}

	public void printq()
	{
		Node temp = head;
		int i=0;
		while(i<size)
		{
			System.out.println(temp.val+"\n");
			temp = temp.next;
			i++;
		}
	}
}

class Node
{	
	int val;
	Node next;
	Node prev;
	public Node(int a)
	{
		val = a;
		next = null;
		prev = null;
	}
	public Node()
	{
		this(0);
	}

}

class EmptyQueueException extends Exception
{
	public EmptyQueueException(){}

	public EmptyQueueException(String message)
	{
		super(message);
	}
}