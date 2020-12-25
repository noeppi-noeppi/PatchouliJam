package io.github.noeppi_noeppi.mods.patchoulijam.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec CLIENT_CONFIG;
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    static {
        init(CLIENT_BUILDER);
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> alwaysChristmas;

    public static void init(ForgeConfigSpec.Builder builder) {
        alwaysChristmas = builder.comment("Whether chests should always display as presents.").define("always_christmas", true);
    }
}
