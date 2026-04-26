package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class ShishiSonsonAbility extends Ability {
   private static final float COOLDOWN = 140.0F;
   private static final int CHARGE_TIME = 20;
   private static final float DAMAGE = 10.0F;
   private static final float MAX_TELEPORT_DISTANCE = 10.0F;
   private static final float MAX_YAW_CHANGE = 45.0F;
   private static final int DORIKI = 5000;
   private static final int WEAPON_MASTERY_POINTS = 20;
   public static final RegistryObject<AbilityCore<ShishiSonsonAbility>> INSTANCE = ModRegistry.registerAbility("shishi_sonson", "Shishi Sonson", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Placing a single, sheathed sword upright and listening to the \"breath\" of their opponent, the user rapidly unsheathes, attacks while passing by their foe, and then resheathes their sword.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ShishiSonsonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(10.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setNodeFactories(ShishiSonsonAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private float initialYaw = 0.0F;
   private boolean isFakeOut = false;

   public ShishiSonsonAbility(AbilityCore<ShishiSonsonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.dealDamageComponent, this.rangeComponent, this.animationComponent, this.hitTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         this.initialYaw = entity.m_146908_();
         this.isFakeOut = false;
         this.chargeComponent.startCharging(entity, 20.0F);
      } else {
         this.isFakeOut = true;
         this.chargeComponent.stopCharging(entity);
      }

   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.animationComponent.start(entity, ModAnimations.ITTORYU_CHARGE, 20);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
      if (!entity.m_9236_().f_46443_) {
         float yawDifference = Mth.m_14177_(entity.m_146908_() - this.initialYaw);
         float clampedYaw = this.initialYaw;
         if (yawDifference > 45.0F) {
            clampedYaw = this.initialYaw + 45.0F;
         } else if (yawDifference < -45.0F) {
            clampedYaw = this.initialYaw - 45.0F;
         }

         if (clampedYaw != this.initialYaw && entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            entity.m_146922_(clampedYaw);
            Set<RelativeMovement> ignoreFlags = EnumSet.of(RelativeMovement.X, RelativeMovement.Y, RelativeMovement.Z, RelativeMovement.X_ROT);
            player.f_8906_.m_9780_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_(), ignoreFlags);
         }

      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!this.isFakeOut) {
         ItemStack stack = entity.m_21205_();
         stack.m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
         BlockPos.MutableBlockPos blockPos = WyHelper.rayTraceBlockSafe(entity, 10.0F).m_122032_();
         Vec3 startPos = entity.m_20182_();
         float actualTeleportDistance = 10.0F;

         for(double f = (double)0.0F; f < (double)1.0F; f += 0.13) {
            double x = Mth.m_14139_(f, startPos.m_7096_(), (double)blockPos.m_123341_());
            double y = Mth.m_14139_(f, startPos.m_7098_(), (double)blockPos.m_123342_());
            double z = Mth.m_14139_(f, startPos.m_7094_(), (double)blockPos.m_123343_());
            Vec3 pos = new Vec3(x, y, z);
            List<Projectile> projectiles = WyHelper.<Projectile>getNearbyEntities(pos, entity.m_9236_(), (double)entity.m_20205_(), (double)entity.m_20206_(), (double)entity.m_20205_(), (Predicate)null, Projectile.class);
            if (!projectiles.isEmpty()) {
               projectiles.sort(TargetHelper.closestComparator(startPos));
               actualTeleportDistance = Mth.m_14116_((float)((Projectile)projectiles.get(0)).m_20238_(startPos));
               break;
            }
         }

         blockPos.m_122190_(WyHelper.rayTraceBlockSafe(entity, actualTeleportDistance));
         double floorDifference = AbilityHelper.getDifferenceToFloor(entity);
         if (floorDifference > (double)1.0F && (double)blockPos.m_123342_() > entity.m_20186_()) {
            blockPos.m_142448_((int)entity.m_20186_());
         }

         for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, actualTeleportDistance, (float)entity.m_21133_((Attribute)ForgeMod.ENTITY_REACH.get()))) {
            if (this.hitTrackerComponent.canHit(target)) {
               boolean flag = this.dealDamageComponent.hurtTarget(entity, target, 10.0F);
               if (flag && !entity.m_9236_().f_46443_) {
                  WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
               }
            }
         }

         entity.m_8127_();
         entity.m_20324_((double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_());
         if (!entity.m_9236_().f_46443_) {
            ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
         }

         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 140.0F);
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128350_("initialYaw", this.initialYaw);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.initialYaw = nbt.m_128457_("initialYaw");
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode shishiSonson = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(16.0F, -2.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)5000.0F).and(TrainingPointsUnlockCondition.weaponMastery((double)20.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.weaponMastery(20).andThen(UnlockAbilityAction.unlock(INSTANCE));
      shishiSonson.setUnlockRule(unlockCondition, unlockAction);
      return shishiSonson;
   }
}
