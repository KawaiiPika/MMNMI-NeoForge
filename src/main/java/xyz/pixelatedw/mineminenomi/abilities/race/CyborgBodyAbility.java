package xyz.pixelatedw.mineminenomi.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class CyborgBodyAbility extends Ability {

    private static final ResourceLocation ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor");

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }

        if (entity.getAttribute(Attributes.ARMOR) != null && entity.getAttribute(Attributes.ARMOR).getModifier(ARMOR_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, 4.0, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.cyborg_body");
    }

    @Override
    protected void startUsing(LivingEntity entity) {}
}
