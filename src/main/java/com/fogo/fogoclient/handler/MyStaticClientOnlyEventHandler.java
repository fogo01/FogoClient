package com.fogo.fogoclient.handler;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.hud.Hud;
import com.fogo.fogoclient.overlay.Overlay;
import com.fogo.fogoclient.xray.Entities;
import com.fogo.fogoclient.xray.Xray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class MyStaticClientOnlyEventHandler {

/*
    @SubscribeEvent
    public static void render(RenderWorldLastEvent event) {
        if(FogoClient.mc == null || FogoClient.mc.player == null)
            return;

        int spawnX = FogoClient.mc.world.getWorldInfo().getSpawnX();
        int spawnY = FogoClient.mc.world.getWorldInfo().getSpawnY();
        int spawnZ = FogoClient.mc.world.getWorldInfo().getSpawnZ();

        Vector3d view = FogoClient.mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        MatrixStack stack = event.getMatrixStack();
        stack.translate(-view.x, -view.y, -view.z); // translate

        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(stack.getLast().getMatrix());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();


        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR );

        RenderSystem.pushMatrix();
        buffer.pos(spawnX -view.x, 0 -view.y, spawnZ -view.z).color(255, 0, 0, 1).endVertex();
        buffer.pos(spawnX -view.x, 256 -view.y, spawnZ -view.z).color(255, 0, 0, 1).endVertex();
        tessellator.draw();
        RenderSystem.popMatrix();

        RenderSystem.popMatrix()
    }*/


    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
        if(FogoClient.mc == null || FogoClient.mc.player == null)
            return;

        Xray.Render(event);

        Overlay.RenderOverlay(event, FogoClient.mc);
    }

    @SubscribeEvent
    public static void renderEntityLiving(RenderLivingEvent.Post event) {
        if(FogoClient.mc == null || FogoClient.mc.player == null)
            return;

        Entities.Render(event);
    }

    @SubscribeEvent
    public static void renderHUD(final RenderGameOverlayEvent.Post event) {
        //FogoClient.mc.fontRenderer.renderString("Test", 1,1, 0x00000000, true, null, null, false, 0xFFFFFFFF, 0x00000000);
        if(FogoClient.mc == null || FogoClient.mc.player == null)
            return;

        Hud.RenderHud(event, FogoClient.mc);
    }

    /*
    @SubscribeEvent
    public static void loadChunkEvent(ChunkEvent.Load event) {
        IChunk chunk = event.getChunk();
        BlockPos pos = new BlockPos(chunk.getPos().x * 16, 1, chunk.getPos().z * 16);
        FogoClient.LOGGER.info(event.getWorld().getBlockState(pos).getBlock().getRegistryName());

        for (int y = 0; y < 256; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    int X = chunk.getPos().x * 16 + x;
                    int Z = chunk.getPos().z * 16 + z;
                    BlockPos pos = new BlockPos(X, y, Z);
                    if (!event.getWorld().isAirBlock(pos)) {
                        FogoClient.LOGGER.info(X + "," + y + "," + Z);
                }
            }
        }
    }
    */

    /*
    @SubscribeEvent
    public static void renderBlock(RenderBlockOverlayEvent event) {
        FogoClient.LOGGER.info(event.getBlockForOverlay());
    }

    @SubscribeEvent
    public static void renderEntity(RenderLivingEvent event) {
        FogoClient.LOGGER.info(event.getEntity());
        event.getRenderer().addLayer(new Deadmau5HeadLayer(event.getRenderer()));
    }
    */
}
