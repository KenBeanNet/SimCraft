package mods.simcraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FenceBlock extends BlockFence {

	private IIcon[] iconArray;
	
	public FenceBlock(String p_i45406_1_, Material p_i45406_2_) {
		super(p_i45406_1_, p_i45406_2_);
		this.setHardness(4.0F);
		this.setResistance(10.0F);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		this.iconArray = new IIcon[16];
		
		for (int i = 0; i < 16; i++)
		{
			iconArray[i] = par1IconRegister.registerIcon("simcraft:bricks/blockBricks" + this.getUnlocalizedName().replace("tile.blockFences", "") + i);
		}
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return iconArray[(par1 % iconArray.length)];
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
		for (int var4 = 0; var4 < 16; var4++)
	    {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
	    }
    }
}
