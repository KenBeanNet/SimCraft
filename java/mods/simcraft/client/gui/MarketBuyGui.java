package mods.simcraft.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mods.simcraft.SimCraft;
import mods.simcraft.common.GuiHelpers;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.network.packet.PacketMarketBuyOpen;
import mods.simcraft.network.packet.PacketMarketItemPriceCheck;
import mods.simcraft.network.packet.PacketMarketSellItems;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class MarketBuyGui extends SimGui 
{
	public MarketItem[] items;
	private MarketItem selectedItem;
	
	private EntityPlayer player;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private MarketTileEntity tile;
	
	private GuiTextField txtTownName;
	private int pageNumber = 0;
	public int maxPageNumber = 1;
	
	private GuiButton[] btnMoreInfo = new GuiButton[9];
	
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
		
		for (int i = 0; i < btnMoreInfo.length; i++)
		{
			btnMoreInfo[i] = new GuiButton(20 + i, 100 * (i % 3) + 43, 60 * (i / 3) + 70, 35, 20, "Info");
			this.buttonList.add(btnMoreInfo[i]);
		}
			
		this.buttonList.add(new GuiButton(4, this.width - 104, 35, 95, 20, "Sell Items"));

		this.buttonList.add(new GuiButton(2, this.width - 70, this.height - 25, 35, 20, "Last"));
		this.buttonList.add(new GuiButton(3, this.width - 35, this.height - 25, 35, 20, "Next"));
	}
	
	public void drawScreen(int x, int y, float f) 
	{
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		if (selectedItem == null)
		{
			txtTownName.drawTextBox();
			
			drawString(this.fontRendererObj, "Search: ", 5, this.height - 18, 0xFFCC00);
			drawString(this.fontRendererObj, "Page " + (pageNumber + 1) + "/" + maxPageNumber, this.width - 125, this.height - 18, 0xFFCC00);
			
			if (items != null)
			{
				for (int i = 0; i < items.length; i++)
				{
					if (items[i] == null)
						continue;
	
			        ItemStack itemStack = new ItemStack(Block.getBlockFromName(items[i].item), 1, items[i].metadata);
			        
			        if (itemStack == null)
			        	continue;
	
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			        RenderHelper.enableGUIStandardItemLighting();
					itemRender.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), itemStack, 100 * (i % 3) + 50, 60 * (i / 3) + 40);
			        drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal(itemStack.getDisplayName()), 100 * (i % 3) + 60, 60 * (i / 3) + 60, 0xFFCC00);
			        RenderHelper.disableStandardItemLighting();
			        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			        
				}
			}
		}
		else
		{
			
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
		int oldPageNumber = pageNumber;
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
		else if (button.id == 4)
		{
			player.openGui(SimCraft.instance, 2, tile.getWorldObj(), xCoord, yCoord, zCoord);
		}
		else if (button.id >= 20 && button.id < 30)
		{
			selectedItem = items[button.id - 20];
		}
		if (oldPageNumber != pageNumber)
			SimCraft.packetPipeline.sendToServer(new PacketMarketBuyOpen(pageNumber));
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
