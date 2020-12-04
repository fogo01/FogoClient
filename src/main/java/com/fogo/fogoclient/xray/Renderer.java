package com.fogo.fogoclient.xray;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.settings.Colors;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderWorldLastEvent;


import java.awt.*;

import static com.fogo.fogoclient.FogoClient.mc;


public class Renderer {

    public static void renderOre(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos, RenderWorldLastEvent event) {
        if(mc == null || mc.player == null || mc.world == null || mc.world.getBlockState(pos).getBlock() == null || mc.world.getBlockState(pos).getBlock().getTranslationKey() == null)
            return;

        String name = mc.world.getBlockState(pos).getBlock().getTranslationKey();

        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_LINES);
        if(Colors.ores.containsKey(name)) {
            Color color = new Color(Colors.ores.get(name));

            star(builder, positionMatrix, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
            //box(builder, positionMatrix, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        }
        //buffer.finish(MyRenderType.OVERLAY_LINES);
        buffer.finish();
    }

    public static void renderChest(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos, RenderWorldLastEvent event) {
        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_LINES);
        box(builder, positionMatrix, pos, 0.8f, 0.5f, 0f);
        //buffer.finish(MyRenderType.OVERLAY_LINES);
        buffer.finish();
    }

    public static void renderSpawner(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos, RenderWorldLastEvent event) {
        if(mc == null || mc.player == null || mc.world == null || mc.world.getTileEntity(pos) == null || !(mc.world.getTileEntity(pos) instanceof MobSpawnerTileEntity))
            return;


        MobSpawnerTileEntity spawner = (MobSpawnerTileEntity) mc.world.getTileEntity(pos);

        //FogoClient.LOGGER.info(ObfuscationReflectionHelper.findMethod(EntityRendererManager.class, "getEntityTexture"));

        ClientPlayerEntity player = FogoClient.mc.player;

        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_LINES);
        float dx = (float) (player.getPosX() - pos.getX());
        float dy = (float) (player.getPosY() - pos.getY());
        float dz = (float) (player.getPosZ() - pos.getZ());
        float sqr_dist = dx*dx + dy*dy +dz*dz;
        if(sqr_dist < 16*16) {
            box(builder, positionMatrix, pos, 0f, 1f, 0f);
        } else {
            box(builder, positionMatrix, pos, 1f, 0f, 0f);
        }
        buffer.finish(MyRenderType.OVERLAY_LINES);


        builder = buffer.getBuffer(MyRenderType.OVERLAY_THIN_LINES);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()-3.5f), new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()+4.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()-3.5f), new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()-3.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()-3.5f), new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()+4.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()+4.5f), new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()+4.5f), .5f, .5f, .5f);

        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()-3.5f), new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()-3.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()-3.5f), new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()-3.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()-1f, pos.getZ()+4.5f), new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()+4.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()+4.5f, pos.getY()-1f, pos.getZ()+4.5f), new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()+4.5f), .5f, .5f, .5f);

        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()-3.5f), new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()+4.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()-3.5f), new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()-3.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()-3.5f), new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()+4.5f), .5f, .5f, .5f);
        line(builder, positionMatrix, new Vector3f(pos.getX()-3.5f, pos.getY()+2f, pos.getZ()+4.5f), new Vector3f(pos.getX()+4.5f, pos.getY()+2f, pos.getZ()+4.5f), .5f, .5f, .5f);

        buffer.finish(MyRenderType.OVERLAY_THIN_LINES);

        builder = buffer.getBuffer(MyRenderType.OVERLAY_TICK_LINES);
        if(spawner.getSpawnerBaseLogic().getCachedEntity() != null) {
            String name = spawner.getSpawnerBaseLogic().getCachedEntity().getEntityString();
            text(name, new Vector3d(pos.getX(), pos.getY(), pos.getZ()), event.getMatrixStack());

            if(Colors.entities.containsKey(name)) {
                Color color = new Color(Colors.entities.get(name));

                cross(builder, positionMatrix, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
            }
        }
        //buffer.finish(MyRenderType.OVERLAY_TICK_LINES);
        buffer.finish();
    }

    public static void renderBeacon(IRenderTypeBuffer.Impl buffer, Matrix4f matrix, BeaconTileEntity beacon) {
        if(beacon == null || beacon.getLevels() == 0)
            return;

        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_THIN_LINES);
        int lvl = beacon.getLevels();
        int range = lvl == 4 ? 50 : lvl == 3 ? 40 : lvl == 2 ? 30 : 20;
        BlockPos pos = beacon.getPos();
        line(builder, matrix, new Vector3f(pos.getX() - range, Math.max(0, pos.getY()-range), pos.getZ() - range), new Vector3f(pos.getX() - range, Math.min(256, pos.getY()+256), pos.getZ() - range), .75f, .75f, .75f);
        line(builder, matrix, new Vector3f(pos.getX() + range+1, Math.max(0, pos.getY()-range), pos.getZ() - range), new Vector3f(pos.getX() + range+1, Math.min(256, pos.getY()+256), pos.getZ() - range), .75f, .75f, .75f);
        line(builder, matrix, new Vector3f(pos.getX() - range, Math.max(0, pos.getY()-range), pos.getZ() + range+1), new Vector3f(pos.getX() - range, Math.min(256, pos.getY()+256), pos.getZ() + range+1), .75f, .75f, .75f);
        line(builder, matrix, new Vector3f(pos.getX() + range+1, Math.max(0, pos.getY()-range), pos.getZ() + range+1), new Vector3f(pos.getX() + range+1, Math.min(256, pos.getY()+256), pos.getZ() + range+1), .75f, .75f, .75f);
    }

    public static void renderLava(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos) {
        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_QUAD);

        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()).color(1, .5f, 0, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()+1).color(1, .5f, 0, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()+1).color(1, .5f, 0, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()).color(1, .5f, 0, .5f).endVertex();

        buffer.finish();
    }

    public static void renderNetherPortal(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos) {
        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_QUAD);

        builder.pos(positionMatrix, pos.getX(), pos.getY(), pos.getZ()+.5f).color(.5f, 0f, .5f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()+.5f).color(.5f, 0f, .5f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()+.5f).color(.5f, 0f, .5f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY(), pos.getZ()).color(.5f, .1f, .5f, .5f).endVertex();

        buffer.finish();
    }

    public static void renderEndPortal(IRenderTypeBuffer.Impl buffer, Matrix4f positionMatrix, BlockPos pos) {
        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_QUAD);

        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()).color(.1f, .1f, .1f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()+1).color(.1f, .1f, .1f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()+1).color(.1f, .1f, .1f, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()).color(.1f, .1f, .1f, .5f).endVertex();

        buffer.finish();
    }

    public static void renderEntity(MatrixStack matrixStack, IRenderTypeBuffer buffer, LivingEntity entity) {
        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_THIN_LINES);

        Matrix4f positionMatrix = matrixStack.getLast().getMatrix();

        Color color = Color.white;
        String name = entity.getEntityString();
        if(Colors.entities.containsKey(name)) {
            color = new Color(Colors.entities.get(name));
        }

        line(builder, positionMatrix, new Vector3f(0, 0, 0), new Vector3f(0, entity.getHeight(), 0), color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        if(entity instanceof IAngerable && ((IAngerable)entity).getAttackTarget() != null) {
            LivingEntity target = ((IAngerable)entity).getAttackTarget();
            FogoClient.LOGGER.info(((IAngerable)entity).getAngerTarget());
            matrixStack.push();
            matrixStack.translate(-entity.getPosX(), -entity.getPosY(), -entity.getPosZ());
            line(builder, positionMatrix, new Vector3f((float) entity.getPosX(), (float) entity.getPosY(), (float) entity.getPosZ()), new Vector3f((float) target.getPosX(), (float) target.getPosY(), (float) target.getPosZ()), 1f, 0f, 0f);
            matrixStack.pop();
        }
    }

    private static void _text(String text, Vector3d pos, MatrixStack matrixStack) {
        matrixStack.push();
        float scale = 0.05f;

        matrixStack.translate(pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5);
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStack.scale(scale, scale, scale);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(mc.player.rotationYaw));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(-mc.player.rotationPitch));

        mc.fontRenderer.drawStringWithShadow(matrixStack, text, 0, 0, 0xFFFFFFFF);

        matrixStack.pop();
    }

    private static void text(String text, Vector3d pos, MatrixStack matrixStack) {
        matrixStack.push();

        double sqrDistance = (pos.getX()-mc.player.getPosX())*(pos.getX()-mc.player.getPosX()) + (pos.getY()-mc.player.getPosY())*(pos.getY()-mc.player.getPosY()) + (pos.getZ()-mc.player.getPosZ())*(pos.getZ()-mc.player.getPosZ());
        float scale = (float) (0.025F * Math.sqrt(sqrDistance)*.5F);

        matrixStack.translate(pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5);
        matrixStack.rotate(mc.getRenderManager().getCameraOrientation());
        matrixStack.scale(-scale, -scale, scale);
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;
        FontRenderer fontrenderer = mc.fontRenderer;// getFontRendererFromRenderManager();
        float f2 = (float)(-fontrenderer.getStringPropertyWidth(new TranslationTextComponent(text)) / 2);
        // text, x, y, color, shadow, matrix4f, buffer, x-ray, bg-color, alpha?
        fontrenderer.func_243247_a(new TranslationTextComponent(text), f2, 0, -1, true, matrix4f, mc.getRenderTypeBuffers().getCrumblingBufferSource(), true, 0, 100);

        //mc.fontRenderer.drawStringWithShadow(matrixStack, text, f2, 0, 0xFFFFFFFF);

        matrixStack.pop();
    }

    private static void line(IVertexBuilder builder, Matrix4f positionMatrix, Vector3f p1, Vector3f p2, float r, float g, float b) {
        builder.pos(positionMatrix, p1.getX(), p1.getY(), p1.getZ()).color(r, g, b, 1f).endVertex();
        builder.pos(positionMatrix, p2.getX(), p2.getY(), p2.getZ()).color(r, g, b, 1f).endVertex();
    }

    private static void cross(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, float r, float g, float b) {
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()+1), new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()), new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()+1), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()), r, g, b);
    }

    private static void star(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, float r, float g, float b) {
        cross(builder, positionMatrix, pos, r, g, b);

        line(builder, positionMatrix, new Vector3f(pos.getX()+.5f, pos.getY()+.5f, pos.getZ()), new Vector3f(pos.getX()+.5f, pos.getY()+.5f, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY()+.5f, pos.getZ()+.5f), new Vector3f(pos.getX()+1, pos.getY()+.5f, pos.getZ()+.5f), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+.5f, pos.getY(), pos.getZ()+.5f), new Vector3f(pos.getX()+.5f, pos.getY()+1, pos.getZ()+.5f), r, g, b);
    }

    private static void box(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, float r, float g, float b) {
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()), new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()), new Vector3f(pos.getX(), pos.getY(), pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()), new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()+1), new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()+1), r, g, b);

        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()), new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY(), pos.getZ()+1), new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY(), pos.getZ()+1), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()+1), r, g, b);

        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()), new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()+1), r, g, b);
        line(builder, positionMatrix, new Vector3f(pos.getX(), pos.getY()+1, pos.getZ()+1), new Vector3f(pos.getX()+1, pos.getY()+1, pos.getZ()+1), r, g, b);
    }

    private static void cube(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, float r, float g, float b) {
        // Counter clockwise
        // Bottom
        builder.pos(positionMatrix, pos.getX(), pos.getY(), pos.getZ()).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY(), pos.getZ()).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY(), pos.getZ()+1).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX(), pos.getY(), pos.getZ()+1).color(r, g, b, .5f).endVertex();
        // Top
        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX(), pos.getY()+1, pos.getZ()+1).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()+1).color(r, g, b, .5f).endVertex();
        builder.pos(positionMatrix, pos.getX()+1, pos.getY()+1, pos.getZ()).color(r, g, b, .5f).endVertex();
    }
}
