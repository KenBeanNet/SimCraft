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
	
	//You don't want the normal render type, or it wont render properly.
    @Override
    public int getRenderType() 
    {
            return -1;
    }
    
    //It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube() {
            return false;
    }
   
    //It's not a normal block, so you need this too.
    public boolean renderAsNormalBlock() {
            return false;
    }
	
	@Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }
	
	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}
	
	public int getMobilityFlag()
    {
		return 2; //Do not allow to be moved by Pistons or such.
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new MarketTileEntity();
	}
}
