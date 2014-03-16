package mods.simcraft.player;

import mods.simcraft.data.JobManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventListener {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			if (event.entity.getExtendedProperties(ExtendedPlayer.EXT_PLAYER) == null)
			{
				System.out.println("[SimCraft] Registering extended properties.");
				ExtendedPlayer.register((EntityPlayer)event.entity);
				if (event.entity.getExtendedProperties(ExtendedPlayer.EXT_PLAYER) != null)
				{
					System.out.println("[SimCraft] Extended properties registered successfully.");
				}
			}
			else
			{
				System.out.println("[SimCraft] Extended properties already exist.");
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityLivingDeath(LivingDeathEvent event)
	{
		if (event.entity instanceof EntityPlayer) // Handles the death of a EntityPlayer so they regain the data upon respawn.
		{
			ExtendedPlayer.saveProxyData((EntityPlayer) event.entity);
		}
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
    public void playerInteract (PlayerInteractEvent event)
    {
        if (event.entityPlayer != null && event.entityPlayer.inventory.getCurrentItem() != null)
        {
        	if (event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemTool)
        	{
        		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(event.entityPlayer);
        		ItemTool tool = (ItemTool)event.entityPlayer.inventory.getCurrentItem().getItem();
        		if (player != null)
        		{
        			if (tool instanceof ItemPickaxe && JobManager.getLevelByExp(player.getExcavatorLevel()) < tool.func_150913_i().getHarvestLevel() + 1)
        			{
        				event.entityPlayer.addChatMessage(new ChatComponentText("You must be a higher Excavator before using this tool!"));
        				event.setCanceled(true);
        			}
        			else if ((tool instanceof ItemAxe || tool instanceof ItemSpade) && JobManager.getLevelByExp(player.getExcavatorLevel()) < tool.func_150913_i().getHarvestLevel() + 1)
        			{
        				event.entityPlayer.addChatMessage(new ChatComponentText("You must be a higher Logger before using this tool!"));
        				event.setCanceled(true);
        			}
        		}
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
    		String[] split = line.split(" ");
    		if (split.length == 3)
    		{
    			try 
    			{
    				EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(split[1]);
    				ExtendedPlayer extPlayer = ExtendedPlayer.getExtendedPlayer(player);
    				if (extPlayer != null)
    				{

        				int amount = Integer.parseInt(split[2]);
        				extPlayer.addSimoleans(amount);
        				if (event.username.toLowerCase().equals(split[1]))
        					event.player.addChatMessage(new ChatComponentText("[SimCraft] You have given yourself " + amount + " simoleans."));
        				else
        				{
        					event.player.addChatMessage(new ChatComponentText("[SimCraft] You have given "+ split[1] + " " + amount + " simoleans."));
        					player.addChatMessage(new ChatComponentText("[SimCraft] " + event.username + " has given you " + amount + " simoleans."));
        				}
        				event.setCanceled(true);
    				}
    				else
    					message = "[SimCraft] Error Cannot Find Online Player : " + split[1];
    			}
    			finally
    			{
    				
    			}
    		}
    		else
    			message = "[SimCraft] Error : .addSimoleans <PLAYER> <AMOUNT> ";
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
