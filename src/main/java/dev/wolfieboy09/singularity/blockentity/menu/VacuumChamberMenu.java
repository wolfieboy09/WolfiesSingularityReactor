package dev.wolfieboy09.singularity.blockentity.menu;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.blockentity.entities.VacuumChamberBlockEntity;
import dev.wolfieboy09.singularity.registry.BlockRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@NothingNullByDefault
public class VacuumChamberMenu extends AbstractContainerMenu {
    private final VacuumChamberBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ContainerData data;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;

    public VacuumChamberMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, Objects.requireNonNull(inv.player.level().getBlockEntity(extraData.readBlockPos())), new SimpleContainerData(4));
    }

    public VacuumChamberMenu(int containerId, Inventory playerInv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.VACUUM_CHAMBER_MENU.get(), containerId);

        if (blockEntity instanceof VacuumChamberBlockEntity entity) {
            this.blockEntity = entity;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into VacuumChamberMenu".formatted(blockEntity.getClass().getCanonicalName()));
        }

        checkContainerSize(playerInv, 2);
        this.levelAccess = ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos());
        this.data = data;
        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(
                iItemHandler -> {
                    this.addSlot(new SlotItemHandler(iItemHandler, 0, 79, 97));
                    this.addSlot(new SlotItemHandler(iItemHandler, 1, 79, 44));
                }
        );
        addDataSlots(data);
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 31;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + column + (row * 9), 8 + (column * 18), 84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, 8 + (column * 18), 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            SingularityReactor.LOGGER.error("Invalid slotIndex: {}", pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) { return stillValid(this.levelAccess, pPlayer, BlockRegistry.VACUUM_CHAMBER.get()); }

    public VacuumChamberBlockEntity getBlockEntity() { return this.blockEntity; }
    public int getEnergy() { return this.data.get(0); }
    public int getMaxEnergy() { return this.data.get(1); }
    public int getBurnTime() { return this.data.get(2); }
    public int getMaxBurnTime() { return this.data.get(3); }
    public int getEnergyStoredScaled() { return (int) (((float) getEnergy() / (float) getMaxEnergy()) * 38); }
}
