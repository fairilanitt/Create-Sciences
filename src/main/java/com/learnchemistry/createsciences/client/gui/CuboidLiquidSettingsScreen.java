package com.learnchemistry.createsciences.client.gui;

import com.learnchemistry.createsciences.cuboid.CuboidLiquidColorPreset;
import com.learnchemistry.createsciences.cuboid.CuboidLiquidSettings;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.Label;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.gui.widget.SelectionScrollInput;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class CuboidLiquidSettingsScreen extends Screen {
    private static final int PANEL_WIDTH = 226;
    private static final int PANEL_HEIGHT = 132;
    private static final int TITLE_COLOR = 0xF4E7C8;
    private static final int LABEL_COLOR = 0xD6C29A;

    private final CuboidLiquidSettings settings;

    private int left;
    private int top;

    public CuboidLiquidSettingsScreen(CuboidLiquidSettings settings) {
        super(Component.translatable("gui.create_sciences.cuboid_liquid_settings"));
        this.settings = settings;
    }

    @Override
    protected void init() {
        left = (width - PANEL_WIDTH) / 2;
        top = (height - PANEL_HEIGHT) / 2;

        Label selectedPreset = new Label(left + 78, top + 69, Component.empty())
                .colored(TITLE_COLOR)
                .withShadow();

        List<? extends Component> options = Arrays.stream(CuboidLiquidColorPreset.values())
                .map(preset -> Component.translatable(preset.translationKey()))
                .toList();

        ScrollInput presetInput = new SelectionScrollInput(left + 74, top + 63, 118, 18)
                .forOptions(options)
                .titled(Component.translatable("gui.create_sciences.liquid_colour"))
                .writingTo(selectedPreset)
                .calling(settings::selectPreset)
                .setState(settings.selectedPreset().ordinal());

        IconButton done = new IconButton(left + PANEL_WIDTH - 28, top + PANEL_HEIGHT - 28, AllIcons.I_CONFIRM);
        done.green = true;
        done.setToolTip(Component.translatable("gui.create_sciences.done"));
        done.withCallback(this::onClose);

        addRenderableWidget(selectedPreset);
        addRenderableWidget(presetInput);
        addRenderableWidget(done);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        renderPanel(guiGraphics);
    }

    private void renderPanel(GuiGraphics guiGraphics) {
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        guiGraphics.fill(left, top, right, bottom, 0xF0191712);
        guiGraphics.fill(left + 2, top + 2, right - 2, bottom - 2, 0xF02E261C);
        guiGraphics.fill(left + 5, top + 5, right - 5, top + 24, 0xFF5B4327);
        guiGraphics.fill(left + 6, top + 25, right - 6, bottom - 6, 0xF01E1A15);
        guiGraphics.fill(left + 5, top + 24, right - 5, top + 25, 0xFF8B6B3C);

        guiGraphics.drawString(font, title, left + 12, top + 10, TITLE_COLOR, false);
        guiGraphics.drawString(font, Component.translatable("gui.create_sciences.liquid_colour"), left + 74, top + 49, LABEL_COLOR, false);

        renderPreview(guiGraphics, left + 28, top + 50);
    }

    private void renderPreview(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.fill(x - 3, y - 3, x + 39, y + 39, 0xFF0D0B09);
        guiGraphics.fill(x - 2, y - 2, x + 38, y + 38, 0xFF6C5534);
        guiGraphics.fill(x, y, x + 36, y + 36, settings.selectedPreset().previewArgb());
        guiGraphics.fill(x, y, x + 36, y + 2, 0x55FFFFFF);
        guiGraphics.fill(x, y + 34, x + 36, y + 36, 0x66000000);

        guiGraphics.drawString(font, Component.translatable("gui.create_sciences.preview"), x - 2, y + 52, AllGuiTextures.FONT_COLOR, false);
    }
}
