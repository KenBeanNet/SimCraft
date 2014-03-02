package mods.simcraft.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

public class OverlayGui
{
    Minecraft mc;
	public OverlayGui()
	{
		mc = Minecraft.getMinecraft();
	}

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTick (RenderTickEvent event)
    {
    	if (mc.inGameHasFocus)
		{
    		mc.getTextureManager().bindTexture(GuiResourceFile.guiIcons);
    		mc.entityRenderer.setupOverlayRendering();
    		GuiIngame gui = mc.ingameGUI;
    		ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    		int width = res.getScaledWidth();
			int height = res.getScaledHeight();
			
			mc.thePlayer.rotationYaw += 10.0F;

			
			gui.drawString(mc.fontRenderer, "Raid Event :", 10, 20, 0xFFFFFF);
			
    		gui.drawTexturedModalRect(10, 10, 0, 0, 32, 32);
		}
    }
}