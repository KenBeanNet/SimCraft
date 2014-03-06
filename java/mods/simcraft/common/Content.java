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
import mods.simcraft.blocks.meta.SimBlockMeta;
import mods.simcraft.blocks.roofs.RoofCornerBlock;
import mods.simcraft.blocks.roofs.RoofIntCornerBlock;
import mods.simcraft.blocks.roofs.RoofSlopeBlock;
import mods.simcraft.data.MarketManager;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemLeaves;

public class Content 
{
	public static final int TOTAL_BRICKS = 8;
	public static final int TOTAL_CARPETS = 2;
	public static final int TOTAL_FLOWERS = 3;
	public static final int TOTAL_ROOFS = 2;
	public Content()
	{
		registerBlocks();
		registerBricks();
		registerFences();
		registerCarpets();
		registerFlowers();
		registerRoofs();
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
            Repository.blockFences[i] = new FenceBlock("stone", Material.rock).setBlockName("blockFences" + i + "_").setBlockTextureName("blockBricks" + i + "_").setCreativeTab(SimCraft.tabFences);
            GameRegistry.registerBlock(Repository.blockFences[i], FenceBlockMeta.class, "blockFences" + i + "_");
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
            Repository.blockFlowers[i] = new FlowerBlock(i).setBlockName("blockFlowers" + i + "_").setBlockTextureName("blockFlowers" + i + "_").setCreativeTab(SimCraft.tabFlowers);
            GameRegistry.registerBlock(Repository.blockFlowers[i], FlowerBlockMeta.class, "blockFlowers" + i + "_");
        }
	}
	
	void registerRoofs()
	{
		Repository.blockRoofCorners = new RoofCornerBlock[TOTAL_ROOFS];
		Repository.blockRoofIntCorners = new RoofIntCornerBlock[TOTAL_ROOFS];
		Repository.blockRoofSlopes = new RoofSlopeBlock[TOTAL_ROOFS];
		
        for (int i = 0; i < TOTAL_ROOFS; i++)
        {
            Repository.blockRoofCorners[i] = new RoofCornerBlock(Material.rock).setBlockName("blockRoofCorner" + i).setBlockTextureName("blockRooftop_" + i).setCreativeTab(SimCraft.tabRoofs);
            Repository.blockRoofIntCorners[i] = new RoofIntCornerBlock(Material.rock).setBlockName("blockRoofIntCorner" + i).setBlockTextureName("blockRooftop_" + i).setCreativeTab(SimCraft.tabRoofs);
            Repository.blockRoofSlopes[i] = new RoofSlopeBlock(Material.rock).setBlockName("blockRoofSlope" + i).setBlockTextureName("blockRooftop_" + i).setCreativeTab(SimCraft.tabRoofs);
            GameRegistry.registerBlock(Repository.blockRoofCorners[i], SimBlockMeta.class, "blockRoofCorner" + i + "_");
            GameRegistry.registerBlock(Repository.blockRoofIntCorners[i], SimBlockMeta.class, "blockRoofIntCorner" + i + "_");
            GameRegistry.registerBlock(Repository.blockRoofSlopes[i], SimBlockMeta.class, "blockRoofSlope" + i + "_");
        }
	}
}
