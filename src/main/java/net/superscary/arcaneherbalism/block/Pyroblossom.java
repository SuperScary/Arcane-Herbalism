package net.superscary.arcaneherbalism.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.arcaneherbalism.block.base.FlowerBlock;
import net.superscary.arcaneherbalism.core.Mod;
import org.jetbrains.annotations.NotNull;

public class Pyroblossom extends FlowerBlock {

    private boolean canCook;

    public Pyroblossom (Holder<MobEffect> effect, float seconds) {
        super(effect, seconds, PROPERTIES.randomTicks());
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

    private boolean hasFlammableNeighbors (LevelReader level, BlockPos pos) {
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            if (level.getBlockState(pos).isFlammable(level, pos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack droppable () {
        return new ItemStack(Items.BLAZE_POWDER, 1);
    }

    @Override
    public boolean isFlammable (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return false;
    }

    private boolean isFlammable (LevelReader level, BlockPos pos, Direction face) {
        if (pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight() && !level.hasChunkAt(pos)) {
            return false;
        } else {
            BlockState state = level.getBlockState(pos);
            return state.ignitedByLava() && state.isFlammable(level, pos, face);
        }
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
    protected void randomTick (@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            int i = random.nextInt(3);
            if (i > 0) {
                BlockPos blockpos = pos;

                for(int j = 0; j < i; ++j) {
                    blockpos = blockpos.offset(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
                    if (!level.isLoaded(blockpos)) {
                        return;
                    }

                    BlockState blockstate = level.getBlockState(blockpos);
                    if (blockstate.isAir()) {
                        if (this.hasFlammableNeighbors(level, blockpos)) {
                            level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(level, blockpos));
                            return;
                        }
                    }
                }
            } else {
                for(int k = 0; k < 3; ++k) {
                    BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                    if (!level.isLoaded(blockpos1)) {
                        return;
                    }

                    if (level.isEmptyBlock(blockpos1.above()) && this.isFlammable(level, blockpos1, Direction.UP)) {
                        level.setBlockAndUpdate(blockpos1.above(), BaseFireBlock.getState(level, blockpos1));
                    }
                }
            }
        }

        super.randomTick(state, level, pos, random);
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

        if (random.nextDouble() < .38d)
            level.addParticle(ParticleTypes.LAVA, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, 0.0d, 0.0d, 0.0d);
        level.addParticle(ParticleTypes.SMOKE, pos.getX() + .5f, pos.getY() + 1.f, pos.getZ() + .5f, 0.0d, 0.0d, 0.0d);
        level.addParticle(ParticleTypes.FLAME, pos.getX() + .5f, pos.getY() + 1.f, pos.getZ() + .5f, 0.0d, 0.0d, 0.0d);
    }

}
