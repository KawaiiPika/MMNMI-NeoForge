package xyz.pixelatedw.mineminenomi.abilities;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TakedownKickAbility extends DropHitAbility {
   private static final UUID COOLDOWN_BONUS_UUID = UUID.fromString("11164ab3-f50d-4f6e-b2a1-3a2ac0c1c895");
   private static final float COOLDOWN = 240.0F;
   private static final float RANGE = 2.5F;
   private static final float DAMAGE = 10.0F;
   public static final RegistryObject<AbilityCore<TakedownKickAbility>> INSTANCE = ModRegistry.registerAbility("takedown_kick", "Takedown Kick", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Jumps high in the sky and kicks down any enemies it reaches temporarily stunning them too.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, TakedownKickAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), DealDamageComponent.getTooltip(10.0F), RangeComponent.getTooltip(2.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setUnlockCheck(TakedownKickAbility::canUnlock).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private boolean isFalling;

   public TakedownKickAbility(AbilityCore<TakedownKickAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::onContinuityEnd);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.isFalling = false;
      Vec3 speed = entity.m_20154_().m_82542_((double)4.0F, (double)0.0F, (double)4.0F);
      if (entity instanceof Mob mob) {
         if (mob.m_5448_() != null && mob.m_5448_().m_6084_()) {
            speed = mob.m_5448_().m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)4.0F, (double)0.0F, (double)4.0F);
         }
      }

      speed = speed.m_82520_((double)0.0F, (double)2.25F, (double)0.0F);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, speed.f_82480_, speed.f_82481_);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (AbilityHelper.getDifferenceToFloor(entity) > (double)5.0F) {
         boolean targetHurt = false;

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 2.5F)) {
            if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 20, 0, false, false));
               AbilityHelper.setDeltaMovement(target, entity.m_20184_().f_82479_, (double)-5.0F, entity.m_20184_().f_82481_);
               targetHurt = true;
            }
         }

         if (targetHurt) {
            if (!this.isFalling) {
               AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)0.0F, (double)0.0F);
               this.isFalling = true;
            }

            this.animationComponent.start(entity, ModAnimations.TAKEDOWN_KICK, 8);
            if (!entity.m_9236_().f_46443_) {
               ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
            }
         }
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      if (entity instanceof Player) {
         this.cooldownComponent.getBonusManager().addBonus(COOLDOWN_BONUS_UUID, "Cooldown Bonus", BonusOperation.ADD, 360.0F);
      }

      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   public void onLanding(LivingEntity entity) {
      super.continuousComponent.stopContinuity(entity);
   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return questProps == null ? false : false;
      } else {
         return false;
      }
   }
}
