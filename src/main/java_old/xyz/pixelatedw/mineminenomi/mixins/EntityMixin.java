package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Iterator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin({Entity.class})
public class EntityMixin {
   @Inject(
      method = {"positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V"},
      at = {@At("TAIL")},
      cancellable = true
   )
   public void mineminenomi$positionRider(Entity passanger, Entity.MoveFunction moveFunc, CallbackInfo ci) {
      Entity entity = (Entity)this;
      if (entity instanceof LivingEntity living) {
         IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(living).orElse((Object)null);
         Iterator var7 = props.getActiveMorphs().iterator();
         if (var7.hasNext()) {
            MorphInfo morph = (MorphInfo)var7.next();
            morph.positionRider(living, passanger);
            return;
         }
      }

   }

   @Inject(
      method = {"spawnAtLocation(Lnet/minecraft/world/level/ItemLike;I)Lnet/minecraft/world/entity/item/ItemEntity;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$spawnAtLocation(ItemLike provider, int offset, CallbackInfoReturnable<ItemEntity> cir) {
      Entity entity = (Entity)this;
      ItemEntity item = KairosekiCoatingHandler.dropCoatedItem(entity, new ItemStack(provider));
      if (item != null) {
         cir.setReturnValue(item);
      }
   }

   @Inject(
      method = {"distanceToSqr(Lnet/minecraft/world/entity/Entity;)D"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void mineminenomi$distanceToSqr(Entity target, CallbackInfoReturnable<Double> cir) {
      Entity entity = (Entity)this;
      double closest = (Double)cir.getReturnValue();
      if (target.isMultipartEntity()) {
         for(PartEntity<?> part : target.getParts()) {
            double distance = entity.m_20280_(part);
            if (distance < closest) {
               cir.setReturnValue(distance);
            }
         }
      }

   }

   @Inject(
      method = {"canSpawnSprintParticle"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$canSpawnSprintParticle(CallbackInfoReturnable<Boolean> ci) {
      Entity entity = (Entity)this;
      if (entity instanceof LivingEntity) {
         for(MobEffectInstance inst : ((LivingEntity)entity).m_21220_()) {
            if (inst.m_19544_() instanceof IVanishEffect && ((IVanishEffect)inst.m_19544_()).isVanished((LivingEntity)entity, inst.m_19557_(), inst.m_19564_()) && ((IVanishEffect)inst.m_19544_()).disableParticles()) {
               ci.setReturnValue(false);
               return;
            }
         }
      }

   }

   @Inject(
      method = {"isInvisible"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$isInvisible(CallbackInfoReturnable<Boolean> ci) {
      Entity entity = (Entity)this;
      if (entity instanceof LivingEntity) {
         for(MobEffectInstance inst : ((LivingEntity)entity).m_21220_()) {
            if (inst.m_19544_() instanceof IVanishEffect && ((IVanishEffect)inst.m_19544_()).isVanished((LivingEntity)entity, inst.m_19557_(), inst.m_19564_())) {
               ci.setReturnValue(true);
               return;
            }
         }
      }

   }

   @Inject(
      method = {"isSteppingCarefully"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$isSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
      Entity entity = (Entity)this;
      if (entity instanceof LivingEntity living) {
         if (living.m_21023_((MobEffect)ModEffects.SILENT.get())) {
            cir.setReturnValue(true);
         }
      }

   }
}
