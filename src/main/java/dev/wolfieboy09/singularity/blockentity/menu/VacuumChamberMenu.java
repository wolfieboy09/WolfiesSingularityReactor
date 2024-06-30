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
    private int containerId;

    public VacuumChamberMenu(int containerId, Inventory inventory, Player player) {
        super(ModMenuTypes.VACUUM_CHAMBER_MENU.get(), containerId, inventory, player);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
