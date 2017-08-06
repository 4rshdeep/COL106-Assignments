public class program
{
	public float[] test(int b, int c)
	{
		float ret[] = {1.0f,2.0f};
		float r1 = (float)((-b + Math.sqrt(b*b - 4*c))/2);
		float r2 = (float)((-b - Math.sqrt(b*b-4*c))/2);
		ret[0] = (r1<r2)?r1:r2;
		ret[1] = (r1>r2)?r1:r2;
		return ret;
	}
}
