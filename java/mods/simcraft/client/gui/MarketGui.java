package mods.simcraft.client.gui;

import mods.simcraft.SimCraft;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.opengl.GL11;

public class MarketGui extends GuiScreen
{
	private EntityPlayer playerPar1;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private MarketTileEntity tileHome;
	
	private int xSize = 176;
    private int ySize = 168;
	
	public MarketGui(EntityPlayer player, MarketTileEntity tile, int x, int y, int z) {
		playerPar1 = player;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		tileHome = tile;
	}

	@Override 
	public void initGui()
	{
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		this.mc.renderEngine.bindTexture(GuiResourceFile.guiMarket);
		
		int totalX = (width - xSize) / 2;
        int totalY = (height - ySize) / 2;
        drawTexturedModalRect(totalX, totalY, 0, 0, xSize, ySize);
        
		super.drawScreen(x, y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
}

