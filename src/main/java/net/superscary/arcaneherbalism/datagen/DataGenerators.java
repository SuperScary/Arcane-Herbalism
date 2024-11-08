package net.superscary.arcaneherbalism.datagen;

import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.datagen.providers.data.CompostableProvider;
import net.superscary.arcaneherbalism.datagen.providers.lang.EnLangProvider;
import net.superscary.arcaneherbalism.datagen.providers.models.BlockModelProvider;
import net.superscary.arcaneherbalism.datagen.providers.models.ItemModelProvider;
import net.superscary.arcaneherbalism.datagen.providers.recipes.BlastingRecipes;
import net.superscary.arcaneherbalism.datagen.providers.recipes.CraftingRecipes;
import net.superscary.arcaneherbalism.datagen.providers.recipes.SmeltingRecipes;
import net.superscary.arcaneherbalism.datagen.providers.tag.BlockTagGenerator;
import net.superscary.arcaneherbalism.datagen.providers.tag.ItemTagGenerator;
import net.superscary.arcaneherbalism.datagen.providers.loot.LootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.superscary.arcaneherbalism.datagen.providers.worldgen.WorldGenProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Mod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gather (GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var pack = generator.getVanillaPack(true);
        var existingFileHelper = event.getExistingFileHelper();
        var localization = new EnLangProvider(generator);

        // LOOT TABLE
        pack.addProvider(bindRegistries(LootTableProvider::new, registries));

        // TAGS
        var blockTagsProvider = pack.addProvider(pOutput -> new BlockTagGenerator(pOutput, registries, existingFileHelper));
        pack.addProvider(pOutput -> new ItemTagGenerator(pOutput, registries, blockTagsProvider.contentsGetter(), existingFileHelper));

        // MODELS & STATES
        pack.addProvider(pOutput -> new BlockModelProvider(pOutput, existingFileHelper));
        pack.addProvider(pOutput -> new ItemModelProvider(pOutput, existingFileHelper));

        // RECIPES
        pack.addProvider(bindRegistries(CraftingRecipes::new, registries));
        pack.addProvider(bindRegistries(SmeltingRecipes::new, registries));
        pack.addProvider(bindRegistries(BlastingRecipes::new, registries));

        // Compostable
        pack.addProvider(packOutput -> new CompostableProvider(packOutput, registries));

        // WORLD GEN
        pack.addProvider(output -> new WorldGenProvider(output, registries));

        // LOCALIZATION MUST RUN LAST
        pack.addProvider(output -> localization);

    }

    private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries (BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> factory, CompletableFuture<HolderLookup.Provider> factories) {
        return pOutput -> factory.apply(pOutput, factories);
    }

}