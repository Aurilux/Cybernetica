package com.vivalux.cyb.block;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.init.CYBBlocks;
import com.vivalux.cyb.init.CYBMisc;
import com.vivalux.cyb.tileentity.TileEntityIntegrationTable;
import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * This is the block for the (eventually) bed-like table where the player can change, upgrade, or add implants
 */
public class BlockIntegrationTable extends Block implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    public IIcon topIcon;

    @SideOnly(Side.CLIENT)
    public IIcon sideIcon;

    @SideOnly(Side.CLIENT)
    public IIcon bottomIcon;

	public BlockIntegrationTable(Material material, String str, String str2) {
		super(material);
		CYBBlocks.setBlock(this, str, str2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
        this.topIcon = reg.registerIcon(MiscUtils.getTexturePath("integ_top"));
        this.sideIcon = reg.registerIcon(MiscUtils.getTexturePath("integ_side"));
        this.bottomIcon = reg.registerIcon(MiscUtils.getTexturePath("integ_bottom"));
	}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        switch (side) {
            case 0: return bottomIcon;
            case 1: return topIcon;
            default: return sideIcon;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityIntegrationTable();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (!player.isSneaking()) {
            TileEntityIntegrationTable tile = (TileEntityIntegrationTable) world.getTileEntity(x, y, z);
            if (tile != null) {
                tile.setPlayer(player);
                player.openGui(Cybernetica.instance, CYBMisc.GUI_ID_INTEG, world, x, y, z);
            }
        }
		return true;
	}
}