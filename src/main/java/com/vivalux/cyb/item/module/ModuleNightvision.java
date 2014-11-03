package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;

public class ModuleNightvision extends Module {
    public ModuleNightvision(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.compatibleImplants = this.HEAD;
    }
}
