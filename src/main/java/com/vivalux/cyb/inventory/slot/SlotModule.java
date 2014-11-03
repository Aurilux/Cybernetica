package com.vivalux.cyb.inventory.slot;

import com.vivalux.cyb.api.Module;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * This is the inventory slot that allows exclusively modules and is used in the ContainerIntegrationTable
 */
public class SlotModule extends Slot {
    int slot;
	public SlotModule(IInventory inventory, int slotIndex, int x, int y, int slotType) {
		super(inventory, slotIndex, x, y);
        this.slot = slotType;
	}

    public boolean isItemValid(ItemStack stack) {
        if (stack == null) return false;
        Item item = stack.getItem();
        return item instanceof Module && ((Module) item).isCompatible(slot);
    }

    public int getSlotStackLimit()
    {
        return 1;
    }
}