package net.superscary.arcaneherbalism.item.base;

import net.minecraft.world.food.FoodProperties;

public class BaseFoodItem extends BaseItem {

    public BaseFoodItem (Properties properties, FoodProperties foodProperties) {
        super(properties.food(foodProperties));
    }

}
