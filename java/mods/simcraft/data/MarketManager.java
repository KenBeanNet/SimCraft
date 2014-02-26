package mods.simcraft.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonStreamParser;

public class MarketManager 
{
	public class MarketItem
	{
		public String item;
		public int count; 
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
		
		System.out.println("Loaded " + itemList.size() + " item(s) from Market");
	}
}
