package mods.simcraft.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import mods.simcraft.SimCraft;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.inventory.SupplyChestContainer;
import mods.simcraft.network.packet.PacketCreateHome;
import mods.simcraft.network.packet.PacketMarketSellItemPriceCheck;
import mods.simcraft.network.packet.PacketMarketSellItems;
import mods.simcraft.tileentity.MarketTileEntity;
import mods.simcraft.tileentity.SupplyChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class MarketSellGui extends GuiContainer
{
	public int totalPrice; //Sets from a Packet Sending Results from Calculate button
	public int totalTax;
	public int totalProfit;
	
	private MarketTileEntity tile;
	private EntityPlayer player; 
	
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    public MarketSellGui(EntityPlayer par1Player, MarketTileEntity par2IInventory)
    {
        super(new MarketContainer(par1Player.inventory, par2IInventory));
        this.upperChestInventory = par1Player.inventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        
        this.xSize = 176;
        this.ySize = 168;
        
        player = par1Player;
        tile = par2IInventory;
    }

	@Override 
	public void initGui()
	{
		super.initGui();
		
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
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		
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
			player.openGui(SimCraft.instance, 3, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
		}
		else if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
		
	}
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
		this.drawGradientRect(0, 0, this.width, this.height, 0xA087CEFA, 0xA087CEFA);
		
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
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiResourceFile.guiMarket);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
    }
}

