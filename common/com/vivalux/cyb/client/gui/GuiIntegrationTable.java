package com.vivalux.cyb.client.gui;

import com.vivalux.cyb.inventory.container.ContainerIntegrationTable;
import com.vivalux.cyb.lib.CYBResources;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiIntegrationTable extends GuiContainer {
	// this class is the gui that will render when interacting with the
	// integration table

	public TileEntityIntegrationTable tile;

	public GuiIntegrationTable(InventoryPlayer inventoryPlayer, TileEntityIntegrationTable tileEntity) {
		super(new ContainerIntegrationTable(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = "Integration Table";
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6 + 2, 4210752);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(CYBResources.TEXTURE_GUI_INTEGTABLE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}