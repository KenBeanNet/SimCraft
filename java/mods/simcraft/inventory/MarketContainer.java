package mods.simcraft.inventory;

import mods.simcraft.tileentity.MarketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MarketContainer extends Container 
{
	MarketTileEntity tile;
    public MarketContainer(IInventory playerInventory, MarketTileEntity chestInventory)
    {
    	tile = chestInventory;
    	//the Slot constructor takes the IInventory and the slot number in that it binds to
        //and the x-y coordinates it resides on-screen
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                        addSlotToContainer(new Slot(chestInventory, j + i * 3,  187 + i * 18, 63 + j * 18));
                }
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 133 + j * 18, 122 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, 133 + i * 18, 180));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
    	return tile.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
    {
    	ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //merges the item into player inventory since its in the tileEntity
                if (slot < 6) {
                        if (!this.mergeItemStack(stackInSlot, 9, 45, true)) {
                                return null;
                        }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                        return null;
                }

                if (stackInSlot.stackSize == 0) {
                        slotObject.putStack(null);
                } else {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}
