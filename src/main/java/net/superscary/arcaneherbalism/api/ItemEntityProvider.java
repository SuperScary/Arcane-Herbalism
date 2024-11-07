package net.superscary.arcaneherbalism.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ItemEntityProvider {

    static ItemEntity asItemEntity (LivingEntity entity, Level level, ItemStack stack) {
        return new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
    }

}
