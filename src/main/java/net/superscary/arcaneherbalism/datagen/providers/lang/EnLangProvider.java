package net.superscary.arcaneherbalism.datagen.providers.lang;

import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.registries.ModBlocks;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import net.superscary.arcaneherbalism.datagen.IDataProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnLangProvider extends LanguageProvider implements IDataProvider {

    public EnLangProvider (DataGenerator generator) {
        super(generator.getPackOutput(), Mod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations () {
        add("itemGroup." + Mod.MOD_ID, Mod.NAME);
        add(ModBlocks.DEADLY_NIGHTSHADE.block(), "Deadly Nightshade");
        add(ModBlocks.POTTED_DEADLY_NIGHTSHADE.block(), "Potted Deadly Nightshade");
        add(ModBlocks.PYROBLOSSOM.block(), "Pyroblossom");
        add(ModBlocks.POTTED_PYROBLOSSOM.block(), "Potted Pyroblossom");
        add(ModBlocks.WEED.block(), "Weed");
        add(ModBlocks.POTTED_WEED.block(), "Potted Weed");

        add(ModItems.EYEBALL.asItem(), "Eyeball");
        add(ModItems.LEAF.asItem(), "Leaf");
    }

}