package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.hud.Hud;
import com.fogo.fogoclient.navigation.Navigation;
import com.fogo.fogoclient.overlay.Overlay;
import com.fogo.fogoclient.xray.Xray;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class ModuleScreen extends Screen {
    private Module module;

    private TextFieldWidget xPosField;

    protected ModuleScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public ModuleScreen(Module module) {
        super(new TranslationTextComponent(String.format("gui.fogoclient.menu.%s", module.name)));
        this.module = module;
    }

    private void addButtons() {
        addModuleButtons();

        this.addButton(new Button(width-42, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.settings"), (button2) -> {

        }));

        switch (module.name) {
            case "hud":
                this.addButton(new Button(300, 0, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                    Overlay.ToggleOverlay();
                }));
                break;
            case "overlay":
                this.addButton(new Button(300, 0, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                    Hud.ToggleHud();
                }));
                break;
            case "xray":
                this.addButton(new Button(300, 0, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                    Xray.ToggleXRay();
                }));
                break;
        }

        if(module.configSpecs != null) {
            for (int i = 0; i < module.configSpecs.length; i++) {
                ForgeConfigSpec.ConfigValue configValue = module.configSpecs[i];
                if(configValue instanceof ForgeConfigSpec.BooleanValue) {
                    this.addButton(new Button(300, 20 + 24 * i, 40, 20, new TranslationTextComponent("gui.fogoclient.toggle"), (button2) -> {
                        configValue.set(!(boolean) configValue.get());
                        configValue.save();
                    }));
                } else if(configValue instanceof ForgeConfigSpec.IntValue) {
                    this.addButton(new Button(300, 20 + 24 * i, 20, 20, new TranslationTextComponent("+"), (button2) -> {
                        configValue.set(((int) configValue.get()) + 1);
                        configValue.save();
                    }));
                    this.addButton(new Button(320, 20 + 24 * i, 20, 20, new TranslationTextComponent("-"), (button2) -> {
                        configValue.set(((int) configValue.get()) - 1);
                        configValue.save();
                    }));
                }
            }
        }
    }

    private void addModuleButtons() {

        for(int i = 0; i < Module.modules.length; i++) {
            Module module = Module.modules[i];
            this.addButton(new Button(1, 10 + 24 * i, 75, 20, new TranslationTextComponent(String.format("gui.fogoclient.menu.%s", module.name)), (button2) -> {
                FogoClient.mc.displayGuiScreen(new ModuleScreen(module));
            }));
        }
    }

    @Override
    protected void init() {
        addButtons();

        this.xPosField = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 40, 20, new TranslationTextComponent("selectWorld.enterName"));
        this.xPosField.setText(Float.toString(Navigation.target.getX()));
        this.xPosField.setResponder((value) -> {
            try{
                Navigation.target.setX(Float.parseFloat(value));
            } catch (Exception e) {
                FogoClient.LOGGER.error(e.getMessage());
            }
            FogoClient.LOGGER.info("X: " + value);
        });
        this.children.add(this.xPosField);
    }

    @Override
    public void tick() {
        xPosField.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        xPosField.render(matrixStack, mouseX, mouseY, partialTicks);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 10, 0xFFFFFF);

        int maxWidth = 0;
        if(module.configSpecs != null) {
            for (int i = 0; i < module.configSpecs.length; i++) {
                ITextComponent text = new TranslationTextComponent(StringUtils.join(module.configSpecs[i].getPath(), '.'));
                maxWidth =  Math.max(maxWidth, this.font.getStringPropertyWidth(text));
                drawString(matrixStack, this.font, text, 100, 20 + 24 * i, 0xFFFFFF);
            }
            for (int i = 0; i < module.configSpecs.length; i++) {
                drawString(matrixStack, this.font, new TranslationTextComponent(module.configSpecs[i].get().toString()), 110 + maxWidth, 20 + 24 * i, 0xFFFFFF);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
