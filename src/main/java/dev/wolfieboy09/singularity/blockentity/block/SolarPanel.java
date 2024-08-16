package dev.wolfieboy09.singularity.blockentity.block;

import dev.wolfieboy09.singularity.blockentity.entities.generators.SolarPanelBlockEntity;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
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
public class SolarPanel extends BaseEntityBlock {
    public SolarPanel(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SolarPanelBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, EntityRegistry.SOLAR_PANEL.get(),
                (level, pos, state, blockEntity) -> blockEntity.tick(level, pos));
    }
}
