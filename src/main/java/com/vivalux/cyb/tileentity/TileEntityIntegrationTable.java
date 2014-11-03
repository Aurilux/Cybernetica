package com.vivalux.cyb.tileentity;

import com.vivalux.cyb.api.Implant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityIntegrationTable extends TileEntity implements IInventory {
    /**
     * The size of each half (implants, modules) of the inventory. Used when we want to iterate through both halves
     * simultaneously
     */
    public static final int INV_SIZE = 4;
    /**
     * The array that holds the implants and modules equipped to the player. Indeces 0-3 are implants, 4-7 are modules
     */
	private ItemStack[] inventory;
    /**
     * Player reference. Gets set by ContainerIntegrationTable constructor.
     */
    private EntityPlayer player;

    public TileEntityIntegrationTable() {
        inventory = new ItemStack[8];
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return inventory[index];
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
		if (itemStack != null && itemStack.stackSize <= amount) {
            //since each slot's max stack size is 1, if we decrease the stack size we are left with null
            setInventorySlotContents(index, null);
        }
		return itemStack;
	}

    /**
     * Sets the selected inventory slot's contents to the specified ItemStack
     * @param index the index of the slot
     * @param newStack the ItemStack to be put into the slot
     */
	@Override
	public void setInventorySlotContents(int index, ItemStack newStack) {
        if (index <= 3) { //Current index is an implant slot
            ItemStack oldStack = this.getStackInSlot(index);
            //Load the modules of this implant
            if (newStack != null && newStack.getItem() instanceof Implant && newStack.hasTagCompound()) {
                this.setInventorySlotContents(index + INV_SIZE, ItemStack.loadItemStackFromNBT(newStack.getTagCompound()));
            }
            //If these conditions are true then we are removing the implant from the inventory
            else if (newStack == null && oldStack != null && oldStack.getItem() instanceof Implant) {
                //Save this implant's corresponding modules before it is removed from the inventory
                ItemStack moduleStack = getStackInSlot(index + INV_SIZE);
                if (moduleStack != null) {
                    Implant.installModule(oldStack, moduleStack);
                    //if we remove the implant, the module has to go along with it
                    this.setInventorySlotContents(index + INV_SIZE, null);
                }
            }
        }
        //change the slot contents once everything is processed or we'd encounter some NullPointer errors
        inventory[index] = newStack;
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
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
            player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public boolean receiveClientEvent(int par1, int par2) {
		return par1 == 1 || super.receiveClientEvent(par1, par2);
	}

    /**
     * Called whenever the IInventory is right-clicked. For our purposes it will load all of the implants
     * from the player's armor inventory and their modules.
     */
	@Override
	public void openInventory() {
        //Reset the inventory. Solves the issue of implant and module inconsistency between tables
        inventory = new ItemStack[8];
        //Load the armor currently equipped to the player. 'setInventoryContents' handles the module loading.
        //Despite the implant slots only accepting implants, other armor can still be added this way. The armor can be
        //removed but cannot be added back
        ItemStack[] armor = player.inventory.armorInventory;
        for (int i = 0; i < armor.length; i++) {
            this.setInventorySlotContents(i, armor[i]);
        }

        //worldObj.addBlockEvent() call?
	}

    /**
     * Called when the gui is closed. Saves the changes made to the player's implants
     */
	@Override
	public void closeInventory() {
        //Save the modules to each implant
        for (int i = 0; i < INV_SIZE; i++) {
            ItemStack implant = this.getStackInSlot(i);
            ItemStack module = this.getStackInSlot(i + INV_SIZE);

            if (implant != null && module != null) {
                Implant.installModule(implant, module);
            }
            else if (implant != null && implant.getItem() instanceof Implant) {
                implant.stackTagCompound = null;
            }
        }
        //Save the armor changes to the player by copying our armor inventory to the player's armor inventory
        System.arraycopy(this.inventory, 0, player.inventory.armorInventory, 0, 4);

        //worldObj.addBlockEvent() call?
    }

    /**
     * Drops any items in this inventory similar to most crafting inventories that don't store contents.
     * In our instance, all our contents/slots are saved so none of the items in this inventory will be dropped.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        //Only modules without corresponding implants will be dropped
        ItemStack module = this.getStackInSlot(slotIndex);
        if (slotIndex >= 4 && module != null) { //module index
            ItemStack implant = this.getStackInSlot(slotIndex - INV_SIZE);
            if (implant == null || !(implant.getItem() instanceof Implant)) {
                this.setInventorySlotContents(slotIndex, null);
                return module;
            }
        }
        return null;
    }

    /**
     * Used to automate the insertion of items such as with hoppers or pipes. Given the nature of this block, no
     * automation is allowed with this tile entity
     */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < INV_SIZE; i++) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			setInventorySlotContents(slotIndex, ItemStack.loadItemStackFromNBT(tagCompound));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		//Write the ItemStacks in the inventory to NBT
        //Since the implant's NBT stores the module info, we just need to store each implant
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < INV_SIZE; i++) {
			if (inventory[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag("Items", tagList);
	}

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        readFromNBT(packet.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}