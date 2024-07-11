package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.singularity.registry.BlockRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlastFurnaceController extends BlockEntity {
    private final Map<Direction, LazyOptional<IItemHandlerModifiable>> INVENTORY = new HashMap<>();
    private final Map<Direction, ItemStackHandler> HANDLERS = new HashMap<>();

    public BlastFurnaceController(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static boolean validateFacesFixedSize(Level world, BlockPos corner, @NotNull BlockPos size) {
        for (int y = 1; y < size.getY() - 1; ++y) {
            for (int x = 1; x < size.getX() - 1; ++x) {
                if (world.getBlockState(corner.offset(x,y,0)).getBlock() == BlockRegistry.BLAST_FURNACE_BRICK.get() || world.getBlockState(corner.offset(-x,y,0)).getBlock() == BlockRegistry.BLAST_FURNACE_BRICK.get()) {
                    return false;
                }
            }
            for (int z = 1; z < size.getZ() - 1; ++z) {
                if (world.getBlockState(corner.offset(0,y,z)).getBlock() == BlockRegistry.BLAST_FURNACE_BRICK.get() || world.getBlockState(corner.offset(0, y, -z)).getBlock() == BlockRegistry.BLAST_FURNACE_BRICK.get()) {
                    return false;
                }
            }
        }
        return true;
    }
}
