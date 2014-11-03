package com.vivalux.cyb.handler;

import com.vivalux.cyb.client.gui.GuiIntegrationTable;
import com.vivalux.cyb.client.gui.lexica.GuiLexica;
import com.vivalux.cyb.init.CYBMisc;
import com.vivalux.cyb.inventory.container.ContainerIntegrationTable;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CYBMisc.GUI_ID_INTEG:
                TileEntityIntegrationTable tileEntityIntegTable = (TileEntityIntegrationTable) world.getTileEntity(x, y, z);
                if (tileEntityIntegTable != null) {
                    return new ContainerIntegrationTable(world, player.inventory, tileEntityIntegTable);
                }
                break;
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CYBMisc.GUI_ID_INTEG:
                TileEntityIntegrationTable tileEntityIntegTable = (TileEntityIntegrationTable) world.getTileEntity(x, y, z);
                if (tileEntityIntegTable != null) {
                    return new GuiIntegrationTable(world, player.inventory, tileEntityIntegTable);
                }
                break;
            case CYBMisc.GUI_ID_LEXICON: return new GuiLexica();
        }
		return null;
	}
}