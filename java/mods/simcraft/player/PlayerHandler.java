package mods.simcraft.player;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.simcraft.SimCraft;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.packet.PacketExtendedInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerHandler
{
		@SubscribeEvent
        public void PlayerLoggedInEvent(PlayerLoggedInEvent  event)
        { //Ran by Server only.  Not on client.
        	SimCraft.packetPipeline.sendTo(new PacketExtendedInfo(ExtendedPlayer.getExtendedPlayer(event.player)), (EntityPlayerMP)event.player);
        }

        @SubscribeEvent
        public void PlayerRespawnEvent(PlayerRespawnEvent  event)
        {	//Ran by Server.
        	SimCraft.packetPipeline.sendTo(new PacketExtendedInfo(ExtendedPlayer.getExtendedPlayer(event.player)), (EntityPlayerMP)event.player);
        }
        
        @SubscribeEvent
        public void PlayerLoggedOutEvent(PlayerLoggedOutEvent  event)
        { //Ran by Server only.  Not on client.
        	HomeManager.setHomeOffline(event.player.getDisplayName());
        }
}