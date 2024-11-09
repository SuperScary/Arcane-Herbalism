package net.superscary.arcaneherbalism.datagen.providers.models;

import net.minecraft.resources.ResourceLocation;
import net.superscary.arcaneherbalism.api.util.ModelType;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;
import net.superscary.arcaneherbalism.core.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelProvider extends BlockStateProvider {

    public BlockModelProvider (PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Mod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels () {
        flowerBlock(ModBlocks.DEADLY_NIGHTSHADE, ModBlocks.POTTED_DEADLY_NIGHTSHADE);
        flowerBlock(ModBlocks.WEED, ModBlocks.POTTED_WEED);
        flowerBlock(ModBlocks.PYROBLOSSOM, ModBlocks.POTTED_PYROBLOSSOM);
    }

    private void flowerBlock (BlockDefinition<?> block, BlockDefinition<?> potted) {
        simpleBlock(block.block(), models().cross(blockTexture(block.block()).getPath(), blockTexture(block.block())).renderType("cutout"));
        pottedBlock(potted, block, potted.getEnglishName());
    }

    private void pottedBlock (BlockDefinition<?> block, BlockDefinition<?> flower, String name) {
        simpleBlock(block.block(), models().singleTexture(name, ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(flower.block())).renderType("cutout"));
    }

    private void leavesBlock (BlockDefinition<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.block(),
                models().cubeAll(blockRegistryObject.id().getPath(), blockTexture(blockRegistryObject.block())).renderType("cutout"));
    }

    private void saplingBlock (BlockDefinition<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.block(),
                models().cross(blockRegistryObject.id().getPath(), blockTexture(blockRegistryObject.block())).renderType("cutout"));
    }

    private void blockItem (BlockDefinition<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.block(), new ModelFile.UncheckedModelFile(Mod.MOD_ID + ":block/" + blockRegistryObject.id().getPath() + appendix));
    }

    private void blockItem (BlockDefinition<?> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.block(), new ModelFile.UncheckedModelFile(Mod.MOD_ID + ":block/" + blockRegistryObject.id().getPath()));
    }

    private void blockWithItem (BlockDefinition<?> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.block(), cubeAll(blockRegistryObject.block()));
    }

}