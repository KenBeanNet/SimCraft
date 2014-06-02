package mods.simcraft.client.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mods.simcraft.SimCraft;
import mods.simcraft.player.ExtendedPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.GuiIngameForge;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class InGameGui extends GuiIngame
{

	//ExtendedPlayer player;
	
	public InGameGui(Minecraft par1Minecraft) {
		super(par1Minecraft);
	}
	
	@Override
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
		ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(par1), k, l);
        }
        else
        {
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }

        ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
        {
            this.renderPumpkinBlur(k, l);
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

            if (f1 > 0.0F)
            {
                this.func_130015_b(f1, k, l);
            }
        }

        int i1;
        int j1;
        int k1;

        if (!this.mc.playerController.enableEverythingIsScrewedUpMode())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(k / 2 - 91, l - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(k / 2 - 91 - 1 + inventoryplayer.currentItem * 20, l - 22 - 1, 0, 22, 24, 22);
            this.mc.getTextureManager().bindTexture(icons);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(775, 769, 1, 0);
            this.drawTexturedModalRect(k / 2 - 7, l / 2 - 7, 0, 0, 16, 16);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();

            //if (this.mc.playerController.shouldDrawHUD())
            //{
                //this.func_110327_a(k, l);
            //}

            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();

            for (i1 = 0; i1 < 9; ++i1)
            {
                j1 = k / 2 - 90 + i1 * 20 + 2;
                k1 = l - 16 - 3;
                this.renderInventorySlot(i1, j1, k1, par1);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            this.mc.mcProfiler.endSection();
            GL11.glDisable(GL11.GL_BLEND);
        }
        
		//Start SimCraft
		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(mc.thePlayer);

		if (player != null)
		{
			drawCenteredString(fontrenderer, "SimCraft V." + SimCraft.VERSION, k / 2, 5, 0xFFFFFF);
		
			//mc.getTextureManager().bindTexture(GuiResourceFile.guiIcons);
			//mc.ingameGUI.drawTexturedModalRect(5, height - 37, 0, 64, 32, 32);	
			drawString(fontrenderer, "$" + player.getSimoleans(), 4, l - 15, 0xFFFFFF);
		}
		//End Sim Craft
		
        int l4;

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            l4 = this.mc.thePlayer.getSleepTimer();
            float f3 = (float)l4 / 100.0F;

            if (f3 > 1.0F)
            {
                f3 = 1.0F - (float)(l4 - 100) / 10.0F;
            }

            j1 = (int)(220.0F * f3) << 24 | 1052704;
            drawRect(0, 0, k, l, j1);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            this.mc.mcProfiler.endSection();
        }

        l4 = 16777215;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        i1 = k / 2 - 91;
        int l1;
        int i2;
        int k2;
        int j2;
        float f2;
        short short1;

        if (this.mc.thePlayer.isRidingHorse())
        {
            this.mc.mcProfiler.startSection("jumpBar");
            this.mc.getTextureManager().bindTexture(Gui.icons);
            f2 = this.mc.thePlayer.getHorseJumpPower();
            short1 = 182;
            l1 = (int)(f2 * (float)(short1 + 1));
            i2 = l - 32 + 3;
            this.drawTexturedModalRect(i1, i2, 0, 84, short1, 5);

            if (l1 > 0)
            {
                this.drawTexturedModalRect(i1, i2, 0, 89, l1, 5);
            }

            this.mc.mcProfiler.endSection();
        } 
        //Remove this code for SimCraft
        /*else if (this.mc.playerController.gameIsSurvivalOrAdventure())
        {
            this.mc.mcProfiler.startSection("expBar");
            this.mc.getTextureManager().bindTexture(Gui.icons);
            j1 = this.mc.thePlayer.xpBarCap();

            if (j1 > 0)
            {
                short1 = 182;
                l1 = (int)(this.mc.thePlayer.experience * (float)(short1 + 1));
                i2 = l - 32 + 3;
                this.drawTexturedModalRect(i1, i2, 0, 64, short1, 5);

                if (l1 > 0)
                {
                    this.drawTexturedModalRect(i1, i2, 0, 69, l1, 5);
                }
            }

            this.mc.mcProfiler.endSection();

            if (this.mc.thePlayer.experienceLevel > 0)
            {
                this.mc.mcProfiler.startSection("expLevel");
                boolean flag2 = false;
                l1 = flag2 ? 16777215 : 8453920;
                String s3 = "" + this.mc.thePlayer.experienceLevel;
                j2 = (k - fontrenderer.getStringWidth(s3)) / 2;
                k2 = l - 31 - 4;
                boolean flag1 = false;
                fontrenderer.drawString(s3, j2 + 1, k2, 0);
                fontrenderer.drawString(s3, j2 - 1, k2, 0);
                fontrenderer.drawString(s3, j2, k2 + 1, 0);
                fontrenderer.drawString(s3, j2, k2 - 1, 0);
                fontrenderer.drawString(s3, j2, k2, l1);
                this.mc.mcProfiler.endSection();
            }
        }*/ 

        String s2;

        if (this.mc.gameSettings.heldItemTooltips)
        {
            this.mc.mcProfiler.startSection("toolHighlight");

            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
            {
                s2 = this.highlightingItemStack.getDisplayName();
                k1 = (k - fontrenderer.getStringWidth(s2)) / 2;
                l1 = l - 59;

                if (!this.mc.playerController.shouldDrawHUD())
                {
                    l1 += 14;
                }

                i2 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);

                if (i2 > 255)
                {
                    i2 = 255;
                }

                if (i2 > 0)
                {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    fontrenderer.drawStringWithShadow(s2, k1, l1, 16777215 + (i2 << 24));
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.mc.isDemo())
        {
            this.mc.mcProfiler.startSection("demo");
            s2 = "";

            if (this.mc.theWorld.getTotalWorldTime() >= 120500L)
            {
                s2 = I18n.format("demo.demoExpired", new Object[0]);
            }
            else
            {
                s2 = I18n.format("demo.remainingTime", new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))});
            }

            k1 = fontrenderer.getStringWidth(s2);
            fontrenderer.drawStringWithShadow(s2, k - k1 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }

        int i3;
        int k3;
        int j3;

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            fontrenderer.drawStringWithShadow("Minecraft 1.7.2 (" + this.mc.debug + ")", 2, 2, 16777215);
            fontrenderer.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            fontrenderer.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            fontrenderer.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            fontrenderer.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            long i5 = Runtime.getRuntime().maxMemory();
            long k5 = Runtime.getRuntime().totalMemory();
            long j5 = Runtime.getRuntime().freeMemory();
            long i6 = k5 - j5;
            String s = "Used memory: " + i6 * 100L / i5 + "% (" + i6 / 1024L / 1024L + "MB) of " + i5 / 1024L / 1024L + "MB";
            i3 = 14737632;
            this.drawString(fontrenderer, s, k - fontrenderer.getStringWidth(s) - 2, 2, 14737632);
            s = "Allocated memory: " + k5 * 100L / i5 + "% (" + k5 / 1024L / 1024L + "MB)";
            this.drawString(fontrenderer, s, k - fontrenderer.getStringWidth(s) - 2, 12, 14737632);
            int offset = 22;
            for (String brd : FMLCommonHandler.instance().getBrandings(false))
            {
                this.drawString(fontrenderer, brd, k - fontrenderer.getStringWidth(brd) - 2, offset+=10, 14737632);
            }
            j3 = MathHelper.floor_double(this.mc.thePlayer.posX);
            k3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            int l3 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            this.drawString(fontrenderer, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(j3), Integer.valueOf(j3 >> 4), Integer.valueOf(j3 & 15)}), 2, 64, 14737632);
            this.drawString(fontrenderer, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(fontrenderer, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(l3), Integer.valueOf(l3 >> 4), Integer.valueOf(l3 & 15)}), 2, 80, 14737632);
            int i4 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(fontrenderer, "f: " + i4 + " (" + Direction.directions[i4] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(j3, k3, l3))
            {
                Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(j3, l3);
                this.drawString(fontrenderer, "lc: " + (chunk.getTopFilledSegment() + 15) + " b: " + chunk.getBiomeGenForWorldCoords(j3 & 15, l3 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + chunk.getSavedLightValue(EnumSkyBlock.Block, j3 & 15, k3, l3 & 15) + " sl: " + chunk.getSavedLightValue(EnumSkyBlock.Sky, j3 & 15, k3, l3 & 15) + " rl: " + chunk.getBlockLightValue(j3 & 15, k3, l3 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(fontrenderer, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(j3, l3))}), 2, 104, 14737632);

            if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive())
            {
                this.drawString(fontrenderer, String.format("shader: %s", new Object[] {this.mc.entityRenderer.getShaderGroup().getShaderGroupName()}), 2, 112, 14737632);
            }

            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            f2 = (float)this.recordPlayingUpFor - par1;
            k1 = (int)(f2 * 255.0F / 20.0F);

            if (k1 > 255)
            {
                k1 = 255;
            }

            if (k1 > 8)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(k / 2), (float)(l - 68), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                l1 = 16777215;

                if (this.recordIsPlaying)
                {
                    l1 = Color.HSBtoRGB(f2 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                fontrenderer.drawString(this.recordPlaying, -fontrenderer.getStringWidth(this.recordPlaying) / 2, -4, l1 + (k1 << 24 & -16777216));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().func_96539_a(1);

        if (scoreobjective != null)
        {
            this.func_96136_a(scoreobjective, l, k, fontrenderer);
        }

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(l - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();
        scoreobjective = this.mc.theWorld.getScoreboard().func_96539_a(0);

        if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1 || scoreobjective != null))
        {
            this.mc.mcProfiler.startSection("playerList");
            NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
            List list = nethandlerplayclient.playerInfoList;
            i2 = nethandlerplayclient.currentServerMaxPlayers;
            j2 = i2;

            for (k2 = 1; j2 > 20; j2 = (i2 + k2 - 1) / k2)
            {
                ++k2;
            }

            int l5 = 300 / k2;

            if (l5 > 150)
            {
                l5 = 150;
            }

            int l2 = (k - k2 * l5) / 2;
            byte b0 = 10;
            drawRect(l2 - 1, b0 - 1, l2 + l5 * k2, b0 + 9 * j2, Integer.MIN_VALUE);

            for (i3 = 0; i3 < i2; ++i3)
            {
                j3 = l2 + i3 % k2 * l5;
                k3 = b0 + i3 / k2 * 9;
                drawRect(j3, k3, j3 + l5 - 1, k3 + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i3 < list.size())
                {
                    GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo)list.get(i3);
                    ScorePlayerTeam scoreplayerteam = this.mc.theWorld.getScoreboard().getPlayersTeam(guiplayerinfo.name);
                    String s4 = ScorePlayerTeam.formatPlayerName(scoreplayerteam, guiplayerinfo.name);
                    fontrenderer.drawStringWithShadow(s4, j3, k3, 16777215);

                    if (scoreobjective != null)
                    {
                        int j4 = j3 + fontrenderer.getStringWidth(s4) + 5;
                        int k4 = j3 + l5 - 12 - 5;

                        if (k4 - j4 > 5)
                        {
                            Score score = scoreobjective.getScoreboard().func_96529_a(guiplayerinfo.name, scoreobjective);
                            String s1 = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                            fontrenderer.drawStringWithShadow(s1, k4 - fontrenderer.getStringWidth(s1), k3, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(icons);
                    byte b2 = 0;
                    boolean flag3 = false;
                    byte b1;

                    if (guiplayerinfo.responseTime < 0)
                    {
                        b1 = 5;
                    }
                    else if (guiplayerinfo.responseTime < 150)
                    {
                        b1 = 0;
                    }
                    else if (guiplayerinfo.responseTime < 300)
                    {
                        b1 = 1;
                    }
                    else if (guiplayerinfo.responseTime < 600)
                    {
                        b1 = 2;
                    }
                    else if (guiplayerinfo.responseTime < 1000)
                    {
                        b1 = 3;
                    }
                    else
                    {
                        b1 = 4;
                    }

                    this.zLevel += 100.0F;
                    this.drawTexturedModalRect(j3 + l5 - 12, k3, 0 + b2 * 10, 176 + b1 * 8, 10, 8);
                    this.zLevel -= 100.0F;
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}