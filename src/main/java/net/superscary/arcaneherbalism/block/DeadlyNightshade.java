package net.superscary.arcaneherbalism.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.arcaneherbalism.block.base.FlowerBlock;
import org.jetbrains.annotations.NotNull;

public class DeadlyNightshade extends FlowerBlock {

    public DeadlyNightshade (Holder<MobEffect> effect, float seconds) {
        super(effect, seconds, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM), true);
    }

    @Override
    public int getLightEmission (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 7;
    }

    @Override
    public boolean hasDynamicLightEmission (@NotNull BlockState state) {
        return true;
    }

    @Override
    public void animateTick (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);
        level.addParticle(ParticleTypes.EFFECT, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
    }
}
