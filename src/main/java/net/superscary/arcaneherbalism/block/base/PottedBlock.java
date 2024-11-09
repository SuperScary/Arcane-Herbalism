package net.superscary.arcaneherbalism.block.base;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.PushReaction;
import net.superscary.arcaneherbalism.api.util.BlockModelType;
import net.superscary.arcaneherbalism.api.util.ModelType;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;

public class PottedBlock extends FlowerPotBlock implements BlockModelType {

    public static final Properties PROPERTIES = Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);

    public BlockDefinition<FlowerBlock> flower;

    public PottedBlock (BlockDefinition<FlowerBlock> block, Properties properties) {
        super(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), block.getDeferredBlock(), properties);
        this.flower = block;
    }

    public PottedBlock (BlockDefinition<FlowerBlock> block) {
        this(block, PROPERTIES);
    }

    public BlockDefinition<FlowerBlock> getFlower() {
        return flower;
    }

    @Override
    public ModelType getModelType () {
        return ModelType.POTTED_BLOCK;
    }
}
