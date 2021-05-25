package net.mcreator.mechanical_craft.procedures;

import net.minecraft.item.ItemStack;

import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.Random;
import java.util.Map;

@MechanicalCraftModElements.ModElement.Tag
public class RezinextraktorRightClickedOnBlockProcedure extends MechanicalCraftModElements.ModElement {
	public RezinextraktorRightClickedOnBlockProcedure(MechanicalCraftModElements instance) {
		super(instance, 28);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency itemstack for procedure RezinextraktorRightClickedOnBlock!");
			return;
		}
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		{
			ItemStack _ist = (itemstack);
			if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
				_ist.shrink(1);
				_ist.setDamage(0);
			}
		}
	}
}
