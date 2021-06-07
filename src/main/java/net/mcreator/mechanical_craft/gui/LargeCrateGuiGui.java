
package net.mcreator.mechanical_craft.gui;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.ScreenManager;

import net.mcreator.mechanical_craft.MechanicalCraftModElements;
import net.mcreator.mechanical_craft.MechanicalCraftMod;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@MechanicalCraftModElements.ModElement.Tag
public class LargeCrateGuiGui extends MechanicalCraftModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;
	public LargeCrateGuiGui(MechanicalCraftModElements instance) {
		super(instance, 60);
		elements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
				ButtonPressedMessage::handler);
		elements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
				GUISlotChangedMessage::handler);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}
	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("large_crate_gui"));
		}
	}
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, LargeCrateGuiGuiWindow::new));
	}
	public static class GuiContainerModFactory implements IContainerFactory {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainerMod(id, inv, extraData);
		}
	}

	public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
		World world;
		PlayerEntity entity;
		int x, y, z;
		private IItemHandler internal;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;
		public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(containerType, id);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.internal = new ItemStackHandler(168);
			BlockPos pos = null;
			if (extraData != null) {
				pos = extraData.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
			if (pos != null) {
				if (extraData.readableBytes() == 1) { // bound to item
					byte hand = extraData.readByte();
					ItemStack itemstack;
					if (hand == 0)
						itemstack = this.entity.getHeldItemMainhand();
					else
						itemstack = this.entity.getHeldItemOffhand();
					itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
				} else if (extraData.readableBytes() > 1) {
					extraData.readByte(); // drop padding
					Entity entity = world.getEntityByID(extraData.readVarInt());
					if (entity != null)
						entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
				} else { // might be bound to block
					TileEntity ent = inv.player != null ? inv.player.world.getTileEntity(pos) : null;
					if (ent != null) {
						ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
					}
				}
			}
			this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, -88, 103) {
			}));
			this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, -70, 103) {
			}));
			this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, -52, 103) {
			}));
			this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, -34, 103) {
			}));
			this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, -16, 103) {
			}));
			this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 2, 103) {
			}));
			this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 20, 103) {
			}));
			this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 38, 103) {
			}));
			this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 56, 103) {
			}));
			this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 74, 103) {
			}));
			this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 92, 103) {
			}));
			this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 110, 103) {
			}));
			this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 128, 103) {
			}));
			this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 146, 103) {
			}));
			this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 164, 103) {
			}));
			this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 182, 103) {
			}));
			this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, 200, 103) {
			}));
			this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, 218, 103) {
			}));
			this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, 236, 103) {
			}));
			this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, 254, 103) {
			}));
			this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 272, 103) {
			}));
			this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, -88, 85) {
			}));
			this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, -70, 85) {
			}));
			this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, -52, 85) {
			}));
			this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, -34, 85) {
			}));
			this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, -16, 85) {
			}));
			this.customSlots.put(26, this.addSlot(new SlotItemHandler(internal, 26, 2, 85) {
			}));
			this.customSlots.put(27, this.addSlot(new SlotItemHandler(internal, 27, 20, 85) {
			}));
			this.customSlots.put(28, this.addSlot(new SlotItemHandler(internal, 28, 38, 85) {
			}));
			this.customSlots.put(29, this.addSlot(new SlotItemHandler(internal, 29, 56, 85) {
			}));
			this.customSlots.put(30, this.addSlot(new SlotItemHandler(internal, 30, 74, 85) {
			}));
			this.customSlots.put(31, this.addSlot(new SlotItemHandler(internal, 31, 92, 85) {
			}));
			this.customSlots.put(32, this.addSlot(new SlotItemHandler(internal, 32, 110, 85) {
			}));
			this.customSlots.put(33, this.addSlot(new SlotItemHandler(internal, 33, 128, 85) {
			}));
			this.customSlots.put(34, this.addSlot(new SlotItemHandler(internal, 34, 146, 85) {
			}));
			this.customSlots.put(35, this.addSlot(new SlotItemHandler(internal, 35, 164, 85) {
			}));
			this.customSlots.put(36, this.addSlot(new SlotItemHandler(internal, 36, 182, 85) {
			}));
			this.customSlots.put(37, this.addSlot(new SlotItemHandler(internal, 37, 200, 85) {
			}));
			this.customSlots.put(38, this.addSlot(new SlotItemHandler(internal, 38, 218, 85) {
			}));
			this.customSlots.put(39, this.addSlot(new SlotItemHandler(internal, 39, 236, 85) {
			}));
			this.customSlots.put(40, this.addSlot(new SlotItemHandler(internal, 40, 254, 85) {
			}));
			this.customSlots.put(41, this.addSlot(new SlotItemHandler(internal, 41, 272, 85) {
			}));
			this.customSlots.put(42, this.addSlot(new SlotItemHandler(internal, 42, -88, 67) {
			}));
			this.customSlots.put(43, this.addSlot(new SlotItemHandler(internal, 43, -70, 67) {
			}));
			this.customSlots.put(44, this.addSlot(new SlotItemHandler(internal, 44, -52, 67) {
			}));
			this.customSlots.put(45, this.addSlot(new SlotItemHandler(internal, 45, -34, 67) {
			}));
			this.customSlots.put(46, this.addSlot(new SlotItemHandler(internal, 46, -16, 67) {
			}));
			this.customSlots.put(47, this.addSlot(new SlotItemHandler(internal, 47, 2, 67) {
			}));
			this.customSlots.put(48, this.addSlot(new SlotItemHandler(internal, 48, 20, 67) {
			}));
			this.customSlots.put(49, this.addSlot(new SlotItemHandler(internal, 49, 38, 67) {
			}));
			this.customSlots.put(50, this.addSlot(new SlotItemHandler(internal, 50, 56, 67) {
			}));
			this.customSlots.put(51, this.addSlot(new SlotItemHandler(internal, 51, 74, 67) {
			}));
			this.customSlots.put(52, this.addSlot(new SlotItemHandler(internal, 52, 92, 67) {
			}));
			this.customSlots.put(53, this.addSlot(new SlotItemHandler(internal, 53, 110, 67) {
			}));
			this.customSlots.put(54, this.addSlot(new SlotItemHandler(internal, 54, 128, 67) {
			}));
			this.customSlots.put(55, this.addSlot(new SlotItemHandler(internal, 55, 146, 67) {
			}));
			this.customSlots.put(56, this.addSlot(new SlotItemHandler(internal, 56, 164, 67) {
			}));
			this.customSlots.put(57, this.addSlot(new SlotItemHandler(internal, 57, 182, 67) {
			}));
			this.customSlots.put(58, this.addSlot(new SlotItemHandler(internal, 58, 200, 67) {
			}));
			this.customSlots.put(59, this.addSlot(new SlotItemHandler(internal, 59, 218, 67) {
			}));
			this.customSlots.put(60, this.addSlot(new SlotItemHandler(internal, 60, 236, 67) {
			}));
			this.customSlots.put(61, this.addSlot(new SlotItemHandler(internal, 61, 254, 67) {
			}));
			this.customSlots.put(62, this.addSlot(new SlotItemHandler(internal, 62, 272, 67) {
			}));
			this.customSlots.put(63, this.addSlot(new SlotItemHandler(internal, 63, -88, 49) {
			}));
			this.customSlots.put(64, this.addSlot(new SlotItemHandler(internal, 64, -70, 49) {
			}));
			this.customSlots.put(65, this.addSlot(new SlotItemHandler(internal, 65, -52, 49) {
			}));
			this.customSlots.put(66, this.addSlot(new SlotItemHandler(internal, 66, -34, 49) {
			}));
			this.customSlots.put(67, this.addSlot(new SlotItemHandler(internal, 67, -16, 49) {
			}));
			this.customSlots.put(68, this.addSlot(new SlotItemHandler(internal, 68, 2, 49) {
			}));
			this.customSlots.put(69, this.addSlot(new SlotItemHandler(internal, 69, 20, 49) {
			}));
			this.customSlots.put(70, this.addSlot(new SlotItemHandler(internal, 70, 38, 49) {
			}));
			this.customSlots.put(71, this.addSlot(new SlotItemHandler(internal, 71, 56, 49) {
			}));
			this.customSlots.put(72, this.addSlot(new SlotItemHandler(internal, 72, 74, 49) {
			}));
			this.customSlots.put(73, this.addSlot(new SlotItemHandler(internal, 73, 92, 49) {
			}));
			this.customSlots.put(74, this.addSlot(new SlotItemHandler(internal, 74, 110, 49) {
			}));
			this.customSlots.put(75, this.addSlot(new SlotItemHandler(internal, 75, 128, 49) {
			}));
			this.customSlots.put(76, this.addSlot(new SlotItemHandler(internal, 76, 146, 49) {
			}));
			this.customSlots.put(77, this.addSlot(new SlotItemHandler(internal, 77, 164, 49) {
			}));
			this.customSlots.put(78, this.addSlot(new SlotItemHandler(internal, 78, 182, 49) {
			}));
			this.customSlots.put(79, this.addSlot(new SlotItemHandler(internal, 79, 200, 49) {
			}));
			this.customSlots.put(80, this.addSlot(new SlotItemHandler(internal, 80, 218, 49) {
			}));
			this.customSlots.put(81, this.addSlot(new SlotItemHandler(internal, 81, 236, 49) {
			}));
			this.customSlots.put(82, this.addSlot(new SlotItemHandler(internal, 82, 254, 49) {
			}));
			this.customSlots.put(83, this.addSlot(new SlotItemHandler(internal, 83, 272, 49) {
			}));
			this.customSlots.put(84, this.addSlot(new SlotItemHandler(internal, 84, -88, 31) {
			}));
			this.customSlots.put(85, this.addSlot(new SlotItemHandler(internal, 85, -70, 31) {
			}));
			this.customSlots.put(86, this.addSlot(new SlotItemHandler(internal, 86, -52, 31) {
			}));
			this.customSlots.put(87, this.addSlot(new SlotItemHandler(internal, 87, -34, 31) {
			}));
			this.customSlots.put(88, this.addSlot(new SlotItemHandler(internal, 88, -16, 31) {
			}));
			this.customSlots.put(89, this.addSlot(new SlotItemHandler(internal, 89, 2, 31) {
			}));
			this.customSlots.put(90, this.addSlot(new SlotItemHandler(internal, 90, 20, 31) {
			}));
			this.customSlots.put(91, this.addSlot(new SlotItemHandler(internal, 91, 38, 31) {
			}));
			this.customSlots.put(92, this.addSlot(new SlotItemHandler(internal, 92, 56, 31) {
			}));
			this.customSlots.put(93, this.addSlot(new SlotItemHandler(internal, 93, 74, 31) {
			}));
			this.customSlots.put(94, this.addSlot(new SlotItemHandler(internal, 94, 92, 31) {
			}));
			this.customSlots.put(95, this.addSlot(new SlotItemHandler(internal, 95, 110, 31) {
			}));
			this.customSlots.put(96, this.addSlot(new SlotItemHandler(internal, 96, 128, 31) {
			}));
			this.customSlots.put(97, this.addSlot(new SlotItemHandler(internal, 97, 146, 31) {
			}));
			this.customSlots.put(98, this.addSlot(new SlotItemHandler(internal, 98, 164, 31) {
			}));
			this.customSlots.put(99, this.addSlot(new SlotItemHandler(internal, 99, 182, 31) {
			}));
			this.customSlots.put(100, this.addSlot(new SlotItemHandler(internal, 100, 200, 31) {
			}));
			this.customSlots.put(101, this.addSlot(new SlotItemHandler(internal, 101, 218, 31) {
			}));
			this.customSlots.put(102, this.addSlot(new SlotItemHandler(internal, 102, 236, 31) {
			}));
			this.customSlots.put(103, this.addSlot(new SlotItemHandler(internal, 103, 254, 31) {
			}));
			this.customSlots.put(104, this.addSlot(new SlotItemHandler(internal, 104, 272, 31) {
			}));
			this.customSlots.put(105, this.addSlot(new SlotItemHandler(internal, 105, -88, 13) {
			}));
			this.customSlots.put(106, this.addSlot(new SlotItemHandler(internal, 106, -70, 13) {
			}));
			this.customSlots.put(107, this.addSlot(new SlotItemHandler(internal, 107, -52, 13) {
			}));
			this.customSlots.put(108, this.addSlot(new SlotItemHandler(internal, 108, -34, 13) {
			}));
			this.customSlots.put(109, this.addSlot(new SlotItemHandler(internal, 109, -16, 13) {
			}));
			this.customSlots.put(110, this.addSlot(new SlotItemHandler(internal, 110, 2, 13) {
			}));
			this.customSlots.put(111, this.addSlot(new SlotItemHandler(internal, 111, 20, 13) {
			}));
			this.customSlots.put(112, this.addSlot(new SlotItemHandler(internal, 112, 38, 13) {
			}));
			this.customSlots.put(113, this.addSlot(new SlotItemHandler(internal, 113, 56, 13) {
			}));
			this.customSlots.put(114, this.addSlot(new SlotItemHandler(internal, 114, 74, 13) {
			}));
			this.customSlots.put(115, this.addSlot(new SlotItemHandler(internal, 115, 92, 13) {
			}));
			this.customSlots.put(116, this.addSlot(new SlotItemHandler(internal, 116, 110, 13) {
			}));
			this.customSlots.put(117, this.addSlot(new SlotItemHandler(internal, 117, 128, 13) {
			}));
			this.customSlots.put(118, this.addSlot(new SlotItemHandler(internal, 118, 146, 13) {
			}));
			this.customSlots.put(119, this.addSlot(new SlotItemHandler(internal, 119, 164, 13) {
			}));
			this.customSlots.put(120, this.addSlot(new SlotItemHandler(internal, 120, 182, 13) {
			}));
			this.customSlots.put(121, this.addSlot(new SlotItemHandler(internal, 121, 200, 13) {
			}));
			this.customSlots.put(122, this.addSlot(new SlotItemHandler(internal, 122, 218, 13) {
			}));
			this.customSlots.put(123, this.addSlot(new SlotItemHandler(internal, 123, 236, 13) {
			}));
			this.customSlots.put(124, this.addSlot(new SlotItemHandler(internal, 124, 254, 13) {
			}));
			this.customSlots.put(125, this.addSlot(new SlotItemHandler(internal, 125, 272, 13) {
			}));
			this.customSlots.put(126, this.addSlot(new SlotItemHandler(internal, 126, -88, -5) {
			}));
			this.customSlots.put(127, this.addSlot(new SlotItemHandler(internal, 127, -70, -5) {
			}));
			this.customSlots.put(128, this.addSlot(new SlotItemHandler(internal, 128, -52, -5) {
			}));
			this.customSlots.put(129, this.addSlot(new SlotItemHandler(internal, 129, -34, -5) {
			}));
			this.customSlots.put(130, this.addSlot(new SlotItemHandler(internal, 130, -16, -5) {
			}));
			this.customSlots.put(131, this.addSlot(new SlotItemHandler(internal, 131, 2, -5) {
			}));
			this.customSlots.put(132, this.addSlot(new SlotItemHandler(internal, 132, 20, -5) {
			}));
			this.customSlots.put(133, this.addSlot(new SlotItemHandler(internal, 133, 38, -5) {
			}));
			this.customSlots.put(134, this.addSlot(new SlotItemHandler(internal, 134, 56, -5) {
			}));
			this.customSlots.put(135, this.addSlot(new SlotItemHandler(internal, 135, 74, -5) {
			}));
			this.customSlots.put(136, this.addSlot(new SlotItemHandler(internal, 136, 92, -5) {
			}));
			this.customSlots.put(137, this.addSlot(new SlotItemHandler(internal, 137, 110, -5) {
			}));
			this.customSlots.put(138, this.addSlot(new SlotItemHandler(internal, 138, 128, -5) {
			}));
			this.customSlots.put(139, this.addSlot(new SlotItemHandler(internal, 139, 146, -5) {
			}));
			this.customSlots.put(140, this.addSlot(new SlotItemHandler(internal, 140, 164, -5) {
			}));
			this.customSlots.put(141, this.addSlot(new SlotItemHandler(internal, 141, 182, -5) {
			}));
			this.customSlots.put(142, this.addSlot(new SlotItemHandler(internal, 142, 200, -5) {
			}));
			this.customSlots.put(143, this.addSlot(new SlotItemHandler(internal, 143, 218, -5) {
			}));
			this.customSlots.put(144, this.addSlot(new SlotItemHandler(internal, 144, 236, -5) {
			}));
			this.customSlots.put(145, this.addSlot(new SlotItemHandler(internal, 145, 254, -5) {
			}));
			this.customSlots.put(146, this.addSlot(new SlotItemHandler(internal, 146, 272, -5) {
			}));
			this.customSlots.put(147, this.addSlot(new SlotItemHandler(internal, 147, -88, -23) {
			}));
			this.customSlots.put(148, this.addSlot(new SlotItemHandler(internal, 148, -70, -23) {
			}));
			this.customSlots.put(149, this.addSlot(new SlotItemHandler(internal, 149, -52, -23) {
			}));
			this.customSlots.put(150, this.addSlot(new SlotItemHandler(internal, 150, -34, -23) {
			}));
			this.customSlots.put(151, this.addSlot(new SlotItemHandler(internal, 151, -16, -23) {
			}));
			this.customSlots.put(152, this.addSlot(new SlotItemHandler(internal, 152, 2, -23) {
			}));
			this.customSlots.put(153, this.addSlot(new SlotItemHandler(internal, 153, 20, -23) {
			}));
			this.customSlots.put(154, this.addSlot(new SlotItemHandler(internal, 154, 38, -23) {
			}));
			this.customSlots.put(155, this.addSlot(new SlotItemHandler(internal, 155, 56, -23) {
			}));
			this.customSlots.put(156, this.addSlot(new SlotItemHandler(internal, 156, 74, -23) {
			}));
			this.customSlots.put(157, this.addSlot(new SlotItemHandler(internal, 157, 92, -23) {
			}));
			this.customSlots.put(158, this.addSlot(new SlotItemHandler(internal, 158, 110, -23) {
			}));
			this.customSlots.put(159, this.addSlot(new SlotItemHandler(internal, 159, 128, -23) {
			}));
			this.customSlots.put(160, this.addSlot(new SlotItemHandler(internal, 160, 146, -23) {
			}));
			this.customSlots.put(161, this.addSlot(new SlotItemHandler(internal, 161, 164, -23) {
			}));
			this.customSlots.put(162, this.addSlot(new SlotItemHandler(internal, 162, 182, -23) {
			}));
			this.customSlots.put(163, this.addSlot(new SlotItemHandler(internal, 163, 200, -23) {
			}));
			this.customSlots.put(164, this.addSlot(new SlotItemHandler(internal, 164, 218, -23) {
			}));
			this.customSlots.put(165, this.addSlot(new SlotItemHandler(internal, 165, 236, -23) {
			}));
			this.customSlots.put(166, this.addSlot(new SlotItemHandler(internal, 166, 254, -23) {
			}));
			this.customSlots.put(167, this.addSlot(new SlotItemHandler(internal, 167, 272, -23) {
			}));
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, 1 + 8 + sj * 18, 39 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, 1 + 8 + si * 18, 39 + 142));
		}

		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 168) {
					if (!this.mergeItemStack(itemstack1, 168, this.inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				} else if (!this.mergeItemStack(itemstack1, 0, 168, false)) {
					if (index < 168 + 27) {
						if (!this.mergeItemStack(itemstack1, 168 + 27, this.inventorySlots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 168, 168 + 27, false)) {
							return ItemStack.EMPTY;
						}
					}
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTake(playerIn, itemstack1);
			}
			return itemstack;
		}

		@Override /**
					 * Merges provided ItemStack with the first avaliable one in the
					 * container/player inventor between minIndex (included) and maxIndex
					 * (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the
					 * Container implementation do not check if the item is valid for the slot
					 */
		protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
			boolean flag = false;
			int i = startIndex;
			if (reverseDirection) {
				i = endIndex - 1;
			}
			if (stack.isStackable()) {
				while (!stack.isEmpty()) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot = this.inventorySlots.get(i);
					ItemStack itemstack = slot.getStack();
					if (slot.isItemValid(itemstack) && !itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack)) {
						int j = itemstack.getCount() + stack.getCount();
						int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
						if (j <= maxSize) {
							stack.setCount(0);
							itemstack.setCount(j);
							slot.putStack(itemstack);
							flag = true;
						} else if (itemstack.getCount() < maxSize) {
							stack.shrink(maxSize - itemstack.getCount());
							itemstack.setCount(maxSize);
							slot.putStack(itemstack);
							flag = true;
						}
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			if (!stack.isEmpty()) {
				if (reverseDirection) {
					i = endIndex - 1;
				} else {
					i = startIndex;
				}
				while (true) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot1 = this.inventorySlots.get(i);
					ItemStack itemstack1 = slot1.getStack();
					if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
						if (stack.getCount() > slot1.getSlotStackLimit()) {
							slot1.putStack(stack.split(slot1.getSlotStackLimit()));
						} else {
							slot1.putStack(stack.split(stack.getCount()));
						}
						slot1.onSlotChanged();
						flag = true;
						break;
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			return flag;
		}

		@Override
		public void onContainerClosed(PlayerEntity playerIn) {
			super.onContainerClosed(playerIn);
			if (!bound && (playerIn instanceof ServerPlayerEntity)) {
				if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
					for (int j = 0; j < internal.getSlots(); ++j) {
						if (j == 0)
							continue;
						if (j == 1)
							continue;
						if (j == 2)
							continue;
						if (j == 3)
							continue;
						if (j == 4)
							continue;
						if (j == 5)
							continue;
						if (j == 6)
							continue;
						if (j == 7)
							continue;
						if (j == 8)
							continue;
						if (j == 9)
							continue;
						if (j == 10)
							continue;
						if (j == 11)
							continue;
						if (j == 12)
							continue;
						if (j == 13)
							continue;
						if (j == 14)
							continue;
						if (j == 15)
							continue;
						if (j == 16)
							continue;
						if (j == 17)
							continue;
						if (j == 18)
							continue;
						if (j == 19)
							continue;
						if (j == 20)
							continue;
						if (j == 21)
							continue;
						if (j == 22)
							continue;
						if (j == 23)
							continue;
						if (j == 24)
							continue;
						if (j == 25)
							continue;
						if (j == 26)
							continue;
						if (j == 27)
							continue;
						if (j == 28)
							continue;
						if (j == 29)
							continue;
						if (j == 30)
							continue;
						if (j == 31)
							continue;
						if (j == 32)
							continue;
						if (j == 33)
							continue;
						if (j == 34)
							continue;
						if (j == 35)
							continue;
						if (j == 36)
							continue;
						if (j == 38)
							continue;
						if (j == 39)
							continue;
						if (j == 40)
							continue;
						if (j == 41)
							continue;
						if (j == 42)
							continue;
						if (j == 43)
							continue;
						if (j == 44)
							continue;
						if (j == 45)
							continue;
						if (j == 46)
							continue;
						if (j == 47)
							continue;
						if (j == 48)
							continue;
						if (j == 49)
							continue;
						if (j == 50)
							continue;
						if (j == 51)
							continue;
						if (j == 52)
							continue;
						if (j == 53)
							continue;
						if (j == 54)
							continue;
						if (j == 55)
							continue;
						if (j == 56)
							continue;
						if (j == 57)
							continue;
						if (j == 58)
							continue;
						if (j == 59)
							continue;
						if (j == 60)
							continue;
						if (j == 61)
							continue;
						if (j == 62)
							continue;
						if (j == 63)
							continue;
						if (j == 64)
							continue;
						if (j == 65)
							continue;
						if (j == 66)
							continue;
						if (j == 67)
							continue;
						if (j == 68)
							continue;
						if (j == 69)
							continue;
						if (j == 70)
							continue;
						if (j == 71)
							continue;
						if (j == 72)
							continue;
						if (j == 73)
							continue;
						if (j == 74)
							continue;
						if (j == 75)
							continue;
						if (j == 76)
							continue;
						if (j == 77)
							continue;
						if (j == 78)
							continue;
						if (j == 79)
							continue;
						if (j == 80)
							continue;
						if (j == 81)
							continue;
						if (j == 82)
							continue;
						if (j == 83)
							continue;
						if (j == 84)
							continue;
						if (j == 85)
							continue;
						if (j == 86)
							continue;
						if (j == 87)
							continue;
						if (j == 88)
							continue;
						if (j == 89)
							continue;
						if (j == 90)
							continue;
						if (j == 91)
							continue;
						if (j == 92)
							continue;
						if (j == 93)
							continue;
						if (j == 94)
							continue;
						if (j == 95)
							continue;
						if (j == 96)
							continue;
						if (j == 97)
							continue;
						if (j == 98)
							continue;
						if (j == 99)
							continue;
						if (j == 100)
							continue;
						if (j == 101)
							continue;
						if (j == 102)
							continue;
						if (j == 103)
							continue;
						if (j == 104)
							continue;
						if (j == 105)
							continue;
						if (j == 106)
							continue;
						if (j == 107)
							continue;
						if (j == 108)
							continue;
						if (j == 109)
							continue;
						if (j == 110)
							continue;
						if (j == 111)
							continue;
						if (j == 112)
							continue;
						if (j == 113)
							continue;
						if (j == 114)
							continue;
						if (j == 115)
							continue;
						if (j == 116)
							continue;
						if (j == 117)
							continue;
						if (j == 118)
							continue;
						if (j == 119)
							continue;
						if (j == 120)
							continue;
						if (j == 121)
							continue;
						if (j == 122)
							continue;
						if (j == 123)
							continue;
						if (j == 124)
							continue;
						if (j == 125)
							continue;
						if (j == 126)
							continue;
						if (j == 127)
							continue;
						if (j == 128)
							continue;
						if (j == 129)
							continue;
						if (j == 130)
							continue;
						if (j == 131)
							continue;
						if (j == 132)
							continue;
						if (j == 133)
							continue;
						if (j == 134)
							continue;
						if (j == 135)
							continue;
						if (j == 136)
							continue;
						if (j == 137)
							continue;
						if (j == 138)
							continue;
						if (j == 139)
							continue;
						if (j == 140)
							continue;
						if (j == 141)
							continue;
						if (j == 142)
							continue;
						if (j == 143)
							continue;
						if (j == 144)
							continue;
						if (j == 145)
							continue;
						if (j == 146)
							continue;
						if (j == 147)
							continue;
						if (j == 148)
							continue;
						if (j == 149)
							continue;
						if (j == 150)
							continue;
						if (j == 151)
							continue;
						if (j == 152)
							continue;
						if (j == 153)
							continue;
						if (j == 154)
							continue;
						if (j == 155)
							continue;
						if (j == 156)
							continue;
						if (j == 157)
							continue;
						if (j == 158)
							continue;
						if (j == 159)
							continue;
						if (j == 160)
							continue;
						if (j == 161)
							continue;
						if (j == 162)
							continue;
						if (j == 163)
							continue;
						if (j == 164)
							continue;
						if (j == 165)
							continue;
						if (j == 166)
							continue;
						if (j == 167)
							continue;
						playerIn.dropItem(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
					}
				} else {
					for (int i = 0; i < internal.getSlots(); ++i) {
						if (i == 0)
							continue;
						if (i == 1)
							continue;
						if (i == 2)
							continue;
						if (i == 3)
							continue;
						if (i == 4)
							continue;
						if (i == 5)
							continue;
						if (i == 6)
							continue;
						if (i == 7)
							continue;
						if (i == 8)
							continue;
						if (i == 9)
							continue;
						if (i == 10)
							continue;
						if (i == 11)
							continue;
						if (i == 12)
							continue;
						if (i == 13)
							continue;
						if (i == 14)
							continue;
						if (i == 15)
							continue;
						if (i == 16)
							continue;
						if (i == 17)
							continue;
						if (i == 18)
							continue;
						if (i == 19)
							continue;
						if (i == 20)
							continue;
						if (i == 21)
							continue;
						if (i == 22)
							continue;
						if (i == 23)
							continue;
						if (i == 24)
							continue;
						if (i == 25)
							continue;
						if (i == 26)
							continue;
						if (i == 27)
							continue;
						if (i == 28)
							continue;
						if (i == 29)
							continue;
						if (i == 30)
							continue;
						if (i == 31)
							continue;
						if (i == 32)
							continue;
						if (i == 33)
							continue;
						if (i == 34)
							continue;
						if (i == 35)
							continue;
						if (i == 36)
							continue;
						if (i == 38)
							continue;
						if (i == 39)
							continue;
						if (i == 40)
							continue;
						if (i == 41)
							continue;
						if (i == 42)
							continue;
						if (i == 43)
							continue;
						if (i == 44)
							continue;
						if (i == 45)
							continue;
						if (i == 46)
							continue;
						if (i == 47)
							continue;
						if (i == 48)
							continue;
						if (i == 49)
							continue;
						if (i == 50)
							continue;
						if (i == 51)
							continue;
						if (i == 52)
							continue;
						if (i == 53)
							continue;
						if (i == 54)
							continue;
						if (i == 55)
							continue;
						if (i == 56)
							continue;
						if (i == 57)
							continue;
						if (i == 58)
							continue;
						if (i == 59)
							continue;
						if (i == 60)
							continue;
						if (i == 61)
							continue;
						if (i == 62)
							continue;
						if (i == 63)
							continue;
						if (i == 64)
							continue;
						if (i == 65)
							continue;
						if (i == 66)
							continue;
						if (i == 67)
							continue;
						if (i == 68)
							continue;
						if (i == 69)
							continue;
						if (i == 70)
							continue;
						if (i == 71)
							continue;
						if (i == 72)
							continue;
						if (i == 73)
							continue;
						if (i == 74)
							continue;
						if (i == 75)
							continue;
						if (i == 76)
							continue;
						if (i == 77)
							continue;
						if (i == 78)
							continue;
						if (i == 79)
							continue;
						if (i == 80)
							continue;
						if (i == 81)
							continue;
						if (i == 82)
							continue;
						if (i == 83)
							continue;
						if (i == 84)
							continue;
						if (i == 85)
							continue;
						if (i == 86)
							continue;
						if (i == 87)
							continue;
						if (i == 88)
							continue;
						if (i == 89)
							continue;
						if (i == 90)
							continue;
						if (i == 91)
							continue;
						if (i == 92)
							continue;
						if (i == 93)
							continue;
						if (i == 94)
							continue;
						if (i == 95)
							continue;
						if (i == 96)
							continue;
						if (i == 97)
							continue;
						if (i == 98)
							continue;
						if (i == 99)
							continue;
						if (i == 100)
							continue;
						if (i == 101)
							continue;
						if (i == 102)
							continue;
						if (i == 103)
							continue;
						if (i == 104)
							continue;
						if (i == 105)
							continue;
						if (i == 106)
							continue;
						if (i == 107)
							continue;
						if (i == 108)
							continue;
						if (i == 109)
							continue;
						if (i == 110)
							continue;
						if (i == 111)
							continue;
						if (i == 112)
							continue;
						if (i == 113)
							continue;
						if (i == 114)
							continue;
						if (i == 115)
							continue;
						if (i == 116)
							continue;
						if (i == 117)
							continue;
						if (i == 118)
							continue;
						if (i == 119)
							continue;
						if (i == 120)
							continue;
						if (i == 121)
							continue;
						if (i == 122)
							continue;
						if (i == 123)
							continue;
						if (i == 124)
							continue;
						if (i == 125)
							continue;
						if (i == 126)
							continue;
						if (i == 127)
							continue;
						if (i == 128)
							continue;
						if (i == 129)
							continue;
						if (i == 130)
							continue;
						if (i == 131)
							continue;
						if (i == 132)
							continue;
						if (i == 133)
							continue;
						if (i == 134)
							continue;
						if (i == 135)
							continue;
						if (i == 136)
							continue;
						if (i == 137)
							continue;
						if (i == 138)
							continue;
						if (i == 139)
							continue;
						if (i == 140)
							continue;
						if (i == 141)
							continue;
						if (i == 142)
							continue;
						if (i == 143)
							continue;
						if (i == 144)
							continue;
						if (i == 145)
							continue;
						if (i == 146)
							continue;
						if (i == 147)
							continue;
						if (i == 148)
							continue;
						if (i == 149)
							continue;
						if (i == 150)
							continue;
						if (i == 151)
							continue;
						if (i == 152)
							continue;
						if (i == 153)
							continue;
						if (i == 154)
							continue;
						if (i == 155)
							continue;
						if (i == 156)
							continue;
						if (i == 157)
							continue;
						if (i == 158)
							continue;
						if (i == 159)
							continue;
						if (i == 160)
							continue;
						if (i == 161)
							continue;
						if (i == 162)
							continue;
						if (i == 163)
							continue;
						if (i == 164)
							continue;
						if (i == 165)
							continue;
						if (i == 166)
							continue;
						if (i == 167)
							continue;
						playerIn.inventory.placeItemBackInInventory(playerIn.world,
								internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
					}
				}
			}
		}

		private void slotChanged(int slotid, int ctype, int meta) {
			if (this.world != null && this.world.isRemote()) {
				MechanicalCraftMod.PACKET_HANDLER.sendToServer(new GUISlotChangedMessage(slotid, x, y, z, ctype, meta));
				handleSlotAction(entity, slotid, ctype, meta, x, y, z);
			}
		}
	}

	public static class ButtonPressedMessage {
		int buttonID, x, y, z;
		public ButtonPressedMessage(PacketBuffer buffer) {
			this.buttonID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
		}

		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.buttonID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
		}

		public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	public static class GUISlotChangedMessage {
		int slotID, x, y, z, changeType, meta;
		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			this.slotID = slotID;
			this.x = x;
			this.y = y;
			this.z = z;
			this.changeType = changeType;
			this.meta = meta;
		}

		public GUISlotChangedMessage(PacketBuffer buffer) {
			this.slotID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.changeType = buffer.readInt();
			this.meta = buffer.readInt();
		}

		public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.slotID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
			buffer.writeInt(message.changeType);
			buffer.writeInt(message.meta);
		}

		public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}
	static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}

	private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}
}
