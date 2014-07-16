package com.vivalux.cyb.lib;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CYBRecipes {

	public static void init() {
		GameRegistry.addRecipe(new ItemStack(CYBItems.circuit), new Object[] {
				"XYX", "YZY", "XYX",
                'X', Items.redstone,
                'Y', Items.glowstone_dust,
                'Z', Items.iron_ingot
        });
        GameRegistry.addRecipe(new ItemStack(CYBItems.eyeImplant), new Object[] {
                "YYY", "XWX", " Z ",
                'W', Items.iron_helmet,
                'X', Items.gold_ingot,
                'Y', Blocks.glass_pane,
                'Z', CYBItems.circuit
        });
        GameRegistry.addRecipe(new ItemStack(CYBItems.armImplant), new Object[] {
                "YYY", "XWX", " Z ",
                'W', Items.iron_chestplate,
                'X', Items.gold_ingot,
                'Y', Blocks.lever,
                'Z', CYBItems.circuit
        });
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleActuator), new Object[] {
                "XXX", "YYY", "XXX",
                'X', Items.iron_ingot,
                'Y', Items.gold_ingot
        });
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleNightvision), new Object[] {
                "XYX", "XWX", "XZX",
                'W', Items.golden_carrot,
                'X', Items.iron_ingot,
                'Y', Blocks.glass_pane,
                'Z', CYBItems.circuit
        });
        GameRegistry.addRecipe(new ItemStack(CYBBlocks.integTable), new Object[] {
                "XYX", "XZX", "XYX",
                'X', Items.iron_ingot,
                'Y', Items.quartz,
                'Z', CYBItems.circuit
        });
		/*GameRegistry.addRecipe(new ItemStack(CYBItems.implantProcessor), new Object[] {
                "XYX", "YXY", "XYX",
                'X', Items.redstone,
                'Y', CYBItems.circuit });
		GameRegistry.addRecipe(new ItemStack(CYBItems.implantModulator), new Object[] {
                "XYX", "YXY", "XYX",
                'X', Items.glowstone_dust,
				'Y', CYBItems.circuit });
		GameRegistry.addRecipe(new ItemStack(CYBItems.implantFibre, 2), new Object[] {
                "XYX", "XZX", "XYX",
                'X', Items.string,
                'Y', Items.rotten_flesh,
                'Z', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(CYBItems.implantFoot), new Object[] {
                "   ", " XX", "XYX",
                'X', Items.iron_ingot,
                'Y', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(CYBItems.implantSensor), new Object[] {
                " X ", "XYX", " X ",
                'X', Items.iron_ingot,
                'Y', CYBItems.implantProcessor });
		GameRegistry.addRecipe(new ItemStack(CYBItems.implantSupport), new Object[] {
                "XXX", " X ", "XXX",
                'X', Items.iron_ingot });
		GameRegistry.addShapelessRecipe(new ItemStack(CYBItems.eyeImplant), new Object[] {
                CYBItems.implantLens, CYBItems.implantModulator,
				CYBItems.implantProcessor, CYBItems.implantSupport });
		GameRegistry.addShapelessRecipe(new ItemStack(CYBItems.armImplant), new Object[] {
                CYBItems.implantSensor, CYBItems.implantSupport,
				CYBItems.implantFibre, CYBItems.implantProcessor });*/
	}
}
