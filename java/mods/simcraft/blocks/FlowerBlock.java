package mods.simcraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.data.MarketManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.IPlantable;

public class FlowerBlock extends BlockFlower implements IPlantable
{
	private IIcon[] iconArray;
	
	public FlowerBlock(int id) {
		super(id);
		setHardness(0.0F);
		setResistance(0.0F);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return iconArray[(par2 % iconArray.length)];
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		iconArray = new IIcon[16];
		for (int i = 0; i < 16; i++)
		{
			iconArray[i] = par1IconRegister.registerIcon("simcraft:flowers/" + this.getTextureName() + i);
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
		for (int var4 = 0; var4 < 16; var4++)
	    {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
	    }
    }
    
	/**
     * is the block grass, dirt or farmland
     */
	@Override
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == Blocks.grass || p_149854_1_ == Blocks.dirt || p_149854_1_ == Blocks.farmland;
    }
}
