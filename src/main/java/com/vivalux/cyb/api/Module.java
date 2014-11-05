package com.vivalux.cyb.api;

import com.vivalux.cyb.api.Implant.ImplantType;
import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.List;

public abstract class Module extends Item {
    /**
     * Which implants this module can be installed on. Will be a combination of the bits listed in ImplantType in the Implant class
     */
    private EnumSet<ImplantType> compatibles = EnumSet.noneOf(ImplantType.class);

    /**
     * The implant this module is installed on
     */
    //TODO do I even need this?
    public Implant chassis;

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(MiscUtils.getTexturePath(this.iconString));
    }

    @Override
    public String getUnlocalizedName() {
        return "module." + this.getCoreUnlocalizedName(super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "module." + this.getCoreUnlocalizedName(super.getUnlocalizedName());
    }

    /**
     * Trims the prefix identifier from the default getUnlocalizedName() ("item.") so we can add our own
     * @param unlocalizedName the unlocalized name
     * @return the trimmed string
     */
    protected String getCoreUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public void setCompatibles(EnumSet<ImplantType> types) {
        this.compatibles = types;
    }

    public EnumSet<ImplantType> getCompatibles() {
        return this.compatibles;
    }

    /**
     * Adds text to the ItemStack's tooltip
     * @param stack the ItemStack of this item
     * @param player the player looking at the stack
     * @param list a list of strings to display in the tooltips. Each entry is it's own line
     * @param advancedTooltips if true, display advanced information in the tooltip. Probably for debugging.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips) {
        String applicableImplants = "";
        if (this.compatibles == EnumSet.allOf(ImplantType.class)) {
            applicableImplants = StatCollector.translateToLocal("tooltip.module.all.blah");
        }
        else {
            for (ImplantType type : ImplantType.values()) {
                if (this.isCompatible(type)) {
                    if (applicableImplants != "") {
                        applicableImplants += ", ";
                    }
                    applicableImplants += StatCollector.translateToLocal("tooltip.module." + type.name().toLowerCase());
                }
            }
        }
        String info = EnumChatFormatting.GREEN + "Compatible with: " + applicableImplants;
        list.add(info);
    }

    /**
     * Determines if the module is compatible with the armorType of the given implant
     * @param type the implantType (armorType) to test against
     * @return true if this module is compatible with the implant
     */
    public boolean isCompatible(ImplantType type) {
        return this.compatibles.contains(type);
    }
}