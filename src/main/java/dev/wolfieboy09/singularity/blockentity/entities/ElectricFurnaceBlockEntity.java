package dev.wolfieboy09.singularity.blockentity.entities;

import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@NothingNullByDefault
public class ElectricFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public ElectricFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EntityRegistry.ELECTRIC_FURNACE.get(), pPos, pBlockState, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() { return Component.translatable("block.singularity.electric_furnace"); }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }
}
