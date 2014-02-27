package mods.simcraft.player;

import mods.simcraft.network.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{
	public static final String EXT_PLAYER = "SCExtended";
	
	// I always include the entity to which the properties belong for easy access
	// It's final because we won't be changing which player it is
	private final EntityPlayer player;

	private int simoleans;
	private int excavator;
	
	public ExtendedPlayer(EntityPlayer player)
	{
		this.player = player;
	}

	public static ExtendedPlayer getExtendedPlayer(EntityPlayer player)
	{
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PLAYER);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger("Simoleans", this.simoleans);
		compound.setInteger("Excavator", this.excavator);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.simoleans = (compound.getInteger("Simoleans"));
		this.excavator = (compound.getInteger("Excavator"));
	}

	@Override
	public void init(Entity entity, World world) {
		System.out.println("[SimCraft] Initializing extended properties.");
		
	}

	public static void saveProxyData(EntityPlayer player) 
	{
		ExtendedPlayer playerData = ExtendedPlayer.getExtendedPlayer(player);

		CommonProxy.storeEntityData(player.getDisplayName(), playerData);
	}
	
	public static void loadProxyData(EntityPlayer player) 
	{
		ExtendedPlayer savedData = CommonProxy.getEntityData(player.getDisplayName());
		
		if(savedData != null) {
			player.registerExtendedProperties(ExtendedPlayer.EXT_PLAYER, savedData);
		}
	}
	
	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(ExtendedPlayer.EXT_PLAYER, new ExtendedPlayer(player));
	}
	
	public void setSimoleans(int value)
	{
		simoleans = value;
	}
	
	public int getSimoleans()
	{
		return simoleans;
	}
	
	public void addSimoleans(int value)
	{
		simoleans += value;
	}
	
	public void setExcavator(int value)
	{
		excavator = value;
	}
	
	public int getExcavator()
	{
		return excavator;
	}
	
	public void addExcavator(int value)
	{
		excavator += value;
	}

}
