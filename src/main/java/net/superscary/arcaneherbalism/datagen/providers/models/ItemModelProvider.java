package net.superscary.arcaneherbalism.datagen.providers.models;

import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;
import net.superscary.arcaneherbalism.core.definitions.ItemDefinition;
import net.superscary.arcaneherbalism.core.registries.ModBlocks;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import net.superscary.arcaneherbalism.datagen.IDataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider implements IDataProvider {

    public ItemModelProvider (PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Mod.MOD_ID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : Mod.getResource(id);
    }

    @Override
    protected void registerModels () {
        // Register item models here
        flower(ModBlocks.DEADLY_NIGHTSHADE);
        flower(ModBlocks.WEED);
        flower(ModBlocks.PYROBLOSSOM);
        basicItem(ModItems.EYEBALL.asItem());
        basicItem(ModItems.LEAF.asItem());
    }

    private ItemModelBuilder blockOff (BlockDefinition<?> block) {
        return withExistingParent(block.id().getPath(), Mod.getResource("block/" + block.id().getPath() + "/" + block.id().getPath() + "_off"));
    }

    private ItemModelBuilder flower (BlockDefinition<?> block) {
        return withExistingParent(block.id().getPath(), mcLoc("item/generated")).texture("layer0", Mod.getResource("block/" + block.id().getPath()));
    }

    private ItemModelBuilder flatSingleLayer (ItemDefinition<?> item, String texture) {
        String id = item.id().getPath();
        return singleTexture(id, mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder flatSingleLayer (ResourceLocation id, String texture) {
        return singleTexture(id.getPath(), mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder builtInItemModel (String name) {
        var model = getBuilder("item/" + name);
        return model;
    }

}
