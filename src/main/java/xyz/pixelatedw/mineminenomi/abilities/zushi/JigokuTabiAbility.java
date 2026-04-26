package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class JigokuTabiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");
    private int force = 4;

    public JigokuTabiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.force = 4;
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(24.0D), e -> e != entity);

             for(LivingEntity target : targets) {
                 AbilityHelper.setDeltaMovement(target, 0.0D, target.getDeltaMovement().y - 4.0D, 0.0D);
                 if (duration % 20 == 0) {
                     target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 25, 5, false, false));
                     target.hurt(entity.damageSources().mobAttack(entity), (float)(this.force * 2));
                     GraviZoneAbility.gravityRing(target, 3, 2, false);

                     BlockPos targetPos = target.blockPosition();
                     for(int dx = -this.force; dx <= this.force; dx++) {
                         for(int dy = -2; dy <= 0; dy++) {
                             for(int dz = -this.force; dz <= this.force; dz++) {
                                 if (dx*dx + dy*dy + dz*dz <= this.force*this.force) {
                                     BlockPos p = targetPos.offset(dx, dy, dz);
                                     if (!entity.level().getBlockState(p).is(net.minecraft.tags.BlockTags.LEAVES) &&
                                         !entity.level().getBlockState(p).isAir()) {
                                         entity.level().setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                                     }
                                 }
                             }
                         }
                     }
                 }
             }

             if (duration % 60 == 0) {
                 ++this.force;
             }

             if (duration > 120) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.jigoku_tabi");
    }
}
