package com.vivalux.cyb.init;

import com.vivalux.cyb.CYBModInfo;
import com.vivalux.cyb.item.Circuit;
import com.vivalux.cyb.item.implant.ImplantCyberneticArm;
import com.vivalux.cyb.item.implant.ImplantCyberneticEye;
import com.vivalux.cyb.item.module.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class CYBItems {
    public static Item circuit, component, relay;
    public static Item implantEye, implantArm, implantLeg;
    public static Item moduleLexica, moduleActuator;
    public static Item moduleNightvision;
    public static Item moduleBlastResist, moduleProjectileResist, moduleFireResist, moduleArmorPlate;

    public static void init() {
        //Materials
        circuit = new Circuit("circuit", "Circuit");
        //component = new Component("component", "Component");
        //relay = new Relay("relay", "Relay");

        //Implants
        implantEye = new ImplantCyberneticEye("eyeImplant", "CyberneticEye", 1);
        implantArm = new ImplantCyberneticArm("armImplant", "CyberneticArm", 1);
        //legImplant = new ImplantCyberneticLeg("legImplant", "Cybernetic Leg", 0, 2);

        //Modules
        //moduleActuator = new ModuleActuator("moduleActuator", "Actuator Module");
        moduleLexica = new ModuleLexica("moduleLexica", "LexicaCybernetica");

        moduleNightvision = new ModuleNightvision("moduleNightvision", "NightVisionModule");

        moduleBlastResist = new ModuleBlastResist("moduleBlastResist", "BlastResistanceModule");
        moduleFireResist = new ModuleFireResist("moduleFireResist", "FireResistanceModule");
        moduleProjectileResist = new ModuleProjectileResist("moduleProjectileResist", "ProjectileResistanceModule");
        moduleArmorPlate = new ModuleArmorPlate("moduleArmorPlate", "ArmorPlateModule");

    }

	//A helper method called by each item's constructor
	public static void setItem(Item item, String str, String str2) {
		item.setUnlocalizedName(str);
		item.setTextureName(str);
		item.setCreativeTab(CYBMisc.tab);
		GameRegistry.registerItem(item, str2, CYBModInfo.MOD_ID);
	}
}