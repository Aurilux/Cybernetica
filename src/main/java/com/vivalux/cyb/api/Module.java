package com.vivalux.cyb.api;

import com.vivalux.cyb.util.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class Module extends Item {
    //A set of bits that determine which implants this module can be applied to
    //TODO perhaps turn these into an Enum? Might be easier to determine tooltip info that way. Might help in removing the instance references as well
    public static final byte HEAD  = 0x1; // 00000001
    public static final byte TORSO = 0x2; // 00000010
    public static final byte LEG   = 0x4; // 00000100
    public static final byte FOOT  = 0x8; // 00001000
    public static final byte ALL = HEAD | TORSO | LEG | FOOT;

    /**
     * Which implants this module can be installed on. Will be a combination of the bits above
     */
    protected int compatibleImplants = 0;

    /**
     * The implant this module is installed on
     */
    //TODO do I even need this?
    public Implant chassis;

    public int getModuleType() {
        return compatibleImplants;
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

    /**
     * Adds text to the ItemStack's tooltip
     * @param stack the ItemStack of this item
     * @param player the player looking at the stack
     * @param list a list of strings to display in the tooltips. Each entry is it's own line
     * @param advancedTooltips if true displayed advanced information in the tooltip. Probably for debugging.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips) {
        String applicableImplants = "";
        if (this.compatibleImplants == this.ALL) {
            applicableImplants = StatCollector.translateToLocal("tooltip.module.all");
        }
        else {
            if ((this.compatibleImplants & this.HEAD) > 0) {
                applicableImplants += StatCollector.translateToLocal("tooltip.module.head");
            }

            if ((this.compatibleImplants & this.TORSO) > 0) {
                if (applicableImplants != "") {
                    applicableImplants += ", ";
                }
                applicableImplants += StatCollector.translateToLocal("tooltip.module.torso");
            }

            if ((this.compatibleImplants & this.LEG) > 0) {
                if (applicableImplants != "") {
                    applicableImplants += ", ";
                }
                applicableImplants += StatCollector.translateToLocal("tooltip.module.leg");
            }

            if ((this.compatibleImplants & this.FOOT) > 0) {
                if (applicableImplants != "") {
                    applicableImplants += ", ";
                }
                applicableImplants += StatCollector.translateToLocal("tooltip.module.foot");
            }
        }
        String info = EnumChatFormatting.GREEN + "Compatible with: " + applicableImplants;
        list.add(info);
    }

    /**
     * Determines if the module is compatible with the armorType of the given implant
     * @param implant the implant to test against
     * @return true if this module is compatible with the implant
     */
    public boolean isCompatible(Implant implant) {
        return this.isCompatible(implant.armorType);
    }

    /**
     * Determines if the module is compatible with the armorType of the given implant
     * @param implantType the implantType (armorType) to test against
     * @return true if this module is compatible with the implant
     */
    public boolean isCompatible(int implantType) {
        switch(implantType) {
            case 0:  implantType = HEAD;  break;
            case 1:  implantType = TORSO; break;
            case 2:  implantType = LEG;   break;
            case 3:
            default: implantType = FOOT;  break;
        }

        return (compatibleImplants & implantType) > 0;
    }
}