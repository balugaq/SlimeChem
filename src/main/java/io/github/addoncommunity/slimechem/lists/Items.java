package io.github.addoncommunity.slimechem.lists;

import io.github.addoncommunity.slimechem.implementation.machines.ChemicalCombiner;
import io.github.addoncommunity.slimechem.implementation.machines.ChemicalDissolver;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import lombok.experimental.UtilityClass;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;

@UtilityClass
public final class Items {

    public static final SlimefunItemStack CHEMICAL_DISSOLVER = new SlimefunItemStack(
            "CHEMICAL_DISSOLVER",
            Material.DIAMOND_BLOCK,
            "&bChemical Dissolver",
            "&7Dissolves materials into their chemical parts and molecules into elements",
            "",
            LoreBuilder.power(ChemicalDissolver.ENERGY, "per item")
    );
    public static final SlimefunItemStack CHEMICAL_COMBINER = new SlimefunItemStack(
            "CHEMICAL_COMBINER",
            Material.EMERALD_BLOCK,
            "&bChemical Combiner",
            "&7Combines elements and molecules into molecules and materials",
            "",
            LoreBuilder.power(ChemicalCombiner.ENERGY, "per item")
    );

    public static final SlimefunItemStack RTG_1 = new SlimefunItemStack(
        "RTG_1",
        HeadTexture.NUCLEAR_REACTOR,
        "&4Radioisotope Thermoelectric Generator I",
        "",
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.GENERATOR),
        LoreBuilder.powerPerSecond(160),
        LoreBuilder.powerCharged(0, 32)
    );
    public static final SlimefunItemStack RTG_2 = new SlimefunItemStack(
        "RTG_2",
        HeadTexture.NUCLEAR_REACTOR,
        "&4Radioisotope Thermoelectric Generator II",
        "",
        "&7Also captures the radiation",
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
        LoreBuilder.powerPerSecond(320),
        LoreBuilder.powerCharged(0, 64)
    );

    public static final SlimefunItemStack CYCLOTRON = new SlimefunItemStack(
        "CYCLOTRON",
        Material.NETHERITE_BLOCK,
        "&4Cyclotron",
        "",
        "&7Fuses 2 isotopes/elements together",
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
        LoreBuilder.powerPerSecond(2040),
        LoreBuilder.powerCharged(0, 4080)
    );
    
    public static final SlimefunItemStack NUCLEAR_FURNACE = new SlimefunItemStack(
            "NUCLEAR_FURNACE",
            Material.FURNACE,
            "&2Nuclear Furnace",
            "&7Smelts items using nuclear substances as fuel"
    );
    
}
