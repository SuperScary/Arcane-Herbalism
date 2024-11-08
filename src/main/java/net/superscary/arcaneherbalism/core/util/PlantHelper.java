package net.superscary.arcaneherbalism.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.superscary.arcaneherbalism.block.base.FlowerBlock;
import net.superscary.arcaneherbalism.core.Mod;
import org.jetbrains.annotations.NotNull;

public class PlantHelper {

    private final FlowerBlock flowerBlock;

    public PlantHelper (@NotNull FlowerBlock flowerBlock) {
        this.flowerBlock = flowerBlock;
    }

    public FlowerBlock getBlock () {
        return flowerBlock;
    }

    public void collectAndEject (ItemStack input, ItemStack output, Level level, BlockPos pos, int seconds) {
        if (input.isEmpty() || output.isEmpty() || level.isClientSide) {
            return;
        }

        var itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(output.getItem(), input.getCount()));
        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
                itemEntity.move(MoverType.PISTON,new Vec3(itemEntity.getX() + 1.5, itemEntity.getY() + 1.5, itemEntity.getZ() + 1.5));
                level.addFreshEntity(itemEntity);
            } catch (InterruptedException e) {
                Mod.getLogger().error(e.getLocalizedMessage());
            }
        }).start();
    }

    public void shearPlant (Player player, Level level, BlockPos pos, ItemStack stack) {
        var itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        level.addFreshEntity(itemEntity);
        player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
    }

}
