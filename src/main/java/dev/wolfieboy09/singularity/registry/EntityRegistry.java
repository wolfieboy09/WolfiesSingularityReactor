package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.blockentity.entities.VacuumChamberBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.wolfieboy09.singularity.SingularityReactor.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);


    public static final RegistryObject<BlockEntityType<VacuumChamberBlockEntity>> VACUUM_CHAMBER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("vacuum_chamber_block_entity", () ->
                    BlockEntityType.Builder.of(VacuumChamberBlockEntity::new,
                            BlockRegistry.VACUUM_CHAMBER.get()).build(null));
}
