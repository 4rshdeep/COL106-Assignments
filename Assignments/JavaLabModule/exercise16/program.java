public class program
{
	public String test(String str_hex)
	{
		String binary="";
		//int val = Integer.parseInt(str_hex, 16);
		//binary = Integer.toBinaryString(val);
		binary="";
		String temp = "";
		String temp1 = "";
		int flag = -1;
		temp = getBinary(str_hex.substring(0,1));
		for(int i=0; i< temp.length(); i++)
		{	
			
			if(temp.substring(i,i+1).equals("1"))
			{
				temp1 += temp.substring(i);
				break;
			}
		}
		binary += temp1;
		for(int i=1; i<str_hex.length(); i++)
		{
			temp = getBinary(str_hex.substring(i,i+1));
			binary += temp;
		}
		return binary;
	}
	
	public String getBinary(String str)
	{
		switch(str)
		{
			case "0" : str="0000";
					break;
			case "1" : str="0001";
					break;
			case "2" : str="0010";
					break;
			case "3" : str="0011";
					break;
			case "4" : str="0100";
					break;
			case "5" : str="0101";
					break;
			case "6" : str="0110";
					break;
			case "7" : str="0111";
					break;
			case "8" : str="1000";
					break;
			case "9" : str="1001";
					break;
			case "A" : str="1010";
					break;
			case "B" : str="1011";
					break;
			case "C" : str="1100";
					break;
			case "D" : str="1101";
					break;
			case "E" : str="1110";
					break;
			case "F" : str="1111";
					break;
		}
		return str;
	}
}
