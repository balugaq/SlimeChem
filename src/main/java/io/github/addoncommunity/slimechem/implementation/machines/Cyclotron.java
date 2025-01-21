package io.github.addoncommunity.slimechem.implementation.machines;

import io.github.addoncommunity.slimechem.implementation.atomic.Element;
import io.github.addoncommunity.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.addoncommunity.slimechem.lists.Categories;
import io.github.addoncommunity.slimechem.lists.Items;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cyclotron extends AbstractMachineBlock {

    private static final int[] BORDER_IN = {9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    private static final int[] BORDER_OUT = {14, 15, 16, 17, 23, 26, 32, 33, 34, 35};

    private static final int[] INPUT_SLOTS = new int[]{19, 20};
    private static final int[] OUTPUT_SLOTS = new int[]{24, 25};

    private static final Map<Location, SlimefunItemStack> results = new HashMap<>();
    private static final Map<Location, Integer> progress = new HashMap<>();
    private static final Set<Location> processing = new HashSet<>();

    public Cyclotron() {
        super(Categories.MACHINES, Items.CYCLOTRON, RecipeType.NULL, new ItemStack[] {
                
        });

        energyCapacity(4080);
        energyPerTick(2040);

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                Location l = blockBreakEvent.getBlock().getLocation();
                results.remove(l);
                progress.remove(l);
                processing.remove(l);
            }
        });
    }

    @Override
    public void setup(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44});
        for (int i : BORDER_IN) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BORDER_OUT) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(getStatusSlot(), new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
    }

    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected boolean process(@Nonnull Block block, @Nonnull BlockMenu menu) {
        Location l = block.getLocation();
        if (processing.contains(l)) {
            int timeleft = progress.get(l);

            if (timeleft > 0) {
                ChestMenuUtils.updateProgressbar(menu, 22, timeleft--, 8, new ItemStack(Material.SLIME_BALL));

                progress.put(l, timeleft);
            } else {
                menu.replaceExistingItem(22, new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "));

                menu.pushItem(results.get(l).clone(), OUTPUT_SLOTS);

                progress.remove(l);
                processing.remove(l);
            }
        } else {
            Isotope first = Isotope.getByItem(menu.getItemInSlot(INPUT_SLOTS[0]));
            Isotope second = Isotope.getByItem(menu.getItemInSlot(INPUT_SLOTS[1]));
            // TODO: add electron/neutron capture
            if (first == null) return false;
            if (second == null) return false;

            Element element = Element.getByNumber(first.getNumber() + second.getNumber());
            if (element == null) return false;

            Isotope result = Isotope.getIsotope((int) Math.round(first.getMass() + second.getMass()), element);

            if (result != null) {
                menu.consumeItem(INPUT_SLOTS[0]);
                menu.consumeItem(INPUT_SLOTS[1]);

                processing.add(l);
                progress.put(l, 8);
                results.put(l, result.getItem());
            }
        }
        
        return true;
    }

    @Override
    protected int getStatusSlot() {
        return 22;
    }
}