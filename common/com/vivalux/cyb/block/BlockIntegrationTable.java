package com.vivalux.cyb.block;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.lib.CYBBlocks;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * This is the block for the bed-like table where the player can change, upgrade, or add implants
 */
public class BlockIntegrationTable extends Block implements ITileEntityProvider {
	public BlockIntegrationTable(Material material, String str, String str2) {
		super(material);
		CYBBlocks.setBlock(this, str, str2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(CYBBlocks.getBlockTexturePath(this.textureName));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 0)
			return new TileEntityIntegrationTable();
		else
			return null;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		player.openGui(Cybernetica.instance, 0, world, x, y, z);
		return true;
	}
}