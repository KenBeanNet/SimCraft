package mods.simcraft.network.packet;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import mods.simcraft.SimCraft;
import mods.simcraft.client.gui.MarketSellGui;
import mods.simcraft.network.SimPacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketMarketItemsSold extends SimPacket 
{
	public PacketMarketItemsSold()
	{
		
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketSellGui)
		{
			player.worldObj.playSoundEffect((double)((float)player.posX + 0.5F), (double)((float)player.posY + 0.5F), (double)((float)player.posZ + 0.5F), SimCraft.MODID + ":coins1", 2.0f, 2.0f);
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
	}

}