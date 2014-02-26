package mods.simcraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.simcraft.SimCraft;
import mods.simcraft.blocks.HomeBlock;
import mods.simcraft.blocks.MarketBlock;
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
        Repository.marketBlock = new MarketBlock(Material.wood).setBlockName("MarketBlock").setCreativeTab(SimCraft.tabBlocks);
        GameRegistry.registerBlock(Repository.marketBlock, "block.market");
    }
}
