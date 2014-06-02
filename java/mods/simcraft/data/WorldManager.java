package mods.simcraft.data;

import mods.simcraft.SimCraft;
import mods.simcraft.player.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldManager 
{
	private int tickCycle;
	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP entity = (EntityPlayerMP)event.entity;			
			ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(entity);
			
			player.runLife();
		}
	}
}
