package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.navigation.Navigation;
import com.fogo.fogoclient.navigation.Waypoint;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.WorldSelectionList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WaypointList  extends ExtendedList<WaypointList.Entry> {

    public WaypointList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);

        for (int i = 0; i < Navigation.waypoints.size(); i++) {
            addEntry(new Entry(Navigation.waypoints.get(i)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public final class Entry extends ExtendedList.AbstractListEntry<Entry> {
        public Waypoint waypoint;
        private long doubleClick;

        public Entry(Waypoint waypoint) {
            //this(waypoint.name, waypoint.dimension, waypoint.x, waypoint.y, waypoint.z);
            this.waypoint = waypoint;
        }

        @Override
        public void render(MatrixStack matrixStack, int index, int y, int x, int width, int height, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            float distance = (float) Math.sqrt((waypoint.x - minecraft.player.getPosX())*(waypoint.x - minecraft.player.getPosX()) + (waypoint.y - minecraft.player.getPosY())*(waypoint.y - minecraft.player.getPosY()) + (waypoint.z - minecraft.player.getPosZ())*(waypoint.z - minecraft.player.getPosZ()));
            drawString(matrixStack, minecraft.fontRenderer, waypoint.name, x, y, 0xFFFFFF);
            String s = String.format("%dm", Math.round(distance));
            drawString(matrixStack, minecraft.fontRenderer, s, x+width - minecraft.fontRenderer.getStringWidth(s)-3, y, 0xFFFFFF);
            drawString(matrixStack, minecraft.fontRenderer, new TranslationTextComponent(waypoint.dimension), x, y + 10, 0x808080);
            drawString(matrixStack, minecraft.fontRenderer, String.format("X:%d, Y:%d, Z:%d", waypoint.x, waypoint.y, waypoint.z), x, y + 20, 0x808080);
            //minecraft.fontRenderer.drawString(matrixStack, name, 10, 100, 0xFFFFFF);

        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            WaypointList.this.setSelected(this);
            if (Util.milliTime() - this.doubleClick < 250L) {
                Navigation.target.setPos(this.waypoint.x, this.waypoint.y, this.waypoint.z);
                FogoClient.mc.displayGuiScreen(new NavigationScreen());
                return true;
            } else {
                this.doubleClick = Util.milliTime();
                return false;
            }
        }
    }
}
