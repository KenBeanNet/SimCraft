package mods.simcraft.network.packet;


import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketHomeCenterList extends SimPacket {

	private String startsWith;
	
	public PacketHomeCenterList() 
	{
	}

	public PacketHomeCenterList(String par1StartsWith) 
	{
		startsWith = par1StartsWith;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		ByteBufUtils.writeUTF8String(buffer, startsWith);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		startsWith = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
		List<Home> homeList = HomeManager.getHomesStartWith(startsWith);
		SimCraft.packetPipeline.sendTo(new PacketHomeCenterListResponse(homeList), (EntityPlayerMP)player);
	}

}