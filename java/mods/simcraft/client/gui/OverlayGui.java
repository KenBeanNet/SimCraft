package mods.simcraft.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.SimCraft;
import mods.simcraft.player.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

public class OverlayGui
{
    Minecraft mc;
    ExtendedPlayer player;
    
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
    		if (player == null)
    			player = ExtendedPlayer.getExtendedPlayer(mc.thePlayer);

    		GuiIngame gui = mc.ingameGUI;
    		ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
    		int width = res.getScaledWidth();
			int height = res.getScaledHeight();
			
			gui.drawCenteredString(mc.fontRenderer, "SimCraft V." + SimCraft.VERSION, width / 2, 5, 0xFFFFFF);
			
    		mc.getTextureManager().bindTexture(GuiResourceFile.guiIcons);
    		
			
			gui.drawTexturedModalRect(5, height - 37, 0, 64, 32, 32);
			
    		gui.drawString(mc.fontRenderer, "$" + player.getSimoleans(), 38, height - 15, 0xFFFFFF);
		}
    }
}