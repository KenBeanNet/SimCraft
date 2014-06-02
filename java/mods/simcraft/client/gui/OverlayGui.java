package mods.simcraft.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.SimCraft;
import mods.simcraft.common.GuiHelpers;
import mods.simcraft.data.JobManager;
import mods.simcraft.player.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayerMP;

public class OverlayGui
{
    Minecraft mc;
    ExtendedPlayer player;
    private int width;
    private int height;
    private ScaledResolution res;
    public static int HEIGHT_SPACER = 10;
    
    private boolean loaded = false;  //Determines if the welcome GUI has been loaded.
    
	public OverlayGui()
	{
		mc = Minecraft.getMinecraft();
	}

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTick (RenderTickEvent event)
    {
    	if (!loaded)
    	{
    		loaded = true;
    		mc.ingameGUI = new InGameGui(mc);
    	}
    	
    	res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    	width = res.getScaledWidth();
    	height = res.getScaledHeight();
    	
    	if (mc.currentScreen instanceof GuiInventory)
    	{
    		player = ExtendedPlayer.getExtendedPlayer(mc.thePlayer);
    		
    		int startHeight = this.height / 6;
    		int startWidth = this.width - 100;
    		
    		mc.currentScreen.drawString(mc.fontRenderer, "Sim Skills" , 20, startHeight + (HEIGHT_SPACER * 0), 0x00FFFF);
    		
    		mc.currentScreen.drawString(mc.fontRenderer, "Excavator : " + player.getExcavatorLevel(), 10, startHeight + (HEIGHT_SPACER * 1), 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Completed : " + JobManager.getPercentOfLevel(player.getExcavator()) + "%", 20, startHeight + (HEIGHT_SPACER * 2), 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Logger : " + player.getLoggerLevel(), 10, startHeight + (HEIGHT_SPACER * 3), 0xFFFFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Completed : " + JobManager.getPercentOfLevel(player.getLogger()) + "%", 20, startHeight + (HEIGHT_SPACER * 4), 0xFFFFFF);
    		
    		mc.currentScreen.drawString(mc.fontRenderer, "Sim Health" , startWidth, startHeight + (HEIGHT_SPACER * 0), 0x00FFFF);
    		mc.currentScreen.drawString(mc.fontRenderer, "Hunger : " , startWidth, startHeight + (HEIGHT_SPACER * 1), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 1), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHunger()), startHeight + (HEIGHT_SPACER * 2), GuiHelpers.getRGBAColorForNeedLevel(player.getHunger()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Comfort : " , startWidth, startHeight + (HEIGHT_SPACER * 2), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 2), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getComfort()), startHeight + (HEIGHT_SPACER * 3), GuiHelpers.getRGBAColorForNeedLevel(player.getComfort()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Hygiene : " , startWidth, startHeight + (HEIGHT_SPACER * 3), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 3), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), startHeight + (HEIGHT_SPACER * 4), GuiHelpers.getRGBAColorForNeedLevel(player.getHygiene()));
    		mc.currentScreen.drawString(mc.fontRenderer, "Bladder : " , startWidth, startHeight + (HEIGHT_SPACER * 4), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 4), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), startHeight + (HEIGHT_SPACER * 5), GuiHelpers.getRGBAColorForNeedLevel(70));
    		mc.currentScreen.drawString(mc.fontRenderer, "Energy : " , startWidth, startHeight + (HEIGHT_SPACER * 5), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 5), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), startHeight + (HEIGHT_SPACER * 6), GuiHelpers.getRGBAColorForNeedLevel(60));
    		mc.currentScreen.drawString(mc.fontRenderer, "Fun : " , startWidth, startHeight + (HEIGHT_SPACER * 6), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 6), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), startHeight + (HEIGHT_SPACER * 7), GuiHelpers.getRGBAColorForNeedLevel(50));
    		mc.currentScreen.drawString(mc.fontRenderer, "Social : " , startWidth, startHeight + (HEIGHT_SPACER * 7), 0xFFFFFF);
    		mc.currentScreen.drawRect(startWidth + 40, startHeight + (HEIGHT_SPACER * 7), startWidth + 40 + GuiHelpers.getSizeForNeedLevel(player.getHygiene()), startHeight + (HEIGHT_SPACER * 8), GuiHelpers.getRGBAColorForNeedLevel(40));
    	}
    }
    
    @SubscribeEvent
    public void onTick(ClientTickEvent event)
    {
    }
}