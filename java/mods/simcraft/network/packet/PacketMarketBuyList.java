package mods.simcraft.network.packet;


import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.client.gui.MarketBuyGui;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketBuyList extends SimPacket 
{
	private MarketItem[] items;
	private int maxPageCount;
	
	public PacketMarketBuyList()
	{
		
	}
	public PacketMarketBuyList(MarketItem[] par1Items, int par2MaxPageCount) 
	{
		items = par1Items;
		maxPageCount = par2MaxPageCount;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		int amount = 0;
		for (int i = 0; i < items.length; i++)
		{
			if (items[i] != null)
				amount++;
		}
		buffer.writeInt(amount);
		for (int i = 0; i < amount; i++)
		{
			ByteBufUtils.writeUTF8String(buffer, items[i].item);
			buffer.writeInt(items[i].count);
			buffer.writeShort(items[i].metadata);
		}
		buffer.writeInt(maxPageCount);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		int amount = buffer.readInt();
		items = new MarketItem[amount];
		for (int i = 0; i < amount; i++)
		{
			items[i] = new MarketItem(ByteBufUtils.readUTF8String(buffer), buffer.readInt(), buffer.readShort());
		}
		maxPageCount = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketBuyGui)
		{
			MarketBuyGui gui = (MarketBuyGui)Minecraft.getMinecraft().currentScreen;
			gui.items = items;
			gui.maxPageNumber = maxPageCount;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
	}

}