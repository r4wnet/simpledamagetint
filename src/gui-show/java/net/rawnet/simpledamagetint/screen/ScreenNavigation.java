package net.rawnet.simpledamagetint.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

final class ScreenNavigation {
    private ScreenNavigation() {
    }

    static void open(Minecraft minecraft, Screen screen) {
        minecraft.setScreenAndShow(screen);
    }
}
