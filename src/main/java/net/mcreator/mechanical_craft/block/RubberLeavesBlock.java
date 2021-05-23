
package net.mcreator.mechanical_craft.block;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import net.mcreator.mechanical_craft.itemgroup.SpaceModItemGroup;
import net.mcreator.mechanical_craft.MechanicalCraftModElements;

@MechanicalCraftModElements.ModElement.Tag
public class RubberLeavesBlock extends MechanicalCraftModElements.ModElement {
	@ObjectHolder("mechanical_craft:rubber_leaves")
	public static final Block block = null;
	public RubberLeavesBlock(MechanicalCraftModElements instance) {
		super(instance, 3);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(SpaceModItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT).hardnessAndResistance(0f, 10f).setLightLevel(s -> 0));
			setRegistryName("rubber_leaves");
		}
	}
}
