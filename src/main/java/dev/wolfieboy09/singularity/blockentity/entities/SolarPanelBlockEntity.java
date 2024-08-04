package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.registry.EntityRegistry;
import dev.wolfieboy09.singularity.util.WorldUtil;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@NothingNullByDefault
public class SolarPanelBlockEntity extends BlockEntity {
    protected final BlockPos pos;

    private final WEnergyStorage energy = new WEnergyStorage(10000, 0, 100) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            Objects.requireNonNull(getLevel()).sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    };
    private final LazyOptional<WEnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);


    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.SOLAR_PANEL.get(), pos, state);
        this.pos = pos;
    }

    public boolean canSeeSun(Level level, BlockPos pos) {
        return WorldUtil.canSeeSun(level, pos);
    }

    public void tick(Level level, BlockPos pos) {
        if (canSeeSun(level, pos)) {
            energy.addEnergy(10);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Energy", this.energy.serializeNBT());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.isEmpty()) return;
        if (nbt.contains("Energy")) {
            this.energy.deserializeNBT(nbt.get("Energy"));
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyOptional.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if (cap == ForgeCapabilities.ENERGY) {
            return this.energyOptional.cast();
        } else {
            return super.getCapability(cap);
        }
    }



    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable
    @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

}
