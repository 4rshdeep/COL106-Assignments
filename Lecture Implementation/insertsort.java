public class insertsort
{
	public static void main(String args[])
	{
		int A[] = {1, 2, 3, 4, 9, -1, 5, 99, 62};
		
		System.out.println("Before Sorting: \n");
		printarr(A);

		int key = 0, i=0;
		for(int j=1; j<A.length; j++)
		{
			key = A[j];
			i = j-1;

			while((i>=0)&&(A[i]>key))
			{	
				A[i+1] = A[i];
				i--;
			}
			A[i+1] = key;
		}
		System.out.println();
		System.out.println("After Sorting: \n");
		printarr(A);
	}
	static void printarr(int[] A)
	{
		for(int i=0; i<A.length; i++)
		{
			System.out.print(A[i] +", ");
		}
	}
}