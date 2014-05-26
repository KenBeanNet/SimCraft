package mods.simcraft.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.Lists;

import mods.simcraft.SimCraft;
import mods.simcraft.data.HomeManager;

public class HomeTileEntity extends SimObjectTileEntity 
{
	public static final String HOME_NAME = "HomeName";
	private String homeName = "";
	
	public HomeTileEntity()
	{
		super(500);
	}
	public HomeTileEntity(int par1BuildTime) {
		super(par1BuildTime);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateEntity()
	{
		if (sentBuildCommands)
		{
			if (buildingTasks.size() > 0)
			{
				if (timeLeft % delay == 0)
				{
					BuildingTask task = buildingTasks.remove(0);
					worldObj.setBlock(task.xCoord, task.yCoord, task.zCoord, task.block);
				}
			}
			if (timeLeft > 0)
			{
				timeLeft -= 1;
				if (timeLeft % 60 == 0 && timeLeft != 0) // We don't want to run at 0, because we want the done sound to play.
					worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), SimCraft.MODID + ":building", 2.0f, 2.0f);
				else if (timeLeft == 0)
					worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), SimCraft.MODID + ":building.complete", 2.0f, 2.0f);
			}
			else 
			{
				timeLeft = 0;
				for (BuildingTask t : buildingTasks)
				{
					worldObj.setBlock(t.xCoord, t.yCoord, t.zCoord, t.block);
				}
				buildingTasks.clear();
			}
		}
	}
	
	@Override
	public boolean isCompleted()
    {
		return timeLeft == 0 || !sentBuildCommands;
    }
	
	public int getBuildingCount(Block blockId)
	{
		int widthOfHome = HomeManager.getHomeSize(level);
		int totalCount = 0;
		for (int x = xCoord - widthOfHome; x <= xCoord + widthOfHome; x++)
		{
			for (int y = yCoord - widthOfHome; y <= yCoord + widthOfHome; y++)
			{
				for (int z = zCoord - widthOfHome; z <= zCoord + widthOfHome; z++)
				{
					if (worldObj.getBlock(x, y, z) == blockId)
						totalCount++;
				}
			}
		}
		return totalCount;
	}
	
	//This should only be called from the TE of the HomeBlock
	public List<SimObjectTileEntity> getBuildingsByBlock(Block blockId)
	{
		List<SimObjectTileEntity> toReturn = Lists.newArrayList();
		int widthOfHome = HomeManager.getHomeSize(level);
		for (int x = xCoord - widthOfHome; x <= xCoord + widthOfHome; x++)
		{
			for (int y = yCoord - widthOfHome; y <= yCoord + widthOfHome; y++)
			{
				for (int z = zCoord - widthOfHome; z <= zCoord + widthOfHome; z++)
				{
					if (worldObj.getBlock(x, y, z) == blockId)
						toReturn.add((SimObjectTileEntity)worldObj.getTileEntity(x, y, z));
				}
			}
		}
		return toReturn;
	}
	
	public void build()
	{
		if (!sentBuildCommands)
		{
			int widthOfHome = HomeManager.getHomeSize(1);
			sentBuildCommands = true;
			for (int x = this.xCoord - widthOfHome; x <= xCoord + widthOfHome; x++)
			{
				for (int z = zCoord - widthOfHome; z <= zCoord + widthOfHome; z++)
				{
					//addBuildingTask(new BuildingTask(x, yCoord - 2, z, Blocks.bedrock));
					//if (x == xCoord - widthOfHome)
						//addBuildingTask(new BuildingTask(x, yCoord - 1, z, Blocks.stone));
					//if (x == xCoord + widthOfHome)
						//addBuildingTask(new BuildingTask(x, yCoord - 1, z, Blocks.stone));
				}
				//addBuildingTask(new BuildingTask(x, yCoord - 1, zCoord + widthOfHome, Blocks.stone));
				//addBuildingTask(new BuildingTask(x, yCoord - 1, zCoord - widthOfHome, Blocks.stone));
			}
			
			super.build(); //Always call Super.Build at end  This way if its alrdy completed, we clear the list. Avoiding server load.
		}
	}
	
	protected void addBuildingTask(BuildingTask task)
	{
		buildingTasks.add(task);
	}
	
	
	@Override
	public boolean hasNextLevel()
	{
		return false;
	}
	
	@Override
	public int upgradeTime(int par1Level)
	{
		switch (par1Level)
		{
			case 2:
				return 3000;
		}
		return 0;
	}

	@Override
	public boolean canUpdate()
	{
		if (isCompleted() && sentBuildCommands)
			return false;
		return true;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey(HOME_NAME))
			homeName = nbt.getString(HOME_NAME);
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (!homeName.isEmpty())
			nbt.setString(HOME_NAME, homeName);
	}
	
	public void setHomeName(String par1Name)
	{
		homeName = par1Name;
	}

	public String getHomeName() {
		return homeName;
	}

}
