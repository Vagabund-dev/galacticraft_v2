
package net.mcreator.mechanical_craft.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.common.ToolType;

import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.mechanical_craft.itemgroup.SpaceModItemGroup;
import net.mcreator.mechanical_craft.MechanicalCraftModElements;

import java.util.List;
import java.util.Collections;

@MechanicalCraftModElements.ModElement.Tag
public class MoonRockBlock extends MechanicalCraftModElements.ModElement {
	@ObjectHolder("mechanical_craft:moon_rock")
	public static final Block block = null;
	public MoonRockBlock(MechanicalCraftModElements instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(SpaceModItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1f, 1f).setLightLevel(s -> 0).harvestLevel(0)
					.harvestTool(ToolType.PICKAXE).setRequiresTool());
			setRegistryName("moon_rock");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}
	}
}
