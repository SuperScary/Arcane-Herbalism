package net.superscary.arcaneherbalism.block.base;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;

public class PottedBlock extends FlowerPotBlock {

    public BlockDefinition<FlowerBlock> flower;

    public PottedBlock (BlockDefinition<FlowerBlock> block, Properties properties) {
        super(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), block.getDeferredBlock(), properties);
        this.flower = block;
    }

    public PottedBlock (BlockDefinition<FlowerBlock> block) {
        this(block, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM));
    }

    public BlockDefinition<FlowerBlock> getFlower() {
        return flower;
    }

}
