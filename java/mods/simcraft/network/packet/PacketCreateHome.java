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

public class PacketCreateHome extends SimPacket {

	private Home home;

	public PacketCreateHome() 
	{
	}
	
	public PacketCreateHome(Home par1Home) 
	{
		this.home = par1Home;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		ByteBufUtils.writeUTF8String(buffer, home.name);
		buffer.writeInt(home.xCoord);
		buffer.writeInt(home.yCoord);
		buffer.writeInt(home.zCoord);
		buffer.writeShort(home.type);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		home = new Home();
    	home.name = ByteBufUtils.readUTF8String(buffer);
    	home.xCoord = buffer.readInt();
    	home.yCoord = buffer.readInt();
    	home.zCoord = buffer.readInt();
    	home.type = buffer.readShort();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (!HomeManager.homeList.containsKey(home.ownerUsername)) //Ensure player doesn't already have home.  Hack?
    	{
			home.ownerUsername = player.getDisplayName();
			home.level = 1;
    		home.dimensionId = player.worldObj.provider.dimensionId;
    		
            TileEntity te = player.worldObj.getTileEntity(home.xCoord, home.yCoord, home.zCoord);
            if (te instanceof HomeTileEntity)
            {
            	HomeManager.createHome(home);
            	HomeTileEntity teHome = (HomeTileEntity)te;
            	teHome.setHomeName(home.name);
            	teHome.setOwner(home.ownerUsername);
            	teHome.setHomeType(home.type);
            	player.worldObj.markBlockForUpdate(home.xCoord, home.yCoord, home.zCoord);
            	teHome.build();

            	SimCraft.packetPipeline.sendToAll(new PacketHomeInfo(home));
            }
            else
            	System.out.println("[ScourgeCraft] The coords of a Home Creation have Invalid TE : " + home.xCoord + " " + home.yCoord + " " + home.zCoord);
    	}
    	else
    		System.out.println("[ScourgeCraft] Player already has home and attemption 2nd creation!");
	}

}