package io.github.noeppi_noeppi.mods;

import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import io.github.noeppi_noeppi.mods.nextchristmas.mill.MillRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class PageGrainMill extends PageTripleProcessingRecipe<MillRecipe> {

    public static final ResourceLocation ICON = new ResourceLocation("next_christmas", "grain_mill");

    public PageGrainMill() {
        super(ModRecipes.MILL);
    }

    @Override
    protected ItemStack getIcon(MillRecipe recipe) {
        Item item = ForgeRegistries.ITEMS.getValue(ICON);
        if (item == null) {
            return super.getIcon(recipe);
        } else {
            return new ItemStack(item);
        }
    }
}
