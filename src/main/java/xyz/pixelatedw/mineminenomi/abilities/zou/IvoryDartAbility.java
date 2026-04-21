package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class IvoryDartAbility extends Ability {

    private static final int COOLDOWN = 280;
    private static final int CONTINUITY = 20;
    private static final float AREA_SIZE = 4.0F;
    private static final float DAMAGE = 20.0F;

    public IvoryDartAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.ENDER_DRAGON_FLAP,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             AbilityHelper.setDeltaMovement(entity,
                 entity.getLookAngle().x * 2.0,
                 entity.getLookAngle().y * 2.0,
                 entity.getLookAngle().z * 2.0);

             entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(AREA_SIZE)).forEach(target -> {
                 if (target != entity && !target.hurtMarked) {
                     target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                     target.hurtMarked = true;
                 }
             });

             if (duration % 5 == 0) {
                 boolean mobGriefing = entity.level().getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                 net.minecraft.world.level.Level.ExplosionInteraction interaction = mobGriefing ? net.minecraft.world.level.Level.ExplosionInteraction.BLOCK : net.minecraft.world.level.Level.ExplosionInteraction.NONE;
                 entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 2.0f, interaction);
             }

             if (duration >= CONTINUITY) {
                 this.startCooldown(entity, COOLDOWN);
                 this.stop(entity);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ivory_dart");
    }
}
