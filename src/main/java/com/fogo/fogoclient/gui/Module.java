package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.settings.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class Module {
    public static Module[] modules = {
            new Module("main",  false,  null),
            new Module("overlay", true,  null),
            new Module("hud",  true,  null),
            new Module("navigation", true,  null),
            new Module("xray", true, new ForgeConfigSpec.ConfigValue[]{
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
    public boolean hasConfig, isToggleable;

    public Module(String name, boolean isToggleable, ForgeConfigSpec.ConfigValue[] configSpecs) {
        this.name = name;
        this.isToggleable = isToggleable;
        this.configSpecs = configSpecs;
        this.hasConfig = configSpecs != null;
    }
}

