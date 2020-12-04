package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.StringUtils;

public class ModuleConfigScreen extends Screen {
    private final Module module;

    public ModuleConfigScreen(Module module) {
        super(new TranslationTextComponent(String.format("gui.fogoclient.config.%s", module.name)));
        this.module = module;
    }

    private void addButtons() {
        // Back button
        this.addButton(new Button(2, 2, 40, 20, new TranslationTextComponent("gui.fogoclient.back"), (button2) -> {
            FogoClient.mc.displayGuiScreen(new ModuleScreen(module));
        }));

        // Config buttons
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

    @Override
    public void onClose() {
        super.onClose();
    }
}
