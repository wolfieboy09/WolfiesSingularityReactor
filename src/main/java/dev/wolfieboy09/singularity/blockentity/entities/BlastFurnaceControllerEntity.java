package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.registry.BlockRegistry;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlastFurnaceControllerEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tick(Level level, BlockPos pos) {
        if (canFormBlastFurnace(level, pos)) {
            System.out.println("Passed - Can form!");
        } else {
            System.out.println("Failed - Can't form");
        }
    }

    public static boolean canFormBlastFurnace(Level world, BlockPos pos) {
        Block block = BlockRegistry.BLAST_FURNACE_BRICK.get();

        for (int y = 1; y < pos.getY() - 1; ++y) {
            for (int x = 1; x < pos.getX() - 1; ++x) {
                if (world.getBlockState(pos.offset(x, y, 0)).getBlock() == block || world.getBlockState(pos.offset(-x, y, 0)).getBlock() == block) {
                    return false;
                }
            }
            for (int z = 1; z < pos.getZ() - 1; ++z) {
                if (world.getBlockState(pos.offset(0,y,z)).getBlock() == block || world.getBlockState(pos.offset(0, y, -z)).getBlock() == block) {
                    return false;
                }
            }
        }
        return true;
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

    public BlastFurnaceControllerEntity(BlockPos pos, BlockState blockState) {
        super(EntityRegistry.BLAST_FURNACE_CONTROLLER.get(), pos, blockState);
    }

}
