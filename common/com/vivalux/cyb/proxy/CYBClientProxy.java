package com.vivalux.cyb.proxy;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.client.model.ModelArmImplant;
import com.vivalux.cyb.client.model.ModelEyeImplant;
import com.vivalux.cyb.handler.GuiHandler;
import com.vivalux.cyb.handler.PlayerEventHandler;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.common.MinecraftForge;

public class CYBClientProxy {

	public static final ModelBase modelEye = new ModelEyeImplant();
	public static final ModelBiped modelArm = new ModelArmImplant();

    /*
    Initialize all event handlers
     */
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Cybernetica.instance, new GuiHandler());
    }

    /*
    Initialize all tile entities
     */
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityIntegrationTable.class, "integrationTable");
    }

    /*
    Initialize all special rendering such as tile entity renderers
     */
    public void registerRenderers() {

    }
}
