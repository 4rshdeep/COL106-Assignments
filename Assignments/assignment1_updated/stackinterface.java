public interface stackinterface
{
	public int size();
	public boolean isEmpty();
	public int top() throws StackEmptyException;
	public void push(int a) throws StackFullException;
	public int pop() throws StackEmptyException;
}