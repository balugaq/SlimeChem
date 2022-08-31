package io.github.addoncommunity.slimechem.implementation.attributes;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

/**
 * This interface marks classes that have a {@link SlimefunItemStack} representation
 *
 * @author Seggan
 */
public interface Itemable {

    @Nonnull
    SlimefunItemStack getItem();
}
