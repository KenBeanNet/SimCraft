package mods.simcraft.blocks.meta;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.common.Repository;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FlowerBlockMeta extends ItemBlock 
{
	public FlowerBlockMeta(Block block) {
		super(block);
	}

	/**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return this.field_150939_a.func_149735_b(2, par1);
    }
    
    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.field_150939_a.getUnlocalizedName().replace("tile.", "") + par1ItemStack.getItemDamage();
    }
}
