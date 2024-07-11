package dev.wolfieboy09.singularity.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static dev.wolfieboy09.singularity.SingularityReactor.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> BLOCKS = TAB.register("blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlockRegistry.FUEL_CELL.get()))
                    .title(Component.translatable("creative_tab.singularity.name"))
                    .displayItems((params, output) -> {
                        output.accept(BlockRegistry.FUEL_CELL.get());
                        output.accept(BlockRegistry.VACUUM_CHAMBER.get());
                        //TODO output.accept(BlockRegistry.ELECTRIC_FURNACE.get());
                        output.accept(ItemRegistry.EMPTY_POWER_MODULE.get());
                        output.accept(BlockRegistry.BLAST_FURNACE_BRICK.get());
                        output.accept(BlockRegistry.BLAST_FURNACE_CONTROLLER.get());
                    }).build());
}
