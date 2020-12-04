package com.fogo.fogoclient.hud;

import com.fogo.fogoclient.settings.Config;
import com.fogo.fogoclient.FogoClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Hud {
    static boolean isActive = false;

    public static void ToggleHud() {
        isActive = !isActive;
    }

    public static void RenderHud(RenderGameOverlayEvent.Post renderEvent, Minecraft mc) {
        if(!isActive)
            return;
        if(mc == null || mc.player == null)
            return;

        if(mc.currentScreen != null || mc.gameSettings.showDebugInfo)
            return;

        int lineHeight = mc.fontRenderer.getWordWrappedHeight(" ", 10);
        int currentLine = 0;
        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();

        if(Config.HUD.SHOW_COMPASS.get()) {
            final String compass = "N - | - E - | - S - | - W - | - ";
            String str = "[]";
            float yaw = Math.abs(mc.player.rotationYaw % 360);
            try {
                str = "[" + compass.substring((int) yaw * 32 / 360) + compass.substring(0, (int) yaw * 32 / 360) + "]";
            } catch (Exception e) {
                FogoClient.LOGGER.info(e.getMessage());
            }

            mc.fontRenderer.drawStringWithShadow(renderEvent.getMatrixStack(), str, renderEvent.getWindow().getScaledWidth()/2f - mc.fontRenderer.getStringWidth(str)/2f, 1, 0xFFFFFFFF);
        }


        if(Config.HUD.SHOW_TIME.get()) {
            float time = 6 + mc.world.getDayTime() / 1000f;
            if (time >= 24)
                time -= 24;
            int h = (int) Math.floor(time);
            int m = (int) Math.floor((time % 1) * 60);
            mc.fontRenderer.drawStringWithShadow(renderEvent.getMatrixStack(), String.format("Time: %02d:%02d", h, m), 1, 1 + lineHeight * currentLine, 0xFFFFFFFF);
            currentLine++;
        }


        if(Config.HUD.SHOW_SPEED.get()) {
            double x = (mc.player.lastTickPosX - mc.player.getPosX()) * 20;
            double z = (mc.player.lastTickPosZ - mc.player.getPosZ()) * 20;
            double horzVel = Math.hypot(x, z);
            mc.fontRenderer.drawStringWithShadow(renderEvent.getMatrixStack(), String.format("Speed: %f", horzVel), 1, 1 + lineHeight * currentLine, 0xFFFFFFFF);
            currentLine++;
        }


        if(Config.HUD.SHOW_WORLDSPAWN.get()) {
            int spawnX = mc.world.getWorldInfo().getSpawnX();
            int spawnY = mc.world.getWorldInfo().getSpawnY();
            int spawnZ = mc.world.getWorldInfo().getSpawnZ();
            mc.fontRenderer.drawStringWithShadow(renderEvent.getMatrixStack(), String.format("World Spawn: %d,%d,%d", spawnX, spawnY, spawnZ), 1, 1 + lineHeight * currentLine, 0xFFFFFFFF);
            currentLine++;
        }

        if(Config.HUD.SHOW_BIOME.get()) {
            int playerX = (int) Math.round(mc.player.getPosX());
            int playerY = (int) Math.round(mc.player.getPosY());
            int playerZ = (int) Math.round(mc.player.getPosZ());

            BlockPos blockPos = new BlockPos(playerX, playerY, playerZ);
            Biome biome = mc.world.getBiome(blockPos);

            String biomeName = biome.toString();

            biomeName = mc.world.getChunk(blockPos).getBiomes().getBiomeIds().toString();

            mc.fontRenderer.drawStringWithShadow(renderEvent.getMatrixStack(), String.format("Biome: %s", biomeName), 1, 1 + lineHeight * currentLine, 0xFFFFFFFF);
            currentLine++;
        }
    }
}
