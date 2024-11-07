package net.superscary.arcaneherbalism.api;

import com.google.common.base.Preconditions;
import net.minecraft.world.item.ItemStack;

public interface RandomRoll {

    ItemStack roll ();

    static int roll (int min, int max) {
        Preconditions.checkArgument(min < max, "The minimum value must be less than the maximum value.");
        return (int) (Math.random() * (max - min + 1) + min);
    }

    static float roll (float min, float max) {
        Preconditions.checkArgument(min < max, "The minimum value must be less than the maximum value.");
        Preconditions.checkArgument(min >= 0, "The minimum value must be greater than or equal to 0.");
        Preconditions.checkArgument(max <= 1, "The maximum value must be less than or equal to 1.");
        return (float) (Math.random() * (max - min) + min);
    }

    static boolean roll (double chance) {
        return Math.random() < chance;
    }

}
