package net.superscary.arcaneherbalism.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.arcaneherbalism.block.base.FlowerBlock;
import net.superscary.arcaneherbalism.core.Mod;
import org.jetbrains.annotations.NotNull;

public class Pyroblossom extends FlowerBlock {

    private boolean canCook;

    public Pyroblossom (Holder<MobEffect> effect, float seconds) {
        super(effect, seconds, PROPERTIES, true);
        this.canCook = true;
    }

    public boolean canCook () {
        return canCook;
    }

    public void setCanCook (boolean canCook) {
        this.canCook = canCook;
    }

    public void countDown () {
        if (canCook) {
            setCanCook(false);
            new Thread(() -> {
                try {
                    Thread.sleep((long) (3 * 1000));
                    setCanCook(true);
                } catch (InterruptedException e) {
                    Mod.getLogger().error(e.getLocalizedMessage());
                }
            }).start();
        }
    }

    @Override
    public ItemStack droppable () {
        return new ItemStack(Items.BLAZE_POWDER, 1);
    }

    @Override
    public boolean isBurning (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return false;
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
    protected boolean mayPlaceOn (BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.MYCELIUM) || state.is(Blocks.SOUL_SOIL) || state.is(Blocks.NETHERRACK) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected void onRemove (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
    }

    @Override
    protected void entityInside (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setRemainingFireTicks(100);
        }

        if (entity instanceof ItemEntity itemEntity && canCook()) {
            var recipe = RecipeType.SMELTING;
            var recipeProvider = level.getRecipeManager().getRecipeFor(recipe, new SingleRecipeInput(itemEntity.getItem()), level);
            if (recipeProvider.isPresent()) {
                var result = recipeProvider.get().value().getResultItem(level.registryAccess());
                var time = recipeProvider.get().value().getCookingTime() / 2 / 20;
                var stack = itemEntity.getItem();
                itemEntity.discard();
                getPlantHelper().collectAndEject(stack, result, level, pos, time);
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, .8F, false);
            }
        }

        countDown();

    }

    @Override
    public void animateTick (@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (random.nextDouble() < 0.3)
            level.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, pos.getX() + .5, pos.getY() + .95, pos.getZ() + .5, 0.0D, 0.0D, 0.0D);
    }

}
