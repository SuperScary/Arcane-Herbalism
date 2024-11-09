package net.superscary.arcaneherbalism.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import net.superscary.arcaneherbalism.api.util.BlockModelType;
import net.superscary.arcaneherbalism.api.util.ModelType;
import net.superscary.arcaneherbalism.core.util.PlantHelper;
import org.jetbrains.annotations.NotNull;

public class FlowerBlock extends net.minecraft.world.level.block.FlowerBlock implements BlockModelType {

    public static final Properties PROPERTIES = Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS)/*.offsetType(OffsetType.XZ)*/.pushReaction(PushReaction.DESTROY);
    public static final Properties PROPERTIES_WITH_OFFSETS = Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY);

    private final Holder<MobEffect> effect;
    private final float seconds;
    private final boolean damagesPlayer;
    private final PlantHelper plantHelper;

    public FlowerBlock (Holder<MobEffect> effect, float seconds, Properties properties, boolean damagesPlayer) {
        super(effect, seconds, properties);
        this.effect = effect;
        this.seconds = seconds;
        this.damagesPlayer = damagesPlayer;
        this.plantHelper = new PlantHelper(this);
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

    public PlantHelper getPlantHelper () {
        return plantHelper;
    }

    public ItemStack droppable () {
        return ItemStack.EMPTY;
    }

    @Override
    protected void entityInside (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity && damagesPlayer) {
            livingEntity.addEffect(new MobEffectInstance(getEffect(), (int) getSeconds(), 1, true, true, false));
            livingEntity.hurt(level.damageSources().generic(), 1.0f);
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (stack.is(Tags.Items.TOOLS_SHEAR)) {
            var droppable = droppable();
            if (!stack.isEmpty()) {
                getPlantHelper().shearPlant(player, level, pos, droppable);
                level.destroyBlock(pos, true);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public ModelType getModelType () {
        return ModelType.FLOWER_BLOCK;
    }
}
