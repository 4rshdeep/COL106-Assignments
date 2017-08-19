import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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
		//if(args.length>0)
		//
		try
		{
			int id;
			int mode;
			int temp;
			int temp1;
			int likingValue;
			File file = new File(args[0]);
			Scanner input = new Scanner(file);
			
			//}
			//else
			//{
			//	Scanner input = new Scanner(System.in);
			//}
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

						//System.out.println(likingValue + " is Greater than Max Liking " + maxLiking);
						favShirtIndex += 1;
						top += 1;
						clothesArray[top] = likingValue;
						favArray[favShirtIndex] = top;
						maxLiking = likingValue;
					}
					else
					{	
						//System.out.println(likingValue + " is Lesser than Max Liking " + maxLiking);
						top += 1;
						clothesArray[top] = likingValue;
					}
				}
				else if(mode==2)
				{
					if(top == -1)
					{
						System.out.println(id +" "+ -1);
						//System.out.println("Maxlikig is :" + maxLiking);
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
						else
						{
							maxLiking = clothesArray[favArray[favShirtIndex]];
						}
						temp = temp1-temp;
						//System.out.println("Renoving Shirts, taking out the favourite one");
						//System.out.println("Total of " + temp + " shirts removed.");
						System.out.println(id + " " + temp);
						//System.out.println("Maxlikig is :" + maxLiking);

					}
	
				}
				else
				{
					System.out.println("Incorrect Value for Mode");
				}
				if(i==in-1)
				{
					input.close();
				}
				//FabricBreakup.printstack();
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println(e.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Please enter a valid file name");
		}		
	}

	/*public static void printstack()
	{
		System.out.print("[");
		for(int i=0; i<top+1; i++)
		{
			System.out.print(clothesArray[i]+",");
		}
		System.out.println("]");
		System.out.println();
		System.out.println();
	}*/
}