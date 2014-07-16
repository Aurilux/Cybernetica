package com.vivalux.cyb.inventory.slot;

import com.vivalux.cyb.api.ImplantModule;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {
	// Slot specifically for IImplantModules
	
	public SlotModule(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack i) {
        if (i == null) return false;
        Item item = i.getItem();
		return item instanceof ImplantModule;
	}

}