package mods.simcraft.player;

import java.util.Random;

import mods.simcraft.SimCraft;
import mods.simcraft.data.JobManager;
import mods.simcraft.network.CommonProxy;
import mods.simcraft.network.packet.PacketExtendedInfo;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{
	public static final String EXT_PLAYER = "SCExtended";
	public static final short TICK_CYCLE_DURATION = 20;
	public static final short HUNGER_TICK_CYCLE = 1080; // Hunger Empty in 1 hour 30 minutes
	
	// I always include the entity to which the properties belong for easy access
	// It's final because we won't be changing which player it is
	private final EntityPlayer player;
	private short tickCycle = 0;
	private short lastSoundEffect = 0;
	private Random random = new Random();

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

	public static void saveProxyData(EntityPlayer par1Player) 
	{
		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(par1Player);
		NBTTagCompound playerData = new NBTTagCompound();
		player.saveNBTData(playerData);
		CommonProxy.storeEntityData(par1Player.getDisplayName(), playerData);
	}
	
	public static void loadProxyData(EntityPlayer par1Player) 
	{
		NBTTagCompound savedData = CommonProxy.getEntityData(par1Player.getDisplayName());
		ExtendedPlayer player = ExtendedPlayer.getExtendedPlayer(par1Player);
		
		if(savedData != null && player != null) {
			player.loadNBTData(savedData);
		}
	}
	
	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(ExtendedPlayer.EXT_PLAYER, new ExtendedPlayer(player));
	}
	
	public void runLife()
	{
		if (tickCycle % TICK_CYCLE_DURATION == 0)
		{

			player.getFoodStats().setFoodLevel(10);

			if (tickCycle % HUNGER_TICK_CYCLE == 0)
			{
				removeHunger(1);
				if (canPlaySound())
				{
					if (getHunger() <= 50 && random.nextInt(5) == 0)
						playSound("hunger");
					else if (getHunger() <= 30 && random.nextInt(3) == 0)
						playSound("hunger");
					else if (getHunger() <= 10)
						playSound("hunger");
				}
			}
		}
		tickCycle++;
		lastSoundEffect--;
	}
	
	private void playSound(String name)
	{
		player.worldObj.playSoundEffect((double)((float)player.posX + 0.5F), (double)((float)player.posY + 0.5F), (double)((float)player.posZ + 0.5F), SimCraft.MODID + ":" + name, 2.0f, 2.0f);
		lastSoundEffect = 400; //20 Seconds?
	}
	private boolean canPlaySound()
	{
		return lastSoundEffect <= 0;
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
	
	public void removeSimoleans(int value)
	{
		simoleans -= value;
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
		SimCraft.packetPipeline.sendTo(new PacketExtendedInfo(this), (EntityPlayerMP)player);
	}
	
	private void levelUp()
	{
		player.worldObj.playSoundEffect((double)((float)player.posX + 0.5F), (double)((float)player.posY + 0.5F), (double)((float)player.posZ + 0.5F), SimCraft.MODID + ":levelup", 2.0f, 2.0f);
	}
	
	public void setHunger(int value)
	{
		hunger = value;
		if (hunger <= 0)
		{
			hunger = 0;
			player.attackEntityFrom(DamageSource.generic, player.getMaxHealth());
		}
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
		if (hunger <= 0)
		{
			hunger = 0;
			player.attackEntityFrom(DamageSource.generic, player.getMaxHealth());
		}
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

	public void respawn() {
		hunger = 100;
		comfort = 100;
		hygiene = 100;
		markDirty();
	}
}
