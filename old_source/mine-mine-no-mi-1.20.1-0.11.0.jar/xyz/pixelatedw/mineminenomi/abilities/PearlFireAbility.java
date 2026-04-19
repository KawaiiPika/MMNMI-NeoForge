package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class PearlFireAbility extends Ability {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 1300;
   public static final RegistryObject<AbilityCore<PearlFireAbility>> INSTANCE = ModRegistry.registerAbility("pearl_fire", "Pearl Fire", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Sets the pearl armor on fire causing any enemy that hits you to take damage and any enemy you hit to be set on fire.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.EQUIPMENT, PearlFireAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 1300.0F), ContinuousComponent.getTooltip(1200.0F)).setUnlockCheck(PearlFireAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(this::damageTakenEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addOnHitEvent(this::onHit).addTryHitEvent(this::tryHitEvent);

   public PearlFireAbility(AbilityCore<PearlFireAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent, this.dealDamageComponent, this.hitTriggerComponent});
      this.addCanUseCheck(this::canUse);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double x = WyHelper.randomDouble();
            double y = WyHelper.randomDouble() / (double)3.0F;
            double z = WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(35);
            data.setSize(1.0F + entity.m_217043_().m_188501_());
            data.setMotion(x / (double)200.0F, 0.02, z / (double)200.0F);
            data.setColor(1.0F, 1.0F, 1.0F, 0.4F);
            WyHelper.spawnParticles(data, (ServerLevel)entity.m_9236_(), entity.m_20185_() + x, entity.m_20186_() + y + (double)0.5F, entity.m_20189_() + z);
            WyHelper.spawnParticles(data, (ServerLevel)entity.m_9236_(), entity.m_20185_() + x, entity.m_20186_() + y + (double)1.5F, entity.m_20189_() + z);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F + this.continuousComponent.getContinueTime());
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return !this.continuousComponent.isContinuous() ? HitTriggerComponent.HitResult.PASS : HitTriggerComponent.HitResult.HIT;
   }

   public boolean onHit(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      AbilityHelper.setSecondsOnFireBy(target, 2, entity);
      return true;
   }

   private float damageTakenEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      if (this.continuousComponent.isContinuous() && damageSource.m_7640_() != null && damageSource.m_7640_() instanceof LivingEntity) {
         this.dealDamageComponent.hurtTarget(user, (LivingEntity)damageSource.m_7640_(), damage / 3.0F);
         return damage - damage / 3.0F;
      } else {
         return damage;
      }
   }

   private Result canUse(LivingEntity entity, IAbility ability) {
      return hasArmorSetEquipped(entity) ? Result.success() : Result.fail((Component)null);
   }

   private static boolean canUnlock(LivingEntity entity) {
      return hasArmorSetEquipped(entity);
   }

   private static boolean hasArmorSetEquipped(LivingEntity entity) {
      ItemStack headStack = entity.m_6844_(EquipmentSlot.HEAD);
      if (!headStack.m_41619_() && headStack.m_41720_() == ModArmors.PEARL_HAT.get()) {
         ItemStack chestStack = entity.m_6844_(EquipmentSlot.CHEST);
         if (!chestStack.m_41619_() && chestStack.m_41720_() == ModArmors.PEARL_ARMOR.get()) {
            ItemStack legsStack = entity.m_6844_(EquipmentSlot.LEGS);
            return !legsStack.m_41619_() && legsStack.m_41720_() == ModArmors.PEARL_LEGS.get();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
