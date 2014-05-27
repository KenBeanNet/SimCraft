package mods.simcraft.network.packet;


import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketHomeInfo extends SimPacket {

	private Home home;
	
	public PacketHomeInfo() 
	{
	}

	public PacketHomeInfo(Home par1Home) 
	{
		this.home = par1Home;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		ByteBufUtils.writeUTF8String(buffer, home.name);
		ByteBufUtils.writeUTF8String(buffer, home.ownerUsername);
		buffer.writeInt(home.level);
		buffer.writeInt(home.xCoord);
		buffer.writeInt(home.yCoord);
		buffer.writeInt(home.zCoord);
		buffer.writeInt(home.dimensionId);
		buffer.writeShort(home.type);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		home = new Home();
    	home.name = ByteBufUtils.readUTF8String(buffer);
    	home.ownerUsername = ByteBufUtils.readUTF8String(buffer);
    	home.level = buffer.readInt();
    	home.xCoord = buffer.readInt();
    	home.yCoord = buffer.readInt();
    	home.zCoord = buffer.readInt();
    	home.dimensionId = buffer.readInt();
    	home.type = buffer.readShort();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		HomeManager.homeList.put(home.ownerUsername, home);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		
	}

}