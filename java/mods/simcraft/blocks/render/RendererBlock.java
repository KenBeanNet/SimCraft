package mods.simcraft.blocks.render;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RendererBlock
{
	  public static IBlockAccess blockAccess;
	  public static Icon overrideBlockTexture = null;
	
	  public static int uvRotateEast = 0;
	  public static int uvRotateWest = 0;
	  public static int uvRotateSouth = 0;
	  public static int uvRotateNorth = 0;
	  public static int uvRotateTop = 0;
	  public static int uvRotateBottom = 0;
	  public static double renderMinX;
	  public static double renderMaxX;
	  public static double renderMinY;
	  public static double renderMaxY;
	  public static double renderMinZ;
	  public static double renderMaxZ;
	  public static Minecraft minecraftRB;
	
	  public RendererBlock(IBlockAccess par1IBlockAccess)
	  {
		  blockAccess = par1IBlockAccess;
		  minecraftRB = Minecraft.getMinecraft();
	  }
	
	  public RendererBlock()
	  {
		  minecraftRB = Minecraft.getMinecraft();
	  }
	  
	  public static IIcon getBlockIcon(Block par1Block, IBlockAccess iblockaccess, int par2, int par3, int par4, int i)
	  {
		  return getIconSafe(par1Block.getBlockTextureFromSide(1));
	  }

	  public static IIcon getIconSafe(IIcon iIcon)
	  {
		  if (iIcon == null)
		  {
			  //iIcon = ((TextureMap)Minecraft.getMinecraft().getTextureMapBlocks().(TextureMap.locationBlocksTexture)).("missingno");
		  }

		  return iIcon;
	  }

	  public static IIcon getBlockIconFromSide(Block par1Block, int par2)
	  {
		  return getIconSafe(par1Block.getBlockTextureFromSide(par2));
	  }

	  public static IIcon getBlockIconFromSideAndMetadata(Block par1Block, int par2, int par3)
	  {
		  return getIconSafe(par1Block.getIcon(par2, par3));
	  }

	  public static IIcon getBlockIcon(Block par1Block)
	  {
		  return getIconSafe(par1Block.getBlockTextureFromSide(1));
	  }

	  public static boolean hasOverrideBlockTexture()
	  {
		  return overrideBlockTexture != null;
	  }

	  public static void setOverrideBlockTexture(Icon par1Icon)
	  {
		  overrideBlockTexture = par1Icon;
	  }
}
