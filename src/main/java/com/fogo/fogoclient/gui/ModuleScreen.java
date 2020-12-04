package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.hud.Hud;
import com.fogo.fogoclient.navigation.Navigation;
import com.fogo.fogoclient.overlay.Overlay;
import com.fogo.fogoclient.xray.Xray;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModuleScreen extends Screen {
    private final Module module;

    public ModuleScreen(Module module) {
        super(new TranslationTextComponent(String.format("gui.fogoclient.menu.%s", module.name)));
        this.module = module;
    }

    private void addButtons() {
        addModuleButtons();

        boolean flag = false;
        // Config button
        if(module.hasConfig) {
            this.addButton(new Button(width - 42, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.config"), (button2) -> {
                FogoClient.mc.displayGuiScreen(new ModuleConfigScreen(module));
            }));
            flag = true;
        }

        // Toggle module button
        if(module.isToggleable) {
            int x = width - 42 * (flag ? 2 : 1);
            switch (module.name) {
                case "hud":
                    this.addButton(new Button(x, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        Overlay.ToggleOverlay();
                    }));
                    break;
                case "overlay":
                    this.addButton(new Button(x, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        Hud.ToggleHud();
                    }));
                    break;
                case "navigation":
                    this.addButton(new Button(x, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        Navigation.ToggleNavigation();
                    }));
                    break;
                case "xray":
                    this.addButton(new Button(x, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        Xray.ToggleXRay();
                    }));
                    break;
                default:
                    this.addButton(new Button(x, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        FogoClient.LOGGER.error("No toggle function!");
                    }));
            }
        }
    }

    private void addModuleButtons() {
        for(int i = 0; i < Module.modules.length; i++) {
            Module module = Module.modules[i];
            switch (module.name) {
                case "navigation":
                    this.addButton(new Button(1, 10 + 24 * i, 75, 20, new TranslationTextComponent(String.format("gui.fogoclient.menu.%s", module.name)), (button2) -> {
                        FogoClient.mc.displayGuiScreen(new NavigationScreen(module));
                    }));
                    break;
                default:
                    this.addButton(new Button(1, 10 + 24 * i, 75, 20, new TranslationTextComponent(String.format("gui.fogoclient.menu.%s", module.name)), (button2) -> {
                        FogoClient.mc.displayGuiScreen(new ModuleScreen(module));
                    }));
                    break;
            }
        }
    }

    @Override
    protected void init() {
        addButtons();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        // Title
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 2, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
