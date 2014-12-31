package com.vivalux.cyb;

import com.vivalux.cyb.handler.*;
import com.vivalux.cyb.init.CYBAchievements;
import com.vivalux.cyb.init.CYBBlocks;
import com.vivalux.cyb.init.CYBItems;
import com.vivalux.cyb.init.CYBRecipes;
import com.vivalux.cyb.proxy.CYBClientProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = CYBModInfo.MOD_ID,
    name = CYBModInfo.MOD_NAME,
    guiFactory = CYBModInfo.GUI_FACTORY,
    version = CYBModInfo.MOD_VERSION)
public class Cybernetica {
	@Instance(CYBModInfo.MOD_ID)
	public static Cybernetica instance;

	@SidedProxy(clientSide = CYBModInfo.CLIENT_PROXY, serverSide = CYBModInfo.SERVER_PROXY)
	public static CYBClientProxy proxy;

    /**
     * Run before anything else. Read your config, create blocks, items, etc
     */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
        //Config should be one of the first things you do
        FMLCommonHandler.instance().bus().register(new ConfigHandler(e.getSuggestedConfigurationFile()));

        //proxy.preInit();

        //Initialize mod-independent objects
		CYBBlocks.init();
		CYBItems.init();
        CYBAchievements.init();

        //PacketHandler.init();
        ChestGenHandler.init();
	}

    /**
     * Build whatever data structures you care about and register handlers, tile entities, renderers, and recipes
     */
	@EventHandler
	public void init(FMLInitializationEvent e) {
        //Register recipes here to ensure that all mod items and blocks, including those from other mods, are added
        CYBRecipes.init();

        proxy.init();

        //Minecraft Forge event handlers
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

        //FML event handlers
        FMLCommonHandler.instance().bus().register(new UpdateHandler());

        //Network handlers
        NetworkRegistry.INSTANCE.registerGuiHandler(Cybernetica.instance, new GuiHandler());
	}

    /**
     * Handle interaction with other mods, complete your setup based on this
     */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

        //proxy.postInit();

        //Inter-mod communications
        //Register the Waila data provider
        /*
		if (Loader.isModLoaded("Waila")) {
            FMLInterModComms.sendMessage("Waila", "register", "");
        }
        */
	}
}