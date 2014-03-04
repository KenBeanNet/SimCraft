package mods.simcraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.simcraft.SimCraft;
import mods.simcraft.blocks.BrickBlock;
import mods.simcraft.blocks.CarpetBlock;
import mods.simcraft.blocks.FenceBlock;
import mods.simcraft.blocks.FlowerBlock;
import mods.simcraft.blocks.HomeBlock;
import mods.simcraft.blocks.HomeCenterBlock;
import mods.simcraft.blocks.MarketBlock;
import mods.simcraft.blocks.meta.FenceBlockMeta;
import mods.simcraft.blocks.meta.FlowerBlockMeta;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemLeaves;

public class Content 
{
	public static final int TOTAL_BRICKS = 8;
	public static final int TOTAL_CARPETS = 2;
	public static final int TOTAL_FLOWERS = 3;
	public Content()
	{
		registerBlocks();
		registerBricks();
		registerFences();
		registerCarpets();
		registerFlowers();
	}
	
	void registerBlocks ()
    {
        Repository.homeBlock = new HomeBlock(Material.wood).setBlockName("HomeBlock").setCreativeTab(SimCraft.tabBlocks);
        GameRegistry.registerBlock(Repository.homeBlock, "block.home");
        Repository.marketBlock = new MarketBlock(Material.wood).setBlockName("MarketBlock").setCreativeTab(SimCraft.tabBlocks);
        GameRegistry.registerBlock(Repository.marketBlock, "block.market");
        Repository.homeCenterBlock = new HomeCenterBlock(Material.wood).setBlockName("HomeCenterBlock").setCreativeTab(SimCraft.tabBlocks);
        GameRegistry.registerBlock(Repository.homeCenterBlock, "block.homecenter");
        Repository.brickBlock = new BrickBlock[TOTAL_BRICKS];
    }
	
	void registerBricks()
	{
        for (int i = 0; i < TOTAL_BRICKS; i++)
        {
            Repository.brickBlock[i] = new BrickBlock(Material.rock).setBlockName("blockBricks" + i + "_").setCreativeTab(SimCraft.tabBricks);
            GameRegistry.registerBlock(Repository.brickBlock[i], "block.blockBricks" + i + "_");
        }
	}
	
	void registerFences()
	{
		Repository.blockFences = new FenceBlock[TOTAL_BRICKS];
        for (int i = 0; i < TOTAL_BRICKS; i++)
        {
            Repository.blockFences[i] = new FenceBlock("stone", Material.rock).setBlockName("blockFences" + i + "_").setCreativeTab(SimCraft.tabFences);
            GameRegistry.registerBlock(Repository.blockFences[i], FenceBlockMeta.class, "block.blockFences" + i + "_");
        }
	}
	
	void registerCarpets()
	{
		Repository.blockCarpets = new CarpetBlock[TOTAL_CARPETS];
        for (int i = 0; i < TOTAL_CARPETS; i++)
        {
            Repository.blockCarpets[i] = new CarpetBlock().setBlockName("blockCarpets" + i + "_").setCreativeTab(SimCraft.tabCarpets);
            GameRegistry.registerBlock(Repository.blockCarpets[i], "block.blockCarpets" + i + "_");
        }
	}
	
	void registerFlowers()
	{
		Repository.blockFlowers = new FlowerBlock[TOTAL_FLOWERS];
        for (int i = 0; i < TOTAL_FLOWERS; i++)
        {
            Repository.blockFlowers[i] = new FlowerBlock(i).setBlockName("blockFlowers" + i + "_").setCreativeTab(SimCraft.tabFlowers);
            GameRegistry.registerBlock(Repository.blockFlowers[i], FlowerBlockMeta.class, "block.blockFlowers" + i + "_");
        }
	}
}
