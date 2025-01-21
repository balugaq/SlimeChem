package io.github.addoncommunity.slimechem.lists;


import io.github.addoncommunity.slimechem.SlimeChem;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Categories {
    
    private static final SlimeChem instance = SlimeChem.getInstance();
    
    public static final ItemGroup MACHINES = new ItemGroup(new NamespacedKey(instance, "machines"), new CustomItemStack(Material.DIAMOND_BLOCK, "Chemical Machines"), 3);
    public static final ItemGroup MOLECULES = new ItemGroup(new NamespacedKey(instance, "molecules"), new CustomItemStack(Material.DIAMOND, "Molecules"), 3);
    public static final ItemGroup ELEMENTS = new ItemGroup(new NamespacedKey(instance, "elements"), new CustomItemStack(Material.DIAMOND, "Elements"), 3);
    public static final ItemGroup ISOTOPES = new ItemGroup(new NamespacedKey(instance, "isotopes"), new CustomItemStack(Material.DIAMOND, "Isotopes"), 3);
    public static final ItemGroup SUBATOMIC = new ItemGroup(new NamespacedKey(instance, "subatomic"), new CustomItemStack(Material.DIAMOND, "Subatomic Particles"), 3);

}
