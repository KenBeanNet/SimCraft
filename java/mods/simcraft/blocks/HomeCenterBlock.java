package mods.simcraft.blocks;

import mods.simcraft.SimCraft;
import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class HomeCenterBlock extends Block 
{
	public HomeCenterBlock(Material material) {
		super(material);

		setBlockUnbreakable();
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
	    	par5EntityPlayer.openGui(SimCraft.instance, 4, par1World, par2, par3, par4);
	        return true;
	    }
	}
	
}
