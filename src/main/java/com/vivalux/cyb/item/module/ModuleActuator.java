package com.vivalux.cyb.item.module;

import com.vivalux.cyb.api.Implant.ImplantType;
import com.vivalux.cyb.api.Module;
import com.vivalux.cyb.init.CYBItems;

import java.util.EnumSet;

/**
 * This will be one of the modules available to the arm and leg implants
 * Each tier increases movement speed with legs, and harvest speed with arms
 */
public class ModuleActuator extends Module {
	public ModuleActuator(String str, String str2) {
        CYBItems.setItem(this, str, str2);
        this.setCompatibles(EnumSet.of(ImplantType.TORSO, ImplantType.LEG));
	}
}