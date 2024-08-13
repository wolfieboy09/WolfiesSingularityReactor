package dev.wolfieboy09.singularity.items;

import dev.wolfieboy09.singularity.api.tier.Tiers;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.wolfieslib.api.storage.WEnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@NothingNullByDefault
public class PowerModule extends Item {
    private final int capacity;
    private final int transferRate;
    private final WEnergyStorage energy;

    public PowerModule(int capacity, int transferRate, Tiers rarity) {
        // Can't use my OWN rarity because it's not in the enum for the minecraft rarity acceptance
        super(new Properties().stacksTo(1));
        this.capacity = capacity;
        this.transferRate = transferRate;
        this.energy = new WEnergyStorage(this.capacity, this.transferRate) {
            @Override
            public void onEnergyChanged() {

            }
        };
    }

    @Override public boolean isEnchantable(ItemStack pStack) { return false; }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isCharging(stack);
    }

    private boolean isCharging(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("IsChargingInventory");
    }

    private void switchChargingMode(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        boolean batteryChargingInv = nbt.getBoolean("IsChargingInventory");
        nbt.putBoolean("IsChargingInventory", !batteryChargingInv);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            switchChargingMode(stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        assert stack.getTag() != null;

        stack.getOrCreateTag().put("Energy", this.energy.serializeNBT());

        if (stack.getTag().contains("Energy")) {
            this.energy.deserializeNBT(stack.getTag().getCompound("Energy"));
        }

        String tooltipText = this.energy.getEnergyStored() +
                " FE / " +
                this.capacity +
                " FE";
        tooltip.add(Component.literal(tooltipText));
        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }
}
