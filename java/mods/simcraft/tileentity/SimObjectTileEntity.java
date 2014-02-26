package mods.simcraft.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import mods.simcraft.SimCraft;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class SimObjectTileEntity extends TileEntity 
{
	public class BuildingTask
	{
		public int xCoord;
		public int yCoord;
		public int zCoord;
		public Block block;
		
		public BuildingTask(int x, int y, int z, Block par1Block)
		{
			xCoord = x;
			yCoord = y;
			zCoord = z;
			block = par1Block;
		}
	}

	protected List<BuildingTask> buildingTasks = Lists.newArrayList();
	
	public static final String NBT_BUILD_TIME = "BuildTime";
	public static final String NBT_TIME_LEFT = "TimeLeft";
	public static final String NBT_OWNER_NAME = "Owner";
	public static final String NBT_LEVEL = "Level";
	public static final String NBT_DIRECTION = "Direction";
	
	private String owner;
	protected int buildTime;
	protected int timeLeft;
	protected int delay;
	protected int level;
	private int direction;
	protected boolean sentBuildCommands = false;
	
	public SimObjectTileEntity(int par1BuildTime)
	{
		owner = "";
		buildTime = par1BuildTime;
		timeLeft = par1BuildTime;
		level = 1;
	}
	
	@Override
	public void updateEntity()
	{
		if (timeLeft > 0)
		{
			timeLeft -= 1;
			if (timeLeft % 60 == 0 && timeLeft != 0) // We don't want to run at 0, because we want the done sound to play.
				worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), SimCraft.MODID + ":" + "construction", 2.0f, 2.0f);
			else if (timeLeft == 0)
				worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), SimCraft.MODID + ":" + "constructiondone", 2.0f, 2.0f);
		}
		else 
			timeLeft = 0;
	}
	
	public boolean upgrade()
	{
		if (hasNextLevel())
		{
			//Consume Stuff here
			{
				buildTime =  upgradeTime(level + 1);
				timeLeft = upgradeTime(level + 1);
				level += 1;
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNextLevel()
	{ 
		return false;
	}
	
	public boolean isCompleted()
	{
		return timeLeft == 0;
	}
	
	public int upgradeTime(int level)
	{
		return 0;
	}
	
	public int percentCompleted()
	{
		return (int)(((buildTime - timeLeft)/(float)buildTime) * 100);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey(NBT_OWNER_NAME))
			owner = nbt.getString(NBT_OWNER_NAME);
		buildTime = nbt.getInteger(NBT_BUILD_TIME);
		timeLeft = nbt.getInteger(NBT_TIME_LEFT);
		level = nbt.getInteger(NBT_LEVEL);
		direction = nbt.getInteger(NBT_DIRECTION);
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (!owner.isEmpty())
			nbt.setString(NBT_OWNER_NAME, owner);
		nbt.setInteger(NBT_BUILD_TIME, buildTime);
		nbt.setInteger(NBT_TIME_LEFT, timeLeft);
		nbt.setInteger(NBT_LEVEL, level);
		nbt.setInteger(NBT_DIRECTION, direction);
	}
	

	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String par1Owner)
	{
		owner = par1Owner;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setDirection(int var1) { this.direction = var1; }
    public int getDirection() { return this.direction; }


	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tileTag = new NBTTagCompound();
		this.writeToNBT(tileTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tileTag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.func_148857_g());
	}
	
	
	public void build()
	{
		timeLeft = buildTime;
		if (isCompleted())
			buildingTasks.clear();
		
		if (buildingTasks.size() > 0)
			delay = buildTime / buildingTasks.size();
	}
}