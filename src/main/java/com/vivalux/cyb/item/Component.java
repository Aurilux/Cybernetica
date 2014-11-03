package com.vivalux.cyb.item;

import com.vivalux.cyb.init.CYBItems;
import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/**
 * This is an item used in most of the recipes for this mod
 * It is also the only item used to repair implants on the anvil
 *
 * Components come in three tiers which can either be created or found, with the higher tiered ones being required
 * to make and repair the higher tiered modules
 */
public class Component extends Item {

    public Component(String str, String str2) {
        CYBItems.setItem(this, str, str2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(MiscUtils.getTexturePath(this.iconString));
    }
}