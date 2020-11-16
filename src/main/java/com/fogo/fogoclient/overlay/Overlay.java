package com.fogo.fogoclient.overlay;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Overlay {
    private static boolean isActive = true;

    public static void ToggleOverlay() {
        isActive = !isActive;
    }

    public static void RenderOverlay(RenderWorldLastEvent renderEvent, Minecraft mc) {

    }

}
