package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SimpleMessageScreenDTO {

    private Component message;
    private int timeVisible;

    public void setMessage(Component message) {
        this.message = message;
    }

    public Component getMessage() {
        return this.message;
    }

    public void setTimeVisible(int timeVisible) {
        this.timeVisible = timeVisible;
    }

    public int getTimeVisible() {
        return this.timeVisible;
    }

    public void sendEvent(ServerPlayer player) {
        player.displayClientMessage(this.message != null ? this.message : Component.empty(), false);
    }
}
