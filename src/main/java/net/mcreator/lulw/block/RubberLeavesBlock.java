
package net.mcreator.lulw.block;

import net.minecraft.block.material.Material;

@LulwModElements.ModElement.Tag
public class RubberLeavesBlock extends LulwModElements.ModElement {

	@ObjectHolder("lulw:rubber_leaves")
	public static final Block block = null;

	public RubberLeavesBlock(LulwModElements instance) {
		super(instance, 22);

	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(SpaceModItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}

	public static class CustomBlock extends Block {

		public CustomBlock() {
			super(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0));

			setRegistryName("rubber_leaves");
		}

	}

}
