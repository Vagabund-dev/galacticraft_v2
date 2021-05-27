
package net.mcreator.mechanical_craft.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import net.mcreator.mechanical_craft.item.RezinextraktorItem;
import net.mcreator.mechanical_craft.MechanicalCraftModElements;

@MechanicalCraftModElements.ModElement.Tag
public class SpaceModItemGroup extends MechanicalCraftModElements.ModElement {
	public SpaceModItemGroup(MechanicalCraftModElements instance) {
		super(instance, 12);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabspace_mod") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(RezinextraktorItem.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
