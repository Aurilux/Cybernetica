package com.vivalux.cyb.handler;

import com.vivalux.cyb.init.CYBMisc;
import com.vivalux.cyb.lib.CYBModInfo;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class ConfigHandler {
	public static Configuration config;

	public ConfigHandler(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
	}

    private void loadConfig() {
        //MISC
        String category = "misc";
        Property currentConfig = config.get(category, "hardcore_mode", false);
        currentConfig.comment = "Whether hardcore mode is enabled. In hardcore mode you are severely penalized for removing an implant.";
        CYBMisc.HARDCORE_MODE = currentConfig.getBoolean(CYBMisc.HARDCORE_MODE);

        //load variable values here
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(CYBModInfo.MOD_ID)) {
            //resync configs
            loadConfig();
        }
    }
}