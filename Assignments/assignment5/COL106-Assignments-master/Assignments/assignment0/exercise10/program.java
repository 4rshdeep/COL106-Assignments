public class program
{
	public int test(int n, int m)
	{
		/*
		Exercise 10: Least common multiple- Given two integers n and m, the objective
		is to compute the LCM (least common multiple) of n and m. LCM is the smallest
		number that is divisble by both n amd m. For e.g. is n is 12 and m is 14, the
		LCM is 84. If n is 32 and m is 16, the LCM is 32.
		*/
		int max = (n>m)?n:m;
		int i=0;
		int lcm=0;
		for(i=max;i<=n*m;i++)
		{
			if( i%n==0 && i%m==0 )
			{
				lcm = i;
				break;
			}
		}
		return lcm;
	}

	/*public static int gethcf(int n, int m)
	{	
		//Let us arbitrarily assume n to be max and m to be minimum
		// Using Euclidean algorithm to compute HCF https://en.wikipedia.org/wiki/Euclidean_algorithm
		int max = n;
		int min = m;
		do
		{
			max = (max>min)?max:min;
			min = (max<min)?max:min;
			System.out.println("Max: " + max);
			System.out.println("Min: " + min);
			max = max-min;
			System.out.println("Max: " + max);
			System.out.println("Min: " + min);
			
		}while(max!=min);
		return max;
		
	}
	*/
}
