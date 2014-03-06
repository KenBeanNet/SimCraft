package mods.simcraft.blocks.roofs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RoofBlock extends Block
{
	protected RoofBlock(Material material)
	{
		super(material);
		this.setHardness(5.0F);
		this.setResistance(2.0F);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 8));
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 12));
    }
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return true;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int i)
    {
    	if ((i == 0) || (i == 1) || (i == 2) || (i == 3)) return 0;
        if ((i == 4) || (i == 5) || (i == 6) || (i == 7)) return 4;
        if ((i == 8) || (i == 9) || (i == 10) || (i == 11)) return 8;
        if ((i == 12) || (i == 13) || (i == 14) || (i == 15)) return 12;
        return 225;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2X, int par3Y, int par4Z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) 
    {
    	int l = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
    	int metaData = par1World.getBlockMetadata(par2X, par3Y, par4Z);

        if (l == 0.0F)
        {
          par1World.setBlockMetadataWithNotify(par2X, par3Y, par4Z, 2 + metaData, 0);
        }

        if (l == 1.0F)
        {
          par1World.setBlockMetadataWithNotify(par2X, par3Y, par4Z, 1 + metaData, 1);
        }

        if (l == 2.0F)
        {
          par1World.setBlockMetadataWithNotify(par2X, par3Y, par4Z, 3 + metaData, 2);
        }

        if (l == 3.0F)
        {
          par1World.setBlockMetadataWithNotify(par2X, par3Y, par4Z, 0 + metaData, 3);
        }
    }
    
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        return this.getIcon(side, blockAccess.getBlockMetadata(x, y, z));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return blockIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	blockIcon = par1IconRegister.registerIcon("simcraft:" + this.getUnlocalizedName().replace("tile.", ""));
    }
}
