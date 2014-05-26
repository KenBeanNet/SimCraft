package mods.simcraft.blocks;

import mods.simcraft.SimCraft;
import mods.simcraft.tileentity.MarketTileEntity;
import mods.simcraft.tileentity.SupplyChestTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SupplyChestBlock extends SimObjectBlock  
{
	public SupplyChestBlock(Material par1Material)
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
	    	SupplyChestTileEntity teHome = (SupplyChestTileEntity)par1World.getTileEntity(par2, par3, par4);
	    	
	    	if (teHome.isCompleted())
	    	{
	    		par5EntityPlayer.openGui(SimCraft.instance, 5, par1World, par2, par3, par4);
	    	}
	    	else
	    		par5EntityPlayer.openGui(SimCraft.instance, 1, par1World, par2, par3, par4);
	    	
	        return true;
	    }
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new SupplyChestTileEntity();
	}
}