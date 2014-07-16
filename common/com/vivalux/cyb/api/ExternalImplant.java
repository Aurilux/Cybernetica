package com.vivalux.cyb.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public abstract class ExternalImplant extends ItemArmor implements ISpecialArmor {

    private int moduleCapacity;

    //probably don't need to have armor material as a parameter for this constructor
    //just send a default value to the 'super' constructor call
	public ExternalImplant(int renderIndex, int armorType, int capacity) {
        super(ArmorMaterial.IRON, renderIndex, armorType);
        moduleCapacity = capacity;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        String itemName = getUnlocalizedName().substring(getUnlocalizedName().lastIndexOf(".") + 1);
        this.itemIcon = reg.registerIcon(itemName);
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		// unlike normal armor items, the player cannot equip this without using
		// the integration table
		return stack;
	}

    public int getModuleCapacity() {
        return moduleCapacity;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
        if(armor.getItem() != this || !armor.hasTagCompound()) return;
        this.implantTick(world, player, armor);
    }

    protected abstract void implantTick(World world, EntityPlayer player, ItemStack armor);

    public static void installModules(ItemStack implant, ItemStack[] modules) {
        NBTTagCompound installedModules = new NBTTagCompound();
        for (ItemStack i : modules) {
            i.writeToNBT(installedModules);
        }
        implant.setTagCompound(installedModules);
    }

    public static boolean hasInstalledModule(ItemStack implant, String moduleName) {
        for (ItemStack i : getInstalledModules(implant, ((ExternalImplant) implant.getItem()).getModuleCapacity())) {
            if (i != null && i.getItem().getClass().getSimpleName().equals(moduleName)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack[] getInstalledModules(ItemStack implant, int capacity) {
        ItemStack[] modules = new ItemStack[capacity];
        NBTTagCompound installedModules = implant.getTagCompound();
        for (int i = 0; i < modules.length; i++) {
            modules[i] = ItemStack.loadItemStackFromNBT(installedModules);
        }
        return modules;
    }
}
