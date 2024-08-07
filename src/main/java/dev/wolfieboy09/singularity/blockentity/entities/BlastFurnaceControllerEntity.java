package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.registry.BlockRegistry;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@NothingNullByDefault
public class BlastFurnaceControllerEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public BlastFurnaceControllerEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.BLAST_FURNACE_CONTROLLER.get(), pos, blockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tick(Level level, BlockPos pos) {
        // switch (direction) {
        //case NORTH -> canFormBlastFurnace(level, pos.south());
        // case WEST -> canFormBlastFurnace(level, pos.east());
        //    case SOUTH -> canFormBlastFurnace(level, pos.north());
          //  case EAST -> canFormBlastFurnace(level, pos.west());
        // }
    }

    public static boolean canFormBlastFurnace(Level level, BlockPos input) {
        return BlockPos.betweenClosedStream(input.offset(-1,-1,1), input.offset(1, 1, 1)).anyMatch(pos -> level.getBlockState(pos).getBlock() == BlockRegistry.BLAST_FURNACE_BRICK.get());
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

}
