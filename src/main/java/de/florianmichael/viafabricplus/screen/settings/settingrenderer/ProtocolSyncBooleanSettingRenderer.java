/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viafabricplus.screen.settings.settingrenderer;

import de.florianmichael.viafabricplus.screen.base.MappedSlotEntry;
import de.florianmichael.viafabricplus.settings.groups.GeneralSettings;
import de.florianmichael.viafabricplus.settings.type_impl.ProtocolSyncBooleanSetting;
import de.florianmichael.viafabricplus.util.ScreenUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;

public class ProtocolSyncBooleanSettingRenderer extends MappedSlotEntry {
    private final ProtocolSyncBooleanSetting value;

    public ProtocolSyncBooleanSettingRenderer(ProtocolSyncBooleanSetting value) {
        this.value = value;
    }

    @Override
    public Text getNarration() {
        return this.value.getName();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.value.setValue(!this.value.getValue());
        ScreenUtil.playClickSound();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mappedRenderer(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        final Text text = this.value.getValue() ? Text.translatable("words.viafabricplus.on") : Text.translatable("words.viafabricplus.off");
        Color color = this.value.getValue() ? Color.GREEN : Color.RED;

        final int length = textRenderer.drawWithShadow(matrices, this.value.getName().formatted(Formatting.GRAY), 3, entryHeight / 2F - textRenderer.fontHeight / 2F, -1);

        textRenderer.drawWithShadow(matrices, "(" + this.value.getProtocolRange().toString() + ")", length + 2, entryHeight / 2F - textRenderer.fontHeight / 2F, -1);
        if (GeneralSettings.INSTANCE.automaticallyChangeValuesBasedOnTheCurrentVersion.getValue()) color = color.darker().darker();
        textRenderer.drawWithShadow(matrices, text, entryWidth - textRenderer.getWidth(text) - 3 - 3, entryHeight / 2F - textRenderer.fontHeight / 2F, color.getRGB());
    }
}
