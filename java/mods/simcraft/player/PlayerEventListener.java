package mods.simcraft.player;

import org.apache.logging.log4j.Level;

import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.JobManager;
import mods.simcraft.network.packet.PacketTeleportPlayerRequest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Finish;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventListener {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			System.out.println("[SimCraft] Registering extended properties.");
			ExtendedPlayer.register((EntityPlayer)event.entity);
			if (event.entity.getExtendedProperties(ExtendedPlayer.EXT_PLAYER) != null)
			{
				System.out.println("[SimCraft] Extended properties registered successfully.");
			}
			
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{
		/*if (event.entity instanceof EntityPlayerMP)
		{
			if (SimCraft.proxy.containsEntityData(((EntityPlayerMP)event.entity).getDisplayName()))
			{
				System.out.println("[SimCraft] Found ExtendedPlayer data for " + ((EntityPlayerMP)event.entity).getDisplayName() + " in memory. Loaded!");
				ExtendedPlayer.loadProxyData((EntityPlayer) event.entity);
			}

			if (event.entity.isDead) // Protect from a user logging in dead and losing player data
			{
				FMLLog.log(Level.INFO, "[SimCraft] Player logged in dead.  Re-storing player data : " + ((EntityPlayerMP)event.entity).getDisplayName());
				ExtendedPlayer.saveProxyData((EntityPlayer) event.entity);
			}
		}*/
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		/*if (event.entity instanceof EntityPlayerMP) 
		{
			ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer((EntityPlayerMP)event.entity);
			player.respawn();
			
			ExtendedPlayer.saveProxyData((EntityPlayer) event.entity);
		}*/
	}
	
	@SubscribeEvent
	public void blockHarvestEvent(HarvestDropsEvent event) 
	{
		if (event.harvester != null) 
		{
			ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(event.harvester);
			if (player != null)
			{
				ItemStack heldStack = event.harvester.getHeldItem();
				if (heldStack != null)
				{
					if (heldStack.getItem() instanceof ItemTool)
					{
						ItemTool pick = (ItemTool)heldStack.getItem();
						if (pick instanceof ItemPickaxe)
							player.addExcavator(Math.max(1, pick.func_150913_i().getHarvestLevel() * 2));
						else if (pick instanceof ItemAxe || pick instanceof ItemSpade)
							player.addLogger(Math.max(1, pick.func_150913_i().getHarvestLevel() * 2));
					}
				}
			}
			
		}
	}
	
	// Player interact event 
    @SubscribeEvent
    public void onPlayerInteract (PlayerInteractEvent event)
    {
        if (event.entityPlayer != null && event.entityPlayer.inventory.getCurrentItem() != null)
        {
        	if (event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemTool)
        	{
        		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
        			return; // Do not require this check if its a right click of a block.
        		
        		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(event.entityPlayer);
        		ItemTool tool = (ItemTool)event.entityPlayer.inventory.getCurrentItem().getItem();
        		if (player != null)
        		{
        			if (tool instanceof ItemPickaxe && JobManager.getLevelByExp(player.getExcavatorLevel()) < tool.func_150913_i().getHarvestLevel() + 1)
        			{
        				event.entityPlayer.addChatMessage(new ChatComponentText("[SimCraft] You must be a higher Excavator before using this tool!"));
        				event.setCanceled(true);
        			}
        			else if ((tool instanceof ItemAxe || tool instanceof ItemSpade) && JobManager.getLevelByExp(player.getExcavatorLevel()) < tool.func_150913_i().getHarvestLevel() + 1)
        			{
        				event.entityPlayer.addChatMessage(new ChatComponentText("[SimCraft] You must be a higher Logger before using this tool!"));
        				event.setCanceled(true);
        			}
        		}
        	}
        }
    }
    
    @SubscribeEvent
    public void onFoodEaten(Finish event)
    {
    	if(event.item.getItem() instanceof ItemFood)
    	{
    		ItemFood foodEaten = (ItemFood)event.item.getItem();
    		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(event.entityPlayer);
    		if (player != null)
    		{
    			player.addHunger(foodEaten.func_150905_g(null));
    			event.entityPlayer.addChatMessage(new ChatComponentText("[SimCraft] You have gained hunger by eatting " + foodEaten.get!"));
    		}
    	}
    }
    
    @SubscribeEvent
	public void ServerChat(ServerChatEvent event)
	{
    	String message = null;
    	String line = event.message.toLowerCase();
	
    	if (line.startsWith(".addsimoleans"))
    	{
			event.setCanceled(true);
    		String[] split = line.split(" ");
    		EntityPlayerMP player = null;
			ExtendedPlayer extPlayer = null;
			int toGiveAmount = 0;
			
    		if (split.length == 3)
    		{
    			player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(split[1]);
    			extPlayer = ExtendedPlayer.getExtendedPlayer(player);
    			
    			toGiveAmount = Integer.parseInt(split[2]);
    		}
    		else if (split.length == 2)
    		{
    			extPlayer = ExtendedPlayer.getExtendedPlayer(event.player);
    			toGiveAmount = Integer.parseInt(split[1]);
    		}
    		else
    			message = "[SimCraft] Error : .addSimoleans <PLAYER> <AMOUNT>";
    		
    		if (extPlayer != null)
			{
    			try 
    			{
        			extPlayer.addSimoleans(toGiveAmount);
        			if (split.length == 2)
        				event.player.addChatMessage(new ChatComponentText("[SimCraft] You have given yourself " + toGiveAmount + " simoleans."));
        			else
        			{
        				event.player.addChatMessage(new ChatComponentText("[SimCraft] You have given "+ split[1] + " " + toGiveAmount + " simoleans."));
        				player.addChatMessage(new ChatComponentText("[SimCraft] " + event.username + " has given you " + toGiveAmount + " simoleans."));
        			}
    			}
    			finally
    			{
    				
    			}
    		}
			else
				message = "[SimCraft] Error : .addSimoleans <PLAYER> <AMOUNT>";
		}
    	else if (line.startsWith(".home"))
    	{
    		event.setCanceled(true);
    		String[] split = line.split(" ");
    		if (split.length == 1) // Send yourself to your home
    		{
        		Home h = HomeManager.getHomeByPlayerName(event.username);
        		if (h != null)
        		{
        			event.player.setPositionAndUpdate(h.xCoord, h.yCoord, h.zCoord);
        			event.player.addChatMessage(new ChatComponentText("[SimCraft] You have teleported home."));
        		}
        		else 
        			event.player.addChatMessage(new ChatComponentText("[SimCraft] You do not have a home!"));
    		}
    		else if (split.length == 2) //Send yourself to another players home
    		{
    			Home h = HomeManager.getHomeByPlayerName(split[1]);
        		if (h != null)
        		{
        			event.player.setPositionAndUpdate(h.xCoord, h.yCoord, h.zCoord);
        			event.player.addChatMessage(new ChatComponentText("[SimCraft] You have teleported to " + split[1] + "'s home."));
        		}
        		else 
        			event.player.addChatMessage(new ChatComponentText("[SimCraft] The Player " + split[1] + " does not have a home!"));
    		}
    		else if (split.length == 3) //Send another player to a players home
    		{
    			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(split[1]);
    			if (player != null)
    			{
    				Home h = HomeManager.getHomeByPlayerName(split[2]);
    				if (h != null)
    				{
    					player.setPositionAndUpdate(h.xCoord, h.yCoord, h.zCoord);
    					event.player.addChatMessage(new ChatComponentText("[SimCraft] You have teleported " + split[2] + " to " + split[1] + "'s home."));
    					player.addChatMessage(new ChatComponentText("[SimCraft] " + event.message + " has sent you to " + split[2] + "'s home."));
    				}
    				else 
            			event.player.addChatMessage(new ChatComponentText("[SimCraft] The Player " + split[2] + " does not have a home!"));
    			}
    			else 
        			event.player.addChatMessage(new ChatComponentText("[SimCraft] The Player " + split[1] + " does not exist or is not online!"));
    		}
    		else
    		{
				message = "[SimCraft] Error : .home / .home <PLAYER> / .home <PLAYER> <PLAYER>";
    		}
    	}
		else if (line.startsWith(".help"))
		{
			event.setCanceled(true);
			event.player.addChatMessage(new ChatComponentText("[SimCraft] The Following are known commands :"));
			event.player.addChatMessage(new ChatComponentText("[SimCraft] .addSimoleans <PLAYER> <AMOUNT>"));
			event.player.addChatMessage(new ChatComponentText("[SimCraft] .home / .home <PLAYER> / .home <PLAYER> <PLAYER>"));
		}
		else if (line.startsWith(".heal"))
		{
			ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(event.player);
			player.respawn();
		}
    	
    	if (message != null)
    	{
    		event.player.addChatMessage(new ChatComponentText(message));
    		event.setCanceled(true);
    	}
	}
    
	public boolean PlayerIsOP(String username)
	{
		if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(username)) {
			return true;
		}
			return false;
	}
}
