package com.vivalux.cyb;

import com.vivalux.cyb.handler.PacketHandler;
import com.vivalux.cyb.lib.*;
import com.vivalux.cyb.proxy.CYBClientProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CYBModInfo.MOD_ID, name = CYBModInfo.MOD_NAME, version = CYBModInfo.VERSION)
public class Cybernetica {
	@Instance(CYBModInfo.MOD_ID)
	public static Cybernetica instance;

	@SidedProxy(clientSide = CYBModInfo.CLIENT_PROXY, serverSide = CYBModInfo.SERVER_PROXY)
	public static CYBClientProxy proxy;
	public static final CYBCreativeTabs creativeTabs = new CYBCreativeTabs(CYBModInfo.MOD_NAME);
	public static final Log log = new Log();

    /**
     * Load configuration and initialize all things that use it such as blocks and items
     */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		CYBBlocks.init();
		CYBItems.init();
        PacketHandler.init();
	}

    /**
     * Register handlers, tile entities, renderers, recipes, and other things
     */
	@EventHandler
	public void init(FMLInitializationEvent e) {
        CYBRecipes.init();

        proxy.registerHandlers();
        proxy.registerTileEntities();
        proxy.registerRenderers();
	}

    /**
     * Handle interaction with other mods
     */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
}
