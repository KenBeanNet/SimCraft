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
    	for (int x = 0; x < 2; x++) {
    		for (int y = 0; y < 3; y++) {
    		addSlotToContainer(new Slot(chestInventory, y + x * 2, 8 + y * 18, 17 + x * 18));
    		}
    	}
        
        bindPlayerInventory((InventoryPlayer)playerInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
    	return tile.isUseableByPlayer(player);
    }
    
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
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
                        if (!this.mergeItemStack(stackInSlot, 6, 42, true)) {
                                return null;
                        }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, 0, 6, false)) {
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
