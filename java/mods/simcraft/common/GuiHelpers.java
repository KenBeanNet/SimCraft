package mods.simcraft.common;

public class GuiHelpers {

	public static int getSizeForNeedLevel(int level)
	{
		return level / 2;
	}
	public static int getColorForNeedLevel(int level)
	{
		if (level > 90)
			return 0x006600;
		else if (level > 80)
			return 0x009900;
		else if (level > 70)
			return 0x00CC00;
		else if (level > 60)
			return 0x99FF99;
		else if (level > 50)
			return 0xFFFFCC;
		else if (level > 40)
			return 0xFFCCCC;
		else if (level > 30)
			return 0xFF6666;
		else if (level > 20)
			return 0xFF0000;
		else 
			return 0xCC0000;
	}
	public static int getRGBAColorForNeedLevel(int level)
	{
		if (level > 90)
			return 0xFF006600;
		else if (level > 80)
			return 0xFF009900;
		else if (level > 70)
			return 0xFF00CC00;
		else if (level > 60)
			return 0xFF99FF99;
		else if (level > 50)
			return 0xFFFFFFCC;
		else if (level > 40)
			return 0xFFFFCCCC;
		else if (level > 30)
			return 0xFFFF6666;
		else if (level > 20)
			return 0xFFFF0000;
		else 
			return 0xFFCC0000;
	}
}
