package mods.simcraft.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTeleportPlayerRequest extends SimPacket {

	private int xCoord;
	private int yCoord;
	private int zCoord;

	public PacketTeleportPlayerRequest() 
	{
	}
	
	public PacketTeleportPlayerRequest(int par1xCoord, int par1yCoord, int par1zCoord) 
	{
		xCoord = par1xCoord;
		yCoord = par1yCoord;
		zCoord = par1zCoord;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(xCoord);
		buffer.writeInt(yCoord);
		buffer.writeInt(zCoord);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		xCoord = buffer.readInt();
		yCoord = buffer.readInt();
		zCoord = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
		player.setPositionAndUpdate(xCoord, yCoord, zCoord);
	}

}