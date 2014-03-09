package mods.simcraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import mods.simcraft.blocks.roofs.render.CornersRenderer;
import mods.simcraft.client.gui.BuildingGui;
import mods.simcraft.client.gui.HomeCenterGui;
import mods.simcraft.client.gui.HomeGui;
import mods.simcraft.client.gui.MarketBuyGui;
import mods.simcraft.client.gui.MarketSellGui;
import mods.simcraft.client.gui.OverlayGui;
import mods.simcraft.common.Repository;
import mods.simcraft.item.render.ItemHomeRenderer;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.MarketTileEntity;
import mods.simcraft.tileentity.SimObjectTileEntity;
import mods.simcraft.tileentity.render.ModelHome;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy 
{
	@Override
    public void registerHandlers()
    {
		super.registerHandlers();
		
		//Client On Screen GUI Display
		FMLCommonHandler.instance().bus().register(new OverlayGui());
	
		//Register Renders to Models
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Repository.homeBlock), new ItemHomeRenderer());
        
        
        ClientRegistry.bindTileEntitySpecialRenderer(HomeTileEntity.class, new ModelHome());
        

		
		Repository.SlopesRenderID = RenderingRegistry.getNextAvailableRenderId();
        //RenderingRegistry.registerBlockHandler(Repository.SlopesRenderID, new RenderSlopes());
        Repository.IntCornersRenderID = RenderingRegistry.getNextAvailableRenderId();
        //RenderingRegistry.registerBlockHandler(Repository.IntCornersRenderID, new RenderIntCorners());
        Repository.CornersRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(Repository.CornersRenderID, new CornersRenderer());
    }
	
	public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
		{
			case 0: 
				return new HomeGui(player, (HomeTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 1: 
				return new BuildingGui((SimObjectTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 2: 
				return new MarketSellGui(player, (MarketTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 3:
				return new MarketBuyGui(player, (MarketTileEntity)world.getTileEntity(x, y, z), x, y, z);
			case 4:
				return new HomeCenterGui(player);
		}
		return null;
	}
	
}
