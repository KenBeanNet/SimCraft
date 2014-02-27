package mods.simcraft.player;

import mods.simcraft.data.JobManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentText;
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
						if (pick != null)
						{
							player.addExcavator(pick.func_150913_i().getHarvestLevel());
						}
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
        			if (JobManager.getLevelByExp(player.getExcavator()) < tool.func_150913_i().getHarvestLevel())
        			{
        				event.entityPlayer.addChatMessage(new ChatComponentText("You must be a higher Excavator before using this tool!"));
        				event.setCanceled(true);
        			}
        		}
        	}
        }
    }
}
