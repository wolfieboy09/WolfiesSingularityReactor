package dev.wolfieboy09.singularity.blockentity.menu;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.entities.VacuumChamberBlockEntity;
import dev.wolfieboy09.singularity.registry.BlockRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VacuumChamberMenu extends AbstractContainerMenu {
    private final VacuumChamberBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ContainerData data;

    public VacuumChamberMenu(int pContainerId, Inventory inv,@NotNull FriendlyByteBuf extraData) {
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
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if(fromStack.getCount() <= 0) fromSlot.set(ItemStack.EMPTY);
        if(!fromSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if(pIndex < 36) {
            // We are inside the player's inventory
            if(!moveItemStackTo(fromStack, 36, 37, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 37) {
            // We are inside the block entity inventory
            if(!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            SingularityReactor.LOGGER.error("Invalid slot index: %s".formatted(pIndex));
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
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
