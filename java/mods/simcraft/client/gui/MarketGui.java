package mods.simcraft.client.gui;

import mods.simcraft.SimCraft;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.network.packet.PacketMarketItemPriceCheck;
import mods.simcraft.network.packet.PacketMarketSellItems;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class MarketGui extends GuiContainer
{
	public int totalPrice; //Sets from a Packet Sending Results from Calculate button
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private MarketTileEntity tile;
	
	private static int xSize = 176;
    private static int ySize = 168;
	
	public MarketGui(InventoryPlayer  player, MarketTileEntity par1Tile, int x, int y, int z)
	{
    	super(new MarketContainer(player, par1Tile));
		xCoord = x;
		yCoord = y;
		zCoord = z;
		tile = par1Tile;
	}

	@Override 
	public void initGui()
	{
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));
		
		this.buttonList.add(new GuiButton(1, this.width - 150, 55, 45, 20, "Calculate"));
		
		this.buttonList.add(new GuiButton(2, this.width - 150, 85, 45, 20, "Sell Items"));
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (button.id == 1)
		{
			SimCraft.packetPipeline.sendToServer(new PacketMarketItemPriceCheck(tile.chestContents));
		}
		else if (button.id == 2)
		{
			SimCraft.packetPipeline.sendToServer(new PacketMarketSellItems(tile));
		}
		else if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer (int par1, int par2)
    {
        
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(GuiResourceFile.guiMarket);
		
		int totalX = (width - xSize) / 2;
        int totalY = (height - ySize) / 2;
        drawTexturedModalRect(totalX, totalY, 0, 0, xSize, ySize);
	}
}

