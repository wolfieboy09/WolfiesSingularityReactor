package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VacuumChamberBlockEntity extends BlockEntity implements MenuProvider {
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public final ContainerData data;
    private int progress = 0;
    private int maxProgress = 260;
    private int energy = 0;
    private int maxEnergy = 4000;

    public static class VacuumSlots {
        public static final int INPUT_SLOT = 0;
        public static final int OUTPUT_SLOT = 1;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override protected void onContentsChanged(int slot) { setChanged(); }
    };

    public VacuumChamberBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(EntityRegistry.VACUUM_CHAMBER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> VacuumChamberBlockEntity.this.progress;
                    case 1 -> VacuumChamberBlockEntity.this.maxProgress;
                    case 2 -> VacuumChamberBlockEntity.this.energy;
                    case 3 -> VacuumChamberBlockEntity.this.maxEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> VacuumChamberBlockEntity.this.progress = value;
                    case 1 -> VacuumChamberBlockEntity.this.maxProgress = value;
                    case 2 -> VacuumChamberBlockEntity.this.energy = value;
                    case 3 -> VacuumChamberBlockEntity.this.maxEnergy = value;
                }
            }

            @Override
            public int getCount() { return 4; }
        };
    }

    @Override public @NotNull Component getDisplayName() {return Component.translatable("block.singularity.vacuum_chamber"); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return null;
    }
}
