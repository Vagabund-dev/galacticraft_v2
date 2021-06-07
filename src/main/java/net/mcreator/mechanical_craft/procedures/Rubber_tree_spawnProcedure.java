package net.mcreator.mechanical_craft.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;

import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.Map;

@MechanicalCraftModElements.ModElement.Tag
public class Rubber_tree_spawnProcedure extends MechanicalCraftModElements.ModElement {
	public Rubber_tree_spawnProcedure(MechanicalCraftModElements instance) {
		super(instance, 49);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency x for procedure Rubber_tree_spawn!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency y for procedure Rubber_tree_spawn!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency z for procedure Rubber_tree_spawn!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency world for procedure Rubber_tree_spawn!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (world instanceof ServerWorld) {
			Template template = ((ServerWorld) world).getStructureTemplateManager()
					.getTemplateDefaulted(new ResourceLocation("mechanical_craft", "rubber_tree_structure_kmen"));
			if (template != null) {
				template.func_237144_a_((ServerWorld) world, new BlockPos((int) (x - 0), (int) y, (int) (z - 0)),
						new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false),
						((World) world).rand);
			}
		}
		if (world instanceof ServerWorld) {
			Template template = ((ServerWorld) world).getStructureTemplateManager()
					.getTemplateDefaulted(new ResourceLocation("mechanical_craft", "rubber_tree_listi"));
			if (template != null) {
				template.func_237144_a_((ServerWorld) world, new BlockPos((int) (x - 1), (int) (y + 9), (int) (z - 1)),
						new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false),
						((World) world).rand);
			}
		}
	}
}
