package mods.simcraft.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mods.simcraft.SimCraft;
import mods.simcraft.common.GuiHelpers;
import mods.simcraft.data.MarketManager.MarketItem;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.network.packet.PacketHomeCenterList;
import mods.simcraft.network.packet.PacketMarketBuyItem;
import mods.simcraft.network.packet.PacketMarketBuyItemPriceCheck;
import mods.simcraft.network.packet.PacketMarketBuyOpen;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class MarketBuyGui extends SimGui 
{
	public MarketItem[] items;
	private MarketItem selectedItem;
	
	private EntityPlayer player;
	private MarketTileEntity tile;
	
	private GuiTextField txtSearchField;
	private String marketSearchName = "";
	private int pageNumber = 1;
	public int maxPageNumber = 1;
	
	private GuiTextField txtPurchaseAmount;
	private GuiButton btnPriceCheck;
	private GuiButton btnPurchase;
	private GuiButton btnSellItems;
	private int totalCount = 0;
	public int tax = 0;
	public int price = 0;
	
	private GuiButton btnBack;
	
	private GuiButton[] btnMoreInfo = new GuiButton[9];
	
	public MarketBuyGui(EntityPlayer par1Player, MarketTileEntity par1Tile)
	{
    	player = par1Player;
		tile = par1Tile;
	}

	@Override 
	public void initGui()
	{
		super.initGui();

		txtSearchField = new GuiTextField(this.fontRendererObj, 50 , this.height - 24, 100, 17);
		txtSearchField.setMaxStringLength(16);
		
		txtPurchaseAmount = new GuiTextField(this.fontRendererObj, this.width / 2 - 5, this.height / 3 + 30 , 100, 17);
		txtPurchaseAmount.setMaxStringLength(16);
		
		for (int i = 0; i < btnMoreInfo.length; i++)
		{
			btnMoreInfo[i] = new GuiButton(20 + i, 120 * (i % 3) + 62, 60 * (i / 3) + 70, 35, 20, "Info");
			btnMoreInfo[i].visible = false;
			this.buttonList.add(btnMoreInfo[i]);
		}
			
		btnPriceCheck = new GuiButton(5, this.width / 2 - 5, this.height / 3 + 60, 100, 20, "Price Check");
		btnPriceCheck.visible = false;
		this.buttonList.add(btnPriceCheck);
		
		btnPurchase = new GuiButton(6, this.width / 2, this.height / 3 + 120, 100, 20, "Purchase");
		btnPurchase.visible = false;
		this.buttonList.add(btnPurchase);
		
		btnBack = new GuiButton(7, 5, 5, 45, 20, "Back");
		btnBack.visible = false;
		this.buttonList.add(btnBack);
		
		btnSellItems = new GuiButton(4, this.width - 104, 35, 95, 20, "Sell Items");
		this.buttonList.add(btnSellItems);

		btnNext.visible = true;
		btnLast.visible = true;
		
		SimCraft.packetPipeline.sendToServer(new PacketMarketBuyOpen(marketSearchName, pageNumber));
	}
	
	public void drawScreen(int x, int y, float f) 
	{
		this.drawGradientRect(0, 0, this.width, this.height, 0xA087CEFA, 0xA087CEFA);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		drawCenteredString(this.fontRendererObj, "MarketPlace!", this.width / 2, 10, 0xFFFFFF);
		
		if (selectedItem == null)
		{
			if (items != null)
			{
				txtSearchField.drawTextBox();
				drawString(this.fontRendererObj, "Search: ", 5, this.height - 18, 0xFFCC00);
				drawString(this.fontRendererObj, "Page " + (pageNumber) + "/" + maxPageNumber, this.width - 125, this.height - 18, 0xFFCC00);
				
				
				for (int i = 0; i < items.length; i++)
				{
					if (items[i] == null)
						continue;
	
			        ItemStack itemStack = new ItemStack(Block.getBlockFromName(items[i].item), 1, items[i].metadata);
			        
			        if (itemStack == null)
			        	continue;
			        
			        btnMoreInfo[i].visible = true;
			        
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			        RenderHelper.enableGUIStandardItemLighting();
					itemRender.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), itemStack, 120 * (i % 3) + 70, 60 * (i / 3) + 40);
			        drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal(itemStack.getDisplayName()), 120 * (i % 3) + 70, 60 * (i / 3) + 60, 0xFFCC00);
			        RenderHelper.disableStandardItemLighting();
			        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			        
				}
			}
		}
		else
		{
			
			ItemStack itemStack = new ItemStack(Block.getBlockFromName(selectedItem.item), 1, selectedItem.metadata);
		
			if (itemStack == null)
			{
				selectedItem = null;
				return;
			}
			
			drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal(itemStack.getDisplayName()), width / 2, height / 4, 0xFFCC00);
			
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), itemStack, width / 2 - 12, height / 3);
	        RenderHelper.disableStandardItemLighting();
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        
	        drawString(this.fontRendererObj, "Purchase Amount: ", this.width / 2 - 100, this.height / 3 + 35, 0xFFFFFF);
	        txtPurchaseAmount.drawTextBox();
	        
	        if (price != 0 && tax != 0)
	        {
	        	drawString(this.fontRendererObj, "Purchase Price: " + price, this.width / 2 - 80, this.height / 3 + 80, 0xFFFFFF);
	        	drawString(this.fontRendererObj, "Purchase Tax: " + tax, this.width / 2 - 80, this.height / 3 + 90, 0xFFFFFF);
	        	drawString(this.fontRendererObj, "Purchase Total: " + getTotalPrice(), this.width / 2 - 80, this.height / 3 + 100, 0xFFFFFF);
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
		int oldPageNumber = pageNumber;
		if (button.id == 2)
		{
			pageNumber--;
			if (pageNumber <= 1)
				pageNumber = 1;
		}
		else if (button.id == 3)
		{
			pageNumber++;
			if (pageNumber >= maxPageNumber)
				pageNumber = maxPageNumber;
		}
		else if (button.id == 4)
		{
			player.openGui(SimCraft.instance, 2, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
		}
		else if (button.id == 5)
		{
			if (txtPurchaseAmount.getText().isEmpty()){
				player.addChatMessage(new ChatComponentText("[SimCraft] You must type your purchase amount."));
				return;
			}
			
			SimCraft.packetPipeline.sendToServer(new PacketMarketBuyItemPriceCheck(selectedItem, Integer.parseInt(txtPurchaseAmount.getText()), tile.getLevel()));
		}
		else if (button.id == 6)
		{
			SimCraft.packetPipeline.sendToServer(new PacketMarketBuyItem(selectedItem, Integer.parseInt(txtPurchaseAmount.getText()), tile.getLevel()));
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
		else if (button.id == 7)
		{
			selectedItem = null;
			resetAllButtons(true);
			resetPurchaseOrder();

			btnPurchase.visible = false;
	        btnPriceCheck.visible = false;
		}
		else if (button.id >= 20 && button.id < 30)
		{
			if (items.length < button.id - 20)
				return;
			
			selectedItem = items[button.id - 20];
			
			resetAllButtons(false);
			
			btnPriceCheck.visible = true;
		}
		if (oldPageNumber != pageNumber)
		{
			resetInfoButtons(false);
			SimCraft.packetPipeline.sendToServer(new PacketMarketBuyOpen(marketSearchName, pageNumber));
		}
	}
	
	public void keyTyped(char c, int i)
	{
		super.keyTyped(c, i);
		txtSearchField.textboxKeyTyped(c, i);
		if (Character.isDigit(c) || c == '\b')
			txtPurchaseAmount.textboxKeyTyped(c, i);

		pageNumber = 1;
		maxPageNumber = 1;
		resetInfoButtons(false);
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		txtSearchField.mouseClicked(i, j, k);
		txtPurchaseAmount.mouseClicked(i, j, k);
	}

	
	public void updateScreen()
	{
		if (marketSearchName != txtSearchField.getText())
			SimCraft.packetPipeline.sendToServer(new PacketMarketBuyOpen(txtSearchField.getText(), pageNumber));
		marketSearchName = txtSearchField.getText();
	}
	
	private void resetInfoButtons(boolean turnOn)
	{	
		items = new MarketItem[9];
		for (int i = 0; i < btnMoreInfo.length; i++)
		{
			btnMoreInfo[i].visible = turnOn;
		}
	}
	
	private void resetAllButtons(boolean turnOn)
	{	
		for (int i = 0; i < btnMoreInfo.length; i++)
		{
			btnMoreInfo[i].visible = turnOn;
		}

		btnNext.visible = turnOn;
		btnLast.visible = turnOn;
		btnBack.visible = !turnOn; //Opposite
        btnSellItems.visible = turnOn;
	}
	
	public void setPurchaseOrder(int par1Price, int par2Tax)
	{
		price = par1Price;
		tax = par2Tax;
		
		btnPriceCheck.visible = false;
		btnPurchase.visible = true;
	}
	
	private void resetPurchaseOrder()
	{
		price = 0;
		tax = 0;
		totalCount = 0;
		
		btnPriceCheck.visible = true;
		btnPurchase.visible = false;
	}
	
	private int getTotalPrice()
	{
		return price + tax;
	}
}
