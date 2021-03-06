package mods.simcraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.SimCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BrickBlock extends Block {

	private IIcon[] iconArray;
	
	public BrickBlock(Material material) {
		super(material);
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
			iconArray[i] = par1IconRegister.registerIcon("simcraft:bricks/" + this.getUnlocalizedName().replace("tile.", "") + i);
		}
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return iconArray[(par2 % iconArray.length)];
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
