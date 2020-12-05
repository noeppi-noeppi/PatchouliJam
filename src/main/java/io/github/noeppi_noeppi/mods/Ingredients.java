package io.github.noeppi_noeppi.mods;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class Ingredients {

    public static Ingredient fromSubItems(Item... items) {
        List<ItemStack> stacks = new ArrayList<>();
        NonNullList<ItemStack> nnl = NonNullList.create();
        for (Item item : items) {
            nnl.clear();
            if (item.getGroup() != null) {
                item.fillItemGroup(item.getGroup(), nnl);
            }
            if (nnl.isEmpty()) {
                nnl.add(new ItemStack(item));
            }
            stacks.addAll(nnl);
        }
        return Ingredient.fromStacks(stacks.toArray(new ItemStack[]{}));
    }

}
