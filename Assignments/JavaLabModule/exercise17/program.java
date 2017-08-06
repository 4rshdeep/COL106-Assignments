public class program
{
	public String[] test(String fileNames[])
	{
		int len = 0;
		for(int i=0; i<fileNames.length; i++)
		{
			if(fileNames[i].endsWith("java"))
			{
				len+=1;
			}
		}
		String javaFiles[] = new String[len];
		int javaFiles_i = 0;
		for(int i=0; i< fileNames.length; i++)
		{
			if(fileNames[i].endsWith(".java"))
			{
				javaFiles[javaFiles_i] = fileNames[i];
				javaFiles_i += 1;
			}
		}
		return javaFiles;
	}
}
