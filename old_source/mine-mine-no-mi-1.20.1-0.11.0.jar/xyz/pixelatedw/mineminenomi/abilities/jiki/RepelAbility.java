package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkGibsonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class RepelAbility extends Ability {
   private static final int COOLDOWN = 100;
   private static final int HOLD_TIME = 100;
   private static final int RANGE = 20;
   private static final int PUNK_CROSS_RANGE = 3;
   private static final int PUNK_CROSS_DAMAGE = 20;
   public static final RegistryObject<AbilityCore<RepelAbility>> INSTANCE = ModRegistry.registerAbility("repel", "Repel", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Repels all metallic objects or projectiles near the user.", (Object)null), ImmutablePair.of("When %s is active this ability will instead shoot the metalic arm forward like a projectile.", new Object[]{PunkGibsonAbility.INSTANCE}), ImmutablePair.of("Repeling enemies affected by %s into other enemies will deal damage and knock them back.", new Object[]{ModEffects.PUNK_CROSS}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RepelAbility::new)).addDescriptionLine(desc[0]).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(100.0F), RangeComponent.getTooltip(20.0F, RangeComponent.RangeType.AOE)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE).addDescriptionLine(desc[1]).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).addDescriptionLine(AbilityDescriptionLine.NEW_LINE).addDescriptionLine(desc[2]).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, DealDamageComponent.getTooltip(20.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private PunkGibsonAbility punkGibson;
   private HashSet<LivingEntity> punkCrossTargets = new HashSet();

   public RepelAbility(AbilityCore<RepelAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.dealDamageComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.punkGibson = (PunkGibsonAbility)AbilityCapability.get(entity).map((props) -> (PunkGibsonAbility)props.getEquippedAbility((AbilityCore)PunkGibsonAbility.INSTANCE.get())).orElse((Object)null);
      boolean isGibsonActive = this.punkGibson != null && this.punkGibson.isContinuous();
      if (isGibsonActive) {
         this.projectileComponent.shoot(entity, 4.0F, 0.5F);
         this.punkGibson.stopItemDrops();
         this.punkGibson.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.stopContinuity(entity));
      } else {
         this.continuousComponent.triggerContinuity(entity, 100.0F);
      }

   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      for(Entity target : WyHelper.getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)20.0F, (Predicate)null, Entity.class)) {
         boolean flag = false;
         if (target.m_6095_().m_204039_(ModTags.Entities.MAGNETIC)) {
            flag = true;
         }

         if (target instanceof LivingEntity) {
            boolean isCyborg = (Boolean)EntityStatsCapability.get((LivingEntity)target).map((props) -> props.isCyborg()).orElse(false);
            if (this.getIronArmorCoverPercentage((LivingEntity)target) >= 0.5F) {
               flag = true;
            } else if (isCyborg) {
               flag = true;
            } else if (((LivingEntity)target).m_21023_((MobEffect)ModEffects.PUNK_CROSS.get())) {
               flag = true;
               ((IMobEffectInstanceMixin)((LivingEntity)target).m_21124_((MobEffect)ModEffects.PUNK_CROSS.get())).setAmplifier(0);
               this.punkCrossTargets.add((LivingEntity)target);
            }
         }

         if (flag) {
            Vec3 dist = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_((double)2.0F);
            AbilityHelper.setDeltaMovement(target, dist.f_82479_, dist.f_82480_, dist.f_82481_);
         }

         if (target instanceof ItemEntity item) {
            if (!item.m_32055_().m_41619_() && item.m_32055_().m_204117_(ModTags.Items.MAGNETIC)) {
               Vec3 vec = item.m_20182_().m_82546_(entity.m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F);
               double speedReduction = (double)8.0F;
               double speed = (double)2.0F;
               double xSpeed = Math.min(speed, -vec.f_82479_ / speedReduction);
               double zSpeed = Math.min(speed, -vec.f_82481_ / speedReduction);
               AbilityHelper.setDeltaMovement(item, -xSpeed, 0.1, -zSpeed);
            }
         }
      }

      for(LivingEntity target : this.punkCrossTargets) {
         if (target.m_6084_() && !target.m_20096_()) {
            List<LivingEntity> nearby = this.rangeComponent.getTargetsInArea(entity, 3.0F);
            nearby.remove(target);

            for(LivingEntity target2 : nearby) {
               if (this.dealDamageComponent.hurtTarget(entity, target2, 20.0F)) {
                  Vec3 speed = entity.m_20154_().m_82541_();
                  AbilityHelper.setDeltaMovement(target2, speed.f_82479_, (double)0.5F, speed.f_82481_);
               }
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private PunkGibsonProjectile createProjectile(LivingEntity entity) {
      if (this.punkGibson != null) {
         PunkGibsonProjectile proj = new PunkGibsonProjectile(entity.m_9236_(), entity, this.punkGibson.getMagneticItems(), this);
         return proj;
      } else {
         PunkGibsonProjectile proj = new PunkGibsonProjectile(entity.m_9236_(), entity, new ArrayList(), this);
         return proj;
      }
   }

   public float getIronArmorCoverPercentage(LivingEntity target) {
      Iterable<ItemStack> iterable = target.m_6168_();
      int i = 0;
      int j = 0;

      for(ItemStack itemstack : iterable) {
         if (!itemstack.m_41619_() && itemstack.m_204117_(ModTags.Items.MAGNETIC)) {
            ++j;
         }

         ++i;
      }

      return i > 0 ? (float)j / (float)i : 0.0F;
   }
}
