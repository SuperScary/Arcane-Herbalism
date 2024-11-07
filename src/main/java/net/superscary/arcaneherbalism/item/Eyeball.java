package net.superscary.arcaneherbalism.item;

import com.google.common.base.Preconditions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.superscary.arcaneherbalism.api.RandomRoll;
import net.superscary.arcaneherbalism.item.base.BaseFoodItem;

import java.util.ArrayList;
import java.util.List;

public class Eyeball extends BaseFoodItem implements RandomRoll {

    private static final FoodProperties EYEBALL_FOOD = new FoodProperties.Builder().nutrition(RandomRoll.roll(0, 1)).saturationModifier(0.1f).alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, RandomRoll.roll(60, 200), 0), 0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 0.38f).build();

    private final EyeballType eyeballType;

    public Eyeball (Properties properties) {
        this(properties, EyeballType.NORMAL);
    }

    public Eyeball (Properties properties, EyeballType eyeballType) {
        super(properties.rarity(eyeballType.getRarity()), EYEBALL_FOOD);
        this.eyeballType = eyeballType;
    }

    public EyeballType getEyeballType () {
        return eyeballType;
    }

    @Override
    public ItemStack roll () {
        return new ItemStack(this, RandomRoll.roll(0, 2));
    }

    public static List<DropEntity> dropsFrom () {
        return new ArrayList<>() {
            {
                add(DropEntity.ZOMBIE);
                add(DropEntity.ZOMBIE_VILLAGER);
                add(DropEntity.HORSE);
                add(DropEntity.PIG);
                add(DropEntity.MULE);
                add(DropEntity.PILLAGER);
                add(DropEntity.VILLAGER);
                add(DropEntity.PLAYER);
                add(DropEntity.WITCH);
            }
        };
    }

    public static boolean canDropFrom (Entity entity) {
        for (DropEntity dropEntity : dropsFrom()) {
            if (dropEntity.getEntityType().equals(entity.getType())) {
                return RandomRoll.roll(0, 2) <= dropEntity.getChance();
            }
        }
        return false;
    }

    public enum EyeballType {
        NORMAL(Rarity.COMMON),
        CREEPY(Rarity.UNCOMMON),
        BLOODSHOT(Rarity.RARE),
        GLOWING(Rarity.EPIC);

        private final Rarity rarity;

        EyeballType (Rarity rarity) {
            this.rarity = rarity;
        }

        public Rarity getRarity () {
            return rarity;
        }

    }

    public enum DropEntity {

        ZOMBIE(EntityType.ZOMBIE, 0.05f),
        ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, 0.05f),
        HORSE(EntityType.HORSE, 0.25f),
        PIG(EntityType.PIG, 0.25f),
        MULE(EntityType.MULE, 0.25f),
        PILLAGER(EntityType.PILLAGER, 0.25f),
        VILLAGER(EntityType.VILLAGER, 0.25f),
        PLAYER(EntityType.PLAYER, 0.25f),
        WITCH(EntityType.WITCH, 0.25f);

        private final EntityType<?> entityType;
        private final float chance;

        DropEntity (EntityType<?> entityType, float chance) {
            Preconditions.checkArgument(chance >= 0 && chance <= 1, "Chance must be between 0 and 1");
            this.entityType = entityType;
            this.chance = chance;
        }

        public EntityType<?> getEntityType () {
            return entityType;
        }

        public float getChance () {
            return chance;
        }

    }

}
