package net.ixios.advancedthaumaturgy.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.ixios.advancedthaumaturgy.AdvThaum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemFertilizer extends ItemBlock
{

	public ItemFertilizer()
	{
		super(AdvThaum.ThaumicFertilizer);
		this.setCreativeTab(AdvThaum.tabAdvThaum);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack,	EntityPlayer player, List list, boolean par4)
	{
		boolean shiftdown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    	if (!shiftdown)
    		return;String desc = StatCollector.translateToLocal("tile.at.fertilizer.desc");
		String[] lines = desc.split("\\|");
		for (String s : lines)
			list.add(s);
	}
}