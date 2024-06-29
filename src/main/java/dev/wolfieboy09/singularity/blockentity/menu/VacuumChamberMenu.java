package dev.wolfieboy09.singularity.blockentity.menu;

import dev.wolfieboy09.singularity.blockentity.entities.VacuumChamberBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VacuumChamberMenu extends AbstractContainerMenu {
    private final VacuumChamberBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public VacuumChamberMenu(int containerId, Inventory inventory, @NotNull FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos(), new SimpleContainerData(2)));
    }

    public VacuumChamberMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.VACUUM_CHAMBER_MENU.get(), containerId);
        checkContainerSize(inventory, 2);
        blockEntity = ((VacuumChamberBlockEntity) entity);
        this.level = inventory.player.level();
        this.data = data;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
