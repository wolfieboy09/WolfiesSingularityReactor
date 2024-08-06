package dev.wolfieboy09.singularity.items;

import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@NothingNullByDefault
public class PowerModule extends Item {
    private final int capacity;
    private final WEnergyStorage energy;

    public PowerModule(int capacity, Rarity rarity) {
        super(new Properties().stacksTo(1).rarity(rarity));
        this.capacity = capacity;
        this.energy = new WEnergyStorage(this.capacity) {
            @Override
            public void onEnergyChanged() {

            }
        };
    }

    @Override public boolean isEnchantable(ItemStack pStack) { return false; }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltipComponent, TooltipFlag pIsAdvanced) {
        tooltipComponent.add(Component.literal(this.energy.getEnergyStored() + " FE / " + this.capacity + " FE"));
        super.appendHoverText(pStack, pLevel, tooltipComponent, pIsAdvanced);
    }
}
