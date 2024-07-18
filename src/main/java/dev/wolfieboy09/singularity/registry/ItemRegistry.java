package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.SingularityReactor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SingularityReactor.MOD_ID);

    public static final RegistryObject<Item> EMPTY_POWER_MODULE = ITEMS.register("empty_power_module",
            () -> new Item(new Item.Properties()));

}
