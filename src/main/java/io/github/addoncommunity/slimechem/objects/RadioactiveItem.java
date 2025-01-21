package io.github.addoncommunity.slimechem.objects;

import io.github.addoncommunity.slimechem.implementation.attributes.Ingredient;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import lombok.Getter;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.inventory.ItemStack;

@Getter
public class RadioactiveItem extends SlimefunItem implements Radioactive {

    private final Radioactivity radioactivity;
    private final Ingredient ingredient;

    public RadioactiveItem(ItemGroup category, Ingredient ingredient, RecipeType recipeType, ItemStack[] recipe, Radioactivity radioactivity) {
        super(category, ingredient.getItem(), recipeType, recipe);

        this.radioactivity = radioactivity;
        this.ingredient = ingredient;
    }
}
