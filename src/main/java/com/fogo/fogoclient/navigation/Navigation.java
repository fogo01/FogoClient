package com.fogo.fogoclient.navigation;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.xray.MyRenderType;
import com.google.gson.Gson;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Navigation {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static final String waypointFileName = FogoClient.mc.gameDir + "/FogoClient/waypoints.json";

    static boolean isActive = false;
    public static BlockPos.Mutable target = new BlockPos.Mutable();
    public static boolean[] relativeTarget = new boolean[] {false, false, false};

    public static List<Waypoint> waypoints = new ArrayList<>();

    static {

    }

    public static void LoadWaypoints() {
        Gson gson = new Gson();
        if (!(new File(waypointFileName).exists()))
            return;

        try {
            waypoints.clear();
            Waypoint[] waypoints1 = gson.fromJson(new FileReader(waypointFileName), Waypoint[].class);
            waypoints.addAll(Arrays.asList(waypoints1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void SaveWaypoints() {
        Gson gson = new Gson();
        File dir = new File(FogoClient.mc.gameDir, "FogoClient/");
        dir.mkdir();
        try {
            Writer file = new FileWriter(waypointFileName);
            file.write(gson.toJson(waypoints));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AddDeathPoint(Entity e) {
        waypoints.add(new Waypoint(String.format("Death %s", DATE_FORMAT.format(new Date())), FogoClient.mc.world.getDimensionKey().getLocation().toString(), (int)e.getPosX(), (int)e.getPosY(), (int)e.getPosZ()));
        SaveWaypoints();
    }

    public static void RenderNavigation(RenderWorldLastEvent renderEvent, Minecraft mc) {
        if(!isActive)
            return;
        if(mc == null || mc.player == null)
            return;

        MatrixStack matrixStack = renderEvent.getMatrixStack();
        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        RenderSystem.disableDepthTest();

        Vector3d playerPos = mc.player.getPositionVec();
        float px = (float) playerPos.x;
        float py = (float) playerPos.y;
        float pz = (float) playerPos.z;

        matrixStack.push();

        Vector3d projectedView = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        Matrix4f matrix = matrixStack.getLast().getMatrix();

        IVertexBuilder builder = buffer.getBuffer(MyRenderType.OVERLAY_LINES);
        builder.pos(matrix, px, py + mc.player.getEyeHeight()/2f, pz).color(0f, 1f, 0f, 1f).endVertex();
        builder.pos(matrix, relativeTarget[0] ? px : target.getX() + .5f, relativeTarget[1] ? py + mc.player.getEyeHeight()/2f : target.getY() + .5f, relativeTarget[2] ? pz : target.getZ() + .5f).color(0f, 1f, 0f, 1f).endVertex();

        buffer.finish(MyRenderType.OVERLAY_LINES);

        matrixStack.pop();
        RenderSystem.enableDepthTest();
    }

    public static void ToggleNavigation() {
        isActive = !isActive;
    }
}
