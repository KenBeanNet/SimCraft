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
	private int numRows;
	MarketTileEntity tile;
	
    public MarketContainer(IInventory playerInventory, MarketTileEntity chestInventory)
    {
    	tile = chestInventory;

        bindPlayerInventory(playerInventory, chestInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
    	return tile.isUseableByPlayer(player);
    }
    
    protected void bindPlayerInventory(IInventory playerInventory, IInventory chestInventory) {

    	for (int chestRow = 0; chestRow < 2; chestRow++)
        {
            for (int chestCol = 0; chestCol < 3; chestCol++)
            {
                addSlotToContainer(new Slot(chestInventory, chestCol + chestRow * 3, 12 + chestCol * 18, 8 + chestRow * 18));
            }
        }
    	
    	// Player inventory
        int rows = 3;
        int cols = 9;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // Slot params: id, x-coord, y-coord (coords are relative to gui box)
                addSlotToContainer(new Slot(playerInventory, x + (y + 1) * cols, 100 + x * 18, 100 + y * 18));
            }
        }
        
        // Player hotbar
        for (int x = 0; x < cols; x++) {
            addSlotToContainer(new Slot(playerInventory, x, 150 + x * 18, 150));
        }
}

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);

            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < 9) {
                            if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
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
