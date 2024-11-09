package net.superscary.arcaneherbalism.core.hooks;

import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.superscary.arcaneherbalism.api.item.ItemEntityProvider;
import net.superscary.arcaneherbalism.api.util.RandomRoll;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import net.superscary.arcaneherbalism.item.Eyeball;

public class EntityHooks {

    public static void registerEntityHooks () {
        NeoForge.EVENT_BUS.addListener(EntityHooks::eyeDropper);
        NeoForge.EVENT_BUS.addListener(EntityHooks::batWingsDropper);
        NeoForge.EVENT_BUS.addListener(EntityHooks::dragonTongDropper);
    }

    /**
     * Drops an eyeball from the entity if it can drop one
     */
    public static void eyeDropper (LivingDropsEvent event) {
        var entity = event.getEntity();
        var level = entity.level();
        if (Eyeball.canDropFrom(entity)) {
            var stack = ModItems.EYEBALL.asItem().roll();
            event.getDrops().add(ItemEntityProvider.asItemEntity(entity, level, stack));
        }
    }

    /**
     * Drops bat wings from the entity if it is a bat
     */
    public static void batWingsDropper (LivingDropsEvent event) {
        var entity = event.getEntity();
        var level = entity.level();
        if (entity instanceof Bat bat && RandomRoll.chance(0.3f)) {
            var stack = new ItemStack(ModItems.BAT_WINGS.asItem());
            event.getDrops().add(ItemEntityProvider.asItemEntity(bat, level, stack));
        }
    }

    /**
     * Drops a dragon tongue when dragon is killed
     */
    public static void dragonTongDropper (LivingDropsEvent event) {
        var entity = event.getEntity();
        var level = entity.level();
        if (entity instanceof EnderDragon dragon && RandomRoll.chance(1.f)) {
            var stack = new ItemStack(ModItems.DRAGON_TONGUE.asItem());
            event.getDrops().add(ItemEntityProvider.asItemEntity(dragon, level, stack));
        }
    }

}
