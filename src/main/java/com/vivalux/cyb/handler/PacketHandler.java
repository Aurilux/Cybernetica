package com.vivalux.cyb.handler;

import com.vivalux.cyb.lib.CYBModInfo;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CYBModInfo.MOD_ID);
    private static int messageID = 0;

    public static void init() {
        //TODO remove this later after I have actual packets to send
        //registerClientPacket(MessageTileEntityIntegrationTable.class);
    }

    private static void registerClientPacket(Class msg) {
        INSTANCE.registerMessage(msg, msg, messageID++, Side.CLIENT);
    }
}
