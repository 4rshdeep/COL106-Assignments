public class program
{
	public int test(String number)
	{	
		int temp=0;
		int[] freq = new int[10];

		for(int i=0; i<number.length(); i++)
		{
			temp = Integer.parseInt(number.substring(i, i+1));
			freq[temp] += 1;

		}
		int max_index = temp;
		int max_freq = 0;
		for(int i=0; i<freq.length; i++)
		{
			if(max_freq<freq[i])
			{
				max_freq = freq[i];
				max_index = i;
			}
		}
		return max_index;
	}

	public void printarr(int[] freq)
	{
		for(int i=0; i<freq.length; i++)
		{
			System.out.println("Index : " + i + "Frequency : " + freq[i]);
			
		}

	}
}
