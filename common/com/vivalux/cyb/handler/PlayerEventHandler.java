package com.vivalux.cyb.handler;

import com.vivalux.cyb.api.ExternalImplant;
import com.vivalux.cyb.inventory.container.ContainerPlayerModified;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerEventHandler {

    private HashMap<String, ArrayList<EntityItem>> savedImplants = new HashMap<String, ArrayList<EntityItem>>();

    //Stores cybernetic implants for a specific player in a hashmap on death
    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent e) {
        ArrayList<EntityItem> savedDrops = new ArrayList<EntityItem>();
        for (EntityItem i : e.drops) {
            if (i.getEntityItem().getItem() instanceof ExternalImplant) {
                savedDrops.add(i);
            }
        }
        e.drops.removeAll(savedDrops);
        System.out.println(savedDrops);
        savedImplants.put(e.entityPlayer.getDisplayName(), savedDrops);
    }

    //Restores the saved implants to the player giving the impression they are not removable
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone e) {
        if (e.wasDeath && savedImplants.containsKey(e.original.getDisplayName())) {
            ArrayList<EntityItem> implants = savedImplants.remove(e.original.getDisplayName());
            for (EntityItem i : implants) {
                ItemStack implantStack = i.getEntityItem();
                ExternalImplant implantItem = (ExternalImplant) implantStack.getItem();
                e.entityPlayer.setCurrentItemOrArmor(3 - implantItem.armorType + 1, implantStack);
            }
        }
    }

    //Replaces the player's armor inventory slots with customs ones that won't allow them to remove implants
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent e) {
        if (!(e.entity instanceof EntityPlayer)) return;
        final EntityPlayer player = (EntityPlayer) e.entity;
        InventoryPlayer inventoryPlayer = player.inventory;
        ContainerPlayer containerPlayer = (ContainerPlayer) player.inventoryContainer;
        player.inventoryContainer = new ContainerPlayerModified(inventoryPlayer, containerPlayer.isLocalWorld, player);
        containerPlayer = (ContainerPlayer) player.inventoryContainer;

        for (int i = 0; i < 4; i++) {
            final int k = i;
            Slot newSlot = new Slot(inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18) {
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack) {
                    if (stack == null) return false;
                    return !(stack.getItem() instanceof ExternalImplant) && stack.getItem().isValidArmor(stack, k, player);
                }

                public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
                    return !(this.getStack().getItem() instanceof ExternalImplant);
                }

                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex() {
                    return ItemArmor.func_94602_b(k);
                }
            };
            newSlot.slotNumber = i+5;
            containerPlayer.inventorySlots.set(i+5, newSlot);
        }
    }
}
