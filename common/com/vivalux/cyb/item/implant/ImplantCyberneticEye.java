package com.vivalux.cyb.item.implant;

import com.vivalux.cyb.Cybernetica;
import com.vivalux.cyb.api.ExternalImplant;
import com.vivalux.cyb.client.model.ModelEyeImplant;
import com.vivalux.cyb.lib.CYBItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ImplantCyberneticEye extends ExternalImplant {
	// this class allows the player to equip modules dealing with sight
	// nightvision, thermal vision, aura vision, unique hud, etc

	public ImplantCyberneticEye(String str, String str2, int renderIndex, int armorType) {
		super(renderIndex, armorType, 1);
		CYBItems.setItem(this, str, str2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(CYBItems.getItemTexturePath(this.iconString));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "cybernetica:textures/armour/eyeImplant.png";
	}

    @Override
    protected void implantTick(World world, EntityPlayer player, ItemStack armor) {
        if (ExternalImplant.hasInstalledModule(armor, "ModuleNightvision")) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 3, 4, true));
        }
    }

    @Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		if ((itemStack != null)) {
			if (itemStack.getItem() instanceof ImplantCyberneticEye) {
				ModelEyeImplant model = (ModelEyeImplant) Cybernetica.proxy.modelEye;
				model.bipedHead.showModel = true;
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
    public void damageArmor(EntityLivingBase entity, ItemStack armor, DamageSource source, int damage, int slot) {
        //Damage should not be dealt to cybernetic implants
    }
}
