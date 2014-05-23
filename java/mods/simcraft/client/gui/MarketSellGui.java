package mods.simcraft.client.gui;

import mods.simcraft.SimCraft;
import mods.simcraft.data.HomeManager;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.network.packet.PacketMarketSellItemPriceCheck;
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
public class MarketSellGui extends GuiContainer
{
	public int totalPrice; //Sets from a Packet Sending Results from Calculate button
	public int totalTax;
	public int totalProfit;
	
	private EntityPlayer player;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private MarketTileEntity tile;
	
	private static int xSize = 176;
    private static int ySize = 168;
	
	public MarketSellGui(EntityPlayer par1Player, MarketTileEntity par1Tile, int x, int y, int z)
	{
    	super(new MarketContainer(par1Player.inventory, par1Tile));
    	player = par1Player;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		tile = par1Tile;
	}

	@Override 
	public void initGui()
	{
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));
		
		this.buttonList.add(new GuiButton(3, this.width - 124, 35, 115, 20, "Buy Items"));
		
		this.buttonList.add(new GuiButton(1, this.width - 124, 60, 115, 20, "Calculate"));
		
		this.buttonList.add(new GuiButton(2, this.width - 124, 85, 115, 20, "Sell Items"));
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
			SimCraft.packetPipeline.sendToServer(new PacketMarketSellItemPriceCheck(tile));
		}
		else if (button.id == 2)
		{
			SimCraft.packetPipeline.sendToServer(new PacketMarketSellItems(tile));
		}
		else if (button.id == 3)
		{
			player.openGui(SimCraft.instance, 3, tile.getWorldObj(), xCoord, yCoord, zCoord);
		}
		else if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer (int par1, int par2)
    {
		
		drawCenteredString(this.fontRendererObj, "MarketPlace!", this.width / 2, 10, 0xFFFFFF);
		
		drawString(this.fontRendererObj, "Market Stats", 10, 50, 0xFFCC00);
		drawString(this.fontRendererObj, "Level " + tile.getLevel(), 10, 60, 0x66CC66);
		drawString(this.fontRendererObj, "Tax 10%", 10, 70, 0x66CC66);
		
		if (totalPrice != 0)
		{
			drawString(this.fontRendererObj, "Price Check Results", this.width - 124, 110, 0xFFCC00);
			drawString(this.fontRendererObj, "Total Value    " + totalPrice, this.width - 124, 120, 0x66CC66);
			drawString(this.fontRendererObj, "Tax              " + totalTax, this.width - 124, 130, 0x66CC66);
			drawString(this.fontRendererObj, "Payout          " + totalProfit, this.width - 124, 140, 0x66CC66);
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		this.drawGradientRect(0, 0, this.width, this.height, 0xA087CEFA, 0xA087CEFA);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(GuiResourceFile.guiMarket);
		
		int totalX = (width - xSize) / 2;
        int totalY = (height - ySize) / 2;
        
        drawTexturedModalRect(totalX, totalY, 0, 0, xSize, ySize);
	}
}

