package com.vivalux.cyb.tileentity;

import com.vivalux.cyb.api.ExternalImplant;
import com.vivalux.cyb.api.ImplantModule;
import com.vivalux.cyb.handler.PacketHandler;
import com.vivalux.cyb.network.message.MessageTileEntityIntegrationTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileEntityIntegrationTable extends TileEntity implements IInventory {
    /**
     * The size of the implant inventory
     */
    public static final int INV_SIZE = 4;
    /**
     * The array that holds the implants equipped to the player
     */
	private ItemStack[] implantInventory;
    /**
     * The array that holds the modules for each implants
     * 0-3 1st implant, 4-7 2nd implant, 8-11 3rd implant, 12-15 4th implant
     */
    private ItemStack[] moduleInventory;
    /**
     * Player reference. Gets set by ContainerIntegrationTable constructor.
     */
    public EntityPlayer player;

	public TileEntityIntegrationTable() {
		implantInventory = new ItemStack[INV_SIZE];
        //TODO increase the module inventory size when we add implants with more module slots
        moduleInventory = new ItemStack[INV_SIZE];
	}

	@Override
	public int getSizeInventory() {
		return implantInventory.length + moduleInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        if (index < 4) {
            return implantInventory[index];
        }
        else {
            return moduleInventory[index - 4];
        }
	}

    /**
     * Decreases the size of the stack in the specified slot
     * @param index the index of the slot
     * @param amount the amount to decrease the stack by
     * @return the resulting itemstack
     */
	@Override
	public ItemStack decrStackSize(int index, int amount) {
		ItemStack itemStack = getStackInSlot(index);
		if (itemStack != null) {
			if (itemStack.stackSize <= amount) {
                if (index < 4 && moduleInventory[index] != null) {
                    NBTTagCompound moduleInfo = new NBTTagCompound();
                    moduleInventory[index].writeToNBT(moduleInfo);
                    itemStack.setTagCompound(moduleInfo);
                }
				setInventorySlotContents(index, null);
			}
            else {
				itemStack = itemStack.splitStack(amount);
				if (itemStack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		return itemStack;
	}

    /**
     * Drops any items in the inventory similar to most crafting inventories.
     * In our instance none of the items in our inventory will be dropped.
     */
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// None of the items in the inventory should be dropped on close
		return null;
	}

    /**
     * Sets the selected inventory slot's contents to the specified itemstack
     * @param index the index of the slot
     * @param stack the itemstack to add to the slot
     */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 4) {
            implantInventory[index] = stack;
            if (stack != null) {
                if (stack.hasTagCompound()) {
                    moduleInventory[index] = ItemStack.loadItemStackFromNBT(stack.getTagCompound());
                }
            }
            else {
                moduleInventory[index] = stack;
            }
        }
        else {
            moduleInventory[index - 4] = stack;
            ItemStack implantStack = getStackInSlot(index - 4);
            if (stack == null && implantStack != null && implantStack.hasTagCompound()) {
                implantStack.stackTagCompound = null;
            }
        }

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord,
                this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D,
                this.zCoord + 0.5D) <= 64.0D;
	
	}

	@Override
	public boolean receiveClientEvent(int par1, int par2) {
		if (par1 == 1) {
			return true;
		}
        else {
			return super.receiveClientEvent(par1, par2);
		}
	}

    @Override
    public void markDirty() {
        installModules();
        super.markDirty();
    }

    /**
     * Saves all modules to the implant ItemStack's NBT
     */
    public void installModules() {
        for (int i = 0; i < INV_SIZE; i++) {
            ItemStack implant = implantInventory[i];
            if (implant != null && implant.getItem() instanceof ExternalImplant) {
                ArrayList<ItemStack> modules = new ArrayList<ItemStack>();
                //when we have implants with more than one module slot, replace the following with the commented code
                //for (int j = i; j < i + 4; j++) {
                //}
                if (moduleInventory[i] != null) {
                    modules.add(moduleInventory[i]);
                }

                ExternalImplant.installModules(implant, modules.toArray(new ItemStack[0]));
            }
        }
    }

    /**
     * Loads all of the modules for the specific implant
     */
    public void loadModules() {
        implantInventory = player.inventory.armorInventory;
        for (int i = 0; i < INV_SIZE; i++) {
            //grabs all of the armor from the player's armor inventory
            ItemStack implantStack = implantInventory[i];
            if (implantStack != null && implantStack.getItem() instanceof ExternalImplant && implantStack.hasTagCompound()) {
                ItemStack[] modules = ExternalImplant.getInstalledModules(implantStack,
                        ((ExternalImplant)implantStack.getItem()).getModuleCapacity());
                //when we have implants with more than one module slot, replace the following with the commented code
                //for (int j = i; j < i + 4; j++) {
                //}
                if (i <= modules.length) {
                    moduleInventory[i] = modules[i];
                }
            }
        }

    }

    /**
     * Called whenever the IInventory is right-clicked. For our purposes it will load all of the implants
     * from the player's armor inventory and their modules.
     */
	@Override
	public void openInventory() {
        loadModules();
	}

	@Override
	public void closeInventory() {
	}

    /**
     * Used to automate the insertion of items such as with pipes
     */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// No automation should be allowed with this tile entity
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
		implantInventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < implantInventory.length) {
				implantInventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < implantInventory.length; ++currentIndex) {
			if (implantInventory[currentIndex] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				implantInventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag("Items", tagList);
	}

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityIntegrationTable(this));
    }
}