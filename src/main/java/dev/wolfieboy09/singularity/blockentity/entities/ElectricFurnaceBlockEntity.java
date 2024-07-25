package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@NothingNullByDefault
public class ElectricFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private final WEnergyStorage energy = new WEnergyStorage(10000, 1000, 0) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            Objects.requireNonNull(getLevel()).sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    };

    private final LazyOptional<WEnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);

    public ElectricFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EntityRegistry.ELECTRIC_FURNACE.get(), pPos, pBlockState, RecipeType.SMELTING);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Energy", energy.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.energy.deserializeNBT(tag);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyOptional.invalidate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable
    @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }


    @Override
    protected Component getDefaultName() { return Component.translatable("block.singularity.electric_furnace"); }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }
}
