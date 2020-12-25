package io.github.noeppi_noeppi.mods.patchoulijam;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.book.page.PageCrafting;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

// Used to draw special recipes that can not be handled by patchouli itself.
public class PageSpecialCrafting extends PageCrafting {

    private final Map<ResourceLocation, ICraftingRecipe> wrappers;

    public PageSpecialCrafting() {
        this.wrappers = new HashMap<>();
        if (ModList.get().isLoaded("snowyweaponry")) {
            Item snowCone = ForgeRegistries.ITEMS.getValue(new ResourceLocation("snowyweaponry", "snow_cone"));
            Item potionCone = ForgeRegistries.ITEMS.getValue(new ResourceLocation("snowyweaponry", "potion_snow_cone"));
            this.register(new RecipeWrapper(
                    new ResourceLocation("snowyweaponry", "potion_cone_recipe"),
                    Ingredients.fromSubItems(potionCone),
                    NonNullList.from(Ingredient.EMPTY,
                            Ingredient.fromItems(snowCone),
                            Ingredient.fromItems(snowCone),
                            Ingredient.fromItems(snowCone),
                            Ingredient.fromItems(snowCone),
                            Ingredients.fromSubItems(Items.POTION, Items.SPLASH_POTION)
                    )
            ));
        }
    }

    private void register(RecipeWrapper wrapper) {
        this.wrappers.put(wrapper.id, wrapper);
    }

    @Override
    protected void drawRecipe(MatrixStack matrixStack, ICraftingRecipe recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        super.drawRecipe(matrixStack, this.wrappers.getOrDefault(recipe.getId(), recipe), recipeX, recipeY, mouseX, mouseY, second);
    }

    private static class RecipeWrapper implements ICraftingRecipe {

        private final ResourceLocation id;
        private final ItemStack[] result;
        private final NonNullList<Ingredient> inputs;

        private RecipeWrapper(ResourceLocation id, Ingredient result, NonNullList<Ingredient> inputs) {
            this.id = id;
            this.result = result.getMatchingStacks();
            this.inputs = inputs;
        }

        @Override
        public boolean matches(@Nonnull CraftingInventory inv, @Nonnull World world) {
            return false;
        }

        @Nonnull
        @Override
        public ItemStack getCraftingResult(@Nonnull CraftingInventory inv) {
            return this.getRecipeOutput();
        }

        @Override
        public boolean canFit(int width, int height) {
            return this.inputs.size() <= width * height;
        }

        @Nonnull
        @Override
        public ItemStack getRecipeOutput() {
            return this.result[((int) (ClientTicker.ticksInGame / 20)) % this.result.length];
        }

        @Nonnull
        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            throw new IllegalStateException("Can not serialise Recipe used internally by PatchouliJam.");
        }

        @Nonnull
        @Override
        public NonNullList<Ingredient> getIngredients() {
            return this.inputs;
        }
    }

    private static class ShapedWrapper extends RecipeWrapper implements IShapedRecipe<CraftingInventory> {

        private final int width;
        private final int height;

        private ShapedWrapper(ResourceLocation id, Ingredient result, int width, int height, NonNullList<Ingredient> inputs) {
            super(id, result, inputs);
            this.width = width;
            this.height = height;
        }

        @Override
        public int getRecipeWidth() {
            return this.width;
        }

        @Override
        public int getRecipeHeight() {
            return this.height;
        }

        @Override
        public boolean canFit(int width, int height) {
            return this.width >= width && this.height >= height && super.canFit(width, height);
        }
    }
}