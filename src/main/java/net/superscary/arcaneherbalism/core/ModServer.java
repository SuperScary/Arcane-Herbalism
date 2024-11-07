package net.superscary.arcaneherbalism.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;

public class ModServer extends ModBase {

    public ModServer (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return null;
    }

}
