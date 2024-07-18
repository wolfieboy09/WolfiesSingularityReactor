package dev.wolfieboy09.singularity.blockentity.block;

import dev.wolfieboy09.singularity.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.blockentity.entities.BlastFurnaceControllerEntity;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


@NothingNullByDefault
public class BlastFurnaceController extends BaseEntityBlock {
    public BlastFurnaceController(Properties properties) {
        super(properties);
    }


    @Override public RenderShape getRenderShape(BlockState pState) { return RenderShape.MODEL; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlastFurnaceControllerEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, EntityRegistry.BLAST_FURNACE_CONTROLLER.get(),
                (level, pos, state, blockEntity) -> blockEntity.tick(level, pos));
    }
}
