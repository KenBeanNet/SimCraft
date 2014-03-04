package mods.simcraft.client.gui;

import org.lwjgl.opengl.GL11;

import mods.simcraft.SimCraft;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.network.packet.PacketMarketBuyOpen;
import mods.simcraft.network.packet.PacketMarketItemPriceCheck;
import mods.simcraft.network.packet.PacketMarketSellItems;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MarketBuyGui extends SimGui 
{
	public ItemStack[] items;
	
	private EntityPlayer player;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private MarketTileEntity tile;
	
	private GuiTextField txtTownName;
	private int pageNumber = 0;
	public int maxPageNumber = 1;
	
	public MarketBuyGui(EntityPlayer par1Player, MarketTileEntity par1Tile, int x, int y, int z)
	{
    	player = par1Player;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		tile = par1Tile;
	}

	@Override 
	public void initGui()
	{
		super.initGui();
		
		SimCraft.packetPipeline.sendToServer(new PacketMarketBuyOpen(pageNumber));
		
		txtTownName = new GuiTextField(this.fontRendererObj, 50 , this.height - 24, 100, 17);
		txtTownName.setMaxStringLength(16);

		this.buttonList.add(new GuiButton(2, this.width - 70, this.height - 25, 35, 20, "Last"));
		this.buttonList.add(new GuiButton(3, this.width - 35, this.height - 25, 35, 20, "Next"));
	}
	
	public void drawScreen(int x, int y, float f) 
	{
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		txtTownName.drawTextBox();
		
		drawString(this.fontRendererObj, "Search: ", 5, this.height - 18, 0xFFCC00);
		drawString(this.fontRendererObj, "Page " + (pageNumber + 1) + "/" + maxPageNumber, this.width - 125, this.height - 18, 0xFFCC00);
		
		if (items != null)
		{
			for (int i = 0; i < items.length; i++)
			{
				if (items[i] == null)
					continue;
				
				mc.getTextureManager().bindTexture(new ResourceLocation("SimCraft", "textures/blocks/bricks/" + items[i].getItem().getUnlocalizedName().substring(5)));
				
				drawTexturedModalRect(0, 0, 0, 0, 64, 64);
			}
		}
		super.drawScreen(x, y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		if (button.id == 2)
		{
			pageNumber--;
			if (pageNumber < 0)
				pageNumber = 0;
		}
		else if (button.id == 3)
		{
			pageNumber++;
			if (pageNumber >= maxPageNumber)
				pageNumber = maxPageNumber;
		}
	}
	
	public void keyTyped(char c, int i)
	{
		super.keyTyped(c, i);
		txtTownName.textboxKeyTyped(c, i);
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		txtTownName.mouseClicked(i, j, k);
	}
}
