package net.superscary.arcaneherbalism.core.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.arcaneherbalism.block.DeadlyNightshade;
import net.superscary.arcaneherbalism.block.Pyroblossom;
import net.superscary.arcaneherbalism.block.base.*;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.Tab;
import net.superscary.arcaneherbalism.core.definitions.BlockDefinition;
import net.superscary.arcaneherbalism.core.definitions.ItemDefinition;
import net.superscary.arcaneherbalism.core.util.Id;
import net.superscary.arcaneherbalism.item.base.BaseBlockItem;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Mod.MOD_ID);

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();
    public static final Map<BlockDefinition<?>, BlockDefinition<?>> FLOWERS_MAP = new HashMap<>();

    // PLANTS
    public static final BlockDefinition<FlowerBlock> ASHCAP_FUNGUS;
    public static final BlockDefinition<FlowerBlock> BLOODBLOOM;
    public static final BlockDefinition<BaseBlock>   BRIARHEART_SHRUB;
    public static final BlockDefinition<FlowerBlock> DEADLY_NIGHTSHADE;
    public static final BlockDefinition<MossBlock>   ELDER_MOSS;
    public static final BlockDefinition<FlowerBlock> FIRETHORN;
    public static final BlockDefinition<FlowerBlock> GHOST_FUNGUS;
    public static final BlockDefinition<FlowerBlock> GLOWMIRE_TOADSTOOL;
    public static final BlockDefinition<FlowerBlock> MISTWALLOW;
    public static final BlockDefinition<FlowerBlock> MOONVEIL_BLOOM;
    public static final BlockDefinition<FlowerBlock> NETTLETHORN;
    public static final BlockDefinition<FlowerBlock> PYROBLOSSOM;
    public static final BlockDefinition<FlowerBlock> SHADOWFERN;
    public static final BlockDefinition<VineBlock>   SILVER_IVY;
    public static final BlockDefinition<GrassBlock>  SONGREED;
    public static final BlockDefinition<FlowerBlock> STARFLOWER;
    public static final BlockDefinition<FlowerBlock> SYLVIAN_DAISY;
    public static final BlockDefinition<BaseBlock>   THUNDERROOT;
    public static final BlockDefinition<FlowerBlock> WEED;
    public static final BlockDefinition<FlowerBlock> WHISPER_CAP_MUSHROOM;
    public static final BlockDefinition<GrassBlock>  WILDSHADE_GRASS;
    public static final BlockDefinition<FlowerBlock> WITCHES_SAGE;
    public static final BlockDefinition<VineBlock>   VENOMVINE;

    // POTS
    public static final BlockDefinition<PottedBlock> POTTED_ASHCAP_FUNGUS;
    public static final BlockDefinition<PottedBlock> POTTED_BLOODBLOOM;
    public static final BlockDefinition<PottedBlock> POTTED_DEADLY_NIGHTSHADE;
    public static final BlockDefinition<PottedBlock> POTTED_FIRETHORN;
    public static final BlockDefinition<PottedBlock> POTTED_GHOST_FUNGUS;
    public static final BlockDefinition<PottedBlock> POTTED_GLOWMIRE_TOADSTOOL;
    public static final BlockDefinition<PottedBlock> POTTED_MISTWALLOW;
    public static final BlockDefinition<PottedBlock> POTTED_MOONVEIL_BLOOM;
    public static final BlockDefinition<PottedBlock> POTTED_NETTLETHORN;
    public static final BlockDefinition<PottedBlock> POTTED_PYROBLOSSOM;
    public static final BlockDefinition<PottedBlock> POTTED_SHADOWFERN;
    public static final BlockDefinition<PottedBlock> POTTED_STARFLOWER;
    public static final BlockDefinition<PottedBlock> POTTED_SYLVIAN_DAISY;
    public static final BlockDefinition<PottedBlock> POTTED_WEED;
    public static final BlockDefinition<PottedBlock> POTTED_WHISPER_CAP_MUSHROOM;
    public static final BlockDefinition<PottedBlock> POTTED_WITCHES_SAGE;

    public static List<BlockDefinition<?>> getBlocks () {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier) {
        return reg(name, id, supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> registerPottedBlock (final String name, BlockDefinition<FlowerBlock> flower, final Supplier<T> supplier) {
        var deferredBlock = REGISTRY.register(Mod.getResource(name).getPath(), supplier);
        BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, null);
        FLOWERS_MAP.put(flower, definition);
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
        ASHCAP_FUNGUS = reg("Ashcap Fungus", Id.ASHCAP_FUNGUS, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES, false));
        BLOODBLOOM = reg("Bloodbloom", Id.BLOODBLOOM, () -> new FlowerBlock(MobEffects.HARM, 25, FlowerBlock.PROPERTIES, true));
        BRIARHEART_SHRUB = reg("Briarheart Shrub", Id.BRIARHEART_SHRUB, DecorativeBlock::new);
        DEADLY_NIGHTSHADE = reg("Deadly Nightshade", Id.DEADLY_NIGHTSHADE, () -> new DeadlyNightshade(MobEffects.POISON, 8));
        ELDER_MOSS = reg("Elder Moss", Id.ELDER_MOSS, () -> new MossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.GRASS)));
        FIRETHORN = reg("Firethorn", Id.FIRETHORN, () -> new FlowerBlock(MobEffects.HARM, 25, FlowerBlock.PROPERTIES_WITH_OFFSETS, true));
        GHOST_FUNGUS = reg("Ghost Fungus", Id.GHOST_FUNGUS, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES_WITH_OFFSETS, false));
        GLOWMIRE_TOADSTOOL = reg("Glowmire Toadstool", Id.GLOWMIRE_TOADSTOOL, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES, false));
        MISTWALLOW = reg("Mistwallow", Id.MISTWALLOW, () -> new FlowerBlock(MobEffects.BLINDNESS, 25, FlowerBlock.PROPERTIES, true));
        MOONVEIL_BLOOM = reg("Moonveil Bloom", Id.MOONVEIL_BLOOM, () -> new FlowerBlock(MobEffects.BLINDNESS, 25, FlowerBlock.PROPERTIES, true));
        NETTLETHORN = reg("Nettlethorn", Id.NETTLETHORN, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES, true));
        PYROBLOSSOM = reg("Pyroblossom", Id.PYROBLOSSOM, () -> new Pyroblossom(MobEffects.HARM, 1));
        SHADOWFERN = reg("Shadowfern", Id.SHADOWFERN, () -> new FlowerBlock(MobEffects.BLINDNESS, 25, FlowerBlock.PROPERTIES, true));
        SILVER_IVY = reg("Silver Ivy", Id.SILVER_IVY, () -> new VineBlock(Block.Properties.ofFullCopy(Blocks.VINE)));
        SONGREED = reg("Songreed", Id.SONGREED, () -> new GrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS)));
        STARFLOWER = reg("Starflower", Id.STARFLOWER, () -> new FlowerBlock(MobEffects.BLINDNESS, 25, FlowerBlock.PROPERTIES, true));
        SYLVIAN_DAISY = reg("Sylvian Daisy", Id.SYLVIAN_DAISY, () -> new FlowerBlock(MobEffects.BLINDNESS, 25, FlowerBlock.PROPERTIES, true));
        THUNDERROOT = reg("Thunderroot", Id.THUNDERROOT, DecorativeBlock::new);
        WEED = reg("Weed", Id.WEED, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES_WITH_OFFSETS, false));
        WHISPER_CAP_MUSHROOM = reg("Whisper Cap Mushroom", Id.WHISPER_CAP_MUSHROOM, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES, false));
        WILDSHADE_GRASS = reg("Wildshade Grass", Id.WILDSHADE_GRASS, () -> new GrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));
        WITCHES_SAGE = reg("Witches Sage", Id.WITCHES_SAGE, () -> new FlowerBlock(MobEffects.POISON, 25, FlowerBlock.PROPERTIES, false));
        VENOMVINE = reg("Venomvine", Id.VENOMVINE, () -> new VineBlock(Block.Properties.ofFullCopy(Blocks.VINE)));

        POTTED_ASHCAP_FUNGUS = registerPottedBlock("potted_ashcap_fungus", ASHCAP_FUNGUS, () -> new PottedBlock(ASHCAP_FUNGUS));
        POTTED_BLOODBLOOM = registerPottedBlock("potted_bloodbloom", BLOODBLOOM, () -> new PottedBlock(BLOODBLOOM));
        POTTED_DEADLY_NIGHTSHADE = registerPottedBlock("potted_deadly_nightshade", DEADLY_NIGHTSHADE, () -> new PottedBlock(DEADLY_NIGHTSHADE));
        POTTED_FIRETHORN = registerPottedBlock("potted_firethorn", FIRETHORN, () -> new PottedBlock(FIRETHORN));
        POTTED_GHOST_FUNGUS = registerPottedBlock("potted_ghost_fungus", GHOST_FUNGUS, () -> new PottedBlock(GHOST_FUNGUS));
        POTTED_GLOWMIRE_TOADSTOOL = registerPottedBlock("potted_glowmire_toadstool", GLOWMIRE_TOADSTOOL, () -> new PottedBlock(GLOWMIRE_TOADSTOOL));
        POTTED_MISTWALLOW = registerPottedBlock("potted_mistwallow", MISTWALLOW, () -> new PottedBlock(MISTWALLOW));
        POTTED_MOONVEIL_BLOOM = registerPottedBlock("potted_moonveil_bloom", MOONVEIL_BLOOM, () -> new PottedBlock(MOONVEIL_BLOOM));
        POTTED_NETTLETHORN = registerPottedBlock("potted_nettlethorn", NETTLETHORN, () -> new PottedBlock(NETTLETHORN));
        POTTED_PYROBLOSSOM = registerPottedBlock("potted_pyroblossom", PYROBLOSSOM, () -> new PottedBlock(PYROBLOSSOM));
        POTTED_SHADOWFERN = registerPottedBlock("potted_shadowfern", SHADOWFERN, () -> new PottedBlock(SHADOWFERN));
        POTTED_STARFLOWER = registerPottedBlock("potted_starflower", STARFLOWER, () -> new PottedBlock(STARFLOWER));
        POTTED_SYLVIAN_DAISY = registerPottedBlock("potted_sylvian_daisy", SYLVIAN_DAISY, () -> new PottedBlock(SYLVIAN_DAISY));
        POTTED_WEED = registerPottedBlock("potted_weed", WEED, () -> new PottedBlock(WEED));
        POTTED_WHISPER_CAP_MUSHROOM = registerPottedBlock("potted_whisper_cap_mushroom", WHISPER_CAP_MUSHROOM, () -> new PottedBlock(WHISPER_CAP_MUSHROOM));
        POTTED_WITCHES_SAGE = registerPottedBlock("potted_witches_sage", WITCHES_SAGE, () -> new PottedBlock(WITCHES_SAGE));
    }

}
