package net.superscary.arcaneherbalism.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.superscary.arcaneherbalism.core.Mod;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> WEED = registerKey("weed_placed");
    public static final ResourceKey<PlacedFeature> DEADLY_NIGHTSHADE = registerKey("deadly_nightshade_placed");
    public static final ResourceKey<PlacedFeature> PYROBLOSSOM = registerKey("pyroblossom_placed");

    public static void bootstrap (BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, WEED, configuredFeatures.getOrThrow(ModConfiguredFeatures.WEED), List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, DEADLY_NIGHTSHADE, configuredFeatures.getOrThrow(ModConfiguredFeatures.DEADLY_NIGHTSHADE), List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PYROBLOSSOM, configuredFeatures.getOrThrow(ModConfiguredFeatures.PYROBLOSSOM), List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Mod.getResource(name));
    }

    private static void register (BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
