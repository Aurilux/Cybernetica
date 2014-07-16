package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.EnumModuleType;
import com.vivalux.cyb.api.ImplantModule;
import com.vivalux.cyb.lib.CYBItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ModuleNightvision extends ImplantModule {

    public ModuleNightvision(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.moduleType = EnumModuleType.EYE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(CYBItems
                .getItemTexturePath(this.iconString));
    }
}
