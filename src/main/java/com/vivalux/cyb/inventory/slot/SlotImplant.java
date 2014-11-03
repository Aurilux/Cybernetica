package com.vivalux.cyb.inventory.slot;

import com.vivalux.cyb.api.Implant;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * This is the inventory slot that allows exclusively implants and is used in the ContainerIntegrationTable
 */
public class SlotImplant extends Slot {
	int slot;
	public SlotImplant(IInventory inventory, int index, int x, int y, int slotType) {
		super(inventory, index, x, y);
		this.slot = slotType;
	}

    public boolean isItemValid(ItemStack stack) {
        if (stack == null) return false;
        Item item = stack.getItem();
        return item instanceof Implant && item.isValidArmor(stack, slot, null);
    }
	
	public int getSlotStackLimit()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex()
    {
        return ItemArmor.func_94602_b(slot);
    }
}