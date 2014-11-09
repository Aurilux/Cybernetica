package com.vivalux.cyb.init;

import com.vivalux.cyb.block.BlockIntegrationTable;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Manages all of the blocks created by this mod
 */
public class CYBBlocks {
    /**
     * The integration table
     */
	public static Block integTable;

    /**
     * Initializes all blocks
     */
	public static void init() {
		integTable = new BlockIntegrationTable(Material.iron, "integTable", "Integration Table");
	}

    /**
     * Registers the block. Called by each block's constructor.
     * @param block the block being registered
     * @param str the block's unlocalized name
     * @param str2 the block's displayed name
     */
	public static void setBlock(Block block, String str, String str2) {
		block.setBlockName(str);
		block.setBlockTextureName(str);
        block.setCreativeTab(CYBMisc.tab);
		GameRegistry.registerBlock(block, str2);
	}
}
