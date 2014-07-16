package com.vivalux.cyb.lib;

import net.minecraft.util.ResourceLocation;

public class CYBResources {

	public static final String PREFIX_TEXTURES = "textures/";
	public static final String PREFIX_GUI = PREFIX_TEXTURES + "gui/";

	public static final ResourceLocation TEXTURE_GUI_INTEGTABLE = getResourceLocation(PREFIX_GUI
			+ "integrationTable.png");

	private static ResourceLocation getResourceLocation(String path) {

		return new ResourceLocation(CYBModInfo.MOD_ID, path);

	}

}
