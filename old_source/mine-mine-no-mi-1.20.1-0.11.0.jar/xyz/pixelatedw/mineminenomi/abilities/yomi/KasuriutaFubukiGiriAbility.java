package xyz.pixelatedw.mineminenomi.abilities.yomi;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.TeleportComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class KasuriutaFubukiGiriAbility extends Ability {
   private static final float MAX_TELEPORT_DISTANCE = 30.0F;
   private static final int COOLDOWN = 160;
   private static final int CHARGE_TIME = 20;
   private static final int RANGE = 3;
   private static final int DAMAGE = 20;
   public static final RegistryObject<AbilityCore<KasuriutaFubukiGiriAbility>> INSTANCE = ModRegistry.registerAbility("kasuriuta_fubuki_giri", "Kasuriuta: Fubuki Giri", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A quick slash at the enemy that also freezes them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KasuriutaFubukiGiriAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ChargeComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.ICE).setUnlockCheck(KasuriutaFubukiGiriAbility::canUnlock).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargingEvent).addTickEvent(this::duringChargingEvent).addEndEvent(this::endChargingEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final TeleportComponent teleportComponent = new TeleportComponent(this);

   public KasuriutaFubukiGiriAbility(AbilityCore<KasuriutaFubukiGiriAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.hitTrackerComponent, this.dealDamageComponent, this.rangeComponent, this.animationComponent, this.teleportComponent});
      this.teleportComponent.setStopIfHit(true);
      this.teleportComponent.setBlockYGain(true);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void startChargingEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.animationComponent.start(entity, ModAnimations.ITTORYU_CHARGE, 20);
   }

   private void duringChargingEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
      AbilityHelper.slowEntityFall(entity, 15);
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_21205_().m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
         this.teleportComponent.calculateDestination(entity, 30.0F);
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, this.teleportComponent.getTeleportDistance(), 3.0F);
         boolean teleported = this.teleportComponent.teleport(entity);
         if (teleported) {
            for(LivingEntity target : targets) {
               if (this.hitTrackerComponent.canHit(target)) {
                  boolean flag = this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
                  if (flag) {
                     MobEffectInstance instance = new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), 70, 0);
                     target.m_7292_(instance);
                     WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
                  }
               }
            }

            entity.m_21011_(InteractionHand.MAIN_HAND, true);
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            this.animationComponent.stop(entity);
            this.cooldownComponent.startCooldown(entity, 160.0F);
         }
      }
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYomiPower()).orElse(false);
   }
}
