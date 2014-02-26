package mods.simcraft.client.gui;

import java.util.HashMap;
import java.util.Map;

import mods.simcraft.SimCraft;
import mods.simcraft.common.DefaultContainer;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import mods.simcraft.tileentity.SimObjectTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
		{
			default: return new DefaultContainer();
			case 0: return new DefaultContainer();
			case 1: return new DefaultContainer();
			case 2: return new MarketContainer(player.inventory, (MarketTileEntity)world.getTileEntity(x, y, z), 0, 0);
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
		{
			default: return null;
			case 0: return new HomeGui(player, (HomeTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 1: return new BuildingGui((SimObjectTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 2: return new MarketGui(player, (MarketTileEntity)world.getTileEntity(x, y, z), x, y, z);
		}
	}
 
}