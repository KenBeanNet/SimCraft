package mods.simcraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class CarpetBlock extends BlockCarpet
{
	private IIcon[] iconArray;
	
	public CarpetBlock()
	  {
	    setHardness(0.8F);
	    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	  }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return iconArray[(p_149691_2_ % this.iconArray.length)];
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
		iconArray = new IIcon[16];
		for (int i = 0; i < 16; i++)
		{
			iconArray[i] = par1IconRegister.registerIcon("simcraft:carpets/" + this.getUnlocalizedName().replace("tile.", "") + i);
		}
    }
}
