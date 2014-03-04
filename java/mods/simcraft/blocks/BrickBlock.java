package mods.simcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.simcraft.SimCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BrickBlock extends Block {

	private IIcon[] iconArray = new IIcon[16];
	
	public BrickBlock(Material material) {
		super(material);
		this.setHardness(4.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(SimCraft.tabBlocks);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon("simcraft:bricks/" + this.getUnlocalizedName().replace("tile.", ""));
    }
}
