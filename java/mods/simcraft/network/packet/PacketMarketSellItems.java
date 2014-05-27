package mods.simcraft.network.packet;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketSellItems extends SimPacket {

	private MarketTileEntity tile;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	
	public PacketMarketSellItems() 
	{
	}

	public PacketMarketSellItems(MarketTileEntity tileEntity) 
	{
		tile = tileEntity;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(tile.xCoord);
		buffer.writeInt(tile.yCoord);
		buffer.writeInt(tile.zCoord);
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
		TileEntity te = player.worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (te instanceof MarketTileEntity)
		{
			tile = (MarketTileEntity)te;
			player.worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), SimCraft.MODID + ":coins1", 2.0f, 2.0f);
			if (!MarketManager.sellItems(player, tile))
				FMLLog.log(Level.ERROR, "[SimCraft] Player unable to sell items!");
			else 
				SimCraft.packetPipeline.sendTo(new PacketMarketItemsSold(), (EntityPlayerMP)player);
		}
	}

}