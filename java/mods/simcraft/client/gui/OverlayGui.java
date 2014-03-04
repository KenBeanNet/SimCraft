package mods.simcraft.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.SimCraft;
import mods.simcraft.common.GuiHelpers;
import mods.simcraft.data.JobManager;
import mods.simcraft.player.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;

public class OverlayGui
{
    Minecraft mc;
    ExtendedPlayer player;
    private int width;
    private int height;
    private ScaledResolution res;
    
	public OverlayGui()
	{
		mc = Minecraft.getMinecraft();
	}

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTick (RenderTickEvent event)
    {
    	res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    	width = res.getScaledWidth();
    	height = res.getScaledHeight();
    	
    	
    	if (mc.inGameHasFocus)
		{
    		if (player == null)
    			player = ExtendedPlayer.getExtendedPlayer(mc.thePlayer);
    		
    		mc.ingameGUI.drawCenteredString(mc.fontRenderer, "SimCraft V." + SimCraft.VERSION, width / 2, 5, 0xFFFFFF);
			
    		mc.getTextureManager().bindTexture(GuiResourceFile.guiIcons);

    		mc.ingameGUI.drawTexturedModalRect(5, height - 37, 0, 64, 32, 32);	
    		mc.ingameGUI.drawString(mc.fontRenderer, "$" + player.getSimoleans(), 38, height - 15, 0xFFFFFF);
		}
    	if (mc.currentScreen instanceof GuiInventory)
    	{
    		mc.currentScreen.drawString(mc.fontRenderer, "Sim Skills" , 30, 25, 0x00FFFF);
    		
    		mc.currentScreen.drawString(mc.fontRenderer, "Excavator Level : " + player.getExcavatorLevel(), 10, 45, 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Completed : " + JobManager.getPercentOfLevel(player.getExcavator()) + "%", 10, 55, 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Logger Level : " + player.getLoggerLevel(), 10, 65, 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Completed : " + JobManager.getPercentOfLevel(player.getLogger()) + "%", 10, 75, 0xFFFFFF);
    		
    		mc.currentScreen.drawString(mc.fontRenderer, "Sim Needs" , 335, 25, 0x00FFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Hunger : " , 315, 45, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 45, 365 + GuiHelpers.getSizeForNeedLevel(player.getHunger()), 53, GuiHelpers.getRGBAColorForNeedLevel(player.getHunger()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Comfort : " , 315, 65, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 65, 365 + GuiHelpers.getSizeForNeedLevel(player.getComfort()), 73, GuiHelpers.getRGBAColorForNeedLevel(player.getComfort()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Hygiene : " , 315, 85, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 85, 365 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), 93, GuiHelpers.getRGBAColorForNeedLevel(player.getHygiene()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Bladder : " , 315, 105, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 105, 415, 113, GuiHelpers.getRGBAColorForNeedLevel(70));
    		mc.currentScreen.drawString(mc.fontRenderer, "Energy : " , 315, 125, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 125, 415, 133, GuiHelpers.getRGBAColorForNeedLevel(60));
    		mc.currentScreen.drawString(mc.fontRenderer, "Fun : " , 315, 145, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 145, 415, 153, GuiHelpers.getRGBAColorForNeedLevel(50));
    		mc.currentScreen.drawString(mc.fontRenderer, "Social : " , 315, 165, 0xFFFFFF);
    		mc.currentScreen.drawRect(365, 165, 415, 173, GuiHelpers.getRGBAColorForNeedLevel(40));
    	}
    }
}