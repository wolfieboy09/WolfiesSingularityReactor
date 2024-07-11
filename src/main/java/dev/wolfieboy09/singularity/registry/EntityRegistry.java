package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.blockentity.entities.BlastFurnaceControllerEntity;
import dev.wolfieboy09.singularity.blockentity.entities.ElectricFurnaceBlockEntity;
import dev.wolfieboy09.singularity.blockentity.entities.FuelCellBlockEntity;
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
            BLOCK_ENTITIES.register("vacuum_chamber_be",
                    () -> BlockEntityType.Builder.of(VacuumChamberBlockEntity::new, BlockRegistry.VACUUM_CHAMBER.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<FuelCellBlockEntity>> FUEL_CELL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fuel_cell_be",
                    () -> BlockEntityType.Builder.of(FuelCellBlockEntity::new, BlockRegistry.FUEL_CELL.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE =
            BLOCK_ENTITIES.register("electric_furnace_be",
                    () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, BlockRegistry.ELECTRIC_FURNACE.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<BlastFurnaceControllerEntity>> BLAST_FURNACE_CONTROLLER =
            BLOCK_ENTITIES.register("blast_furnace_be",
                    () -> BlockEntityType.Builder.of(BlastFurnaceControllerEntity::new, BlockRegistry.BLAST_FURNACE_CONTROLLER.get())
                            .build(null));

}
