package com.vivalux.cyb.inventory.container;

import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.inventory.slot.SlotImplant;
import com.vivalux.cyb.inventory.slot.SlotModule;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerIntegrationTable extends Container {
	private TileEntityIntegrationTable tile;
    private World world;

	public ContainerIntegrationTable(World world, InventoryPlayer inventoryPlayer, TileEntityIntegrationTable tileEntity) {
		this.tile = tileEntity;
        this.world = world;
        tile.openInventory();

        for (int i = 0; i < 4; i++) {
            //implant slots
            this.addSlotToContainer(new SlotImplant(tile, 3 - i, i * 18 + 53, 20, i));
            //module slots
            this.addSlotToContainer(new SlotModule(tile, 3 - i + 4, i * 18 + 53, 53, i));
        }
		bindPlayerInventory(inventoryPlayer);
	}

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                if (row < 3) { //the player's inventory
                    addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
                }
                else { //row == 3, the player's hotbar
                    addSlotToContainer(new Slot(inventoryPlayer, col, 8 + col * 18, 142));
                }
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);

        if (!this.world.isRemote) {
            for (int i = 0; i < tile.getSizeInventory(); ++i) {
                ItemStack itemstack = this.tile.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
        tile.closeInventory();
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

    /**
     * This function handles shift-clicking items into inventories
     * @param player the player with the gui open
     * @param slotIndex the index of the slot being changed
     * @return the remainder of the ItemStack that couldn't fit in the inventory
     */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //the shift-clicked slot is part of the player's inventory (main or hotbar) if the slot slotIndex is
            //greater than the size of Integration Table's inventory (minus one), which includes implants and modules
            if (slotIndex > tile.getSizeInventory() - 1) {
                if (itemstack.getItem() instanceof ItemArmor &&
                        !((Slot)this.inventorySlots.get(((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) {
                    int j = ((ItemArmor)itemstack.getItem()).armorType;

                    if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                        return null;
                    }
                }
                else if (itemstack.getItem() instanceof Module &&
                        !((Slot)this.inventorySlots.get(4 + ((Module)itemstack.getItem()).getModuleType())).getHasStack()) {
                    int j = 4 + ((Module)itemstack.getItem()).getModuleType();

                    if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                        return null;
                    }
                }
                //if it is not either an implant or module and it's in the main inventory, move it to the hotbar
                else if (slotIndex >= 8 && slotIndex < 35 && !this.mergeItemStack(itemstack1, 35, 44, false)) {
                    return null;
                }
                //if it is not either an implant or module and it's in the hotbar, move it to the main inventory
                else if (slotIndex >= 35 && slotIndex < 44 && !this.mergeItemStack(itemstack1, 8, 35, false)) {
                    return null;
                }
            }
            //The shift-clicked slot was one of the implant or module slots
            else {
                if (!this.mergeItemStack(itemstack1, 8, 44, false)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
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
	
	@Override
	public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
		return super.mergeItemStack(stack, start, end, reverse);
	}
}