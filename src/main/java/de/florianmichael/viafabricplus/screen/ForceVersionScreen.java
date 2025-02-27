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
package de.florianmichael.viafabricplus.screen;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.model.ComparableProtocolVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.function.Consumer;

@SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
public class ForceVersionScreen extends Screen {
    private final Screen prevScreen;
    private final Consumer<ComparableProtocolVersion> selectionConsumer;

    public ForceVersionScreen(final Screen prevScreen, final Consumer<ComparableProtocolVersion> selectionConsumer) {
        super(Text.literal("Force version"));

        this.prevScreen = prevScreen;
        this.selectionConsumer = selectionConsumer;
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(new SlotList(this.client, width, height, 3 + 3 /* start offset */ + (textRenderer.fontHeight + 2) * 3 /* title is 2 */, height + 5, textRenderer.fontHeight + 4));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        matrices.push();
        matrices.scale(2F, 2F, 2F);
        drawCenteredTextWithShadow(matrices, textRenderer, "ViaFabricPlus", width / 4, 3, Color.ORANGE.getRGB());
        matrices.pop();
        drawCenteredTextWithShadow(matrices, textRenderer, Text.translatable("forceversion.viafabricplus.title"), width / 2, (textRenderer.fontHeight + 2) * 2 + 3, -1);
    }

    @Override
    public void close() {
        client.setScreen(prevScreen);
    }

    public class SlotList extends AlwaysSelectedEntryListWidget<DummyProtocolSlot> {

        public SlotList(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int entryHeight) {
            super(minecraftClient, width, height, top, bottom, entryHeight);

            this.addEntry(new ResetProtocolSlot());
            ViaLoadingBase.getProtocols().stream().map(ViaProtocolSlot::new).forEach(this::addEntry);
        }
    }


    public abstract static class DummyProtocolSlot extends AlwaysSelectedEntryListWidget.Entry<DummyProtocolSlot> {
    }

    public class ResetProtocolSlot extends DummyProtocolSlot {

        @Override
        public Text getNarration() {
            return Text.translatable("words.viafabricplus.cancelreset");
        }


        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            ForceVersionScreen.this.selectionConsumer.accept(null);
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            ForceVersionScreen.this.close();
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            drawCenteredTextWithShadow(matrices, textRenderer, ((MutableText) getNarration()).formatted(Formatting.GOLD), x + entryWidth / 2, y + entryHeight / 2 - textRenderer.fontHeight / 2, -1);
        }
    }

    public class ViaProtocolSlot extends DummyProtocolSlot {
        private final ProtocolVersion protocolVersion;

        public ViaProtocolSlot(final ProtocolVersion protocolVersion) {
            this.protocolVersion = protocolVersion;
        }

        @Override
        public Text getNarration() {
            return Text.literal(this.protocolVersion.getName());
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            ForceVersionScreen.this.selectionConsumer.accept(ViaLoadingBase.fromProtocolVersion(protocolVersion));
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            ForceVersionScreen.this.close();
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            drawCenteredTextWithShadow(matrices, textRenderer, this.protocolVersion.getName(), x + entryWidth / 2, y - 1 + entryHeight / 2 - textRenderer.fontHeight / 2, -1);
        }
    }
}
