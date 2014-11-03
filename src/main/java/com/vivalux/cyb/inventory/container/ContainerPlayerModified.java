package com.vivalux.cyb.inventory.container;

import com.vivalux.cyb.api.Implant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * This is a replacement class for the basic ContainerPlayer, the container responsible for managing the player's
 * inventory gui. The only thing I change is the 'transferStackInSlot' function to disallow the player from manually
 * equipping and un-equipping implants because you can't just add and remove implants on a whim. 'transferStackInSlot'
 * only handles shift-clicking items into inventories, specifically armor into armor slots in this instance. The slots
 * themselves handle the usual moving items with mouse clicks to prevent the player from equipping and un-equipping the
 * implants.
 */
public class ContainerPlayerModified extends ContainerPlayer {

    public ContainerPlayerModified(InventoryPlayer inventory, boolean isLocal, EntityPlayer player) {
        super(inventory, isLocal, player);
    }

    /**
     * Most of this was copied directly from the vanilla ContainerPlayer. This is the function called when the player
     * shift-clicks items into an inventory. The only change I made will be identified below.
     * @param player the player with his gui open
     * @param slotIndex the index of the slot currently being changed
     * @return any remaining ItemStack that wasn't able to fit in the current slot
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex == 0) {
                if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotIndex >= 1 && slotIndex < 5) {
                if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return null;
                }
            }
            else if (slotIndex >= 5 && slotIndex < 9) {
                if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return null;
                }
            }
            //I just added a few conditions here to check for implants
            else if (!(itemstack.getItem() instanceof Implant) && itemstack.getItem() instanceof ItemArmor &&
                    !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                    return null;
                }
            }
            else if (slotIndex >= 9 && slotIndex < 36) {
                if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
                    return null;
                }
            }
            else if (slotIndex >= 36 && slotIndex < 45) {
                if (!this.mergeItemStack(itemstack1, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
