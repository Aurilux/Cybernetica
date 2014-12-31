package com.vivalux.cyb.proxy;

import com.vivalux.cyb.client.model.ModelArmImplant;
import com.vivalux.cyb.client.model.ModelEyeImplant;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;

public class CYBClientProxy {
	public static final ModelBase modelEye = new ModelEyeImplant();
	public static final ModelBiped modelArm = new ModelArmImplant();

    public String currentLexicaTopic = "home";
    public String previousLexicaTopic = "";

    public void init() {
        //register client-side components such as renderers, key handlers, etc
        //registerHandlers();
        registerTileEntities();
        //registerRenderers();
    }

    /*
    Initialize all event handlers
     */
    public void registerHandlers() {
    }

    /*
    Initialize all tile entities
     */
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityIntegrationTable.class, "integTable");
    }

    /*
    Initialize all special rendering such as tile entity renderers
     */
    public void registerRenderers() {
    }
}
