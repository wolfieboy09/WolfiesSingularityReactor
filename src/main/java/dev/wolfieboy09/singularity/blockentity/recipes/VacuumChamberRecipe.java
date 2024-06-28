package dev.wolfieboy09.singularity.blockentity.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.wolfieboy09.singularity.SingularityReactor;
import dev.wolfieboy09.singularity.jei.category.VacuumingCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VacuumChamberRecipe implements Recipe<SimpleContainer> {
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredient;
    private final ResourceLocation id;
    public final static int FALLBACK_ENERGY_USAGE = 500;
    public final static int FALLBACK_COOKING_TIME = 250;
    protected final int cookingTime;
    protected final int energyUsage;

    public VacuumChamberRecipe(ResourceLocation id, ItemStack result, NonNullList<Ingredient> ingredient, int cookingTime, int energyUsage) {
        this.id = id;
        this.result = result;
        this.ingredient = ingredient;
        this.cookingTime = cookingTime;
        this.energyUsage = energyUsage;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return false;
    }

    @Override public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) { return result; }
    @Override public boolean canCraftInDimensions(int i, int i1) { return true; }
    @Override public ItemStack getResultItem(RegistryAccess registryAccess) { return result.copy(); }
    @Override public ResourceLocation getId() { return id; }
    @Override public RecipeSerializer<?> getSerializer() { return Serializer.INSTANCE; }
    @Override public RecipeType<?> getType() { return Type.INSTANCE; }
    @Override public NonNullList<Ingredient> getIngredients() { return ingredient; }

    public ItemStack getResultItem() { return result.copy(); }

    public static void gatherAllRecipes(RecipeManager recipeManager, IRecipeRegistration registration) {
        List<VacuumChamberRecipe> vacuumChamberRecipes = recipeManager.getAllRecipesFor(Type.INSTANCE);
        registration.addRecipes(VacuumingCategory.VACUUM_CHAMBER_RECIPE_TYPE, vacuumChamberRecipes);
    }

    public static class Type implements RecipeType<VacuumChamberRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "vacuuming";
    }

    public static class Serializer implements RecipeSerializer<VacuumChamberRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SingularityReactor.MOD_ID, "vacuuming");

        @Override
        public VacuumChamberRecipe fromJson(ResourceLocation pRecipeId, JsonObject serializedRecipe) {
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
            JsonArray ingredient = GsonHelper.getAsJsonArray(serializedRecipe, "ingredient");
            int cookingTime = GsonHelper.getAsInt(serializedRecipe, "time", FALLBACK_COOKING_TIME);
            int energyUsage = GsonHelper.getAsInt(serializedRecipe, "energy", FALLBACK_ENERGY_USAGE);
            NonNullList<Ingredient> input = NonNullList.withSize(1, Ingredient.EMPTY);
            input.set(1, Ingredient.fromJson(ingredient.get(1)));
            return new VacuumChamberRecipe(pRecipeId, result, input, cookingTime, energyUsage);
        }

        @Override
        public @Nullable VacuumChamberRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> ingredient = NonNullList.withSize(1, Ingredient.EMPTY);
            ingredient.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
            ItemStack result = buffer.readItem();
            int cookingTime = buffer.readInt();
            int energyUsage = buffer.readInt();
            return new VacuumChamberRecipe(id, result, ingredient, cookingTime, energyUsage);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, VacuumChamberRecipe recipe) {
            Ingredient ingredient = Ingredient.merge(recipe.getIngredients());
            ingredient.toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeVarInt(recipe.cookingTime);
            buffer.writeVarInt(recipe.energyUsage);
        }
    }
}
