package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.navigation.Navigation;
import com.fogo.fogoclient.navigation.Waypoint;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWaypointScreen extends Screen {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private Waypoint waypoint = null;

    private TextFieldWidget nameField;
    private TextFieldWidget dimensionField;
    private TextFieldWidget[] posFields = new TextFieldWidget[3];

    public AddWaypointScreen(Waypoint waypoint) {
        this();
        this.waypoint = waypoint;
    }
    public AddWaypointScreen() {
        super(new TranslationTextComponent("gui.fogoclient.addwaypoint"));
    }
    protected AddWaypointScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        addButtons();

        nameField = new TextFieldWidget(this.font, this.width / 2 - 60, 42, 120, 20, new TranslationTextComponent("gui.fogoclient.inputfield"));
        if (waypoint == null)
            nameField.setText(DATE_FORMAT.format(new Date()));
        else
            nameField.setText(waypoint.name);
        this.children.add(nameField);

        dimensionField = new TextFieldWidget(this.font, this.width / 2 - 60, 82, 120, 20, new TranslationTextComponent("gui.fogoclient.inputfield"));
        if(waypoint == null)
            dimensionField.setText(minecraft.world.getDimensionKey().getLocation().toString());
        else
            dimensionField.setText(waypoint.dimension);
        this.children.add(dimensionField);

        for(int i = 0; i < posFields.length; i++) {
            posFields[i] = new TextFieldWidget(this.font, this.width/2 - (40+20)*(3 - i)+15, 130, 40, 20, new TranslationTextComponent("gui.fogoclient.inputfield"));
            posFields[i].setValidator(NavigationScreen.intTest());
            this.children.add(posFields[i]);
        }
        if(waypoint == null) {
            posFields[0].setText(Integer.toString((int) Math.floor(minecraft.player.getPosX())));
            posFields[1].setText(Integer.toString((int) Math.floor(minecraft.player.getPosY())));
            posFields[2].setText(Integer.toString((int) Math.floor(minecraft.player.getPosZ())));
        } else {
            posFields[0].setText(String.valueOf(waypoint.x));
            posFields[1].setText(String.valueOf(waypoint.y));
            posFields[2].setText(String.valueOf(waypoint.z));
        }


        this.setFocusedDefault(nameField);
    }

    private void addButtons() {
        // Back button
        this.children.add(
            this.addButton(new Button(2, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.back"), (button2) -> {
                FogoClient.mc.displayGuiScreen(new NavigationWaypointsScreen());
            }))
        );

        this.children.add(
            this.addButton(new Button(this.width / 2 -76, 200, 76*2, 20, new TranslationTextComponent("gui.fogoclient.waypoints.save"), (button2) -> {
                try {
                    if(waypoint == null) {
                        Navigation.waypoints.add(new Waypoint(nameField.getText(), dimensionField.getText(), Integer.parseInt(posFields[0].getText()), Integer.parseInt(posFields[1].getText()), Integer.parseInt(posFields[2].getText())));
                    } else {
                        Navigation.waypoints.set(Navigation.waypoints.indexOf(waypoint), new Waypoint(nameField.getText(), dimensionField.getText(), Integer.parseInt(posFields[0].getText()), Integer.parseInt(posFields[1].getText()), Integer.parseInt(posFields[2].getText())));
                    }
                    Navigation.SaveWaypoints();
                } catch (Exception e) {
                    FogoClient.LOGGER.error(e.getMessage());
                }
                this.minecraft.displayGuiScreen(new NavigationWaypointsScreen());
            }))
        );
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        // Title
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 2, 0xFFFFFF);

        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.fogoclient.waypointname"), this.width / 2, 30, 0xFFFFFF);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.fogoclient.waypointdimension"), this.width / 2, 70, 0xFFFFFF);


        drawString(matrixStack, font,"X:", this.width/2 - 175, 130, 0xFFFFFF);
        drawString(matrixStack, font,"Y:", this.width/2 - 115, 130, 0xFFFFFF);
        drawString(matrixStack, font,"Z:", this.width/2 - 55, 130, 0xFFFFFF);

        nameField.render(matrixStack, mouseX, mouseY, partialTicks);
        dimensionField.render(matrixStack, mouseX, mouseY, partialTicks);
        for(TextFieldWidget posField : posFields) {
            posField.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
