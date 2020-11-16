package com.fogo.fogoclient.xray;

import net.minecraftforge.client.event.RenderLivingEvent;

import java.util.HashMap;

import static com.fogo.fogoclient.FogoClient.mc;

public class Entities {
    public static HashMap<String, Integer> entities;

    static {
        entities = new HashMap<>();
        entities.put("minecraft:pig", 0x343434);
    }

    public static void Render(RenderLivingEvent.Post event) {
        if(!Xray.isActive)
            return;
        if(mc == null || mc.player == null)
            return;

        Renderer.renderEntity(event.getMatrixStack(), event.getBuffers(), event.getEntity());
    }
}
