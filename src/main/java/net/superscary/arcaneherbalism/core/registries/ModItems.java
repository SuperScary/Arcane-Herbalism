package net.superscary.arcaneherbalism.core.registries;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.Tab;
import net.superscary.arcaneherbalism.core.definitions.ItemDefinition;
import net.superscary.arcaneherbalism.core.util.Id;
import net.superscary.arcaneherbalism.item.Eyeball;
import net.superscary.arcaneherbalism.item.base.BaseItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ModItems {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Mod.MOD_ID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    // REGISTER ITEMS HERE
    public static final ItemDefinition<BaseItem> BAT_WINGS;
    public static final ItemDefinition<BaseItem> DRAGON_TONGUE;
    public static final ItemDefinition<BaseItem> DRIED_LEAF;
    public static final ItemDefinition<Eyeball> EYEBALL;
    public static final ItemDefinition<BaseItem> LEAF;

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory) {
        return item(name, id, factory, Tab.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Preconditions.checkArgument(id.getNamespace().equals(Mod.MOD_ID), "Can only register items in " + Mod.MOD_ID + " namespace");
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, Tab.MAIN)) {
            Tab.add(definition);
        } else if (group != null) {
            Tab.addExternal(group, definition);
        }

        ITEMS.add(definition);
        return definition;
    }

    static {
        BAT_WINGS = item("Bat Wings", Id.BAT_WINGS, p -> new BaseItem());
        DRAGON_TONGUE = item("Dragon Tongue", Id.DRAGON_TONGUE, p -> new BaseItem());
        DRIED_LEAF = item("Dried Leaf", Id.DRIED_LEAF, p -> new BaseItem());
        EYEBALL = item("Eyeball", Id.EYEBALL, Eyeball::new);
        LEAF = item("Leaf", Id.LEAF, p -> new BaseItem());
    }

}
