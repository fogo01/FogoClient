package com.fogo.fogoclient.settings;

import net.minecraft.block.Blocks;

import java.util.HashMap;

public class Colors {
    public static HashMap<String, Integer> entities;
    public static HashMap<String, Integer> ores;

    static {
        entities = new HashMap<>();
        entities.put("minecraft:pig", 0xFF335C);
        entities.put("minecraft:zombie", 0x339933);
        entities.put("minecraft:skeleton", 0xabab92);
        entities.put("minecraft:spider", 0x2e2e2e);
        entities.put("minecraft:cave_spider", 0x0b221b);
        entities.put("minecraft:silverfish", 0x545454);
        entities.put("minecraft:blaze", 0xff9900);
        entities.put("minecraft:magma_cube", 0x9e1800);


        ores = new HashMap<>();
        ores.put(Blocks.COAL_ORE.getTranslationKey(), 0x343434);
        ores.put(Blocks.IRON_ORE.getTranslationKey(), 0xBC9980);
        ores.put(Blocks.GOLD_ORE.getTranslationKey(), 0xFCEE4B);
        ores.put(Blocks.REDSTONE_ORE.getTranslationKey(), 0xAA0404);
        ores.put(Blocks.LAPIS_ORE.getTranslationKey(), 0x1542CC);
        ores.put(Blocks.DIAMOND_ORE.getTranslationKey(), 0x5DECF5);
        ores.put(Blocks.EMERALD_ORE.getTranslationKey(), 0x17DD62);

        ores.put(Blocks.NETHER_QUARTZ_ORE.getTranslationKey(), 0xDDCBBE);
        ores.put(Blocks.NETHER_GOLD_ORE.getTranslationKey(), 0xFCEE4B);
        ores.put(Blocks.ANCIENT_DEBRIS.getTranslationKey(), 0x84523C);
    }
}
