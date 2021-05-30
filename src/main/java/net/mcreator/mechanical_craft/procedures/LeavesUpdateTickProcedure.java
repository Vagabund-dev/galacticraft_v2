package net.mcreator.mechanical_craft.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.block.Blocks;

import net.mcreator.mechanical_craft.block.RubbertreewoodBlock;
import net.mcreator.mechanical_craft.block.RubbertreesaplingBlock;
import net.mcreator.mechanical_craft.MechanicalCraftModVariables;
import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.Map;

@MechanicalCraftModElements.ModElement.Tag
public class LeavesUpdateTickProcedure extends MechanicalCraftModElements.ModElement {
	public LeavesUpdateTickProcedure(MechanicalCraftModElements instance) {
		super(instance, 51);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency x for procedure LeavesUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency y for procedure LeavesUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency z for procedure LeavesUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency world for procedure LeavesUpdateTick!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		MechanicalCraftModVariables.repeatTimes = (double) 4;
		MechanicalCraftModVariables.Offset = (double) Math.floor(((MechanicalCraftModVariables.repeatTimes) / (-2)));
		MechanicalCraftModVariables.MapVariables.get(world).xOff = (double) (MechanicalCraftModVariables.Offset);
		MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
		MechanicalCraftModVariables.MapVariables.get(world).LogFound = (boolean) (false);
		MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
		for (int index0 = 0; index0 < (int) ((MechanicalCraftModVariables.repeatTimes)); index0++) {
			MechanicalCraftModVariables.MapVariables.get(world).yOff = (double) (MechanicalCraftModVariables.Offset);
			MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
			for (int index1 = 0; index1 < (int) ((MechanicalCraftModVariables.repeatTimes)); index1++) {
				MechanicalCraftModVariables.MapVariables.get(world).zOff = (double) (MechanicalCraftModVariables.Offset);
				MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
				for (int index2 = 0; index2 < (int) ((MechanicalCraftModVariables.repeatTimes)); index2++) {
					if (((world.getBlockState(new BlockPos((int) (x + (MechanicalCraftModVariables.MapVariables.get(world).xOff)),
							(int) (y + (MechanicalCraftModVariables.MapVariables.get(world).yOff)),
							(int) (z + (MechanicalCraftModVariables.MapVariables.get(world).zOff)))))
									.getBlock() == RubbertreewoodBlock.block.getDefaultState().getBlock())) {
						MechanicalCraftModVariables.MapVariables.get(world).LogFound = (boolean) (true);
						MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
						MechanicalCraftModVariables.repeatTimes = (double) 0;
					}
					MechanicalCraftModVariables.MapVariables
							.get(world).zOff = (double) ((MechanicalCraftModVariables.MapVariables.get(world).zOff) + 1);
					MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
				}
				MechanicalCraftModVariables.MapVariables.get(world).yOff = (double) ((MechanicalCraftModVariables.MapVariables.get(world).yOff) + 1);
				MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
			}
			MechanicalCraftModVariables.MapVariables.get(world).xOff = (double) ((MechanicalCraftModVariables.MapVariables.get(world).xOff) + 1);
			MechanicalCraftModVariables.MapVariables.get(world).syncData(world);
		}
		if ((!(MechanicalCraftModVariables.MapVariables.get(world).LogFound))) {
			if ((Math.random() < 0.3)) {
				world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
				if ((Math.random() < 0.1)) {
					if (world instanceof World && !world.isRemote()) {
						ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(RubbertreesaplingBlock.block, (int) (1)));
						entityToSpawn.setPickupDelay((int) 10);
						world.addEntity(entityToSpawn);
					}
				}
			}
		}
	}
}
