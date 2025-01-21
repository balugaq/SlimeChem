package io.github.addoncommunity.slimechem;

import io.github.addoncommunity.slimechem.implementation.atomic.isotopes.IsotopeLoader;
import io.github.addoncommunity.slimechem.setup.Registry;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;


public class SlimeChem extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static SlimeChem instance;
    
    @Override
    public void onEnable() {
        instance = this;

        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 9490);

        IsotopeLoader isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();

        Registry.setup(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Slimefun-Addon-Community/SlimeChem/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
