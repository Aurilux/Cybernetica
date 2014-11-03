package com.vivalux.cyb.handler;

import com.vivalux.cyb.api.Implant;
import com.vivalux.cyb.init.CYBAchievements;
import com.vivalux.cyb.init.CYBBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.Item;

public class AchievementListener {

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        //Detects if the player crafts the integration table, the very first achievement
        if (e.crafting.getItem() == Item.getItemFromBlock(CYBBlocks.integTable)) {
            e.player.addStat(CYBAchievements.getAchievement(0), 1);
        }

        //Detects if the player crafts their first implant, the second achievement
        if (e.crafting.getItem() instanceof Implant) {
            e.player.addStat(CYBAchievements.getAchievement(1), 1);
        }
    }
}