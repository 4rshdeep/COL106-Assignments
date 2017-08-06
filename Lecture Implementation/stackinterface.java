public interface stackinterface
{
	public int size();
	public boolean isEmpty();
	public int top() throws StackEmptyException;
	public void push(int a) throws StackFullException;
	public int pop() throws StackEmptyException;
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