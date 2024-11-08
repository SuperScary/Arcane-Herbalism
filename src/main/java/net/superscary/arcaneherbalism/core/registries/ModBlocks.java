package net.superscary.arcaneherbalism.core.registries;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.superscary.arcaneherbalism.block.DeadlyNightshade;
import net.superscary.arcaneherbalism.block.Pyroblossom;
import net.superscary.arcaneherbalism.block.base.BaseBlock;
import net.superscary.arcaneherbalism.block.base.FlowerBlock;
import net.superscary.arcaneherbalism.block.base.PottedBlock;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.Tab;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;
import net.superscary.arcaneherbalism.core.definitions.ItemDefinition;
import net.superscary.arcaneherbalism.item.base.BaseBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Mod.MOD_ID);

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();
    public static final Map<BlockDefinition<?>, BlockDefinition<?>> FLOWERS_MAP = new HashMap<>();

    // PLANTS
    public static final BlockDefinition<FlowerBlock> DEADLY_NIGHTSHADE;
    public static final BlockDefinition<FlowerBlock> PYROBLOSSOM;
    public static final BlockDefinition<FlowerBlock> WEED;

    // POTS
    public static final BlockDefinition<PottedBlock> POTTED_DEADLY_NIGHTSHADE;
    public static final BlockDefinition<PottedBlock> POTTED_PYROBLOSSOM;
    public static final BlockDefinition<PottedBlock> POTTED_WEED;

    public static List<BlockDefinition<?>> getBlocks () {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, final Supplier<T> supplier) {
        return reg(name, Mod.getResource(name), supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier, boolean addToTab) {
        return reg(name, id, supplier, null, addToTab);
    }

    public static <T extends Block> BlockDefinition<T> registerPottedBlock (final String name, BlockDefinition<FlowerBlock> flower, final Supplier<T> supplier) {
        var deferredBlock = REGISTRY.register(Mod.getResource(name).getPath(), supplier);
        BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, null);
        FLOWERS_MAP.put(flower, definition);
        BLOCKS.add(definition);
        return definition;
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier, @Nullable BiFunction<Block, Item.Properties, BlockItem> itemFactory, boolean addToTab) {
        var deferredBlock = REGISTRY.register(id.getPath(), supplier);
        var deferredItem = ModItems.REGISTRY.register(id.getPath(), () -> {
            var block = deferredBlock.get();
            var itemProperties = new Item.Properties();
            if (itemFactory != null) {
                var item = itemFactory.apply(block, itemProperties);
                if (item == null) {
                    throw new IllegalArgumentException("BlockItem factory for " + id + " returned null.");
                }
                return item;
            } else if (block instanceof BaseBlock) {
                return new BaseBlockItem(block, itemProperties);
            } else {
                return new BlockItem(block, itemProperties);
            }
        });
        var itemDef = new ItemDefinition<>(name, deferredItem);
        if (addToTab) Tab.add(itemDef);
        BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, itemDef);
        BLOCKS.add(definition);
        return definition;
    }

    /**
     * Built during common setup to allow potable flowers
     */
    public static void buildFlowerPots () {
        for (var block : ModBlocks.FLOWERS_MAP.entrySet()) {
            Mod.getLogger().info("Adding flower pot for {}", block.getKey().id());
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(block.getKey().id(), block.getValue().getDeferredBlock());
        }
    }

    static {
        DEADLY_NIGHTSHADE = reg("deadly_nightshade", () -> new DeadlyNightshade(MobEffects.POISON, 8));
        PYROBLOSSOM = reg("pyroblossom", () -> new Pyroblossom(MobEffects.HARM, 1));
        WEED = reg("weed", () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES_WITH_OFFSETS, false));

        POTTED_DEADLY_NIGHTSHADE = registerPottedBlock("potted_deadly_nightshade", DEADLY_NIGHTSHADE, () -> new PottedBlock(DEADLY_NIGHTSHADE));
        POTTED_PYROBLOSSOM = registerPottedBlock("potted_pyroblossom", PYROBLOSSOM, () -> new PottedBlock(PYROBLOSSOM));
        POTTED_WEED = registerPottedBlock("potted_weed", WEED, () -> new PottedBlock(WEED));
    }

}
