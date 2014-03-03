package mods.simcraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class SimGui extends GuiScreen 
{

	protected GuiButton btnHelp;
	
	@Override 
	public void initGui()
	{
		btnHelp = new GuiButton(9, 5, 5, 45, 20, "Help");
		btnHelp.visible = false;
		this.buttonList.add(btnHelp);
		
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
}
