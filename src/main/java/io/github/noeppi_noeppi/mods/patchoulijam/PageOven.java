package io.github.noeppi_noeppi.mods.patchoulijam;

import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.OvenRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class PageOven extends PageTripleProcessingRecipe<OvenRecipe> {

    public static final ResourceLocation ICON = new ResourceLocation("next_christmas", "oven");

    public PageOven() {
        super(ModRecipes.OVEN);
    }

    @Override
    protected ItemStack getIcon(OvenRecipe recipe) {
        Item item = ForgeRegistries.ITEMS.getValue(ICON);
        if (item == null) {
            return super.getIcon(recipe);
        } else {
            return new ItemStack(item);
        }
    }
}
