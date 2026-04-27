package xyz.pixelatedw.mineminenomi.abilities.kachi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class VulcanizationAbility extends Ability {

    private static final ResourceLocation VULCAN_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "vulcanization_damage");

    public VulcanizationAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kachi_kachi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var inst = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (inst != null && !inst.hasModifier(VULCAN_MODIFIER_ID)) {
                inst.addTransientModifier(new AttributeModifier(VULCAN_MODIFIER_ID, 4.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var inst = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (inst != null) {
                inst.removeModifier(VULCAN_MODIFIER_ID);
            }
        }
        this.startCooldown(entity, 300);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.vulcanization");
    }
}
