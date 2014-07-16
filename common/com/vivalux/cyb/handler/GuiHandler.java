package com.vivalux.cyb.handler;

import com.vivalux.cyb.client.gui.GuiIntegrationTable;
import com.vivalux.cyb.inventory.container.ContainerIntegrationTable;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!player.isSneaking()) {
			TileEntity t = world.getTileEntity(x, y, z);
			if (t != null) {
				switch (ID) {
				    case 0: return new ContainerIntegrationTable(player.inventory, (TileEntityIntegrationTable) t);
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!player.isSneaking() && world.isRemote) {
			TileEntity t = world.getTileEntity(x, y, z);
			if (t != null) {
				switch (ID) {
                    case 0: return new GuiIntegrationTable(player.inventory,(TileEntityIntegrationTable) t);
				}
			}
		}
		return null;
	}
}