package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.TeleportComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SparkStepAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/spark_step.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/spark_step.png");
   private static final int COOLDOWN = 40;
   private static final float MAX_TELEPORT_DISTANCE = 100.0F;
   private static final float SAFE_TELEPORT_DISTANCE = 64.0F;
   private static final Color COLOR = WyHelper.hexToRGB("#F0EC7155");
   private static final AbilityDescriptionLine.IDescriptionLine TELEPORT_TOOLTIP = (entity, ability) -> {
      AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_TELEPORT_DISTANCE, 64.0F)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS);
      return statBuilder.build().getStatDescription();
   };
   public static final RegistryObject<AbilityCore<SparkStepAbility>> INSTANCE = ModRegistry.registerAbility("spark_step", "Spark Step", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Teleport the user to their desired location", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SparkStepAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F), TELEPORT_TOOLTIP).setIcon(DEFAULT_ICON).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final TeleportComponent teleportComponent = new TeleportComponent(this);
   private boolean hasFallDamage = true;
   private boolean isCancelled = false;

   public SparkStepAbility(AbilityCore<SparkStepAbility> core) {
      super(core);
      this.setDisplayIcon(DEFAULT_ICON);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.damageTakenComponent, this.teleportComponent});
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         if (AbilityUseConditions.canUseMomentumAbilities(entity).isSuccess()) {
            this.chargeComponent.startCharging(entity, 30.0F);
         }
      } else {
         this.chargeComponent.stopCharging(entity);
      }

   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.hasFallDamage = false;
         this.isCancelled = false;
      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         float maxDistance = Math.max(25.0F, 100.0F - 100.0F * (1.0F - entity.m_21223_() / entity.m_21233_()) * 2.0F);
         BlockHitResult trace = this.teleportComponent.getTeleportHit(entity, maxDistance, false);
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (this.chargeComponent.getChargeTime() % 5.0F == 1.0F) {
               WyHelper.spawnParticleEffectForOwner((ParticleEffect)ModParticleEffects.SPARK_STEP.get(), player, trace.m_82450_().m_7096_(), trace.m_82450_().m_7098_() + (double)0.5F, trace.m_82450_().m_7094_(), (ParticleEffect.Details)null);
            }
         }

         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
         AbilityHelper.slowEntityFall(entity, 2);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GENERIC_YELLOW_LIGHTNING_USE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.isCancelled) {
            float maxDistance = Math.max(25.0F, 100.0F - 100.0F * (1.0F - entity.m_21223_() / entity.m_21233_()) * 2.0F);
            float safeDistance = Math.max(16.0F, 64.0F - 64.0F * (1.0F - entity.m_21223_() / entity.m_21233_()) * 2.0F);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GENERIC_YELLOW_LIGHTNING_USE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            this.teleportComponent.teleport(entity, maxDistance, safeDistance);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GENERIC_YELLOW_LIGHTNING_USE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.LIGHTNING_TELEPORT.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         }

         this.cooldownComponent.startCooldown(entity, 100.0F);
      }
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.hasFallDamage = true;
         return 0.0F;
      } else {
         if (damage >= 5.0F) {
            if (this.chargeComponent.isCharging()) {
               this.isCancelled = true;
               this.chargeComponent.stopCharging(entity);
            } else {
               this.disableComponent.startDisable(entity, 40.0F);
            }
         }

         return damage;
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("hasFallDamage", this.hasFallDamage);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.hasFallDamage = nbt.m_128471_("hasFallDamage");
   }
}
