package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.goro.KariParticleEffect;

public class KariAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/kari.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/kari.png");
   private static final int CHARGE_TIME = 60;
   private static final int MIN_COOLDOWN = 60;
   private static final int MAX_COOLDOWN = 240;
   private static final int EXPLOSION_SIZE = 4;
   private static final int EXPLOSION_DAMAGE = 10;
   private static final int RANGE = 4;
   public static final RegistryObject<AbilityCore<KariAbility>> INSTANCE = ModRegistry.registerAbility("kari", "Kari", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user heats the air around them with lightning until it explodes with a thunder clap.", (Object)null), ImmutablePair.of("Can be used to avoid and neutralize projectiles.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KariAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 240.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(4.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.FIRE).setIcon(DEFAULT_ICON).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private static final KariParticleEffect.Details DETAILS = new KariParticleEffect.Details();
   private final Interval particleInterval = new Interval(2);

   public KariAbility(AbilityCore<KariAbility> core) {
      super(core);
      this.setDisplayIcon(DEFAULT_ICON);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         this.particleInterval.restartIntervalToZero();
         this.chargeComponent.startCharging(entity, 60.0F);
      } else {
         this.chargeComponent.stopCharging(entity);
      }

   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
         if (this.particleInterval.canTick()) {
            DETAILS.setRange(2);
            DETAILS.setSize(2.0F);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KARI.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), DETAILS);
         }

         float range = this.rangeComponent.getBonusManager().applyBonus(4.0F);

         for(Entity target : TargetHelper.getEntitiesInArea(entity.m_9236_(), entity, (double)range, (TargetPredicate)null, Entity.class)) {
            if (target instanceof LivingEntity) {
               AbilityHelper.setSecondsOnFireBy(target, 3, entity);
            } else if (target instanceof NuProjectileEntity) {
               NuProjectileEntity abilityProj = (NuProjectileEntity)target;
               IDamageSourceHandler handler = IDamageSourceHandler.getHandler(abilityProj.getDamageSource());
               if (handler.hasType(SourceType.PHYSICAL)) {
                  LivingEntity thrower = abilityProj.getOwner();
                  if (thrower != null && thrower != entity) {
                     AbilityHelper.setSecondsOnFireBy(target, 3, entity);
                  }
               } else {
                  target.m_146870_();
               }
            }
         }

      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 4.0F);
         explosion.setStaticDamage(10.0F);
         explosion.setDestroyBlocks(false);
         explosion.m_46061_();
         entity.m_9236_().m_6263_((Player)null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12089_, SoundSource.WEATHER, 2.0F, 0.5F + entity.m_217043_().m_188501_() * 0.2F);
         float cooldown = Math.max(60.0F, 240.0F * this.chargeComponent.getChargePercentage());
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }
}
