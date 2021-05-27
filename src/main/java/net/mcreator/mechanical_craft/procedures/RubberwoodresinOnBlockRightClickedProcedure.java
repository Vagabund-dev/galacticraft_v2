package net.mcreator.mechanical_craft.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;

import net.mcreator.mechanical_craft.item.RezinextraktorItem;
import net.mcreator.mechanical_craft.item.RawrezinItem;
import net.mcreator.mechanical_craft.block.RubbertreewoodBlock;
import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.Map;

@MechanicalCraftModElements.ModElement.Tag
public class RubberwoodresinOnBlockRightClickedProcedure extends MechanicalCraftModElements.ModElement {
	public RubberwoodresinOnBlockRightClickedProcedure(MechanicalCraftModElements instance) {
		super(instance, 26);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency entity for procedure RubberwoodresinOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency x for procedure RubberwoodresinOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency y for procedure RubberwoodresinOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency z for procedure RubberwoodresinOnBlockRightClicked!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency world for procedure RubberwoodresinOnBlockRightClicked!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(RezinextraktorItem.block, (int) (1)).getItem())) {
			{
				BlockPos _bp = new BlockPos((int) x, (int) y, (int) z);
				BlockState _bs = RubbertreewoodBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			if (world instanceof World && !world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(RawrezinItem.block, (int) (1)));
				entityToSpawn.setPickupDelay((int) 10);
				world.addEntity(entityToSpawn);
			}
			if (world instanceof World && !world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(RawrezinItem.block, (int) (1)));
				entityToSpawn.setPickupDelay((int) 10);
				world.addEntity(entityToSpawn);
			}
		}
	}
}
