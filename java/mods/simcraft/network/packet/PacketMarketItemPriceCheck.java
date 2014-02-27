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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketItemPriceCheck extends SimPacket {

	private HashMap<String, Integer> items = new HashMap<String, Integer>();
	
	public PacketMarketItemPriceCheck() 
	{
	}

	public PacketMarketItemPriceCheck(ItemStack[] par1Items) 
	{
		for (ItemStack s : par1Items)
		{
			items.put(s.getUnlocalizedName(), s.stackSize);
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeShort(items.size());
		for (String s : items.keySet())
		{
			ByteBufUtils.writeUTF8String(buffer, s);
			buffer.writeInt(items.get(s));
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
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
		int totalPrice = MarketManager.getPriceCheck(items);
		
		SimCraft.packetPipeline.sendTo(new PacketMarketItemPriceCheckResult(totalPrice), (EntityPlayerMP)player);
	}

}