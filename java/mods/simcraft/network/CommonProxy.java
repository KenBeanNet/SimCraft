package mods.simcraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mods.simcraft.SimCraft;
import mods.simcraft.tileentity.HomeTileEntity;
import mods.simcraft.tileentity.SimObjectTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	
	public void registerHandlers()
    {
		GameRegistry.registerTileEntity(SimObjectTileEntity.class, SimCraft.MODID + "SimObjectTileEntity");
		GameRegistry.registerTileEntity(HomeTileEntity.class, SimCraft.MODID + "HomeTileEntity");
    }
	
	public File getMinecraftDir()
    {
        return new File(".");
    }
}