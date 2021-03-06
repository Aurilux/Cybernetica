package com.vivalux.cyb.api;

import com.vivalux.cyb.init.CYBItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

public abstract class Implant extends ItemArmor implements ISpecialArmor {
    /**
     * An enum with a set of bits that determine which implants a module can be applied to.
     * I could have just used a series of bit variables, but enums are a bit easier to manage.
     * This also replaces the 'armorType' uses inside this mod.
     *
     * Bit flags should be used more to determine settings such as text formatting options (Bold, Italic, etc), while
     * enums are best used to represent 'Types', such as what I've done here
     */
    public enum ImplantType {
        HEAD, TORSO, LEG, FOOT
    }

    /**
     * The ImplantType for this specific implant. Used to ensure modules get installed to the appropriate implant
     */
    private ImplantType implantType;

    //TODO remove this variable after the redesign
    private int moduleCapacity;

	public Implant(int renderIndex, ImplantType type, int capacity) {
        super(ArmorMaterial.IRON, renderIndex, type.ordinal());
        this.implantType = type;
        this.moduleCapacity = capacity;
	}

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        String itemName = getUnlocalizedName().substring(getUnlocalizedName().lastIndexOf(".") + 1);
        this.itemIcon = reg.registerIcon(itemName);
    }

    @Override
    public String getUnlocalizedName() {
        return "implant." + this.getCoreUnlocalizedName(super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "implant." + this.getCoreUnlocalizedName(super.getUnlocalizedName());
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
        if (stack.hasTagCompound()) {
            ItemStack module = ItemStack.loadItemStackFromNBT(stack.getTagCompound());
            String info = EnumChatFormatting.AQUA + StatCollector.translateToLocal(module.getItem().getUnlocalizedName() + ".name");
            list.add(info);
        }
        else {
            String info = EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.module.none");
            list.add(info);
        }
    }

    /**
     * Unlike normal armor items, the player cannot equip implants without using the integration table
     * @param stack the ItemStack containing an instance of this item
     * @param world the world the player is in
     * @param player the player that just right-clicked the ItemStack
     * @return the changed stack
     */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

    @Override
    public boolean getIsRepairable(ItemStack object, ItemStack resource) {
        return resource.getItem() == CYBItems.component && resource.getItemDamage() == 0 || super.getIsRepairable(object, resource);
    }

    /**
     * Unlike normal armor items, the player cannot enchant implants
     * @return an int describing how "enchantable" an item is
     */
    @Override
    public int getItemEnchantability() {
        return 0;
    }

    /**
     * Returns the number of modules this implant can have installed
     * TODO This will get removed in the next version
     * @return moduleCapacity
     */
    public int getModuleCapacity() {
        return this.moduleCapacity;
    }

    public ImplantType getImplantType() {
        return this.implantType;
    }

    /**
     * The ImplantType enum ordinal values corresponds to the respective armor slots. This is a helper method to get
     * that information
     * @return the ordinal value of the ImplantType
     */
    public int implantTypeAsArmor() {
        return this.getImplantType().ordinal();
    }

    /**
     * Performs actions determined by the implant every tick
     * @param world the world the player is in
     * @param player the player the armor is equipped to
     * @param armor the armor currently ticked
     */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
        if(armor.getItem() != this || !armor.hasTagCompound()) return;
        this.implantTick(world, player, armor);
    }

    /**
     * The update tick for this implant. This will be different for each implant and for each module it has installed.
     * I could technically just use 'onArmorTick', but I feel the name of this method gives better context
     * @param world the world the player is in
     * @param player the player the implant is equipped to
     * @param armor the armor currently ticked
     */
    protected abstract void implantTick(World world, EntityPlayer player, ItemStack armor);

    /**
     * Installs modules onto the implant by writing them to NBT
     * @param implant the implant the modules are getting installed on
     * @param module the array of modules to install
     */
    public static void installModule(ItemStack implant, ItemStack module) {
        //Verify that both ItemStacks are what they are supposed to be
        if (!(implant.getItem() instanceof Implant) && !(module.getItem() instanceof Module)) return;

        NBTTagCompound installedModules = new NBTTagCompound();
        module.writeToNBT(installedModules);
        implant.setTagCompound(installedModules);
    }

    /**
     * Checks to see if a specific module, given by its name, is installed
     * @param implant the inplant to check
     * @param moduleClass the class of the module to find
     * @return true if the module is installed, false otherwise
     */
    public static boolean hasInstalledModule(ItemStack implant, Class moduleClass) {
        for (ItemStack i : getInstalledModules(implant, ((Implant) implant.getItem()).getModuleCapacity())) {
            if (i != null && i.getItem().getClass().equals(moduleClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all of the modules currently installed on an implant
     * @param implant the implant to get modules from
     * @param capacity the capacity of the implant
     * @return the array of installed modules
     */
    public static ItemStack[] getInstalledModules(ItemStack implant, int capacity) {
        ItemStack[] modules = new ItemStack[capacity];
        NBTTagCompound installedModules = implant.getTagCompound();
        for (int i = 0; i < modules.length; i++) {
            modules[i] = ItemStack.loadItemStackFromNBT(installedModules);
        }
        return modules;
    }
}