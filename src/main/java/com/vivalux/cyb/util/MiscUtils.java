package com.vivalux.cyb.util;

import com.vivalux.cyb.lib.CYBModInfo;

public class MiscUtils {
    /**
     * Helper method to get the texture path of the block/item
     * @param str the block's/item's unlocalized name
     * @return the texture path
     */
    public static String getTexturePath(String str) {
        return CYBModInfo.MOD_ID + ":" + str;
    }
}
