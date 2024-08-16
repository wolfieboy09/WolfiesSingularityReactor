package dev.wolfieboy09.singularity.blockentity.entities.generators;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.intergration.computercraft.SolarPanelPeripheral;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import dev.wolfieboy09.singularity.util.WorldUtil;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
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

    public final WEnergyStorage energy = new WEnergyStorage(10000, 0, 100) {
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

    private int generationRate;

    public WEnergyStorage energyStorage() {
        return energy;
    }

    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.SOLAR_PANEL.get(), pos, state);
    }

    public boolean canSeeSun(Level level, BlockPos pos) {
        return WorldUtil.canSeeSun(level, pos);
    }

    public int generationRate() {
        return this.generationRate;
    }

    public void tick(Level level, BlockPos pos) {
        if (canSeeSun(level, pos) && level.isDay() && this.energy.getEnergyStored() != this.energy.getMaxEnergyStored()) {
            float timeOfDay = level.getTimeOfDay(1.0F);
            float sunlightIntensity = level.getBrightness(LightLayer.SKY, pos.above());

            float generationRateFactor = calculateGenerationRateFactor(timeOfDay, sunlightIntensity);
            this.generationRate = (int) (2 * generationRateFactor);
            this.energy.addEnergy(this.generationRate);
        } else {
            this.generationRate = 0;
        }
    }

    private float calculateGenerationRateFactor(float timeOfDay, float sunlightIntensity) {
        float factor = (float) Math.sin(timeOfDay) * sunlightIntensity;
        return Mth.clamp(factor, 0.0F, 1.0F);
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
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction direction) {
        if (cap == ForgeCapabilities.ENERGY) {
            return this.energyOptional.cast();
        }

        if (!SingularityReactor.COMPUTER_CRAFT_LOADED)
            return super.getCapability(cap, direction);
        if (cap == dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL) {
            return LazyOptional.of(() -> new SolarPanelPeripheral(this)).cast();
        }
        return super.getCapability(cap, direction);
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
