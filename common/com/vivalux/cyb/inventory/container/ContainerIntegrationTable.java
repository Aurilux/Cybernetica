package com.vivalux.cyb.inventory.container;

import com.vivalux.cyb.api.ExternalImplant;
import com.vivalux.cyb.api.ImplantModule;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerIntegrationTable extends Container {
	private TileEntityIntegrationTable tile;
    protected EntityPlayer player;

	public ContainerIntegrationTable(InventoryPlayer inventoryPlayer, TileEntityIntegrationTable tileEntity) {
		tile = tileEntity;
        this.player = inventoryPlayer.player;
        tile.player = inventoryPlayer.player;
        tile.openInventory();
        for (int i = 0; i < 4; i++) {
            final int k = i;
            //implant slots
            this.addSlotToContainer(new Slot(tile, 3 - k, k * 18 + 53, 20) {
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack) {
                    if (stack == null) return false;
                    Item item = stack.getItem();
                    return item instanceof ExternalImplant && item.isValidArmor(stack, k, player);
                }

                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex() {
                    return ItemArmor.func_94602_b(k);
                }
            });
        }
        for (int i = 0; i < 4; i++) {
            final int k = i;
            //module slots
            this.addSlotToContainer(new Slot(tile, 3 - k + 4, k * 18 + 53, 53) {
                public int getSlotStackLimit()
                {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack) {
                    if (stack == null) return false;
                    Item item = stack.getItem();
                    return item instanceof ImplantModule && ((ImplantModule) item).moduleType.getType() == k;
                }
            });
        }
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
        //drop the item if the player is holding something with the mouse while in the gui
		InventoryPlayer inventoryplayer = player.inventory;
		if (inventoryplayer.getItemStack() != null) {
			player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
			inventoryplayer.setItemStack((ItemStack) null);
		}
        super.onContainerClosed(player);
        tile.closeInventory();
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		//player inventory
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		//player hotbar
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        //The slot that was just shift-clicked on
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //the shift-clicked slot is part of the player's inventory (main or hotbar) if the slot index is
            //greater than the size of Integration Table's inventory (minus one), which includes implants and modules
            if (index > tile.getSizeInventory() - 1) {
                if (itemstack.getItem() instanceof ItemArmor &&
                        !((Slot)this.inventorySlots.get(((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) {
                    int j = ((ItemArmor)itemstack.getItem()).armorType;

                    if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                        return null;
                    }
                }
                else if (itemstack.getItem() instanceof ImplantModule &&
                        !((Slot)this.inventorySlots.get(4 + ((ImplantModule)itemstack.getItem()).moduleType.getType())).getHasStack()) {
                    //TODO adapt this to account for higher tiered chassis with more module slots
                    int j = 4 + ((ImplantModule)itemstack.getItem()).moduleType.getType();

                    if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                        return null;
                    }
                }
                //if it is not either an implant or module and it's in the main inventory, move it to the hotbar
                else if (index >= 8 && index < 35 && !this.mergeItemStack(itemstack1, 35, 44, false)) {
                    return null;
                }
                //if it is not either an implant or module and it's in the hotbar, move it to the main inventory
                else if (index >= 35 && index < 44 && !this.mergeItemStack(itemstack1, 8, 35, false)) {
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
                slot.putStack((ItemStack)null);
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