package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.Implant.ImplantType;
import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;

import java.util.EnumSet;

public class ModuleNightvision extends Module {
    public ModuleNightvision(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.setCompatibles(EnumSet.of(ImplantType.HEAD));
    }
}
