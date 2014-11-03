package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;

public class ModuleBlastResist extends Module {
    public ModuleBlastResist(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.compatibleImplants = this.ALL;
    }
}
