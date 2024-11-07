package net.superscary.arcaneherbalism.core.hooks;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.superscary.arcaneherbalism.api.ItemEntityProvider;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import net.superscary.arcaneherbalism.item.Eyeball;

public class EntityHooks {

    public static void registerEntityHooks () {
        NeoForge.EVENT_BUS.addListener(EntityHooks::eyeDropper);
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

}
