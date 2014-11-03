package com.vivalux.cyb.handler;

import com.vivalux.cyb.init.CYBItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * Adds items to random chest gen such as dungeon chests
 */
public class ChestGenHandler {
    //TODO may remove most of this later once I've decided if I need it or not
    public static void init() {
        //since the lexica module can be used independently add it to the bonus chest for people who want a head start
        String c = ChestGenHooks.BONUS_CHEST;
        ChestGenHooks.addItem(c, new WeightedRandomChestContent(new ItemStack(CYBItems.moduleLexica), 1, 1, 12));

        //c = ChestGenHooks.DUNGEON_CHEST;
        //ChestGenHooks.addItem(c, new WeightedRandomChestContent(new ItemStack(CYBItems.component), 1, 1, 1));
    }
}
