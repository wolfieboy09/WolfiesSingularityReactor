package dev.wolfieboy09.singularity.jei.category;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.blockentity.recipes.VacuumChamberRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VacuumingCategory implements IRecipeCategory<VacuumChamberRecipe> {
    public static final ResourceLocation LOCATION = new ResourceLocation(SingularityReactor.MOD_ID, "vacuuming");
    public static final RecipeType<VacuumChamberRecipe> VACUUM_CHAMBER_RECIPE_TYPE = new RecipeType<>(LOCATION, VacuumChamberRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public VacuumingCategory(IDrawable background, IDrawable icon) {
        this.background = background;
        this.icon = icon;
    }

    @Override public RecipeType<VacuumChamberRecipe> getRecipeType() { return VACUUM_CHAMBER_RECIPE_TYPE; }
    @Override public Component getTitle() { return Component.translatable("block.singularity.vacuum_chamber"); }
    @Override public IDrawable getBackground() { return background; }
    @Override public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VacuumChamberRecipe recipe, IFocusGroup focuses) {

    }
}
