package com.vivalux.cyb.util;

import com.vivalux.cyb.CYBModInfo;
import net.minecraft.util.ResourceLocation;

public class CYBResources {
	public static final ResourceLocation TEXTURE_GUI_INTEG = getResourceLocation("textures/gui/integrationTable.png");
    public static final ResourceLocation TEXTURE_GUI_LEXICA = getResourceLocation("textures/gui/lexicaGui.png");

	private static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(CYBModInfo.MOD_ID + ":" + path);
	}
}