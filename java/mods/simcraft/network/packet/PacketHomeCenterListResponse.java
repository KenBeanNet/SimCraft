package mods.simcraft.network.packet;

import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import mods.simcraft.client.gui.HomeCenterGui;
import mods.simcraft.common.Home;
import mods.simcraft.network.SimPacket;

public class PacketHomeCenterListResponse  extends SimPacket 
{
	private List<Home> homeList;
	private int homeCenterPageCount;
	
	public PacketHomeCenterListResponse()
	{
		
	}
	
	public PacketHomeCenterListResponse(List<Home> par1HomeList, int pageCount)
	{
		homeList = par1HomeList;
		homeCenterPageCount = pageCount;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(homeList.size());
		for (int i = 0; i < homeList.size(); i++)
		{
			ByteBufUtils.writeUTF8String(buffer, homeList.get(i).name);
			ByteBufUtils.writeUTF8String(buffer, homeList.get(i).ownerUsername);
			buffer.writeInt(homeList.get(i).level);
			buffer.writeInt(homeList.get(i).xCoord);
			buffer.writeInt(homeList.get(i).yCoord);
			buffer.writeInt(homeList.get(i).zCoord);
			buffer.writeInt(homeList.get(i).dimensionId);
			buffer.writeShort(homeList.get(i).type);
			buffer.writeBoolean(homeList.get(i).isOnline);
		}
		buffer.writeInt(homeCenterPageCount);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		homeList = Lists.newArrayList();
		int size = buffer.readInt();
		for (int i=0; i < size; i++)
		{
			Home home = new Home();
	    	home.name = ByteBufUtils.readUTF8String(buffer);
	    	home.ownerUsername = ByteBufUtils.readUTF8String(buffer);
	    	home.level = buffer.readInt();
	    	home.xCoord = buffer.readInt();
	    	home.yCoord = buffer.readInt();
	    	home.zCoord = buffer.readInt();
	    	home.dimensionId = buffer.readInt();
	    	home.type = buffer.readShort();
	    	home.isOnline = buffer.readBoolean();
	    	homeList.add(home);
		}
		homeCenterPageCount = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof HomeCenterGui)
		{
			HomeCenterGui gui = (HomeCenterGui)Minecraft.getMinecraft().currentScreen;
			gui.homeList = homeList;
			gui.maxPageNumber = homeCenterPageCount;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

}
