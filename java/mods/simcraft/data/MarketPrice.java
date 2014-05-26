package mods.simcraft.data;

import java.util.HashMap;

import mods.simcraft.data.MarketManager.MarketItem;

public class MarketPrice 
{
	public static HashMap<String, Integer> itemList = new HashMap<String, Integer>();
	
	public static int getDefaultPriceOnItem(MarketItem item)
	{
		return getDefaultPriceOnItem(item, 1);
	}
	public static int getDefaultPriceOnItem(MarketItem item, int amount)
	{
		if (item != null && itemList.containsKey(item.item))
			return itemList.get(item.item) * amount;
		
		return 100 * amount;
	}
}
