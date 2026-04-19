package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Hanpatsu — Repel; user repels themselves off the ground at high speed.
 * Implemented as a powerful dash in the look direction.
 */
public class HanpatsuAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    public HanpatsuAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        Vec3 dash = look.scale(3.0).add(0, 0.5, 0);
        entity.setDeltaMovement(dash);
        entity.hurtMarked = true;
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.hanpatsu"); }
}
