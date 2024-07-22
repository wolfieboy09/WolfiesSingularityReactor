package dev.wolfieboy09.singularity.jei.category;

import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.wolfieslib.api.annotations.NothingNullByDefault;
import dev.wolfieboy09.singularity.blockentity.recipes.VacuumChamberRecipe;
import dev.wolfieboy09.singularity.registry.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@NothingNullByDefault
public class VacuumingCategory implements IRecipeCategory<VacuumChamberRecipe> {
    public static final ResourceLocation LOCATION = new ResourceLocation(SingularityReactor.MOD_ID, "vacuuming");
    public static final ResourceLocation GUI_LOCATION = new ResourceLocation(SingularityReactor.MOD_ID, "textures/gui/vacuum_chamber_gui.png");
    private int renderProgress = 0;
    public static final RecipeType<VacuumChamberRecipe> VACUUM_CHAMBER_RECIPE_TYPE = new RecipeType<>(LOCATION, VacuumChamberRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;


    public VacuumingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_LOCATION, 16, 11, 92, 68);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.VACUUM_CHAMBER.get()));
    }

    @Override public RecipeType<VacuumChamberRecipe> getRecipeType() { return VACUUM_CHAMBER_RECIPE_TYPE; }
    @Override public Component getTitle() { return Component.translatable("jei.singularity.vacuuming"); }
    @Override public IDrawable getBackground() { return background; }
    @Override public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VacuumChamberRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 14).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 14).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(VacuumChamberRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(GUI_LOCATION, 57, 3, 176, 0, 14, 14);
    }


}
