package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.blockentity.menu.VacuumChamberMenu;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@NothingNullByDefault
public class VacuumChamberBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(2);
    private final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> this.inventory);
    private final WEnergyStorage energy = new WEnergyStorage(10000, 1000, 0, 0) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            Objects.requireNonNull(getLevel()).sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    };
    private final LazyOptional<WEnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);
    private int progress = 0;
    private int maxProgress = 0;

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> VacuumChamberBlockEntity.this.energy.getEnergyStored();
                case 1 -> VacuumChamberBlockEntity.this.energy.getMaxEnergyStored();
                case 2 -> VacuumChamberBlockEntity.this.progress;
                case 3 -> VacuumChamberBlockEntity.this.maxProgress;
                default -> throw new UnsupportedOperationException("Unexpected index: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> VacuumChamberBlockEntity.this.energy.setEnergy(value);
                case 2 -> VacuumChamberBlockEntity.this.progress = value;
                case 3 -> VacuumChamberBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) { @Override protected void onContentsChanged(int slot) { setChanged(); } };
    public VacuumChamberBlockEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.VACUUM_CHAMBER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override public Component getDisplayName() { return Component.translatable("block.singularity.vacuum_chamber"); }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int idx = 0; idx < this.itemHandler.getSlots(); idx++) {
            inventory.setItem(idx, this.itemHandler.getStackInSlot(idx));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Inventory", this.inventory.serializeNBT());
        nbt.put("Energy", this.energy.serializeNBT());
        nbt.putInt("Progress", this.progress);
        nbt.putInt("MaxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.isEmpty()) return;

        if (nbt.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        }

        if (nbt.contains("Energy")) {
            this.energy.deserializeNBT(nbt.get("Energy"));
        }
        if (nbt.contains("Progress", Tag.TAG_INT)) {
            this.progress = nbt.getInt("Progress");
        }
        if (nbt.contains("MaxProgress", Tag.TAG_INT)) {
            this.maxProgress = nbt.getInt("MaxProgress");
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryOptional.invalidate();
        this.energyOptional.invalidate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.inventoryOptional.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return this.energyOptional.cast();
        } else {
            return super.getCapability(cap);
        }
    }

    public LazyOptional<ItemStackHandler> getInventoryOptional() {return this.inventoryOptional; }
    public ItemStackHandler getInventory() { return this.inventory; }

    public LazyOptional<WEnergyStorage> getEnergyOptional() { return this.energyOptional; }
    public WEnergyStorage getEnergy() {return this.energy; }


    private void sendUpdate() {
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new VacuumChamberMenu(containerId, inventory, this, this.data);
    }
}
