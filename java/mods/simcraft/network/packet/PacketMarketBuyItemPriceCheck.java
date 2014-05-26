package mods.simcraft.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.data.MarketPrice;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketBuyItemPriceCheck extends SimPacket {

	private MarketItem item;
	private int amount;
	private int marketLevel;

	public PacketMarketBuyItemPriceCheck() 
	{
	}
	
	public PacketMarketBuyItemPriceCheck(MarketItem par1Item, int par2Amount, int par3MarketLevel) 
	{
		item = par1Item;
		amount = par2Amount;
		marketLevel = par3MarketLevel;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		ByteBufUtils.writeUTF8String(buffer, item.item);
		buffer.writeInt(amount);
		buffer.writeShort(item.metadata);
		buffer.writeShort(marketLevel);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		item = new MarketItem(ByteBufUtils.readUTF8String(buffer), buffer.readInt(), buffer.readShort());
		amount = item.count;
		marketLevel = buffer.readShort();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		int price = MarketPrice.getDefaultPriceOnItem(item, amount);
		int tax = MarketManager.getTaxOnBuyPrice(marketLevel, price);
		
		SimCraft.packetPipeline.sendTo(new PacketMarketBuyItemPriceCheckResult(price, tax), (EntityPlayerMP)player);
	}

}