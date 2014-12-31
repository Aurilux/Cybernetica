package com.vivalux.cyb.handler;

import com.vivalux.cyb.api.Implant;
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

/**
 * This class handles all player related events
 */
public class PlayerEventHandler {
    /**
     * A hashmap that is used to store implants on player death, then to re-equip them on player respawn
     */
    private HashMap<String, ArrayList<EntityItem>> savedImplants = new HashMap<String, ArrayList<EntityItem>>();

    /**
     * Stores cybernetic implants for a specific player in a hashmap on death
     * TODO this equips implants that aren't currently equipped as armor. Fot it so that it only stores implants equipped as armor
     * @param e the PlayersDropsEvent event
     */
    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent e) {
        //the arraylist that will store all of the player's implants
        ArrayList<EntityItem> savedDrops = new ArrayList<EntityItem>();

        //iterate through all of the player drops (the items in the player's inventory) and save all the implants
        for (EntityItem i : e.drops) {
            if (i.getEntityItem().getItem() instanceof Implant) {
                savedDrops.add(i);
            }
        }

        //remove all of the found implants from the drops list so they don't spawn in world
        e.drops.removeAll(savedDrops);
        savedImplants.put(e.entityPlayer.getDisplayName(), savedDrops);
    }

    /**
     * Restores the saved implants to the player giving the impression they are not removable
     * @param e the PlayerEvent.Clone event
     */
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone e) {
        //The PlayerEvent.Clone gets called both on death and dimension travel so only if the event was called on
        //player's actual death and our hashmap has the player's name (used as the key) do we restore their implants
        if (e.wasDeath && savedImplants.containsKey(e.original.getDisplayName())) {
            //Simultaneously get and remove the implants stored in our hashmap
            ArrayList<EntityItem> implants = savedImplants.remove(e.original.getDisplayName());

            //Iterate through the arraylist and equip all of the implants
            for (EntityItem i : implants) {
                ItemStack implantStack = i.getEntityItem();
                Implant implantItem = (Implant) implantStack.getItem();
                e.entityPlayer.setCurrentItemOrArmor(3 - implantItem.armorType + 1, implantStack);
            }
        }
    }

    /**
     * Replaces the player's inventory container with a custom one that won't allow them to manually remove implants
     * @param e the EntityJoinWorldEvent
     */
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent e) {
        //The EntityJoinWorldEvent gets triggered anytime ANY entity joins the world (spawns in world) so if its not a
        //player, we don't want/need to do anything
        if (!(e.entity instanceof EntityPlayer)) return;

        final EntityPlayer player = (EntityPlayer) e.entity;
        InventoryPlayer inventoryPlayer = player.inventory;
        ContainerPlayer containerPlayer = (ContainerPlayer) player.inventoryContainer;
        player.inventoryContainer = new ContainerPlayerModified(inventoryPlayer, containerPlayer.isLocalWorld, player);
        containerPlayer = (ContainerPlayer) player.inventoryContainer;

        for (int i = 0; i < 4; i++) {
            //dynamically modified classes (such as the slot below) require that certain variables must be final in
            //order for their values to be used (I can't just use 'i')
            final int k = i;

            //This is a slightly different slot than my SlotImplant as this one must prevent the insertion and
            //extraction of implants but still allow usual armor
            Slot newSlot = new Slot(inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18) {
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack) {
                    if (stack == null) return false;
                    return !(stack.getItem() instanceof Implant) && stack.getItem().isValidArmor(stack, k, player);
                }

                public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
                    return !(this.getStack().getItem() instanceof Implant);
                }

                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex() {
                    return ItemArmor.func_94602_b(k);
                }
            };
            newSlot.slotNumber = i + 5;
            containerPlayer.inventorySlots.set(i + 5, newSlot);
        }
    }
}
