
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
public class MediumcrateguiGui extends MechanicalCraftModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;
	public MediumcrateguiGui(MechanicalCraftModElements instance) {
		super(instance, 58);
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
			event.getRegistry().register(containerType.setRegistryName("mediumcrategui"));
		}
	}
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, MediumcrateguiGuiWindow::new));
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
			this.internal = new ItemStackHandler(96);
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
			this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, -55, 84) {
			}));
			this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, -37, 84) {
			}));
			this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, -19, 84) {
			}));
			this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, -1, 84) {
			}));
			this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 17, 84) {
			}));
			this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 35, 84) {
			}));
			this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 53, 84) {
			}));
			this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 71, 84) {
			}));
			this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 89, 84) {
			}));
			this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 107, 84) {
			}));
			this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 125, 84) {
			}));
			this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 143, 84) {
			}));
			this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 161, 84) {
			}));
			this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 179, 84) {
			}));
			this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 197, 84) {
			}));
			this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 215, 84) {
			}));
			this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, -55, 66) {
			}));
			this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, -37, 66) {
			}));
			this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, -19, 66) {
			}));
			this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, -1, 66) {
			}));
			this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 17, 66) {
			}));
			this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, 35, 66) {
			}));
			this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, 53, 66) {
			}));
			this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, 71, 66) {
			}));
			this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, 89, 66) {
			}));
			this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, 107, 66) {
			}));
			this.customSlots.put(26, this.addSlot(new SlotItemHandler(internal, 26, 125, 66) {
			}));
			this.customSlots.put(27, this.addSlot(new SlotItemHandler(internal, 27, 143, 66) {
			}));
			this.customSlots.put(28, this.addSlot(new SlotItemHandler(internal, 28, 161, 66) {
			}));
			this.customSlots.put(29, this.addSlot(new SlotItemHandler(internal, 29, 179, 66) {
			}));
			this.customSlots.put(30, this.addSlot(new SlotItemHandler(internal, 30, 197, 66) {
			}));
			this.customSlots.put(31, this.addSlot(new SlotItemHandler(internal, 31, 215, 66) {
			}));
			this.customSlots.put(32, this.addSlot(new SlotItemHandler(internal, 32, -55, 48) {
			}));
			this.customSlots.put(33, this.addSlot(new SlotItemHandler(internal, 33, -37, 48) {
			}));
			this.customSlots.put(34, this.addSlot(new SlotItemHandler(internal, 34, -19, 48) {
			}));
			this.customSlots.put(35, this.addSlot(new SlotItemHandler(internal, 35, -1, 48) {
			}));
			this.customSlots.put(36, this.addSlot(new SlotItemHandler(internal, 36, 17, 48) {
			}));
			this.customSlots.put(37, this.addSlot(new SlotItemHandler(internal, 37, 35, 48) {
			}));
			this.customSlots.put(38, this.addSlot(new SlotItemHandler(internal, 38, 53, 48) {
			}));
			this.customSlots.put(39, this.addSlot(new SlotItemHandler(internal, 39, 71, 48) {
			}));
			this.customSlots.put(40, this.addSlot(new SlotItemHandler(internal, 40, 89, 48) {
			}));
			this.customSlots.put(41, this.addSlot(new SlotItemHandler(internal, 41, 107, 48) {
			}));
			this.customSlots.put(42, this.addSlot(new SlotItemHandler(internal, 42, 125, 48) {
			}));
			this.customSlots.put(43, this.addSlot(new SlotItemHandler(internal, 43, 143, 48) {
			}));
			this.customSlots.put(44, this.addSlot(new SlotItemHandler(internal, 44, 161, 48) {
			}));
			this.customSlots.put(45, this.addSlot(new SlotItemHandler(internal, 45, 179, 48) {
			}));
			this.customSlots.put(46, this.addSlot(new SlotItemHandler(internal, 46, 197, 48) {
			}));
			this.customSlots.put(47, this.addSlot(new SlotItemHandler(internal, 47, 215, 48) {
			}));
			this.customSlots.put(48, this.addSlot(new SlotItemHandler(internal, 48, -55, 30) {
			}));
			this.customSlots.put(49, this.addSlot(new SlotItemHandler(internal, 49, -37, 30) {
			}));
			this.customSlots.put(50, this.addSlot(new SlotItemHandler(internal, 50, -19, 30) {
			}));
			this.customSlots.put(51, this.addSlot(new SlotItemHandler(internal, 51, -1, 30) {
			}));
			this.customSlots.put(52, this.addSlot(new SlotItemHandler(internal, 52, 17, 30) {
			}));
			this.customSlots.put(53, this.addSlot(new SlotItemHandler(internal, 53, 35, 30) {
			}));
			this.customSlots.put(54, this.addSlot(new SlotItemHandler(internal, 54, 53, 30) {
			}));
			this.customSlots.put(55, this.addSlot(new SlotItemHandler(internal, 55, 71, 30) {
			}));
			this.customSlots.put(56, this.addSlot(new SlotItemHandler(internal, 56, 89, 30) {
			}));
			this.customSlots.put(57, this.addSlot(new SlotItemHandler(internal, 57, 107, 30) {
			}));
			this.customSlots.put(58, this.addSlot(new SlotItemHandler(internal, 58, 125, 30) {
			}));
			this.customSlots.put(59, this.addSlot(new SlotItemHandler(internal, 59, 143, 30) {
			}));
			this.customSlots.put(60, this.addSlot(new SlotItemHandler(internal, 60, 161, 30) {
			}));
			this.customSlots.put(61, this.addSlot(new SlotItemHandler(internal, 61, 179, 30) {
			}));
			this.customSlots.put(62, this.addSlot(new SlotItemHandler(internal, 62, 197, 30) {
			}));
			this.customSlots.put(63, this.addSlot(new SlotItemHandler(internal, 63, 215, 30) {
			}));
			this.customSlots.put(64, this.addSlot(new SlotItemHandler(internal, 64, -55, 12) {
			}));
			this.customSlots.put(65, this.addSlot(new SlotItemHandler(internal, 65, -37, 12) {
			}));
			this.customSlots.put(66, this.addSlot(new SlotItemHandler(internal, 66, -19, 12) {
			}));
			this.customSlots.put(67, this.addSlot(new SlotItemHandler(internal, 67, -1, 12) {
			}));
			this.customSlots.put(68, this.addSlot(new SlotItemHandler(internal, 68, 17, 12) {
			}));
			this.customSlots.put(69, this.addSlot(new SlotItemHandler(internal, 69, 35, 12) {
			}));
			this.customSlots.put(70, this.addSlot(new SlotItemHandler(internal, 70, 53, 12) {
			}));
			this.customSlots.put(71, this.addSlot(new SlotItemHandler(internal, 71, 71, 12) {
			}));
			this.customSlots.put(72, this.addSlot(new SlotItemHandler(internal, 72, 89, 12) {
			}));
			this.customSlots.put(73, this.addSlot(new SlotItemHandler(internal, 73, 107, 12) {
			}));
			this.customSlots.put(74, this.addSlot(new SlotItemHandler(internal, 74, 125, 12) {
			}));
			this.customSlots.put(75, this.addSlot(new SlotItemHandler(internal, 75, 143, 12) {
			}));
			this.customSlots.put(76, this.addSlot(new SlotItemHandler(internal, 76, 161, 12) {
			}));
			this.customSlots.put(77, this.addSlot(new SlotItemHandler(internal, 77, 179, 12) {
			}));
			this.customSlots.put(78, this.addSlot(new SlotItemHandler(internal, 78, 197, 12) {
			}));
			this.customSlots.put(79, this.addSlot(new SlotItemHandler(internal, 79, 215, 12) {
			}));
			this.customSlots.put(80, this.addSlot(new SlotItemHandler(internal, 80, -55, -6) {
			}));
			this.customSlots.put(81, this.addSlot(new SlotItemHandler(internal, 81, -37, -6) {
			}));
			this.customSlots.put(82, this.addSlot(new SlotItemHandler(internal, 82, -19, -6) {
			}));
			this.customSlots.put(83, this.addSlot(new SlotItemHandler(internal, 83, -1, -6) {
			}));
			this.customSlots.put(84, this.addSlot(new SlotItemHandler(internal, 84, 17, -6) {
			}));
			this.customSlots.put(85, this.addSlot(new SlotItemHandler(internal, 85, 35, -6) {
			}));
			this.customSlots.put(86, this.addSlot(new SlotItemHandler(internal, 86, 53, -6) {
			}));
			this.customSlots.put(87, this.addSlot(new SlotItemHandler(internal, 87, 71, -6) {
			}));
			this.customSlots.put(88, this.addSlot(new SlotItemHandler(internal, 88, 89, -6) {
			}));
			this.customSlots.put(89, this.addSlot(new SlotItemHandler(internal, 89, 107, -6) {
			}));
			this.customSlots.put(90, this.addSlot(new SlotItemHandler(internal, 90, 125, -6) {
			}));
			this.customSlots.put(91, this.addSlot(new SlotItemHandler(internal, 91, 143, -6) {
			}));
			this.customSlots.put(92, this.addSlot(new SlotItemHandler(internal, 92, 161, -6) {
			}));
			this.customSlots.put(93, this.addSlot(new SlotItemHandler(internal, 93, 179, -6) {
			}));
			this.customSlots.put(94, this.addSlot(new SlotItemHandler(internal, 94, 197, -6) {
			}));
			this.customSlots.put(95, this.addSlot(new SlotItemHandler(internal, 95, 215, -6) {
			}));
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, -2 + 8 + sj * 18, 20 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, -2 + 8 + si * 18, 20 + 142));
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
				if (index < 96) {
					if (!this.mergeItemStack(itemstack1, 96, this.inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				} else if (!this.mergeItemStack(itemstack1, 0, 96, false)) {
					if (index < 96 + 27) {
						if (!this.mergeItemStack(itemstack1, 96 + 27, this.inventorySlots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 96, 96 + 27, false)) {
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
						if (j == 37)
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
						if (i == 37)
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
