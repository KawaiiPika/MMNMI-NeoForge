package xyz.pixelatedw.mineminenomi.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.networking.packets.CCreateCrewPacket;

public class CreateCrewScreen extends Screen {
    private static final Style ERROR_STYLE = Style.EMPTY.withColor(ChatFormatting.RED);
    private Player player;
    private EditBox nameEdit;
    private Component errorMessage = Component.empty();
    private int errorMessageVisibleTicks;

    public CreateCrewScreen() {
        super(Component.empty());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int posX = this.width / 2;
        int posY = this.height / 2;
        this.nameEdit.render(graphics, mouseX, mouseY, partialTicks);
        if (this.errorMessageVisibleTicks > 0) {
            int errorMessagePosX = posX - this.font.width(this.errorMessage.getString()) / 2 - 1;
            // Simplified from RendererHelper.drawStringWithBorder
            graphics.drawString(this.font, this.errorMessage, errorMessagePosX, posY - 35, 0xFF0000, true);
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        this.player = this.minecraft.player;
        int posX = this.width / 2;
        int posY = this.height / 2;
        this.nameEdit = new EditBox(this.font, posX - 152, posY - 10, 300, 20, Component.empty());
        this.nameEdit.setMaxLength(255);
        this.nameEdit.setValue(this.player.getDisplayName().getString() + "'s Crew");
        this.addWidget(this.nameEdit);
        this.setFocused(this.nameEdit);

        Button createCrewButton = Button.builder(Component.translatable("gui.create"), this::createCrew)
                .bounds(posX - 30, posY + 50, 60, 20)
                .build();
        this.addRenderableWidget(createCrewButton);
    }

    @Override
    public void resize(Minecraft minecraft, int x, int y) {
        String crewName = this.nameEdit.getValue();
        super.resize(minecraft, x, y);
        this.nameEdit.setValue(crewName);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.errorMessageVisibleTicks > 0) {
            --this.errorMessageVisibleTicks;
        }
    }

    private void createCrew(Button btn) {
        net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CCreateCrewPacket(this.nameEdit.getValue()));
    }
}
