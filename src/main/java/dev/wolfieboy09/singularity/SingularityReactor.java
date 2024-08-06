package dev.wolfieboy09.singularity;

import com.mojang.logging.LogUtils;
import dev.wolfieboy09.singularity.api.storage.FuelStorage;
import dev.wolfieboy09.singularity.blockentity.menu.ModMenuTypes;
import dev.wolfieboy09.singularity.blockentity.screen.VacuumChamberScreen;
import dev.wolfieboy09.singularity.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(SingularityReactor.MOD_ID)
public class SingularityReactor {
    public static final String MOD_ID = "singularity";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final boolean COMPUTER_CRAFT_LOADED = ModList.get().isLoaded("computercraft");
    public static Level WORLD;


    public SingularityReactor() {

        // Register ourselves for server and other game events we are interested in
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoad);

        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        EntityRegistry.BLOCK_ENTITIES.register(bus);
        CreativeTabRegistry.TAB.register(bus);
        RecipeSerializer.SERIALIZER.register(bus);
        ModMenuTypes.MENUS.register(bus);
        bus.addListener(this::registerCapabilities);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // private void commonSetup(final FMLCommonSetupEvent event) {}

    // private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent public void onServerStarting(ServerStartingEvent event) { LOGGER.info("Started up on dist DEDICATED_SERVER"); }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.VACUUM_CHAMBER_MENU.get(), VacuumChamberScreen::new);
        }
    }

    public void registerCapabilities(@NotNull RegisterCapabilitiesEvent event) {
        event.register(FuelStorage.class);
    }

    private void onWorldLoad(@NotNull LevelEvent.Load event) {
        WORLD = (Level) event.getLevel();
    }
}