package net.rawnet.simpledamagetint.screen;

import java.util.Locale;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.config.ArmorDamageRenderSelection;
import net.rawnet.simpledamagetint.config.ModConfig;
import net.rawnet.simpledamagetint.event.OverlayReloadListener;

public class ConfigScreen extends ConfigScreenBase {
    private static final String KEY = "options.simpledamagetint";
    private static final int PANEL_WIDTH = 240;
    private static final int ROW_HEIGHT = 20;
    private static final int GAP = 4;
    private static final int SWATCH_WIDTH = 76;
    private static final int SWATCH_HEIGHT = 30;
    private static final int PANEL_BACKGROUND = 0x66000000;
    private static final int BORDER = -9803158;
    private static final int CHECKER_LIGHT = -5592406;
    private static final int CHECKER_DARK = -10066330;
    private static final int LABEL = -6250336;
    private static final int TEXT = -2039584;
    private static final int ERROR = -43691;
    private final Screen parent;
    private final ModConfig config;
    private EditBox hexField;
    private Button armorButton;
    private Button
            rainbowButton;
    private final ColorSlider[] sliders = new ColorSlider[ColorChannel.values().length];
    private boolean syncing;
    private int panelLeft;
    private int titleTop;
    private int labelTop;
    private int panelTop;
    private int panelBottom;
    private int swatchTop;

    public ConfigScreen(Screen parent) {
        super(Component.translatable(KEY));
        this.parent = parent;
        this.config = SimpleDamageTint.getConfig();
    }

    protected void init() {
        int y;
        this.panelLeft = (this.width - PANEL_WIDTH) / 2;
        this.titleTop = y = this.height / 2 - 97;
        this.labelTop = y += 22;
        this.panelTop = y += 12;
        this.swatchTop = this.panelTop + 6;
        this.panelBottom = this.panelTop + SWATCH_HEIGHT + 12;
        y = this.panelBottom + 10;
        int prefixWidth = this.font.width("#") + 2;
        this.hexField = new EditBox(this.font, this.panelLeft + SWATCH_WIDTH + 12 + prefixWidth,
                this.swatchTop + 5, 152 - prefixWidth, ROW_HEIGHT, Component.translatable(KEY + ".damageColor")) {
            @Override
            public void insertText(String text) {
                String digits = text.startsWith("#") ? text.substring(1) : text;
                if (digits.chars().allMatch(c -> Character.digit(c, 16) >= 0)) {
                    super.insertText(digits);
                }
            }
        };
        this.hexField.setMaxLength(8);

        this.hexField.setResponder(this::onHexChanged);
        this.addRenderableWidget(this.hexField);
        int sliderWidth = (PANEL_WIDTH - GAP) / 2;
        int slidersTop = y;
        for (ColorChannel channel : ColorChannel.values()) {
            int column = channel.ordinal() % 2;
            int row = channel.ordinal() / 2;
            ColorSlider slider = new ColorSlider(this.panelLeft + column * (sliderWidth + GAP), slidersTop + row * (ROW_HEIGHT + GAP), sliderWidth, channel, channel.extract(this.config.damageColor));
            this.sliders[channel.ordinal()] = slider;
            this.addRenderableWidget(slider);
        }
        this.armorButton = Button.builder(this.armorButtonLabel(), button -> this.setArmorTint(ConfigScreen.next(this.config.damageTintOnArmor))).bounds(this.panelLeft, slidersTop + 48 + 8, PANEL_WIDTH, ROW_HEIGHT).tooltip(Tooltip.create(Component.translatable("options.simpledamagetint.showDamageTintOnArmor.tooltip"))).build();
        this.addRenderableWidget(this.armorButton);
        this.rainbowButton = Button.builder(rainbowLabel(), button -> setRainbow(!config.rainbowTint))
                .bounds(panelLeft, slidersTop + 80, PANEL_WIDTH, ROW_HEIGHT).build();
        this.addRenderableWidget(rainbowButton);
        int buttonWidth = sliderWidth;
        int buttonsTop = slidersTop + 104;
        this.addRenderableWidget(Button.builder(Component.translatable("controls.reset"), button -> {
            setRainbow(false);
            this.setColor(ModConfig.DEFAULT_DAMAGE_COLOR);
            this.setArmorTint(ModConfig.DEFAULT_DAMAGE_TINT_ON_ARMOR);
        }).bounds(this.panelLeft, buttonsTop, buttonWidth, ROW_HEIGHT).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).bounds(this.panelLeft + buttonWidth + GAP, buttonsTop, buttonWidth, ROW_HEIGHT).build());
        this.syncHexField();
    }

    @Override
    protected void drawConfig(ConfigGraphics graphics) {
        graphics.centered(this.title, this.width / 2, this.titleTop, -1);
        graphics.label(Component.translatable(KEY + ".damageColor"), this.panelLeft, this.labelTop, LABEL);
        int left = this.panelLeft - 6;
        int right = this.panelLeft + PANEL_WIDTH + 6;
        graphics.fill(left, this.panelTop, right, this.panelBottom, PANEL_BACKGROUND);
        graphics.fill(left, this.panelTop, right, this.panelTop + 1, BORDER);
        graphics.fill(left, this.panelBottom - 1, right, this.panelBottom, BORDER);
        graphics.fill(left, this.panelTop, left + 1, this.panelBottom, BORDER);
        graphics.fill(right - 1, this.panelTop, right, this.panelBottom, BORDER);
        graphics.label(Component.literal("#"), this.panelLeft + SWATCH_WIDTH + 12, this.swatchTop + 11, TEXT);
        this.renderSwatch(graphics, this.panelLeft, this.swatchTop, SWATCH_WIDTH, SWATCH_HEIGHT);
    }

    private void renderSwatch(ConfigGraphics graphics, int x, int y, int width, int height) {
        for (int cy = 0; cy < height; cy += 5) {
            for (int cx = 0; cx < width; cx += 5) {
                boolean light = (cx / 5 + cy / 5) % 2 == 0;
                graphics.fill(x + cx, y + cy, Math.min(x + cx + 5, x + width), Math.min(y + cy + 5, y + height), light ? CHECKER_LIGHT : CHECKER_DARK);
            }
        }
        graphics.fill(x, y, x + width, y + height, this.config.currentTintColor());
        graphics.fill(x - 1, y - 1, x + width + 1, y, BORDER);
        graphics.fill(x - 1, y + height, x + width + 1, y + height + 1, BORDER);
        graphics.fill(x - 1, y, x, y + height, BORDER);
        graphics.fill(x + width, y, x + width + 1, y + height, BORDER);
    }

    private void onHexChanged(String value) {
        if (this.syncing) {
            return;
        }
        Integer color = ConfigScreen.parseColor(value);
        this.hexField.setTextColor(color == null ? ERROR : TEXT);
        if (color == null) {
            return;
        }
        this.applyColor(color);
        this.syncing = true;
        for (ColorChannel channel : ColorChannel.values()) {
            this.sliders[channel.ordinal()].setValueSilently(channel.extract(color));
        }
        this.syncing = false;
    }

    private static Integer parseColor(String value) {
        String hex = value.startsWith("#") ? value.substring(1) : value;
        if (hex.length() != 6 && hex.length() != 8) {
            return null;
        }
        try {
            long parsed = Long.parseLong(hex, 16);
            return hex.length() == 8 ? (int)parsed : (int)(parsed | 0xFF000000L);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    private void updateColorFromSliders() {
        if (this.syncing) {
            return;
        }
        int color = 0;
        for (ColorChannel channel : ColorChannel.values()) {
            color |= this.sliders[channel.ordinal()].getIntValue() << channel.shift;
        }
        this.applyColor(color);
        this.syncHexField();
    }

    private void setColor(int color) {
        this.applyColor(color);
        this.syncing = true;
        for (ColorChannel channel : ColorChannel.values()) {
            this.sliders[channel.ordinal()].setValueSilently(channel.extract(color));
        }
        this.syncing = false;
        this.syncHexField();
    }

    private Component rainbowLabel() {
        return Component.translatable(KEY + ".rainbow", config.rainbowTint
                ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
    }

    private void setRainbow(boolean enabled) {
        config.rainbowTint = enabled;
        rainbowButton.setMessage(rainbowLabel());
        OverlayReloadListener.callEvent();
    }

    private Component armorButtonLabel() {
        return Component.translatable("options.simpledamagetint.showDamageTintOnArmor").append(": ").append(this.config.damageTintOnArmor.getDisplayName());
    }

    private static ArmorDamageRenderSelection next(ArmorDamageRenderSelection current) {
        ArmorDamageRenderSelection[] values = ArmorDamageRenderSelection.values();
        return values[(current.ordinal() + 1) % values.length];
    }

    private void setArmorTint(ArmorDamageRenderSelection value) {
        this.config.damageTintOnArmor = value;
        this.armorButton.setMessage(this.armorButtonLabel());
    }

    private void applyColor(int color) {
        this.config.damageColor = color;
        OverlayReloadListener.callEvent();
    }

    private void syncHexField() {
        this.syncing = true;
        this.hexField.setValue(String.format(Locale.ROOT, "%08x", this.config.damageColor));
        this.hexField.setTextColor(TEXT);
        this.syncing = false;
    }

    public void onClose() {
        this.config.save();
        ScreenNavigation.open(this.minecraft, this.parent);
    }

    private enum ColorChannel {
        RED("options.simpledamagetint.colorpicker.red", 16),
        GREEN("options.simpledamagetint.colorpicker.green", 8),
        BLUE("options.simpledamagetint.colorpicker.blue", 0),
        OPACITY("options.simpledamagetint.colorpicker.opacity", 24);

        private final String translationKey;
        private final int shift;

        ColorChannel(String translationKey, int shift) {
            this.translationKey = translationKey;
            this.shift = shift;
        }

        int extract(int color) {
            return color >> this.shift & 0xFF;
        }
    }

    private class ColorSlider
    extends AbstractSliderButton {
        private final ColorChannel channel;

        ColorSlider(int x, int y, int width, ColorChannel channel, int value) {
            super(x, y, width, ROW_HEIGHT, Component.empty(), (double)value / 255.0);
            this.channel = channel;
            this.updateMessage();
        }

        int getIntValue() {
            return (int)Math.round(this.value * 255.0);
        }

        void setValueSilently(int value) {
            this.value = value / 255.0;
            this.updateMessage();
        }

        protected void updateMessage() {
            this.setMessage(Component.translatable(this.channel.translationKey, this.getIntValue()));
        }

        protected void applyValue() {
            updateColorFromSliders();
        }
    }
}


