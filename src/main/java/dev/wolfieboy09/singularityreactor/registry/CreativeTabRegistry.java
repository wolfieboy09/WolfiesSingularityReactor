package dev.wolfieboy09.singularityreactor.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static dev.wolfieboy09.singularityreactor.SingularityReactor.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> BLOCKS = TAB.register("blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlockRegistry.FUEL_CELL.get(), 1))
                    .title(Component.literal("Blocks"))
                    .displayItems((params, output) -> {
                        output.accept(BlockRegistry.FUEL_CELL.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        TAB.register(eventBus);
    }
}
