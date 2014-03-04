package mods.simcraft.network.packet;


import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.network.SimPacket;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMarketBuyOpen extends SimPacket 
{
	private int pageNumber;
	
	public PacketMarketBuyOpen()
	{
		
	}
	
	public PacketMarketBuyOpen(int par1PageNumber) 
	{
		pageNumber = par1PageNumber;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		buffer.writeInt(pageNumber);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		pageNumber = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
		ItemStack[] items = MarketManager.getItems(pageNumber); 
		SimCraft.packetPipeline.sendTo(new PacketMarketBuyList(items), (EntityPlayerMP)player);
	}

}