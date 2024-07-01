package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.storage.SingularityFuelStorage;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FuelCellBlockEntity extends BlockEntity implements MenuProvider {
    private SingularityFuelStorage fuel = new SingularityFuelStorage(10000, 500);
    private LazyOptional<SingularityFuelStorage> lazyFuel = LazyOptional.of(() -> this.fuel);

    public FuelCellBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EntityRegistry.FUEL_CELL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override public Component getDisplayName() { return Component.translatable("block.singularity.fuel_cell"); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        CompoundTag data = new CompoundTag();
        data.put("Fuel", this.fuel.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag data = nbt.getCompound(SingularityReactor.MOD_ID);
        if (data.isEmpty()) return;

        if (data.contains("Fuel", Tag.TAG_INT)) {
            this.fuel.deserializeNBT(data.getCompound("Fuel"));
        }
    }
}
