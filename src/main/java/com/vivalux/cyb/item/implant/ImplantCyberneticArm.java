package com.vivalux.cyb.item.implant;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.api.Implant;
import com.vivalux.cyb.client.model.ModelArmImplant;
import com.vivalux.cyb.init.CYBItems;
import com.vivalux.cyb.item.module.ModuleArmorPlate;
import com.vivalux.cyb.item.module.ModuleBlastResist;
import com.vivalux.cyb.item.module.ModuleFireResist;
import com.vivalux.cyb.item.module.ModuleProjectileResist;
import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ImplantCyberneticArm extends Implant {
	// this implant provides modules equipped to the arm

	public ImplantCyberneticArm(String str, String str2, int renderIndex) {
        super(renderIndex, ImplantType.TORSO, 1);
		CYBItems.setItem(this, str, str2);
	}

    @Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(MiscUtils.getTexturePath(this.iconString));
	}

    @Override
    protected void implantTick(World world, EntityPlayer player, ItemStack armor) {
    }

    @Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if ((itemStack != null)) {
			if (itemStack.getItem() instanceof ImplantCyberneticArm) {
				ModelArmImplant model = (ModelArmImplant) Cybernetica.proxy.modelArm;
				model.bipedRightArm.showModel = true;
				model.isSneak = entityLiving.isSneaking();
				model.isRiding = entityLiving.isRiding();
				model.isChild = entityLiving.isChild();
				model.heldItemRight = ((EntityPlayer) entityLiving).getCurrentArmor(0) != null ? 1 : 0;
				return model;
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "cybernetica:textures/armour/armImplant.png";
	}

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        //Depending on modules, this could reduce damage from blast, fire, projectiles, etc
        if(Implant.hasInstalledModule(armor, ModuleFireResist.class) && source.isFireDamage()) {
            return new ArmorProperties(1, 1, MathHelper.floor_double(damage * .125D));
        }
        else if (Implant.hasInstalledModule(armor, ModuleBlastResist.class) && source.isExplosion()) {
            return new ArmorProperties(1, 1, MathHelper.floor_double(damage * .125D));
        }
        else if (Implant.hasInstalledModule(armor, ModuleProjectileResist.class) && source.isProjectile()) {
            return new ArmorProperties(1, 1, MathHelper.floor_double(damage * .125D));
        }
        return new ArmorProperties(0, 0, 0);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        //Without the proper modules, this won't provide any armor protection
        if (Implant.hasInstalledModule(armor, ModuleArmorPlate.class)) {
            return 2;
        }
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        //Damage should not be dealt to cybernetic implants
    }
}