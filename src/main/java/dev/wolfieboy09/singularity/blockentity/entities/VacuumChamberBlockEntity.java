package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.menu.VacuumChamberMenu;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VacuumChamberBlockEntity extends BlockEntity implements MenuProvider, Tickable {
    private final ItemStackHandler inventory = new ItemStackHandler(2);
    private final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> this.inventory);

    private final EnergyStorage energy = new EnergyStorage(10000, 1000, 0, 0);
    private final LazyOptional<EnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);

    public VacuumChamberBlockEntity(BlockEntityType<?> pType, BlockPos pos, BlockState blockState) {
        super(EntityRegistry.VACUUM_CHAMBER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override public Component getDisplayName() {return Component.translatable("block.singularity.vacuum_chamber"); }


    @Override
    public void tick() {

    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag data = new CompoundTag();
        data.put("Inventory", this.inventory.serializeNBT());
        data.put("Energy", this.energy.serializeNBT());
        nbt.put(SingularityReactor.MOD_ID, data);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag data = nbt.getCompound(SingularityReactor.MOD_ID);
        if (data.isEmpty()) return;

        if (data.contains("Inventory", Tag.TAG_COMPOUND)) {
            this.inventory.deserializeNBT(data.getCompound("Inventory"));
        }

        if (data.contains("Energy", Tag.TAG_INT)) {
            this.energy.deserializeNBT(data.getCompound("Energy"));
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

    public LazyOptional<EnergyStorage> getEnergyOptional() { return this.energyOptional; }
    public EnergyStorage getEnergy() {return this.energy; }


    private void sendUpdate() {
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VacuumChamberMenu(pContainerId, pPlayerInventory, pPlayer);
    }


}
