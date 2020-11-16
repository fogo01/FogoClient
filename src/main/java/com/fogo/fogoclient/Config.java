package com.fogo.fogoclient;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_HUD = "hud";
    public static final String CATEGORY_XRAY = "X-Ray";

    public static ForgeConfigSpec CLIENT_CONFIG;

    public static class HUD {
        public static ForgeConfigSpec.BooleanValue SHOW_COMPASS;
        public static ForgeConfigSpec.BooleanValue SHOW_TIME;
        public static ForgeConfigSpec.BooleanValue SHOW_SPEED;
        public static ForgeConfigSpec.BooleanValue SHOW_WORLDSPAWN;
        public static ForgeConfigSpec.BooleanValue SHOW_BIOME;
    }

    public static class XRAY {
        public static ForgeConfigSpec.IntValue RANGE;
        public static ForgeConfigSpec.BooleanValue SHOW_SPAWNERS;
        public static ForgeConfigSpec.BooleanValue SHOW_CHESTS;
        public static ForgeConfigSpec.BooleanValue SHOW_ORES;
        public static ForgeConfigSpec.BooleanValue SHOW_LAVA;
        public static ForgeConfigSpec.BooleanValue SHOW_BEACONS;
        public static ForgeConfigSpec.BooleanValue SHOW_PORTALS;
        public static final String SUBCATEGORY_SPAWNER = "spawner";

        public static class SPAWNER {
            public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_ENTITYNAME;
            public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_SPAWNAREA;
        }
    }


    static {

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("Hud settings").push(CATEGORY_HUD);
            HUD.SHOW_COMPASS = CLIENT_BUILDER.comment("Whether the compass should be shown or not").define("showCompass", true);
            HUD.SHOW_TIME = CLIENT_BUILDER.comment("Whether the time should be shown or not").define("showTime", true);
            HUD.SHOW_SPEED = CLIENT_BUILDER.comment("Whether the player's speed should be shown or not").define("showSpeed", true);
            HUD.SHOW_WORLDSPAWN = CLIENT_BUILDER.comment("Whether the world spawn should be shown or not").define("showWorldSpawn", true);
            HUD.SHOW_BIOME = CLIENT_BUILDER.comment("Whether the player's current biome should be shown or not").define("showBiome", true);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("X-Ray settings").push(CATEGORY_XRAY);
            XRAY.RANGE = CLIENT_BUILDER.comment("Range of the X-ray").defineInRange("range", 32, 8, 128);

            CLIENT_BUILDER.comment("Spawner settings").push(XRAY.SUBCATEGORY_SPAWNER);
                XRAY.SHOW_SPAWNERS = CLIENT_BUILDER.comment("Whether spawners should be shown or not").define("showSpawners", true);
                XRAY.SPAWNER.SHOW_ENTITYNAME = CLIENT_BUILDER.comment("Whether spawners' entity name should be shown or not").define("showEntityName", true);
                XRAY.SPAWNER.SHOW_SPAWNAREA = CLIENT_BUILDER.comment("Whether spawners' spawning area should be shown or not").define("showSpawnArea", true);
            CLIENT_BUILDER.pop();
            XRAY.SHOW_CHESTS = CLIENT_BUILDER.comment("Whether chests should be shown or not").define("showChests", true);
            XRAY.SHOW_ORES = CLIENT_BUILDER.comment("Whether ores should be shown or not").define("showOres", true);
            XRAY.SHOW_LAVA = CLIENT_BUILDER.comment("Whether lava should be shown or not").define("showLava", true);
            XRAY.SHOW_BEACONS = CLIENT_BUILDER.comment("Whether beacons should be shown or not").define("showBeacons", true);
            XRAY.SHOW_PORTALS = CLIENT_BUILDER.comment("Whether portals should be shown or not").define("showPortals", true);
        CLIENT_BUILDER.pop();


        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {

    }
}
