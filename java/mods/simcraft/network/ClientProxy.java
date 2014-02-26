package mods.simcraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import mods.simcraft.common.Repository;
import mods.simcraft.item.render.ItemHomeRenderer;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.render.ModelHome;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy 
{
	@Override
    public void registerHandlers()
    {
		super.registerHandlers();
		
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Repository.homeBlock), new ItemHomeRenderer());
        
        ClientRegistry.bindTileEntitySpecialRenderer(HomeTileEntity.class, new ModelHome());
    }
	
	public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }
	
}
