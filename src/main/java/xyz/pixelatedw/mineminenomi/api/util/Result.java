package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.network.chat.Component;

public class Result {
    private final boolean success;
    private final Component message;

    private Result(boolean success, Component message) {
        this.success = success;
        this.message = message;
    }

    public static Result success() {
        return new Result(true, null);
    }

    public static Result fail(Component message) {
        return new Result(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFail() {
        return !success;
    }

    public Component getMessage() {
        return message;
    }
}
