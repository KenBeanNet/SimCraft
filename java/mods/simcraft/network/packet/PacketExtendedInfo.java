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
	private int excavator;
	private int logger;
	
	private int hunger;
	private int comfort;
	private int hygiene;

	public PacketExtendedInfo() 
	{
	}
	
	public PacketExtendedInfo(ExtendedPlayer player) 
	{
		this.simoleans = player.getSimoleans();
		this.excavator = player.getExcavator();
		this.logger = player.getLogger();
		
		this.hunger = player.getHunger();
		this.comfort = player.getComfort();
		this.hygiene = player.getHygiene();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(simoleans);
		buffer.writeInt(excavator);
		buffer.writeInt(logger);
		
		buffer.writeInt(hunger);
		buffer.writeInt(comfort);
		buffer.writeInt(hygiene);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		
    	simoleans = buffer.readInt();
    	excavator = buffer.readInt();
    	logger = buffer.readInt();
    	
    	hunger = buffer.readInt();
    	comfort = buffer.readInt();
    	hygiene = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		ExtendedPlayer par1Player = ExtendedPlayer.getExtendedPlayer(player);
        if (par1Player != null)
        {
        	par1Player.setSimoleans(simoleans);
        	par1Player.setExcavator(excavator);
        	par1Player.setLogger(logger);
        	
        	par1Player.setHunger(hunger);
        	par1Player.setComfort(comfort);
        	par1Player.setHygiene(hygiene);
        }
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}