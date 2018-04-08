import java.util.Scanner;

public class checker 
{
	
	public static void main(String[] args)
	{
		queue q = new queue();
		

		Scanner input = new Scanner(System.in);
		int quit = 0;
		int in = -1;
		int valu = 0;

		while(quit!=1)
		{
			System.out.println("Enter 1 for enqueue,\n 2 for dequeue,\n 3 for size,\n 4 for isEmpty,\n 5 to quit \n\n\n");
			in = input.nextInt();

			System.out.println("Processing\n");
			switch(in)
			{
				case 1:
				System.out.println("Enter Integer to enqueue"); 
				valu = input.nextInt();
				q.enqueue(valu);
				q.printq();

				break;

				case 2: 
				try
				{
					q.dequeue();
					q.printq();	
				}
				catch(EmptyQueueException e) 
				{
					System.err.println(e.getMessage());
				}
				break;

				case 3:
				System.out.println(q.qsize());
				break;

				case 4:
				if(q.isEmpty())
				{
					System.out.println("Yes the queue is empty");
				}
				break;
				
				case 5:
				quit = 1;
				break;

				
			}
			System.out.println("-------------------------------------\n\n\n");
		}

	}
	
}