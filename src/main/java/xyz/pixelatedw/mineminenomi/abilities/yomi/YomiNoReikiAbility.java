package xyz.pixelatedw.mineminenomi.abilities.yomi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class YomiNoReikiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi");
    private static final int COOLDOWN = 100;

    public YomiNoReikiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            net.minecraft.world.phys.AABB aabb = entity.getBoundingBox().inflate(5.0D);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive())) {
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.FROSTBITE, 200, 1));
                target.hurt(entity.level().damageSources().freeze(), 4.0F);
            }

            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.yomi_no_reiki.on"));

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yomi_no_reiki");
    }
}
