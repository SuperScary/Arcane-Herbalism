package net.superscary.arcaneherbalism.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FlowerBlock extends net.minecraft.world.level.block.FlowerBlock {

    private final Holder<MobEffect> effect;
    private final float seconds;
    private final boolean damagesPlayer;

    public FlowerBlock (Holder<MobEffect> effect, float seconds, Properties properties, boolean damagesPlayer) {
        super(effect, seconds, properties);
        this.effect = effect;
        this.seconds = seconds;
        this.damagesPlayer = damagesPlayer;
    }

    public FlowerBlock (Holder<MobEffect> effect, float seconds, Properties properties) {
        this(effect, seconds, properties, true);
    }

    public Holder<MobEffect> getEffect () {
        return effect;
    }

    public float getSeconds () {
        return seconds;
    }

    @Override
    protected void entityInside (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity && damagesPlayer) {
            livingEntity.addEffect(new MobEffectInstance(getEffect(), (int) getSeconds(), 1, true, true, false));
            livingEntity.hurt(level.damageSources().generic(), 1.0f);
        }
        super.entityInside(state, level, pos, entity);
    }
}
