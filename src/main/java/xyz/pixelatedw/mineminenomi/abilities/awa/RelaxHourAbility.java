package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile;

public class RelaxHourAbility extends Ability {
   private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "awa_awa_no_mi");
   private static final long MAX_DURATION = 100;

   public RelaxHourAbility() {
      super(FRUIT);
   }

   @Override
   protected void startUsing(LivingEntity entity) {
   }

   @Override
   protected void onTick(LivingEntity entity, long duration) {
      if (duration > MAX_DURATION) {
         this.stop(entity);
         return;
      }

      if (!entity.level().isClientSide && duration % 5 == 0) {
         RelaxHourProjectile proj = new RelaxHourProjectile(entity.level(), entity);
         proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.0F, 1.0F);
         entity.level().addFreshEntity(proj);
      }
   }

   @Override
   protected void stopUsing(LivingEntity entity) {
      this.startCooldown(entity, 80);
   }

   @Override
   public Component getDisplayName() {
      return Component.translatable("ability.mineminenomi.relax_hour");
   }
}
