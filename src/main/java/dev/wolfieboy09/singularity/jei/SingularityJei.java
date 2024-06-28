package dev.wolfieboy09.singularity.jei;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.recipes.VacuumChamberRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@JeiPlugin
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SingularityJei implements IModPlugin {
    @Override public ResourceLocation getPluginUid() { return new ResourceLocation(SingularityReactor.MOD_ID, "jei_plugin"); }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        VacuumChamberRecipe.gatherAllRecipes(Minecraft.getInstance().level.getRecipeManager(), registration);
    }
}
