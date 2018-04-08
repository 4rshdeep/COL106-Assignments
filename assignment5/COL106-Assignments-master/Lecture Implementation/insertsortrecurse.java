public class insertsortrecurse
{/**
* InsertSort Using Recursion
* @version 1.0
* @author Arshdeep Singh
*/
	public static void main(String args[])
	{
		int A[] = {1, 2, 3, 4, 9, -1, 5, 99, 62};
		
		System.out.println("Before Sorting: \n");
		printarr(A);
		
		A = insertsort(A);
		System.out.println();
		System.out.println("After Sorting: \n");
		printarr(A);
	}
	
	static int[] insertsort(int[] A)
	{
		if(A.length==1) 
			return A;
		else
		{	
			int B[] = new int[A.length-1];
			for(int i=0; i<B.length; i++)
			{
				B[i] = A[i];
			}
			return insert(insertsort(B), A[A.length-1]);
		}
	}

	static int[] insert(int[] A, int key)
	{
		if(key>A[A.length-1])
		{
			return append(A, key);
		}
		if((A.length==1)&&(key<A[0]))
		{
			int[] key1 = new int[1];
			key1[0] = key;
			return append(key1, A[0]);
		}
		else
		{	
			int B[] = new int[A.length-1];
			for(int i=0; i<B.length; i++){B[i]=A[i];}
			return append(insert(B, key), A[A.length-1]);
		}
	}

	static int[] append(int[] A, int key)
	{
		int[] B = new int[A.length+1];
			for(int i=0; i<A.length; i++)
			{
				B[i] = A[i];
			} 
			B[B.length-1] = key;
		return B;
	}

	static void printarr(int[] A)
	{
		for(int i=0; i<A.length; i++)
		{
			System.out.print(A[i] +", ");
		}
	}
}