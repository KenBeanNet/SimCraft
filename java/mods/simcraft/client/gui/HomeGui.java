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
public class HomeGui extends SimGui
{
	private EntityPlayer playerPar1;
	private GuiTextField txtTownName;
	private String townName;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private short homeType;
	private HomeTileEntity tileHome;
	
	private GuiButton btnUpgradeLevel;
	private GuiButton btnBuildHome;
	
	private GuiButton btnResidential;
	private GuiButton btnCommercial;
	private GuiButton btnIndustrial;
	
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
		super.initGui();
		
		txtTownName = new GuiTextField(this.fontRendererObj, 90 , 95, 100, 20);
		txtTownName.setMaxStringLength(16);
		
		btnUpgradeLevel = new GuiButton(1, this.width - 124, 60, 115, 20, "Upgrade Level " + (tileHome.getLevel() + 1));
		btnUpgradeLevel.visible = false;
		this.buttonList.add(btnUpgradeLevel);
		
		btnBuildHome = new GuiButton(0, this.width / 2  - 60, this.height - 25, 120, 20, "Start Construction");
		btnBuildHome.visible = false;
		this.buttonList.add(btnBuildHome);
		
		btnResidential = new GuiButton(5, 88, 132, 60, 20, "Residential");
		btnResidential.visible = false;
		this.buttonList.add(btnResidential);
		btnCommercial = new GuiButton(6, 148, 132, 60, 20, "Commercial");
		btnCommercial.visible = false;
		this.buttonList.add(btnCommercial);
		btnIndustrial = new GuiButton(7, 208, 132, 60, 20, "Industrial");
		btnIndustrial.visible = false;
		this.buttonList.add(btnIndustrial);
		
		btnHelp.visible = true;
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
			
			drawString(this.fontRendererObj, "Creating a home is simple.  First you need to name your home below.", 30, 50, 0xFFCC66);
			drawString(this.fontRendererObj, "Then hit the create button.  Your home will start constructing!", 30, 60, 0xFFCC66);
			drawString(this.fontRendererObj, "After it is fully built, you can upgrade your home to increase", 30, 70, 0xFFCC66);
			drawString(this.fontRendererObj, "your total overall space.", 30, 80, 0xFFCC66);
			
			fontRendererObj.drawString("Home Name : ", 20, 102, 0xFFFFFF, false);
			
			fontRendererObj.drawString("Home Type : ", 20, 140, 0xFFFFFF, false);
			
			fontRendererObj.drawString("Home Design : (Coming Soon)", 20, 178, 0xFFFFFF, false);
		
			txtTownName.drawTextBox();
			
			switch (homeType)
			{
				default:
				{
					btnResidential.packedFGColour = 0xFFFFFF;
					btnCommercial.packedFGColour = 0xFFFFFF;
					btnIndustrial.packedFGColour = 0xFFFFFF;
					break;
				}
				case 1:
				{
					btnResidential.packedFGColour = 0x00FFFF;
					btnCommercial.packedFGColour = 0xFFFFFF;
					btnIndustrial.packedFGColour = 0xFFFFFF;
					break;
				}
				case 2:
				{
					btnResidential.packedFGColour = 0xFFFFFF;
					btnCommercial.packedFGColour = 0x00FFFF;
					btnIndustrial.packedFGColour = 0xFFFFFF;
					break;
				}
				case 3:
				{
					btnResidential.packedFGColour = 0xFFFFFF;
					btnCommercial.packedFGColour = 0xFFFFFF;
					btnIndustrial.packedFGColour = 0x00FFFF;
					break;
				}
			}
			btnBuildHome.visible = true;
			btnResidential.visible = true;
			btnCommercial.visible = true;
			btnIndustrial.visible = true;
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
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		if (button.id == 0)
		{
			Home h = HomeManager.getHomeByPlayerName(playerPar1.getDisplayName());
			if (h == null)
			{
				if (townName.length() < 4)
				{
					playerPar1.addChatMessage(new ChatComponentText("[SimCraft] Your town must have more than 4 letters."));
					return;
				}
				else if (homeType == 0)
				{
					playerPar1.addChatMessage(new ChatComponentText("[SimCraft] You must select a Home Type."));
					return;
				}
				
				Home home = new Home();
				home.level = 1;
				home.xCoord = xCoord;
				home.yCoord = yCoord;
				home.zCoord = zCoord;
				home.ownerUsername = playerPar1.getDisplayName();
				home.name = townName;
				home.type = homeType;
			
				tileHome.build();
				SimCraft.packetPipeline.sendToServer(new PacketCreateHome(home));
				
				playerPar1.openGui(SimCraft.instance, 1, tileHome.getWorldObj(), xCoord, yCoord, zCoord);
			}
		}
		else if (button.id == 5)
			homeType = 1;
		else if (button.id == 6)
			homeType = 2;
		else if (button.id == 7)
			homeType = 3;
		else if (button.id == 9) //Help
		{
			
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

