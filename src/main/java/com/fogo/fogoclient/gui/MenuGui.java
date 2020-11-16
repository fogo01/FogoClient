package com.fogo.fogoclient.gui;

import com.fogo.fogoclient.FogoClient;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MenuGui extends Screen {

    protected MenuGui(ITextComponent titleIn) {
        super(titleIn);
    }

    public MenuGui() {
        super(new TranslationTextComponent("gui.fogoclient.menu"));
    }

    private void addButtons() {
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), (button2) -> {
            this.minecraft.displayGuiScreen(null);
            this.minecraft.mouseHelper.grabMouse();
        }));
    }

    @Override
    protected void init() {
        addButtons();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
