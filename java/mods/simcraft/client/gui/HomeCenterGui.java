package mods.simcraft.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.packet.PacketHomeCenterList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

public class HomeCenterGui extends GuiScreen
{
	public List<Home> homeList;
	
	private GuiTextField txtHomeSearch;
	private String townName = "";
	private int pageNumber;
	private boolean showOffline;
	private int maxPageNumber = 1;

	private EntityPlayer entityPlayer;
	private GuiButton[] btnTeleportHome = new GuiButton[10];
	
	public HomeCenterGui(EntityPlayer par1EntityPlayer) {
		entityPlayer = par1EntityPlayer;
	}
	
	@Override 
	public void initGui()
	{
		txtHomeSearch = new GuiTextField(this.fontRendererObj, 50 , this.height - 24, 100, 17);
		txtHomeSearch.setMaxStringLength(16);
		
		for (int i = 0; i < 9; i++)
		{
			btnTeleportHome[i] = (new GuiButton(20 + i, 265, (i * 20) + 70 - 7, 65, 20, "Visit"));
			btnTeleportHome[i].visible = false;
			this.buttonList.add(btnTeleportHome[i]);
		}
		

		this.buttonList.add(new GuiButton(2, this.width - 70, this.height - 25, 35, 20, "Last"));
		this.buttonList.add(new GuiButton(3, this.width - 35, this.height - 25, 35, 20, "Next"));
		
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));

		SimCraft.packetPipeline.sendToServer(new PacketHomeCenterList(txtHomeSearch.getText()));
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		drawCenteredString(fontRendererObj, "Home Center", this.width / 2, 10, 0x6699FF);
		drawString(this.fontRendererObj, "Search: ", 5, this.height - 18, 0xFFCC00);
		drawString(this.fontRendererObj, "Page " + (pageNumber + 1) + "/" + maxPageNumber, this.width - 125, this.height - 18, 0xFFCC00);
		

		drawString(fontRendererObj, "Home Name", 10, 50, 0xFFFFFF);
		drawString(fontRendererObj, "Home Owner", 80, 50, 0xFFFFFF);
		drawString(fontRendererObj, "Level", 150, 50, 0xFFFFFF);
		drawString(fontRendererObj, "Type", 200, 50, 0xFFFFFF);
		drawString(fontRendererObj, "Action", 280, 50, 0xFFFFFF);
		if (homeList != null)
		{
			for (int i = 0; i < homeList.size(); i++)
			{
				drawString(fontRendererObj, homeList.get(i).name, 10, (i * 20) + 70, 0xFFCC00);
				drawString(fontRendererObj, homeList.get(i).ownerUsername, 80, (i * 20) + 70, 0x66CC66);
				drawString(fontRendererObj, homeList.get(i).level + "", 160, (i * 20) + 70, 0x66CC66);
				drawString(fontRendererObj, HomeManager.getHomeTypeFromInt(homeList.get(i).type), 190, (i * 20) + 70, 0x66CC66);
				btnTeleportHome[i].visible = true;
			}
		}
		
		txtHomeSearch.drawTextBox();
		
		super.drawScreen(x, y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	
	public void keyTyped(char c, int i)
	{
		super.keyTyped(c, i);
		txtHomeSearch.textboxKeyTyped(c, i);
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		txtHomeSearch.mouseClicked(i, j, k);
	}
	
	public void updateScreen()
	{
		if (townName != txtHomeSearch.getText())
			SimCraft.packetPipeline.sendToServer(new PacketHomeCenterList(txtHomeSearch.getText()));
		townName = txtHomeSearch.getText();
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			
		}
		else if (button.id == 8)
		{
			if (pageNumber <= 0)
				return;
			else 
				pageNumber--;
		}
		else if (button.id == 9)
		{
			pageNumber++;
		}
		else if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
		else if (button.id >= 20 && button.id <= 30)
		{
			if (homeList.size() >= button.id - 20)
			{
				Home h = homeList.get(button.id - 20);
				//Teleport Player
			}
		}
	}
}
