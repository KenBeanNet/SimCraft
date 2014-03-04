package mods.simcraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.simcraft.SimCraft;
import mods.simcraft.blocks.BrickBlock;
import mods.simcraft.blocks.HomeBlock;
import mods.simcraft.blocks.MarketBlock;
import net.minecraft.block.material.Material;

public class Content 
{
	public static int TOTAL_BRICKS = 128;
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
        Repository.brickBlock = new BrickBlock[TOTAL_BRICKS];
        for (int i = 0; i < TOTAL_BRICKS; i++)
        {
            Repository.brickBlock[i] = new BrickBlock(Material.rock).setBlockName("blockBricks_" + i).setCreativeTab(SimCraft.tabBlocks);
            GameRegistry.registerBlock(Repository.brickBlock[i], "block.blockBricks_" + i);
        }
    }
}
