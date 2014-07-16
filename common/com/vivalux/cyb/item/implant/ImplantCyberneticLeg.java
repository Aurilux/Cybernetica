package com.vivalux.cyb.item.implant;

import com.vivalux.cyb.api.ExternalImplant;
import com.vivalux.cyb.lib.CYBItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ImplantCyberneticLeg extends ExternalImplant {
	// this implant provides modules equipped to the arm

	public ImplantCyberneticLeg(String str, String str2, int renderIndex, int armorType) {
        super(renderIndex, armorType, 1);
        CYBItems.setItem(this, str, str2);
	}

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        //Depending on modules, this could reduce damage from blast, fire, projectiles, etc
        return new ArmorProperties(0, 0, 0);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        //Without the proper modules, this won't provide any armor protection
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        //Damage should not be dealt to cybernetic implants
    }

    @Override
    protected void implantTick(World world, EntityPlayer player, ItemStack armor) {

    }
}
