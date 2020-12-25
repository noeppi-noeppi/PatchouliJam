package io.github.noeppi_noeppi.mods.patchoulijam;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.page.abstr.PageDoubleRecipe;

public abstract class PageTripleRecipe<T> extends PageDoubleRecipe<T> {

    @SerializedName("recipe3")
    protected ResourceLocation recipe3Id;

    protected transient T recipe3;
    protected transient ITextComponent title3;
    protected transient boolean title3active = false;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
        this.recipe3 = this.loadRecipe(entry, this.recipe3Id);
        if (this.recipe2 == null && this.recipe1 == null) {
            this.recipe1 = this.recipe3;
        } else if (this.recipe2 == null) {
            this.recipe2 = this.recipe3;
        }

        this.title3 = this.getRecipeOutput(this.recipe3).getDisplayName();
        if (this.title2.equals(this.title3) || this.title2.getUnformattedComponentText().equals("-")) {
            this.title2 = StringTextComponent.EMPTY;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.recipe3 != null) {
            int recipeX = this.getX();
            int recipeY = this.getY();
            this.title3active = true;
            this.drawRecipe(matrixStack, this.recipe3, recipeX, recipeY + (2 * this.getRecipeHeight()) - (this.title2.getString().isEmpty() ? 10 : 0)  - (this.title3.getString().isEmpty() ? 10 : 0), mouseX, mouseY, true);
            this.title3active = false;
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected ITextComponent getTitle(boolean second) {
        return second ? (this.title3active ? this.title3 : this.title2) : this.title1;
    }
}
