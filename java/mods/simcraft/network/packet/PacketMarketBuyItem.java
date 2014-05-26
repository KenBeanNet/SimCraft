package mods.simcraft.network.packet;


import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.data.MarketPrice;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.network.SimPacket;
import mods.simcraft.player.ExtendedPlayer;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PacketMarketBuyItem extends SimPacket {

	private MarketItem item;
	private int count;
	private short marketLevel;
	
	public PacketMarketBuyItem() 
	{
	}

	public PacketMarketBuyItem(MarketItem par1Item, int par2Count, short par3MarketLevel) 
	{
		item = par1Item;
		count = par2Count;
		marketLevel = par3MarketLevel;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
		ByteBufUtils.writeUTF8String(buffer, item.item);
		buffer.writeInt(count);
		buffer.writeShort(item.metadata);
		buffer.writeShort(marketLevel);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		item = new MarketItem(ByteBufUtils.readUTF8String(buffer), buffer.readInt(), buffer.readShort());
		count = item.count;
		marketLevel = buffer.readShort();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		ExtendedPlayer extPlayer = ExtendedPlayer.getExtendedPlayer(player);
		if (extPlayer != null)
		{
			int price = MarketPrice.getDefaultPriceOnItem(item, count);
			int tax = MarketManager.getTaxOnBuyPrice(marketLevel, price);
			
			if (extPlayer.getSimoleans() >= price+tax)
			{
				if (player.inventory.addItemStackToInventory(new ItemStack(Block.getBlockFromName(item.item), count, item.metadata)))
				{
					player.addChatMessage(new ChatComponentText("[SimCraft] You have purchased "+ count + " " + StatCollector.translateToLocal(item.item.replace("simcraft:", "") + item.metadata + ".name") + "(s) for " + (price + tax) + " simoleans." ));
					extPlayer.removeSimoleans(price+tax);
				}
				else
				{
					player.addChatMessage(new ChatComponentText("[SimCraft] You do not have space to buy "+ count + " " + StatCollector.translateToLocal(item.item.replace("simcraft:", "") + item.metadata + ".name") + "(s)." ));
				}
			}
			else
			{ //Not Enough Money
				player.addChatMessage(new ChatComponentText("[SimCraft] You do not have enough money to purchase "+ count + " " + StatCollector.translateToLocal(item.item.replace("simcraft:", "") + item.metadata + ".name") + "(s) for " + (price + tax) + " simoleans." ));
			}
		}
	}

}