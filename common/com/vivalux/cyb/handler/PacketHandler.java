package com.vivalux.cyb.handler;

import com.vivalux.cyb.lib.CYBModInfo;
import com.vivalux.cyb.network.message.MessageTileEntityIntegrationTable;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CYBModInfo.MOD_ID);
    private static int messageID = 0;

    public static void init() {
        INSTANCE.registerMessage(MessageTileEntityIntegrationTable.class, MessageTileEntityIntegrationTable.class, messageID++, Side.CLIENT);
    }
}
