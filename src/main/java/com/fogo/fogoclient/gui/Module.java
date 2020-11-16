package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class Module {
    public static Module[] modules = {
            new Module("main", null),
            new Module("overlay", null),
            new Module("hud", null),
            new Module("navigation", null),
            new Module("xray", new ForgeConfigSpec.ConfigValue[]{
                    Config.XRAY.RANGE,
                    Config.XRAY.SHOW_CHESTS,
                    Config.XRAY.SHOW_SPAWNERS,
                    Config.XRAY.SHOW_ORES,
                    Config.XRAY.SHOW_BEACONS,
                    Config.XRAY.SHOW_PORTALS,
                    Config.XRAY.SHOW_LAVA,
            }),
    };

    public String name;
    public ForgeConfigSpec.ConfigValue[] configSpecs;

    public Module(String name, ForgeConfigSpec.ConfigValue[] configSpecs) {
        this.name = name;
        this.configSpecs = configSpecs;
    }
}

