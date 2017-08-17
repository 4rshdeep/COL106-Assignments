import java.util.Scanner;
public class FabricBreakup
{
	public static int[] clothesArray;
	public static int[] favArray;
	public static int favShirtIndex;
	public static int top;
	public static int maxLiking;

	/*
	*clothes array stores liking value of the clothes, pretty useless
	*favArray stores the maximum liking in case the max liked is removed update the max liked beneath that cloth
	*favShirtIndex is the last index of favarray and top is the last in the clothes array
	*favarray stores the index value of the max liked cloth and can be found by favArray[favShirtIndex]
	*/

	public FabricBreakup(int n)
	{
		clothesArray  = new int[n+1];
		favArray      = new int[n+1];
		favShirtIndex = -1;
		top = -1;
		maxLiking = -1;
	}

	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		int id;
		int mode;
		int temp;
		int temp1;
		int likingValue;
		//System.out.println("Num of Operations");
		int in = input.nextInt();
		//System.out.println("Number of Operations is: " + in);
		FabricBreakup fb = new FabricBreakup(in+1);

		for(int i=0; i<in; i++)
		{
			id 	 = input.nextInt();
			//System.out.println("id: " + id);
			mode = input.nextInt();
			//System.out.println("mode: "+ mode);
			//mode 1 to put and mode 2 to party
			if(mode==1)
			{
				//put shirt in stack
				likingValue = input.nextInt();
				//System.out.println("Liking Value is " + likingValue);
				if(likingValue>=maxLiking)
				{
					//System.out.println("Greater than Max Liking");
					favShirtIndex += 1;
					top += 1;
					clothesArray[top] = likingValue;
					favArray[favShirtIndex] = top;
					maxLiking = likingValue;
				}
				else
				{	
					//System.out.println("Lesser than Max Liking");
					top += 1;
					clothesArray[top] = likingValue;
				}
			}
			else
			{
				if(top == -1)
				{
					System.out.println(id +" "+ -1);
				}
				else
				{
					//remove the favourite shirt and count how many shirts were kept back into the heap
					temp1 = top;
					top = favArray[favShirtIndex] - 1;
					temp = favArray[favShirtIndex];
					favShirtIndex -= 1;
					if(favShirtIndex==-1)
					{
						maxLiking=-1;
					}
					temp = temp1-temp;
					//System.out.println("Renoving Shirts, taking out the favourite one");
					//System.out.println("Total of " + temp + " shirts removed.");
					System.out.println(id + " " + temp);
				}
			}
			/*for(i=0; i<=top; i++)
			{
				System.out.print(clothesArray[i] + ", ");
			}
			System.out.println();*/
		}
	}
}