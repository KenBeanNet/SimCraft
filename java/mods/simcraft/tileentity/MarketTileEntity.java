package mods.simcraft.tileentity;

import java.util.Arrays;
import java.util.Comparator;

import mods.simcraft.SimCraft;
import mods.simcraft.common.Repository;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class MarketTileEntity extends SimObjectTileEntity implements IInventory
{
	public static final int BUILD_TIME = 500;
	
	public ItemStack[] chestContents;
	
	public MarketTileEntity() {
		super(BUILD_TIME);
		
		this.chestContents = new ItemStack[getSizeInventory()];
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return chestContents[var1];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                if (stack.stackSize <= amt) {
                        setInventorySlotContents(slot, null);
                } else {
                        stack = stack.splitStack(amt);
                        if (stack.stackSize == 0) {
                                setInventorySlotContents(slot, null);
                        }
                }
        }
        return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                setInventorySlotContents(slot, null);
        }
        return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		chestContents[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
        }
	}

	@Override
	public String getInventoryName() {
		return "markettileentity";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
				var1.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
        NBTTagList nbttaglist = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        chestContents = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < chestContents.length)
            {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < chestContents.length; i++)
        {
            if (chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
	}
	public void soldItems()
	{
		this.chestContents = new ItemStack[getSizeInventory()];
	}
    
}
