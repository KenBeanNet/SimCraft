package mods.simcraft.blocks;

import java.util.Random;

import mods.simcraft.SimCraft;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MarketBlock extends SimObjectBlock  
{
	public MarketBlock(Material par1Material)
	{
		super(par1Material);
	
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
	    if (par1World.isRemote)
	    {
	        return true;
	    }
	    else
	    {
	    	MarketTileEntity teHome = (MarketTileEntity)par1World.getTileEntity(par2, par3, par4);
	    	
	    	if (teHome.isCompleted())
	    	{
	    		par5EntityPlayer.openGui(SimCraft.instance, 2, par1World, par2, par3, par4);
	    	}
	    	else
	    		par5EntityPlayer.openGui(SimCraft.instance, 1, par1World, par2, par3, par4);
	    	
	        return true;
	    }
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new MarketTileEntity();
	}
}
