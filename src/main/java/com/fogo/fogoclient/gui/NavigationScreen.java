package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.navigation.Navigation;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class NavigationScreen extends ModuleScreen {

    private TextFieldWidget[] targetFields = new TextFieldWidget[3];

    public NavigationScreen(Module module) {
        super(module);
    }

    public NavigationScreen() {
        super(Module.modules[3]);
    }

    @Override
    protected void init() {
        super.init();

        for(int i = 0; i < targetFields.length; i++) {
            targetFields[i] = new TextFieldWidget(this.font, this.width - (40+20)*(3 - i)+15, 35, 40, 20, new TranslationTextComponent("gui.fogoclient.inputfield"));
            targetFields[i].setValidator(intTest());
            this.children.add(targetFields[i]);
        }

        if(Navigation.relativeTarget[0])
            targetFields[0].setText("~");
        else
            targetFields[0].setText(Integer.toString(Navigation.target.getX()));
        targetFields[0].setResponder((value) -> {
            if(value.equals("~")) {
                Navigation.relativeTarget[0] = true;
            } else {
                Navigation.relativeTarget[0] = false;
                try {
                    Navigation.target.setX(Integer.parseInt(value));
                } catch (Exception e) {
                    FogoClient.LOGGER.error(e.getMessage());
                }
            }
        });

        if(Navigation.relativeTarget[1])
            targetFields[1].setText("~");
        else
            targetFields[1].setText(Integer.toString(Navigation.target.getY()));
        targetFields[1].setResponder((value) -> {
            if(value.equals("~")) {
                Navigation.relativeTarget[1] = true;
            } else {
                Navigation.relativeTarget[1] = false;
                try {
                    Navigation.target.setY(Integer.parseInt(value));
                } catch (Exception e) {
                    FogoClient.LOGGER.error(e.getMessage());
                }
            }
        });

        if(Navigation.relativeTarget[2])
            targetFields[2].setText("~");
        else
            targetFields[2].setText(Integer.toString(Navigation.target.getZ()));
        targetFields[2].setResponder((value) -> {
            if(value.equals("~")) {
                Navigation.relativeTarget[2] = true;
            } else {
                Navigation.relativeTarget[2] = false;
                try {
                    Navigation.target.setZ(Integer.parseInt(value));
                } catch (Exception e) {
                    FogoClient.LOGGER.error(e.getMessage());
                }
            }
        });

        addButtons();
    }

    private void addButtons() {
        this.addButton(new Button(width/2-50, 70, 100, 20, new TranslationTextComponent("gui.fogoclient.menu.waypoints"), (button2) -> {
            minecraft.displayGuiScreen(new NavigationWaypointsScreen());
        }));
    }

    @Override
    public void tick() {
        super.tick();

        for (TextFieldWidget targetField : targetFields) {
            targetField.tick();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        drawString(matrixStack, font,new TranslationTextComponent("gui.fogoclient.menu.navigationtarget"), 100, 40, 0xFFFFFF);

        drawString(matrixStack, font,"X:", this.width - 175, 40, 0xFFFFFF);
        drawString(matrixStack, font,"Y:", this.width - 115, 40, 0xFFFFFF);
        drawString(matrixStack, font,"Z:", this.width - 55, 40, 0xFFFFFF);

        for (TextFieldWidget targetField : targetFields) {
            targetField.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    public static Predicate<String> intTest() {
        return s -> StringUtils.isEmpty(s) || s.matches("^-?\\d*$|^~$");
    }
}
