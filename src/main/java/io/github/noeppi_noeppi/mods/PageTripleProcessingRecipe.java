package io.github.noeppi_noeppi.mods;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class PageTripleProcessingRecipe<T extends IRecipe<?>> extends PageTripleRecipeRegistry<T> {

    public PageTripleProcessingRecipe(IRecipeType<T> recipeType) {
        super(recipeType);
    }

    protected void drawRecipe(MatrixStack matrixStack, T recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        this.mc.getTextureManager().bindTexture(this.book.craftingTexture);
        RenderSystem.enableBlend();
        AbstractGui.blit(matrixStack, recipeX, recipeY, 11.0F, 71.0F, 96, 24, 128, 256);
        this.parent.drawCenteredStringNoShadow(matrixStack, this.getTitle(second).func_241878_f(), 58, recipeY - 10, this.book.headerColor);
        this.parent.renderIngredient(matrixStack, recipeX + 4, recipeY + 4, mouseX, mouseY, recipe.getIngredients().get(0));
        this.parent.renderItemStack(matrixStack, recipeX + 40, recipeY + 4, mouseX, mouseY, this.getIcon(recipe));
        this.parent.renderItemStack(matrixStack, recipeX + 76, recipeY + 4, mouseX, mouseY, recipe.getRecipeOutput());
    }

    protected ItemStack getRecipeOutput(T recipe) {
        return recipe == null ? ItemStack.EMPTY : recipe.getRecipeOutput();
    }

    protected int getRecipeHeight() {
        return 45;
    }

    protected ItemStack getIcon(T recipe) {
        return recipe.getIcon();
    }
}
