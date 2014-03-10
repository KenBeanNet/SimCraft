package mods.simcraft.network.packet;

import java.util.HashMap;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.network.PacketPipeline;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketSellItemPriceCheck extends SimPacket {

	private HashMap<String, Integer> items = new HashMap<String, Integer>();
	private MarketTileEntity tile;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	public PacketMarketSellItemPriceCheck() 
	{
	}

	public PacketMarketSellItemPriceCheck(MarketTileEntity par1Tile) 
	{
		tile = par1Tile;
		for (ItemStack s : tile.chestContents)
		{
			if (s != null)
				items.put(s.getUnlocalizedName(), s.stackSize);
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(tile.xCoord);
		buffer.writeInt(tile.yCoord);
		buffer.writeInt(tile.zCoord);
		buffer.writeShort(items.size());
		for (String s : items.keySet())
		{
			ByteBufUtils.writeUTF8String(buffer, s);
			buffer.writeInt(items.get(s));
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		xCoord = buffer.readInt();
		yCoord = buffer.readInt();
		zCoord = buffer.readInt();
		short length = buffer.readShort();
		for (int i = 0; i < length; i++)
		{
			String name = ByteBufUtils.readUTF8String(buffer);
			int count = buffer.readInt();
			items.put(name, count);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
		//int totalPrice = MarketManager.getPriceCheck(items);
		TileEntity te = player.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (te instanceof MarketTileEntity)
		{
			tile = (MarketTileEntity)te;
			//int totalTax = MarketManager.getTaxOnPrice(tile.getLevel(), totalPrice);
			//int totalProfit = totalPrice - totalTax;
			//SimCraft.packetPipeline.sendTo(new PacketMarketItemPriceCheckResult(totalPrice, totalTax, totalProfit), (EntityPlayerMP)player);
		}
		
	}

}