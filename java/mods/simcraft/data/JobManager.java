package mods.simcraft.data;

public class JobManager 
{
	public static int getLevelByExp(int value)
	{
		if (value < 100)
			return 1;
		else if (value > 101 && value < 300)
			return 2;
		return 0;
	}
}
