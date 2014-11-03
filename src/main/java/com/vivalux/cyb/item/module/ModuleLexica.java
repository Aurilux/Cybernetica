package com.vivalux.cyb.item.module;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;
import com.vivalux.cyb.init.CYBMisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModuleLexica extends Module {
    public ModuleLexica(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.compatibleImplants = this.TORSO;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        Cybernetica.proxy.currentLexicaTopic = "home";
        player.openGui(Cybernetica.instance, CYBMisc.GUI_ID_LEXICON, world, 0, 0, 0);
        return stack;
    }
}