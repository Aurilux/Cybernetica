package com.vivalux.cyb.lib;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.item.implant.ImplantCyberneticArm;
import com.vivalux.cyb.item.implant.ImplantCyberneticEye;
import com.vivalux.cyb.item.implant.ImplantCyberneticLeg;
import com.vivalux.cyb.item.module.ModuleActuator;
import com.vivalux.cyb.item.module.ModuleNightvision;
import com.vivalux.cyb.item.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class CYBItems {
    public static Item circuit;
    public static Item eyeImplant, armImplant, legImplant;
    public static Item moduleNightvision, moduleActuator;

    public static void init() {
        //Materials
        circuit = new Circuit("circuit", "Circuit");

        //Implants
        eyeImplant = new ImplantCyberneticEye("eyeImplant", "Occular implant", 0, 0);
        armImplant = new ImplantCyberneticArm("armImplant", "Cybernetic arm", 0, 1);
        //legImplant = new ImplantCyberneticLeg("legImplant", "Cybernetic Leg", 0, 2);

        //Modules
        /*implantProcessor = new ItemImplantProcessor("implantProcessor", "Implant processor");
        implantModulator = new ItemImplantModulator("implantModulator", "Implant modulator");
        implantFibre = new ItemFibre("implantFibre", "Augmented fibre");
        implantFoot = new ItemFoot("implantFoot", "Augmented foot");
        implantSensor = new ItemSensor("implantSensor", "Pressure sensor");
        implantSupport = new ItemSupport("implantSupport", "Support chassis");*/
        moduleActuator = new ModuleActuator("moduleActuator", "Actuator Module");
        moduleNightvision = new ModuleNightvision("moduleNightVision", "Night-Vision Module");
    }

	//A helper method called by each item's constructor
	public static void setItem(Item item, String str, String str2) {
		item.setUnlocalizedName(str);
		item.setTextureName(str);
		item.setCreativeTab(Cybernetica.creativeTabs);
		GameRegistry.registerItem(item, str2);
	}

	public static String getItemTexturePath(String str) {
		return CYBModInfo.MOD_ID + ":" + str;
	}
}