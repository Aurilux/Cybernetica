package com.vivalux.cyb.item;

import com.vivalux.cyb.lib.CYBItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemFoot extends Item {

	public ItemFoot(String str, String str2) {
		CYBItems.setItem(this, str, str2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(CYBItems
				.getItemTexturePath(this.iconString));
	}

}
