package com.fogo.fogoclient.xray;

import com.fogo.fogoclient.settings.Colors;
import com.fogo.fogoclient.settings.Config;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import static com.fogo.fogoclient.FogoClient.mc;

public class Xray {
    static boolean isActive = false;

    public static void ToggleXRay() {
        isActive = !isActive;
    }

    public static void Render(RenderWorldLastEvent event) {
        if(!isActive)
            return;
        if(mc == null || mc.player == null)
            return;

        locateTileEntities(mc.player, event);
    }

    public static void locateTileEntities(ClientPlayerEntity player, RenderWorldLastEvent event) {
        MatrixStack matrixStack = event.getMatrixStack();
        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        RenderSystem.disableDepthTest();

        Vector3d playerPos = player.getPositionVec();
        int px = (int) playerPos.x;
        int py = (int) playerPos.y;
        int pz = (int) playerPos.z;
        World world = player.getEntityWorld();

        matrixStack.push();

        Vector3d projectedView = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        Matrix4f matrix = matrixStack.getLast().getMatrix();

        BlockPos.Mutable pos = new BlockPos.Mutable();
        int range = Config.XRAY.RANGE.get();
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    if(dx*dx+dy*dy+dz*dz > range*range)
                        continue;
                    pos.setPos(px + dx, py + dy, pz + dz);
                    if(Config.XRAY.SHOW_SPAWNERS.get()) {
                        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof MobSpawnerTileEntity) {
                            Renderer.renderSpawner(buffer, matrix, pos, event);
                        }
                    }
                    if(Config.XRAY.SHOW_CHESTS.get()) {
                        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof ChestTileEntity) {
                            Renderer.renderChest(buffer, matrix, pos, event);
                        }
                    }
                    if(Config.XRAY.SHOW_BEACONS.get()) {
                        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof BeaconTileEntity) {
                            Renderer.renderBeacon(buffer, matrix, ((BeaconTileEntity) world.getTileEntity(pos)));
                        }
                    }
                    if(Config.XRAY.SHOW_ORES.get()) {
                        if (Colors.ores.containsKey(world.getBlockState(pos).getBlock().getTranslationKey())) {
                            Renderer.renderOre(buffer, matrix, pos, event);
                        }
                    }
                    if(Config.XRAY.SHOW_LAVA.get()) {
                        if (world.getBlockState(pos).getBlock() == Blocks.LAVA) {
                            Renderer.renderLava(buffer, matrix, pos);
                        }
                    }
                    if(Config.XRAY.SHOW_PORTALS.get()) {
                        if (world.getBlockState(pos).getBlock() == Blocks.NETHER_PORTAL) {
                            Renderer.renderNetherPortal(buffer, matrix, pos);
                        }
                    }
                    if(Config.XRAY.SHOW_PORTALS.get()) {
                        if (world.getBlockState(pos).getBlock() == Blocks.END_PORTAL) {
                            Renderer.renderEndPortal(buffer, matrix, pos);
                        }
                    }
                }
            }
        }

        matrixStack.pop();
        RenderSystem.enableDepthTest();
    }
}
