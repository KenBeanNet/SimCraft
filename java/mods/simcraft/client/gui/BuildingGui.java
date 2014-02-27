package mods.simcraft.client.gui;

import mods.simcraft.tileentity.SimObjectTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class BuildingGui extends GuiScreen {
	
	private SimObjectTileEntity teHome;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	
	public BuildingGui(SimObjectTileEntity par1TeHome, int x, int y, int z) {
		teHome = par1TeHome;
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	@Override 
	public void initGui()
	{
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);

		drawRect(100, this.height / 2 - 10, 100 + (int)(teHome.percentCompleted() * 2.5), this.height / 2 + 13, 0xaF0000FF);
		
		drawCenteredString(this.fontRendererObj, "Building Under Construction : " + teHome.percentCompleted() + "% Completed!", this.width / 2, this.height / 2, 0xFFFFFF);
	}
	
	@Override
	public void updateScreen()
	{
		if (teHome.isCompleted())
			Minecraft.getMinecraft().thePlayer.closeScreen();
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
