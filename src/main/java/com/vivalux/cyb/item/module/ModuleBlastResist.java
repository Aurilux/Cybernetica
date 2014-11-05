package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.Implant;
import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;

import java.util.EnumSet;

public class ModuleBlastResist extends Module {
    public ModuleBlastResist(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.setCompatibles(EnumSet.allOf(Implant.ImplantType.class));
    }
}
