package mods.simcraft.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.client.gui.MarketBuyGui;
import mods.simcraft.client.gui.MarketSellGui;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.data.MarketPrice;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketBuyItemPriceCheckResult extends SimPacket {

	private int price;
	private int tax;

	public PacketMarketBuyItemPriceCheckResult() 
	{
	}
	
	public PacketMarketBuyItemPriceCheckResult(int par1Price, int par2Tax) 
	{
		price = par1Price;
		tax = par2Tax;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(price);
		buffer.writeInt(tax);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		price = buffer.readInt();
		tax = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketBuyGui)
		{
			MarketBuyGui gui = (MarketBuyGui)Minecraft.getMinecraft().currentScreen;
			gui.setPurchaseOrder(price, tax);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}

}