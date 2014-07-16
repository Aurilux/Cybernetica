package com.vivalux.cyb.network.message;

import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageTileEntityIntegrationTable implements IMessage, IMessageHandler<MessageTileEntityIntegrationTable, IMessage> {
    public int x, y, z;

    public MessageTileEntityIntegrationTable() {}

    public MessageTileEntityIntegrationTable(TileEntityIntegrationTable tile) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageTileEntityIntegrationTable message, MessageContext ctx) {
        TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof TileEntityIntegrationTable) {
            //do stuff
        }
        return null;
    }
}