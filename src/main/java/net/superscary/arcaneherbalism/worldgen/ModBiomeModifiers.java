package net.superscary.arcaneherbalism.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.superscary.arcaneherbalism.core.Mod;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> WEED = registerKey("weed");
    public static final ResourceKey<BiomeModifier> DEADLY_NIGHTSHADE = registerKey("deadly_nightshade");
    public static final ResourceKey<BiomeModifier> PYROBLOSSOM = registerKey("pyroblossom");

    public static void bootstrap (BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(WEED, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WEED)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(DEADLY_NIGHTSHADE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DEADLY_NIGHTSHADE)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(PYROBLOSSOM, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_NETHER), HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PYROBLOSSOM)), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        name = name + "_modifier";
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Mod.getResource(name));
    }

}
