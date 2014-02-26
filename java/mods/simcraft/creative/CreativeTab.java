package mods.simcraft.creative;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs 
{
	 ItemStack display;
	 
	public CreativeTab(String label) {
		super(label);
		
	}
	
	public void init (ItemStack stack)
	{
		display = stack;
	}
	@Override
	public ItemStack getIconItemStack()
    {
        return display;
    }
	
	@Override
    public Item getTabIconItem ()
    {
        return display.getItem();
    }
}