package mods.simcraft.network.packet;

import java.util.HashMap;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.client.gui.MarketGui;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketItemPriceCheckResult extends SimPacket {

	private int totalPrice;
	
	public PacketMarketItemPriceCheckResult() 
	{
	}

	public PacketMarketItemPriceCheckResult(int par1Price) 
	{
		totalPrice = par1Price;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeShort(totalPrice);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		totalPrice = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketGui)
		{
			MarketGui gui = (MarketGui)Minecraft.getMinecraft().currentScreen;
			gui.totalPrice = totalPrice;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	
	}

}