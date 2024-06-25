package dev.wolfieboy09.singularityreactor.Registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static dev.wolfieboy09.singularityreactor.SingularityReactor.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Block> FUEL_CELL = registerBlock("fuel_cell", ()-> new Block(BlockBehaviour.Properties.of()));

    private static <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block) {
        return registerBlock(id, block, Rarity.COMMON);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block, Rarity rarity) {
        RegistryObject<T> registeredBlock = BLOCKS.register(id, block);
        BLOCK_ITEMS.register(id, () -> new BlockItem(registeredBlock.get(), new Item.Properties().rarity(rarity)));
        return registeredBlock;
    }
}
