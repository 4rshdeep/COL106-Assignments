public class program
{
	public int test(char key[], char answer[])
	{
		
		int sum = 0;
		for(int i=0; i<key.length; i++)
		{
			if(answer[i]=='?')
			{
				//Pass
			}
			else
			{
				if(answer[i]==key[i])
				{
					sum += 4;
				}
				else
				{
					sum -= 1;
				}
			}
		}
		return sum;
	}
}
