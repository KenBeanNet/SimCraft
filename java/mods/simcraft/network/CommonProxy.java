package mods.simcraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mods.simcraft.SimCraft;
import mods.simcraft.blocks.roofs.render.CornersRenderer;
import mods.simcraft.client.gui.OverlayGui;
import mods.simcraft.common.Repository;
import mods.simcraft.inventory.DefaultContainer;
import mods.simcraft.inventory.MarketContainer;
import mods.simcraft.inventory.SupplyChestContainer;
import mods.simcraft.player.ExtendedPlayer;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import mods.simcraft.tileentity.SimObjectTileEntity;
import mods.simcraft.tileentity.SupplyChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public void registerHandlers()
    {
		GameRegistry.registerTileEntity(SimObjectTileEntity.class, SimCraft.MODID + "SimObjectTileEntity");
		GameRegistry.registerTileEntity(HomeTileEntity.class, SimCraft.MODID + "HomeTileEntity");
		GameRegistry.registerTileEntity(MarketTileEntity.class, SimCraft.MODID + "MarketSoldTileEntity");
		GameRegistry.registerTileEntity(SupplyChestTileEntity.class, SimCraft.MODID + "SupplyChestTileEntity");
    }
	
	public File getMinecraftDir()
    {
        return new File(".");
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
		{
			case 0: 
				return new DefaultContainer();
			case 1: 
				return new DefaultContainer();
			case 2: 
				return new MarketContainer(player.inventory, (MarketTileEntity)world.getTileEntity(x, y, z));
			case 3: 
				return new DefaultContainer();
			case 4:
				return new DefaultContainer();
			case 5:
				return new SupplyChestContainer(player.inventory, (SupplyChestTileEntity)world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	* Adds an entity's custom data to the map for temporary storage
	* @param compound An NBT Tag Compound that stores the IExtendedEntityProperties data only
	*/
	public static void storeEntityData(String name, NBTTagCompound compound)
	{
		extendedEntityData.put(name, compound);
	}
		
	/**
	* Removes the compound from the map and returns the NBT tag stored for name or null if none exists
	*/
	public static NBTTagCompound getEntityData(String name)
	{
		return extendedEntityData.remove(name);
	}
	
	public static boolean containsEntityData(String name)
	{
		return extendedEntityData.containsKey(name);
	}
}