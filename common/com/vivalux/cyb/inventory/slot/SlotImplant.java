package com.vivalux.cyb.inventory.slot;

import com.vivalux.cyb.api.ExternalImplant;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotImplant extends Slot {
	// Slot specifically used for IImplants

	int slot;
	public SlotImplant(IInventory inventory, int par2, int par3, int par4, int slot) {
		super(inventory, par2, par3, par4);
		this.slot = slot;
	}

	public boolean isItemValid(ItemStack i) {
		return i.getItem() instanceof ExternalImplant;
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