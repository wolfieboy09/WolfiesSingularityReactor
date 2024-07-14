package dev.wolfieboy09.singularity.registry;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.recipes.VacuumChamberRecipe;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializer {
    public static final DeferredRegister<net.minecraft.world.item.crafting.RecipeSerializer<?>> SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SingularityReactor.MOD_ID);

    public static final RegistryObject<net.minecraft.world.item.crafting.RecipeSerializer<VacuumChamberRecipe>> VACUUMING_SERIALIZER =
            SERIALIZER.register(VacuumChamberRecipe.Type.ID, () -> VacuumChamberRecipe.Serializer.INSTANCE);
}
