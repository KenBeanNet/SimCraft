package mods.simcraft.player;

import mods.simcraft.SimCraft;
import mods.simcraft.data.JobManager;
import mods.simcraft.network.CommonProxy;
import mods.simcraft.network.packet.PacketExtendedInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	private int logger;
	
	private int hunger = 100;
	private int comfort = 100;
	private int hygiene = 100;
	private int bladder = 100;
	private int energy = 100;
	private int fun = 100;
	private int social = 100;
	
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
		compound.setInteger("Logger", this.logger);
		
		compound.setInteger("Hunger", this.hunger);
		compound.setInteger("Comfort", this.comfort);
		compound.setInteger("Hygiene", this.hygiene);
		compound.setInteger("Bladder", this.bladder);
		compound.setInteger("Energy", this.energy);
		compound.setInteger("Fun", this.fun);
		compound.setInteger("Social", this.social);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		simoleans = (compound.getInteger("Simoleans"));
		excavator = (compound.getInteger("Excavator"));
		logger = (compound.getInteger("Logger"));
		
		hunger = (compound.getInteger("Hunger"));
		comfort = (compound.getInteger("Comfort"));
		hygiene = (compound.getInteger("Hygiene"));
		bladder = (compound.getInteger("Bladder"));
		energy = (compound.getInteger("Energy"));
		fun = (compound.getInteger("Fun"));
		social = (compound.getInteger("Social"));
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
		markDirty();
	}
	
	public void setExcavator(int value)
	{
		excavator = value;
	}
	
	public int getExcavator()
	{
		return excavator;
	}
	
	public int getExcavatorLevel()
	{
		return JobManager.getLevelByExp(excavator);
	}
	
	public int getLogger()
	{
		return logger;
	}
	
	public void setLogger(int value)
	{
		logger = value;
	}
	
	public void addLogger(int value)
	{
		int oldLevel = getLoggerLevel();
		logger += value;
		markDirty();
		
		if (oldLevel != getLoggerLevel())
			levelUp();
	}
	
	public void addExcavator(int value)
	{
		int oldLevel = getExcavatorLevel();
		excavator += value;
		markDirty();
		
		if (oldLevel != getExcavatorLevel())
			levelUp();
	}
	public int getLoggerLevel() {
		return JobManager.getLevelByExp(logger);
	}

	private void markDirty()
	{
		SimCraft.packetPipeline.sendTo(new PacketExtendedInfo(getExtendedPlayer(player)), (EntityPlayerMP)player);
	}
	
	private void levelUp()
	{
		player.worldObj.playSoundEffect((double)((float)player.posX + 0.5F), (double)((float)player.posY + 0.5F), (double)((float)player.posZ + 0.5F), SimCraft.MODID + ":levelup", 2.0f, 2.0f);
	}
	
	public void setHunger(int value)
	{
		hunger = value;
	}
	public int getHunger()
	{
		return hunger;
	}
	public void addHunger(int value)
	{
		hunger += value;
		if (hunger > 100)
			hunger = 100;
		markDirty();
	}
	public void removeHunger(int value)
	{
		hunger -= value;
		if (hunger < 0)
			hunger = 0;
		markDirty();
	}
	public void setComfort(int value)
	{
		comfort = value;
	}
	public int getComfort()
	{
		return comfort;
	}
	public void addComfort(int value)
	{
		comfort += value;
		if (comfort > 100)
			comfort = 100;
		markDirty();
	}
	public void removeComfort(int value)
	{
		comfort -= value;
		if (comfort < 0)
			comfort = 0;
		markDirty();
	}
	public void setHygiene(int value)
	{
		hygiene = value;
	}
	public int getHygiene()
	{
		return hygiene;
	}
	public void addHygiene(int value)
	{
		hygiene += value;
		if (hygiene > 100)
			hygiene = 100;
		markDirty();
	}
	public void removeHygiene(int value)
	{
		hygiene -= value;
		if (hygiene < 0)
			hygiene = 0;
		markDirty();
	}
}
