package mods.simcraft.network.packet;

import java.util.HashMap;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.client.gui.MarketSellGui;
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

public class PacketMarketSellItemPriceCheckResult extends SimPacket {

	private int totalPrice;
	private int totalTax;
	private int totalProfit;
	
	public PacketMarketSellItemPriceCheckResult() 
	{
	}

	public PacketMarketSellItemPriceCheckResult(int par1Price, int par2Tax, int par3Profit) 
	{
		totalPrice = par1Price;
		totalTax = par2Tax;
		totalProfit = par3Profit;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(totalPrice);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		totalPrice = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketSellGui)
		{
			MarketSellGui gui = (MarketSellGui)Minecraft.getMinecraft().currentScreen;
			gui.totalPrice = totalPrice;
			gui.totalTax = totalTax;
			gui.totalProfit = totalProfit;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	
	}

}