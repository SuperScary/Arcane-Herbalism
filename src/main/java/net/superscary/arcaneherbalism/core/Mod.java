package net.superscary.arcaneherbalism.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface Mod {

    String MOD_ID = "arcaneherbalism";
    String NAME = "Arcane Herbalism";

    Logger LOGGER = LoggerFactory.getLogger(NAME);

    static Mod instance () {
        return ModBase.INSTANCE;
    }

    static ResourceLocation getResource (String name) {
        return custom(MOD_ID, name);
    }

    static ResourceLocation getMinecraftResource (String name) {
        return custom("minecraft", name);
    }

    static ResourceLocation custom (String id, String name) {
        return ResourceLocation.fromNamespaceAndPath(id, name);
    }

    Collection<ServerPlayer> getPlayers ();

    Level getClientLevel ();

    MinecraftServer getCurrentServer ();

    static Logger getLogger() {
        return LOGGER;
    }

}
