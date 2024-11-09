package net.superscary.arcaneherbalism.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.arcaneherbalism.core.Mod;
import net.superscary.arcaneherbalism.core.registries.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SmeltingRecipes extends RecipeProvider {

    public SmeltingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return Mod.NAME + " Smelting Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ModItems.LEAF), RecipeCategory.MISC, new ItemStack(ModItems.DRIED_LEAF, 1), 0.1F, 75)
                .unlockedBy("has_item", has(ModItems.LEAF))
                .save(consumer, Mod.getResource("dried_leaf_smelting"));
    }

}