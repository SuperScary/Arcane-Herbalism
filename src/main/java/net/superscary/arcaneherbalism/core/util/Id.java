package net.superscary.arcaneherbalism.core.util;

import net.minecraft.resources.ResourceLocation;
import net.superscary.arcaneherbalism.core.Mod;

public class Id {

    public static final ResourceLocation BAT_WINGS = id("bat_wings");
    public static final ResourceLocation DRAGON_TONGUE = id("dragon_tongue");
    public static final ResourceLocation DRIED_LEAF = id("dried_leaf");
    public static final ResourceLocation EYEBALL = id("eyeball");
    public static final ResourceLocation LEAF = id("leaf");

    public static final ResourceLocation ASHCAP_FUNGUS = id("ashcap_fungus");
    public static final ResourceLocation BLOODBLOOM = id("bloodbloom");
    public static final ResourceLocation BRIARHEART_SHRUB = id("briarheart_shrub");
    public static final ResourceLocation DEADLY_NIGHTSHADE = id("deadly_nightshade");
    public static final ResourceLocation ELDER_MOSS = id("elder_moss");
    public static final ResourceLocation FIRETHORN = id("firethorn");
    public static final ResourceLocation GHOST_FUNGUS = id("ghost_fungus");
    public static final ResourceLocation GLOWMIRE_TOADSTOOL = id("glowmire_toadstool");
    public static final ResourceLocation MISTWALLOW = id("mistwallow");
    public static final ResourceLocation MOONVEIL_BLOOM = id("moonveil_bloom");
    public static final ResourceLocation NETTLETHORN = id("nettlethorn");
    public static final ResourceLocation PYROBLOSSOM = id("pyroblossom");
    public static final ResourceLocation SHADOWFERN = id("shadowfern");
    public static final ResourceLocation SILVER_IVY = id("silver_ivy");
    public static final ResourceLocation SONGREED = id("songreed");
    public static final ResourceLocation STARFLOWER = id("starflower");
    public static final ResourceLocation SYLVIAN_DAISY = id("sylvian_daisy");
    public static final ResourceLocation THUNDERROOT = id("thunderroot");
    public static final ResourceLocation WEED = id("weed");
    public static final ResourceLocation WHISPER_CAP_MUSHROOM = id("whisper_cap_mushroom");
    public static final ResourceLocation WILDSHADE_GRASS = id("wildshade_grass");
    public static final ResourceLocation WITCHES_SAGE = id("witches_sage");
    public static final ResourceLocation VENOMVINE = id("venomvine");

    private static ResourceLocation id(String path) {
        return Mod.getResource(path);
    }

}
