package com.vivalux.cyb.api;

import net.minecraft.item.Item;

public abstract class ImplantModule extends Item {
    /*
    Determines which implants this module can be applied to
     */
    public EnumModuleType moduleType;
    /*
    The implant this module is installed on
     */
    public ExternalImplant chassis;

	public boolean worksWithImplantType(EnumModuleType type) {
		return type == moduleType;
	}
}