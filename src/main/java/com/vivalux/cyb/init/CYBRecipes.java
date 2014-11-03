package com.vivalux.cyb.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CYBRecipes {
	public static void init() {
        //Misc
		GameRegistry.addRecipe(new ItemStack(CYBItems.circuit),
				"XYX", "YZY", "XYX",
                'X', Items.redstone,
                'Y', Items.glowstone_dust,
                'Z', Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(CYBBlocks.integTable),
            "XYX", "XZX", "XYX",
            'X', Items.iron_ingot,
            'Y', Items.quartz,
            'Z', CYBItems.circuit
        );

        //Implants
        GameRegistry.addRecipe(new ItemStack(CYBItems.implantEye),
                "YYY", "XWX", " Z ",
                'W', Items.iron_helmet,
                'X', Items.gold_ingot,
                'Y', Blocks.glass_pane,
                'Z', CYBItems.circuit
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.implantArm),
                "YYY", "XWX", " Z ",
                'W', Items.iron_chestplate,
                'X', Items.gold_ingot,
                'Y', Blocks.lever,
                'Z', CYBItems.circuit
        );

        //Modules
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleActuator),
                "XXX", "YYY", "XXX",
                'X', Items.iron_ingot,
                'Y', Items.gold_ingot
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleNightvision),
                "XYX", "XWX", "XZX",
                'W', Items.golden_carrot,
                'X', Items.iron_ingot,
                'Y', Blocks.glass_pane,
                'Z', CYBItems.circuit
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleBlastResist),
            "XXX", "XYX", "XZX",
            'X', Items.iron_ingot,
            'Y', Blocks.tnt,
            'Z', CYBItems.circuit
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleProjectileResist),
            "XXX", "XYX", "XZX",
            'X', Items.iron_ingot,
            'Y', Items.arrow,
            'Z', CYBItems.circuit
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleFireResist),
            "XXX", "XYX", "XZX",
            'X', Items.iron_ingot,
            'Y', Items.lava_bucket,
            'Z', CYBItems.circuit
        );
        GameRegistry.addRecipe(new ItemStack(CYBItems.moduleArmorPlate),
            "XXX", "XYX", "XXX",
            'X', Items.iron_ingot,
            'Y', CYBItems.circuit
        );
        GameRegistry.addShapelessRecipe(new ItemStack(CYBItems.moduleLexica),
            CYBItems.circuit, Items.book);
	}
}
