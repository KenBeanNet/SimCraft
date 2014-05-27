package mods.simcraft.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import cpw.mods.fml.common.FMLLog;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class HomeManager {

	public static HashMap<String, Home> homeList = new HashMap<String, Home>();
	public static Gson gson = new Gson();
	
	public static void loadAllHomes()
	{
		File file = new File(SimCraft.proxy.getMinecraftDir(), "Homes");
		file.mkdir();
		System.out.print("[SimCraft] Loading Homes.....");
		for (final File fileEntry : file.listFiles()) {
			loadHome(fileEntry.getName().replace(".json", ""));
	    }
		System.out.println("Loaded " + homeList.size() + " home(s)");
	}
	
	public static void createHome(Home home)
	{
		System.out.print("[SimCraft] Creating new home for player " + home.ownerUsername + "......");

        try
        {
        	FileWriter writer = new FileWriter(SimCraft.proxy.getMinecraftDir() + "/Homes/" + home.ownerUsername + ".json");
        	writer.write(gson.toJson(home));
        	writer.close();
            homeList.put(home.ownerUsername, home);
        }
        catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public static void saveHome(Home home)
	{
		try
        {
        	FileWriter writer = new FileWriter(SimCraft.proxy.getMinecraftDir() + "/Homes/" + home.ownerUsername + ".json");
        	String longT = gson.toJson(home);
        	System.out.println(longT);
        	writer.write(longT);
        	writer.close();
        }
        catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	private static void loadHome(String username)
	{
		Home home = null;
		
		try
        {
			BufferedReader br = new BufferedReader(new FileReader(SimCraft.proxy.getMinecraftDir() + "/Homes/" + username + ".json"));
			home = gson.fromJson(br, Home.class);
			br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
        homeList.put(username, home);
	}
	
	
	public static int getHomeSize(int homeLevel)
	{
		switch (homeLevel)
		{
			case 1:
				return 5;
			case 2: 
				return 8;
		}
		return 1;
	}
	
	public static Home getHomeByPlayerName(String username)
	{
		for (String h : homeList.keySet())
		{
			if (h.toLowerCase().equals(username.toLowerCase()))
				return homeList.get(h);
		}
		return null;
	}
	
	public static boolean IsPointInHome(Home h, int x, int y, int z)
	{
		int widthOfHome = HomeManager.getHomeSize(h.level);
		
		if (h.xCoord - widthOfHome <= x && h.xCoord + widthOfHome >= x 
				&& h.yCoord - widthOfHome <= y && h.yCoord + widthOfHome >= y
				&& h.zCoord - widthOfHome <= z && h.zCoord + widthOfHome >= z )
		{
			return true;
		}
		return false;
	}
	
	public static boolean IsPointInHome(int x, int y, int z)
	{
		for (Home h : homeList.values())
		{
			if (IsPointInHome(h, x, y, z))
				return true;
		}
		return false;
	}
	
	public static Home getPointInHome(int x, int y, int z)
	{
		for (Home h : homeList.values())
		{
			if (IsPointInHome(h, x, y, z))
				return h;
		}
		return null;
	}
	
	public static boolean hasHome(String username)
	{
		return homeList.containsKey(username);
	}
	
	public static List<Home> getHomesStartWith(String par1StartsWith, int pageNumber)
	{
		int startStoring = (pageNumber - 1) * 9; //This is for the sorting with page.  We will start sorting after this amount of finds;
		int control = 0;
		List<Home> toReturn = Lists.newArrayList();
		for (Home h : homeList.values())
		{
			if (h.ownerUsername.toLowerCase().startsWith(par1StartsWith.toLowerCase()) || h.name.toLowerCase().startsWith(par1StartsWith.toLowerCase()))
			{
				if (startStoring > control)
					control++;
				else
					toReturn.add(h);
			}
			
			if (toReturn.size() >= 9)
				break;
		}
		
		return toReturn;
	}
	
	public static int getHomesPageCountStartWith(String par1StartsWith)
	{
		int toReturn = 0;
		for (Home h : homeList.values())
		{
			if (h.ownerUsername.toLowerCase().startsWith(par1StartsWith.toLowerCase()) || h.name.toLowerCase().startsWith(par1StartsWith.toLowerCase()))
			{
				toReturn++;
			}
		}
		
		return MathHelper.ceiling_double_int(toReturn / 9.0);
	}
	
	public static String getHomeTypeFromId(short value)
	{
		switch (value)
		{
			case 1:
				return "Residential";
			case 2:
				return "Commercial";
			case 3:
				return "Industrial";
			default:
				return "";
		}
	}
	
	public static void setHomeOffline(String playerName)
	{
		if (homeList.containsKey(playerName))
		{
			homeList.get(playerName).isOnline = false;
		}
	}
	
	public static void setHomeOnline(String playerName)
	{
		if (homeList.containsKey(playerName))
		{
			homeList.get(playerName).isOnline = true;
		}
	}
}
