package mods.simcraft.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.common.Repository;
import mods.simcraft.player.ExtendedPlayer;
import mods.simcraft.tileentity.MarketTileEntity;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonStreamParser;

import cpw.mods.fml.common.FMLLog;

public class MarketManager 
{
	public static class MarketItem
	{
		public MarketItem(String par1ItemName, int par2Count, int par3Metadata)
		{
			item = par1ItemName;
			count = par2Count;
			metadata = par3Metadata;
		}
		public String item;
		public int count; 
		public int metadata;
	}
	
	public static HashMap<String, MarketItem> itemList = new HashMap<String, MarketItem>();
	public static Gson gson = new Gson();
	
	
	public static void loadMarketPlace()
	{
		File file = new File(SimCraft.proxy.getMinecraftDir(), "/config/simcraft/");
		file.mkdir();
		System.out.print("[SimCraft] Loading MarketPlace.....");
		
		try {
			JsonStreamParser parser = new JsonStreamParser(new FileReader(SimCraft.proxy.getMinecraftDir() + "/config/simcraft/market.json"));
		    while(parser.hasNext())
		    {
		    	MarketItem item = gson.fromJson(parser.next(), MarketItem.class);
		    	if (item != null && item.item != null)
		    		itemList.put(item.item, item);
		    }
		} 
		catch (FileNotFoundException e) 
		{
			FileWriter writer;
			try 
			{
				writer = new FileWriter(SimCraft.proxy.getMinecraftDir() + "/config/simcraft/market.json");
				writer.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		catch (JsonIOException e)
		{
			//This is OK, just means no items in the MarketPlace yet.
		}
		
		for (int i = 0; i < Repository.blockFlowers.length; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				String itemName = "simcraft:" + Repository.blockFlowers[i].getUnlocalizedName().replace("tile.", "");
				if (!itemList.containsKey(itemName + j))
					itemList.put(itemName + j, new MarketItem(itemName, 1, j));
			}
		}
		
		for (int i = 0; i < Repository.blockBrick.length; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				String itemName = "simcraft:" + Repository.blockBrick[i].getUnlocalizedName().replace("tile.", "");
				if (!itemList.containsKey(itemName + j))
					itemList.put(itemName + j, new MarketItem(itemName, 1, j));
			}
		}
		
		System.out.println("Loaded " + itemList.size() + " item(s) from Market");
	}
	public static void saveMarketPlace()
	{
		try
        {
        	FileWriter writer = new FileWriter(SimCraft.proxy.getMinecraftDir() + "/config/simcraft/market.json");
        	String longT = gson.toJson(itemList);
        	writer.write(longT);
        	writer.close();
        }
        catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public static int getTaxOnBuyPrice(int marketLevel, int totalPrice)
	{
		double taxLevel = 0.0;
		switch (marketLevel)
		{
			case 1:
			{
				taxLevel = 0.50;
				break;
			}
			case 2:
			{
				taxLevel = 0.40;
				break;
			}
		}
		return MathHelper.floor_double(totalPrice * taxLevel);
	}
	

	public static int getTaxOnSellPrice(int marketLevel, int totalPrice)
	{
		double taxLevel = 0.0;
		switch (marketLevel)
		{
			case 1:
			{
				taxLevel = 0.10;
				break;
			}
			case 2:
			{
				taxLevel = 0.08;
				break;
			}
		}
		return MathHelper.floor_double(totalPrice * taxLevel);
	}
	
	public static boolean sellItems(EntityPlayer player, MarketTileEntity tile)
	{
		ExtendedPlayer extPlayer = ExtendedPlayer.getExtendedPlayer(player);
		if (extPlayer == null)
			return false; //Protect items from being lost if we dont have an EXTPlayer to give money
		
		for (ItemStack s : tile.chestContents)
		{
			if (s == null)
				continue;


			int price;
			String editItemName = "simcraft:" + s.getUnlocalizedName().replace("tile.", "");
			
			if (itemList.containsKey(editItemName))
			{
				itemList.get(editItemName).count += s.stackSize;
				price = MarketPrice.getDefaultPriceOnItem(getMarketItemByUnlocalizedName(editItemName), s.stackSize);
			}
			else
			{
				MarketItem item = new MarketItem(editItemName, s.stackSize, s.getItemDamage());
				price = MarketPrice.getDefaultPriceOnItem(item, s.stackSize);
				//itemList.put(editItemName, item); Do not put on MarketPlace, Due to issue with naming convention between Minecraft
				//Items and SimCraft Items // TODO
			}
			
			int tax = MarketManager.getTaxOnSellPrice(tile.getLevel(), price);
			extPlayer.addSimoleans(price-tax);
			player.addChatMessage(new ChatComponentText("[SimCraft] You have sold "+ s.stackSize + " " + s.getDisplayName() + "(s) for " + (price - tax) + " simoleans." ));
		}
		tile.sellItems(); // Clear the Inventory and kill the items.
		saveMarketPlace(); // Save the data in the database.
		return true;
	}
	
	public static MarketItem[] getItems(String searchName, int pageNumber) 
	{
		int startStoring = (pageNumber - 1) * 9; //This is for the sorting with page.  We will start sorting after this amount of finds;
		int control = 0;
		int i = 0;

		MarketItem[] toReturn = new MarketItem[9];
		MarketItem marketItem = null;
		for (String s : itemList.keySet())
		{
			marketItem = itemList.get(s);
			if (marketItem == null || marketItem.item == null)
				continue;
			
			if (StatCollector.translateToLocal(marketItem.item.replace("simcraft:", "") + marketItem.metadata + ".name").toLowerCase().contains(searchName.toLowerCase()))
			{
				if (control < startStoring)
				{
					control++;
					continue;
				}
				
				if (marketItem.item != null)
				{
					toReturn[i] = marketItem;
					i++;
				}
			}
			if (i == 9)
				break;
		}
		return toReturn;
	}
	
	public static int getSellPriceOnItems(HashMap<String, Integer> items)
	{
		int toReturn = 0;
		
		for (String s : items.keySet())
		{
			toReturn += MarketPrice.getDefaultPriceOnItem(getMarketItemByUnlocalizedName(s), items.get(s));
		}
		
		return toReturn;
	}
	
	public static MarketItem getMarketItemByUnlocalizedName(String name)
	{
		return itemList.get(name);
	}
	
	public static int getItemsPageCount(String searchName) {
		int toReturn = 0;
		MarketItem marketItem = null;
		for (String s : itemList.keySet())
		{
			marketItem = itemList.get(s);
			if (marketItem == null || marketItem.item == null)
				continue;
			
			if (StatCollector.translateToLocal(marketItem.item.replace("simcraft:", "") + marketItem.metadata + ".name").toLowerCase().contains(searchName.toLowerCase()))
			{
				if (marketItem.item != null)
				{
					toReturn++;
				}
			}
		}
		return MathHelper.ceiling_double_int(toReturn / 9.0) > 0 ? MathHelper.ceiling_double_int(toReturn / 9.0) : 1;
	}
}
