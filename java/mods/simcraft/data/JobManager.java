package mods.simcraft.data;

public class JobManager 
{
	public static int getLevelByExp(int value)
	{
		if (value > getTotalExpForLevel(3))
			return 4;
		else if (value > getTotalExpForLevel(2) + 1 && value < getTotalExpForLevel(3))
			return 3;
		else if (value > getTotalExpForLevel(1) + 1 && value < getTotalExpForLevel(2))
			return 2;
		return 1;
	}
	
	public static int getPercentOfLevel(int value)
	{
		int currentLevel = getLevelByExp(value);
		value = getTotalExpForLevel(currentLevel) - value;
		return (int)(((value * 100f) / getTotalExpForLevel(currentLevel)));
	}
	
	public static int getTotalExpForLevel(int level)
	{
		switch (level)
		{
			case 1:
				return 100;
			case 2:
				return 300;
			case 3:
				return 1000;
		}
		return 100;
	}
}
