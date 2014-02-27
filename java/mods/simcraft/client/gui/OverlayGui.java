package mods.simcraft.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

public class OverlayGui
{
    Minecraft mc = Minecraft.getMinecraft();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tickEnd (ClientTickEvent event)
    {
    	if (mc.inGameHasFocus)
		{
    		mc.getTextureManager().bindTexture(GuiResourceFile.guiIcons);
    		mc.entityRenderer.setupOverlayRendering();
    		GuiIngame gui = mc.ingameGUI;
    		ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    		int width = res.getScaledWidth();
			int height = res.getScaledHeight();
    		
			
			mc.fontRenderer.drawString("Raid Event :", 0, 14, 0x00000);
			
    		gui.drawTexturedModalRect(10, 10, 0, 0, 32, 32);
		}
    }
}