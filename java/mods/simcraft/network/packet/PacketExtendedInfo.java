package mods.simcraft.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.player.ExtendedPlayer;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketExtendedInfo extends SimPacket {

	private int simoleans;

	public PacketExtendedInfo() 
	{
	}
	
	public PacketExtendedInfo(ExtendedPlayer player) 
	{
		this.simoleans = player.getSimoleans();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(simoleans);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		
    	simoleans = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		ExtendedPlayer par1Player = ExtendedPlayer.getExtendedPlayer(player);
        if (par1Player != null)
        {
        	par1Player.setSimoleans(simoleans);
        }
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}