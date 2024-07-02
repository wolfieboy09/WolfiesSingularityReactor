package dev.wolfieboy09.singularity.jei;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.menu.ModMenuTypes;
import dev.wolfieboy09.singularity.blockentity.menu.VacuumChamberMenu;
import dev.wolfieboy09.singularity.blockentity.recipes.VacuumChamberRecipe;
import dev.wolfieboy09.singularity.jei.category.VacuumingCategory;
import dev.wolfieboy09.singularity.registry.BlockRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.VACUUM_CHAMBER.get()), VacuumingCategory.VACUUM_CHAMBER_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(VacuumChamberMenu.class, ModMenuTypes.VACUUM_CHAMBER_MENU.get(), VacuumingCategory.VACUUM_CHAMBER_RECIPE_TYPE, 0, 2, 2, 36);
    }
}
