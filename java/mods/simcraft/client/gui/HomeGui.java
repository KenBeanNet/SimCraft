package mods.simcraft.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import mods.simcraft.SimCraft;
import mods.simcraft.common.Home;
import mods.simcraft.data.HomeManager;
import mods.simcraft.network.packet.PacketCreateHome;
import mods.simcraft.tileentity.HomeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class HomeGui extends GuiScreen
{
	private EntityPlayer playerPar1;
	private GuiTextField txtTownName;
	private String townName;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private HomeTileEntity tileHome;
	
	private GuiButton btnUpgradeLevel;
	private GuiButton btnBuildHome;
	
	public HomeGui(EntityPlayer player, HomeTileEntity tile, int x, int y, int z) {
		playerPar1 = player;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		tileHome = tile;
	}

	@Override 
	public void initGui()
	{
		txtTownName = new GuiTextField(this.fontRendererObj, 180 , this.height - 75, 100, 20);
		txtTownName.setMaxStringLength(16);
		
		btnUpgradeLevel = new GuiButton(1, this.width - 124, 60, 115, 20, "Upgrade Level " + (tileHome.getLevel() + 1));
		btnUpgradeLevel.visible = false;
		this.buttonList.add(btnUpgradeLevel);
		
		btnBuildHome = new GuiButton(0, 195, this.height - 50, 65, 20, "Build");
		btnBuildHome.visible = false;
		this.buttonList.add(btnBuildHome);
		
		this.buttonList.add(new GuiButton(10, this.width - 50, 5, 45, 20, "Close"));
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		if (MyTownHall())
		{
			drawCenteredString(this.fontRendererObj, "Welcome Home!", this.width / 2, 10, 0x6699FF);
			
			drawString(this.fontRendererObj, "Home Stats", 10, 50, 0xFFCC00);
			drawString(this.fontRendererObj, "Level " + tileHome.getLevel(), 10, 60, 0x66CC66);
			drawString(this.fontRendererObj, "Size " + HomeManager.getHomeSize(tileHome.getLevel()), 10, 70, 0x66CC66);
			
			if (tileHome.hasNextLevel())
			{
				drawString(this.fontRendererObj, "Next Level Stats", 10, 100, 0xFFCC00);
				drawString(this.fontRendererObj, "Level " + (tileHome.getLevel() + 1), 10, 110, 0x66CC66);
				drawString(this.fontRendererObj, "Size " + HomeManager.getHomeSize(tileHome.getLevel() + 1), 10, 70, 0x66CC66);
				drawString(this.fontRendererObj, "Upgrade Requirements", 10, 140, 0xFFCC00);
				btnUpgradeLevel.visible = true;
			}
		}
		else if (OtherPlayerTownHall())
		{
			drawCenteredString(this.fontRendererObj, tileHome.getHomeName(), this.width / 2, 10, 0x6699FF);
		}
		else if (AlreadyHaveTownHall())
		{
			drawCenteredString(this.fontRendererObj, "You already have a home!", this.width / 2, 10, 0xFF0000);
		}
		else 
		{
			drawCenteredString(this.fontRendererObj, "Building a Home", this.width / 2, this.height / 25, 0x6699FF);
			
			drawString(this.fontRendererObj, "How to Create a Home", 20, 40, 0xFF0033);
			
			drawString(this.fontRendererObj, "Creating a home is simple.  First you need to name your town below.", 30, 50, 0xFFCC66);
			drawString(this.fontRendererObj, "Then hit the create button.  Your home will start constructing!", 30, 60, 0xFFCC66);
			drawString(this.fontRendererObj, "After it is fully built, you can upgrade your home to increase", 30, 70, 0xFFCC66);
			drawString(this.fontRendererObj, "your total overall space.", 30, 80, 0xFFCC66);
			
			fontRendererObj.drawString("Name Of your Home : ", 65, this.height - 69, 0xFFFFFF, false);
		
			txtTownName.drawTextBox();
			
			btnBuildHome.visible = true;
		}
		
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
		txtTownName.textboxKeyTyped(c, i);
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		txtTownName.mouseClicked(i, j, k);
	}
	
	public void updateScreen()
	{
		townName = txtTownName.getText();
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			Home h = HomeManager.getHomeByPlayerName(playerPar1.getDisplayName());
			if (h == null)
			{
				if (townName.length() < 4)
				{
					playerPar1.addChatMessage(new ChatComponentText("Your town must have more than 4 letters."));
					return;
				}
				
				Home home = new Home();
				home.level = 1;
				home.xCoord = xCoord;
				home.yCoord = yCoord;
				home.zCoord = zCoord;
				home.ownerUsername = playerPar1.getDisplayName();
				home.name = townName;
			
				tileHome.build();
				SimCraft.packetPipeline.sendToServer(new PacketCreateHome(home));
				
				playerPar1.openGui(SimCraft.instance, 1, tileHome.getWorldObj(), xCoord, yCoord, zCoord);
			}
		}
		else if (button.id == 5) //Show Raids
		{
			playerPar1.openGui(SimCraft.instance, 5, tileHome.getWorldObj(), xCoord, yCoord, zCoord);
		}
		else if (button.id == 10)
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	private boolean MyTownHall()
	{
		return AlreadyHaveTownHall() && tileHome != null && tileHome.getOwner().equals(playerPar1.getDisplayName());
	}
	
	private boolean OtherPlayerTownHall()
	{
		return tileHome != null && !tileHome.getOwner().isEmpty();
	}
	
	private boolean AlreadyHaveTownHall()
	{
		return HomeManager.hasHome(playerPar1.getDisplayName());
	}
}

