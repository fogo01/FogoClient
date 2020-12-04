package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.navigation.Navigation;
import com.fogo.fogoclient.navigation.Waypoint;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class NavigationWaypointsScreen extends Screen {
    private WaypointList waypointList;
    private Button editButton;
    private Button deleteButton;

    public NavigationWaypointsScreen() {
        super(new TranslationTextComponent("gui.fogoclient.waypoints"));
    }

    @Override
    protected void init() {
        addButtons();

        this.waypointList = new WaypointList(this.minecraft, this.width, this.height, 48, this.height - 64, 36);
        this.children.add(waypointList);
    }

    private void addButtons() {
        // Back button
        this.addButton(new Button(2, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.back"), (button2) -> {
            FogoClient.mc.displayGuiScreen(new NavigationScreen());
        }));

        this.addButton(new Button(this.width / 2 -76, this.height - 52, 76*2, 20, new TranslationTextComponent("gui.fogoclient.waypoints.add"), (button2) -> {
            this.minecraft.displayGuiScreen(new AddWaypointScreen());
        }));
        this.editButton = this.addButton(new Button(this.width / 2 - 76, this.height - 28, 72, 20, new TranslationTextComponent("gui.fogoclient.waypoints.edit"), (button2) -> {
            if(waypointList.getSelected() == null)
                return;
            this.minecraft.displayGuiScreen(new AddWaypointScreen(waypointList.getSelected().waypoint));
        }));
        this.deleteButton = this.addButton(new Button(this.width / 2 + 4, this.height - 28, 72, 20, new TranslationTextComponent("gui.fogoclient.waypoints.delete"), (button2) -> {
            if(waypointList.getSelected() == null)
                return;
            Navigation.waypoints.remove(waypointList.getSelected().waypoint);
            this.minecraft.displayGuiScreen(this);
        }));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        waypointList.render(matrixStack, mouseX, mouseY, partialTicks);

        // Title
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 2, 0xFFFFFF);

        drawCenteredString(matrixStack, this.font, I18n.format("Dimension: %s", minecraft.world.getDimensionKey().getLocation()), this.width / 2, 12, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
