package net.superscary.arcaneherbalism.datagen.providers.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.superscary.arcaneherbalism.core.registries.ModItems;

import java.util.concurrent.CompletableFuture;

public class CompostableProvider extends DataMapProvider {

    public CompostableProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather () {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.LEAF.id(), new Compostable(0.35f, true), false)
                .add(ModItems.DRIED_LEAF.id(), new Compostable(0.6f, true), false);
    }
}
