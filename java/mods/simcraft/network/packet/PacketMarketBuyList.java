package mods.simcraft.network.packet;


import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.client.gui.MarketBuyGui;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
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
	private ItemStack[] items;
	
	public PacketMarketBuyList()
	{
		
	}
	public PacketMarketBuyList(ItemStack[] par1Items) 
	{
		items = par1Items;
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
			ByteBufUtils.writeUTF8String(buffer, items[i].getItem().getUnlocalizedName());
			buffer.writeInt(items[i].stackSize);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		int amount = buffer.readInt();
		for (int i = 0; i < amount; i++)
		{
			items[i] = new ItemStack(Block.getBlockFromName(ByteBufUtils.readUTF8String(buffer)), buffer.readInt());
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof MarketBuyGui)
		{
			MarketBuyGui gui = (MarketBuyGui)Minecraft.getMinecraft().currentScreen;
			gui.items = items;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
	}

}