package com.vivalux.cyb.init;

import com.vivalux.cyb.handler.AchievementListener;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class CYBAchievements {
    private static AchievementPage page;
    private static Achievement[] achievements;
    private static AchievementInfo[] achievementInfo = {
        new AchievementInfo(0, 0, new ItemStack(CYBBlocks.integTable), false, -1),
        new AchievementInfo(2, 1, new ItemStack(CYBItems.implantEye), false, 0)
    };

    public static void init() {
        achievements = new Achievement[achievementInfo.length];
        Achievement achievement;
        Achievement parent;
        String achievementKey;
        AchievementInfo info;
        for (int i = 0; i < achievementInfo.length; i++) {
            info = achievementInfo[i];
            achievementKey = "achievement_" + i;
            parent = info.parent > -1 ? achievements[info.parent] : null;
            achievement = new Achievement(achievementKey, achievementKey, info.xPos, info.yPos, info.icon, parent).registerStat();
            if (info.isSpecial) {
                achievement = achievement.setSpecial();
            }
            achievements[i] = achievement;
        }

        page = new AchievementPage("Cybernetica", achievements);
        AchievementPage.registerAchievementPage(page);

        //Register achievement completion event listener
        FMLCommonHandler.instance().bus().register(new AchievementListener());
    }

    public static Achievement getAchievement(int index) {
        return achievements[index];
    }

    //Just a class to easily store info for achievement registration
    private static class AchievementInfo {
        /** The x-coord of the achievement icon on the page's grid */
        public int xPos = 0;
        /** The y-coord of the achievement icon on the page's grid */
        public int yPos = 0;
        /** Item or block icon to be displayed in the achievement icon */
        public ItemStack icon;
        /** Whether the achievement is special. Special achievement icons will be 'spiked' */
        public boolean isSpecial = false;
        /**
         * The parent achievement of this achievement. -1 indicates no parent.
         * Any other number is the index of the achievement in the 'achievements' array
         */
        public int parent;

        public AchievementInfo(int x, int y, ItemStack i, boolean s, int p) {
            xPos = x;
            yPos = y;
            icon = i;
            isSpecial = s;
            parent = p;
        }
    }
}