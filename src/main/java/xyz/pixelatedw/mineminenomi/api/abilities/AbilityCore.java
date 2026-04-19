package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;

public abstract class AbilityCore<T> {
    public Component getLocalizedName() {
        return Component.literal("Stub Ability");
    }

    public boolean isHidden() {
        return false;
    }
}
