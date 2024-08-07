package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static dev.wolfieboy09.singularity.SingularityReactor.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SingularityReactor.MOD_ID);

    public static final RegistryObject<Block> FUEL_CELL = registerBlock("fuel_cell",
            () -> new FuelCell(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion()), 1);

    public static final RegistryObject<Block> VACUUM_CHAMBER = registerBlock("vacuum_chamber",
            () -> new VacuumChamber(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> ELECTRIC_FURNACE = registerBlock("electric_furnace",
            () -> new ElectricFurnace(BlockBehaviour.Properties.copy(Blocks.FURNACE).sound(SoundType.METAL).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLAST_FURNACE_BRICK = registerBlock("blast_furnace_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(3.0F, 7.0F)));

    public static final RegistryObject<Block> BLAST_FURNACE_CONTROLLER = registerBlock("blast_furnace_controller",
            () -> new BlastFurnaceController(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(3.0F, 7.0F)));

    public static final RegistryObject<Block> SOLAR_PANEL = registerBlock("solar_panel",
            () -> new SolarPanel(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), 1);

    public static final RegistryObject<Block> COAL_BURNER = registerBlock("coal_burner",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 1);

    public static final RegistryObject<Block> GEOTHERMAL_GENERATOR = registerBlock("geothermal_generator",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 1);




    // registry stuff
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int stacksTo) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stacksTo);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int stacksTo, Rarity rarity) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stacksTo, rarity);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Rarity rarity) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, rarity);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int stacksTo) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(stacksTo)));
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int stacksTo, Rarity rarity) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(stacksTo).rarity(rarity)));
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Rarity rarity) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(rarity)));
    }
}
