package com.example.examplemod.datagen.providers.recipes;

import com.example.examplemod.core.Mod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
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

    }

}