package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
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
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class ShiShishiSonsonAbility extends Ability {
   private static final float COOLDOWN = 180.0F;
   private static final int CHARGE_TIME = 20;
   private static final float DAMAGE = 30.0F;
   private static final float MAX_TELEPORT_DISTANCE = 30.0F;
   private static final float MAX_YAW_CHANGE = 90.0F;
   private static final float MAX_PITCH_CHANGE = 12.0F;
   private static final int DORIKI = 5000;
   private static final int WEAPON_MASTERY_POINTS = 20;
   public static final RegistryObject<AbilityCore<ShiShishiSonsonAbility>> INSTANCE = ModRegistry.registerAbility("shi_shishi_sonson", "Shi Shishi Sonson", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A stronger version of the original Shishi Sonson, where the user swings their sword in a very quick and powerful unsheathing move to slice their opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ShiShishiSonsonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(30.0F), RangeComponent.getTooltip(30.0F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setNodeFactories(ShiShishiSonsonAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final TeleportComponent teleportComponent = new TeleportComponent(this);
   private float initialYaw = 0.0F;

   public ShiShishiSonsonAbility(AbilityCore<ShiShishiSonsonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.dealDamageComponent, this.rangeComponent, this.animationComponent, this.hitTrackerComponent, this.teleportComponent});
      this.cooldownComponent.addStartEvent(100, this::startCooldownEvent);
      this.teleportComponent.setStopIfHit(true);
      this.teleportComponent.setBlockYGain(true);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         this.initialYaw = entity.m_146908_();
         this.chargeComponent.startCharging(entity, 20.0F);
      } else {
         this.chargeComponent.stopCharging(entity);
      }

   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.animationComponent.start(entity, ModAnimations.ITTORYU_CHARGE, 20);
      if (entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(SPinCameraPacket.pinClampedYawAndPitch(entity.m_146908_(), 90.0F, entity.m_146909_(), 12.0F), player);
      }

   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_21205_().m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      this.teleportComponent.calculateDestination(entity, 30.0F, 30.0F);
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, this.teleportComponent.getTeleportDistance(), (float)entity.m_21133_((Attribute)ForgeMod.ENTITY_REACH.get()));
      boolean teleported = this.teleportComponent.teleport(entity);
      if (!teleported) {
         this.cooldownComponent.startCooldown(entity, 180.0F);
      } else {
         for(LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target)) {
               boolean flag = this.dealDamageComponent.hurtTarget(entity, target, 30.0F);
               if (flag && !entity.m_9236_().f_46443_) {
                  WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
               }
            }
         }

         entity.m_21011_(InteractionHand.MAIN_HAND, true);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         this.cooldownComponent.startCooldown(entity, 180.0F);
      }
   }

   public void startCooldownEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(new SUnpinCameraPacket(), player);
      }

      this.animationComponent.stop(entity);
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128350_("initialYaw", this.initialYaw);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.initialYaw = nbt.m_128457_("initialYaw");
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode shiShishiSonson = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-5.0F, 1.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      shiShishiSonson.setUnlockRule(unlockCondition, unlockAction);
      return shiShishiSonson;
   }
}
