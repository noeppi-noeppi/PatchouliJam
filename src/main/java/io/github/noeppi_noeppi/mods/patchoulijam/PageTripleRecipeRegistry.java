package io.github.noeppi_noeppi.mods.patchoulijam;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.BookEntry;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class PageTripleRecipeRegistry<T extends IRecipe<?>> extends PageTripleRecipe<T> {

    private final IRecipeType<T> recipeType;

    public PageTripleRecipeRegistry(IRecipeType<T> recipeType) {
        this.recipeType = recipeType;
    }

    @Nullable
    private T getRecipe(ResourceLocation id) {
        //noinspection unchecked
        return (T) Objects.requireNonNull(Minecraft.getInstance().world).getRecipeManager().getRecipes((IRecipeType<IRecipe<IInventory>>) this.recipeType).get(id);
    }

    protected T loadRecipe(BookEntry entry, ResourceLocation id) {
        if (id == null) {
            return null;
        }

        T recipe = this.getRecipe(id);
        if (recipe == null) {
            recipe = this.getRecipe(new ResourceLocation("crafttweaker", id.getPath()));
        }

        if (recipe != null) {
            entry.addRelevantStack(recipe.getRecipeOutput(), this.pageNum);
        }

        return recipe;
    }
}
