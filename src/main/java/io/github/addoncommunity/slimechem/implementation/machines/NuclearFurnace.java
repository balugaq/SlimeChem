package io.github.addoncommunity.slimechem.implementation.machines;

import io.github.addoncommunity.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.addoncommunity.slimechem.implementation.attributes.Atom;
import io.github.addoncommunity.slimechem.lists.Categories;
import io.github.addoncommunity.slimechem.lists.Items;
import io.github.addoncommunity.slimechem.setup.Registry;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuclearFurnace extends AbstractMachineBlock implements RecipeDisplayItem {

    private static final int[] BACKGROUND = {0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 16, 17, 18, 19, 20, 22, 23, 24, 25, 26};
    private static final int FUEL = 21;
    private static final int INPUT = 3;
    private static final int OUTPUT = 15;
    private static final int STATUS = 12;

    private final Map<Material, ItemStack> recipes = new HashMap<>();
    private final Map<String, Integer> fuels = new HashMap<>();
    private final List<ItemStack> displayRecipes = new ArrayList<>();

    private final Map<Block, Integer> map = new HashMap<>();

    public NuclearFurnace() {
        super(Categories.MACHINES, Items.NUCLEAR_FURNACE, RecipeType.NULL, new ItemStack[] {
                
        });

        Slimefun.getMinecraftRecipeService().subscribe(snapshot -> {
            for (FurnaceRecipe furnaceRecipe : snapshot.getRecipes(FurnaceRecipe.class)) {
                RecipeChoice choice = furnaceRecipe.getInputChoice();

                if (choice instanceof RecipeChoice.MaterialChoice) {
                    for (Material input : ((RecipeChoice.MaterialChoice) choice).getChoices()) {
                        this.recipes.put(input, furnaceRecipe.getResult());
                    }
                }
            }
        });

        this.fuels.put(SlimefunItems.TINY_URANIUM.getItemId(), 3);
        this.fuels.put(SlimefunItems.SMALL_URANIUM.getItemId(), 36);
        this.fuels.put(SlimefunItems.URANIUM.getItemId(), 160);
        this.fuels.put(SlimefunItems.NEPTUNIUM.getItemId(), 320);
        this.fuels.put(SlimefunItems.PLUTONIUM.getItemId(), 480);
        this.fuels.put(SlimefunItems.BOOSTED_URANIUM.getItemId(), 720);

        for (Atom atom : Registry.getRadioactiveItems()) {
            if (atom instanceof Isotope && ((Isotope) atom).isMainIsotope()) {
                continue;
            }
            this.fuels.put(atom.getItem().getItemId(), atom.getRadiationLevel() * 10);
        }

        //display recipes
        for (Map.Entry<String, Integer> entry : this.fuels.entrySet()) {
            SlimefunItem sfItem = SlimefunItem.getById(entry.getKey());
            if (sfItem != null) {
                ItemStack stack = sfItem.getItem().clone();
                ItemMeta meta = stack.getItemMeta();
                if (meta != null && meta.getLore() != null) {
                    List<String> lore = meta.getLore();
                    lore.add(" ");
                    lore.add(ChatColor.GOLD + "Smelts: " + entry.getValue() + " items");
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    this.displayRecipes.add(stack);
                }
            }
        }
    }

    @Override
    public void setup(@Nonnull BlockMenuPreset preset) {
        for (int i : BACKGROUND) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(STATUS, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        this.map.put(b, 0);
        menu.replaceExistingItem(STATUS, getFuelItem(0));
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu menu) {
        ItemStack input = menu.getItemInSlot(INPUT);

        if (input == null) return false;

        int fuel = this.map.getOrDefault(b, 0);

        if (fuel < 1) {
            fuel = addFuel(menu, menu.getItemInSlot(FUEL));
            if (fuel < 1) return false;
        }

        process(b, menu, input, fuel);
        return true;
    }

    @Override
    protected int getStatusSlot() {
        return STATUS;
    }

    /**
     * returns amount of fuel added
     */
    private int addFuel(@Nonnull BlockMenu menu, @Nullable ItemStack fuel) {
        if (fuel == null) return 0;
        
        String id = StackUtils.getId(fuel);

        if (id == null) return 0;

        int amount = this.fuels.getOrDefault(id, 0);

        if (amount < 1) return 0;

        menu.consumeItem(FUEL, 1);

        return amount;

    }

    private void process(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull ItemStack input, int fuel) {
        ItemStack output = this.recipes.get(input.getType());

        if (output != null && menu.fits(output, OUTPUT)) {
            menu.pushItem(output.clone(), OUTPUT);
            menu.consumeItem(INPUT, 1);
            fuel--;
        }
        
        this.map.put(b, fuel);
        menu.replaceExistingItem(STATUS, getFuelItem(fuel), false);
        
    }

    public int[] getOutputSlots() {
        return new int[] {OUTPUT};
    }

    @Override
    public int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        String id = StackUtils.getId(item);
        if (id == null) {
            if (this.recipes.containsKey(item.getType())) {
                return new int[] {INPUT};
            }
        } else if (this.fuels.containsKey(id)) {
            return new int[] {FUEL};
        }
        return new int[0];
    }

    public int[] getInputSlots() {
        return new int[] {INPUT, FUEL};
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return this.displayRecipes;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Fuels";
    }

    @Nonnull
    private static ItemStack getFuelItem(int fuel) {
        return new CustomItemStack(
                fuel < 1 ? Material.GRAY_STAINED_GLASS_PANE :
                        fuel < 36 ? Material.RED_STAINED_GLASS_PANE :
                                fuel < 161 ? Material.ORANGE_STAINED_GLASS_PANE :
                                        Material.YELLOW_STAINED_GLASS_PANE, "&6Fuel: " + fuel
        );
    }

}
