package com.fogo.fogoclient.handler;

import com.fogo.fogoclient.FogoClient;
import com.fogo.fogoclient.gui.MenuGui;
import com.fogo.fogoclient.gui.Module;
import com.fogo.fogoclient.gui.ModuleScreen;
import com.fogo.fogoclient.settings.Keybindings;
import com.fogo.fogoclient.hud.Hud;
import com.fogo.fogoclient.reference.Key;
import com.fogo.fogoclient.xray.Xray;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyInputEventHandler {
    private static Key getPressedKeybinding() {
        if (Keybindings.menu.isPressed()) {
            return Key.MENU;
        }

        if (Keybindings.openHud.isPressed()) {
            return Key.OPEN_HUD;
        }

        if (Keybindings.toggleXray.isPressed()) {
            return Key.TOGGLE_XRAY;
        }

        return Key.UNKNOWN;
    }

    @SubscribeEvent
    public static void handleKeyInputEvent(final InputEvent.KeyInputEvent event) {
        Key key = getPressedKeybinding();
        //FogoClient.LOGGER.info(key);
        //FogoClient.LOGGER.info(event.getKey());

        if (key == Key.MENU) {
            if(!(FogoClient.mc.currentScreen instanceof MenuGui)) {
                FogoClient.mc.displayGuiScreen(new ModuleScreen(Module.modules[0]));
            } else {
                FogoClient.mc.displayGuiScreen(null);
                FogoClient.mc.mouseHelper.grabMouse();
            }
        }
        if (key == Key.OPEN_HUD) {
            Hud.ToggleHud();
        }
        if (key == Key.TOGGLE_XRAY) {
            Xray.ToggleXRay();
        }
    }
}
