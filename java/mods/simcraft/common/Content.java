package mods.simcraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.simcraft.SimCraft;
import mods.simcraft.blocks.HomeBlock;
import net.minecraft.block.material.Material;

public class Content 
{

	public Content()
	{
		registerBlocks();
	}
	
	void registerBlocks ()
    {
        Repository.homeBlock = new HomeBlock(Material.wood).setBlockName("HomeBlock").setCreativeTab(SimCraft.tabBlocks);
        GameRegistry.registerBlock(Repository.homeBlock, "block.home");
    }
}
