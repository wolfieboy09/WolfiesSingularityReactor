package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.api.tier.Tiers;
import dev.wolfieboy09.singularity.items.PowerModule;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SingularityReactor.MOD_ID);

    public static final RegistryObject<Item> EMPTY_POWER_MODULE = ITEMS.register("empty_power_module",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STARTER_POWER_MODULE = ITEMS.register("power_module_starter",
            () -> new PowerModule(10000, 1000, Tiers.STARTER));

}
