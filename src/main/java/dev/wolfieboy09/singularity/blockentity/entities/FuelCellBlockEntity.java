package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.api.storage.FuelStorage;
import dev.wolfieboy09.singularity.capabilities.SingularityCapabilities;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NothingNullByDefault
public class FuelCellBlockEntity extends BlockEntity implements MenuProvider {
    private final FuelStorage fuel = new FuelStorage(10000, 500);
    private final LazyOptional<FuelStorage> lazyFuel = LazyOptional.of(() -> this.fuel);

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FuelCellBlockEntity.this.fuel.getFuelStored();
                case 1 -> FuelCellBlockEntity.this.fuel.getMaxFuelStored();
                default -> throw new UnsupportedOperationException("Unexpected index: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) FuelCellBlockEntity.this.fuel.setFuel(value);
        }

        @Override
        public int getCount() { return 2; }
    };

    public FuelCellBlockEntity(BlockPos pPos, BlockState pBlockState) { super(EntityRegistry.FUEL_CELL_BLOCK_ENTITY.get(), pPos, pBlockState); }
    @Override public Component getDisplayName() { return Component.translatable("block.singularity.fuel_cell"); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFuel.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("Fuel", this.fuel.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.isEmpty()) return;

        if (nbt.contains("Fuel")) {
            this.fuel.deserializeNBT(nbt.get("Fuel"));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == SingularityCapabilities.FUEL) {
            return this.lazyFuel.cast();
        } else {
            return super.getCapability(cap);
        }
    }

    public LazyOptional<FuelStorage> getFuelOptional() { return this.lazyFuel; }
    public FuelStorage getFuel() { return this.fuel; }

    private void sendUpdate() {
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
}
