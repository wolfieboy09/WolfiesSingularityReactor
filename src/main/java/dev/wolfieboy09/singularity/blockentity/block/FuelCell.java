package dev.wolfieboy09.singularity.blockentity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FuelCell extends BaseEntityBlock {
    public FuelCell(Properties pProperties) { super(pProperties); }

    // I place you under my protected wing
    protected static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE_CENTER;
    protected static final VoxelShape SHAPE_BASE;
    protected static final VoxelShape SHAPE_HANDEL_LEFT;
    protected static final VoxelShape SHAPE_HANDEL_RIGHT;
    protected static final VoxelShape SHAPE_HANDEL_TOP;
    protected static final VoxelShape SHAPE_HANDEL;
    protected static final VoxelShape SHAPE_COMMON;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { pBuilder.add(FACING); }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState pState) { return RenderShape.MODEL; }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) { return SHAPE_COMMON; }

    static {
        SHAPE_CENTER = Block.box(5.0, 0.0, 5.0, 11.0, 12.0, 11.0);
        SHAPE_BASE = Block.box(6.6, 0.0, 6.6, 11.0, 1.2, 11.0);

        SHAPE_HANDEL_LEFT = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
        SHAPE_HANDEL_RIGHT = Block.box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        SHAPE_HANDEL_TOP = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);

        SHAPE_HANDEL = Shapes.or(SHAPE_HANDEL_LEFT, SHAPE_HANDEL_TOP, SHAPE_HANDEL_RIGHT);
        SHAPE_COMMON = Shapes.or(SHAPE_CENTER, SHAPE_HANDEL, SHAPE_BASE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) { return null; }
}
