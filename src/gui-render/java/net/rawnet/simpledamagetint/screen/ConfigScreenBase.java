package net.rawnet.simpledamagetint.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

abstract class ConfigScreenBase extends Screen {
    protected ConfigScreenBase(Component title) {
        super(title);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        drawConfig(new ConfigGraphics(graphics));
    }

    protected abstract void drawConfig(ConfigGraphics graphics);

    protected final class ConfigGraphics {
        private final GuiGraphics graphics;

        private ConfigGraphics(GuiGraphics graphics) {
            this.graphics = graphics;
        }

        void centered(Component text, int x, int y, int color) {
            graphics.drawCenteredString(font, text, x, y, color);
        }

        void label(Component text, int x, int y, int color) {
            graphics.drawString(font, text, x, y, color, false);
        }

        void fill(int left, int top, int right, int bottom, int color) {
            graphics.fill(left, top, right, bottom, color);
        }
    }
}
