package net.superscary.arcaneherbalism.datagen.providers.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.registries.ModBlocks;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import net.superscary.arcaneherbalism.datagen.IDataProvider;

public class EnLangProvider extends LanguageProvider implements IDataProvider {

    public EnLangProvider (DataGenerator generator) {
        super(generator.getPackOutput(), Mod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations () {
        add("itemGroup." + Mod.MOD_ID, Mod.NAME);
        blocks();
        items();
    }

    protected void blocks () {
        for (var blockDef : ModBlocks.getBlocks()) {
            add(blockDef.block(), blockDef.getEnglishName());
        }
    }

    protected void items () {
        for (var itemDef : ModItems.getItems()) {
            add(itemDef.asItem(), itemDef.getEnglishName());
        }
    }

}