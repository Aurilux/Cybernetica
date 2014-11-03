package com.vivalux.cyb.item;

import com.vivalux.cyb.init.CYBItems;
import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class Relay  extends Item {

    public Relay(String str, String str2) {
        CYBItems.setItem(this, str, str2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(MiscUtils.getTexturePath(this.iconString));
    }
}