import java.util.Scanner;
public class arraymax
{
	public static void main(String args[])
	{
		int A[] = {1, 2, 3, 4, 9, -1, 5, 99, 62};

		int max = 0;
		for(int i=0; i<A.length; i++)
		{
			if(A[i]>max)
			{
				max = A[i];
			}
		}
		System.out.println("Iterative Method says: " + max);

		max = amax(A);
		System.out.println("Recursive Method says: "+max);
	}

	static int amax(int A[])
	{
		if(A.length == 1)
		{
			return A[0];
		}
		else
		{	
			int B[] =  new int[A.length-1];
			for(int i=0; i<A.length-1; i++)
			{
				B[i] = A[i];
			}
			int C = amax(B);
			return Math.max(C, A[A.length-1]);
		}
	}
}