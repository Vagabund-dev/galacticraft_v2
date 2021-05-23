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
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.Map;

@MechanicalCraftModElements.ModElement.Tag
public class Bonemeal_Rubber_TreeProcedure extends MechanicalCraftModElements.ModElement {
	public Bonemeal_Rubber_TreeProcedure(MechanicalCraftModElements instance) {
		super(instance, 22);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency entity for procedure Bonemeal_Rubber_Tree!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency x for procedure Bonemeal_Rubber_Tree!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency y for procedure Bonemeal_Rubber_Tree!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency z for procedure Bonemeal_Rubber_Tree!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MechanicalCraftMod.LOGGER.warn("Failed to load dependency world for procedure Bonemeal_Rubber_Tree!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Items.BONE_MEAL, (int) (1)).getItem())) {
			if (world instanceof ServerWorld) {
				Template template = ((ServerWorld) world).getStructureTemplateManager()
						.getTemplateDefaulted(new ResourceLocation("mechanical_craft", "rubber_tree_structure1"));
				if (template != null) {
					template.func_237144_a_((ServerWorld) world, new BlockPos((int) (x - 2), (int) y, (int) (z - 2)),
							new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false),
							((World) world).rand);
				}
			}
		}
	}
}
