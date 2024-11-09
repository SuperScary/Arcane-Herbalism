package net.superscary.arcaneherbalism.api.util;

import com.google.common.base.Preconditions;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

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

    static boolean chance (float chance) {
        Preconditions.checkArgument(chance >= 0 && chance <= 100, "The chance must be between 0 and 100.");
        if (chance == 0) return false;
        if (chance == 100) return true;
        if (chance < 1) chance *= 100;
        var random = new Random();
        return random.nextFloat() * 100 <= chance;
    }

}
