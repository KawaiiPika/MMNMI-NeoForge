package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.brawler.KingPunchProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KingPunchAbility extends Ability {
   private static final int CHARGE_TIME = 1000;
   private static final float MIN_COOLDOWN = 400.0F;
   private static final float MAX_COOLDOWN = 1400.0F;
   private static final float MIN_DAMAGE = 33.333332F;
   private static final float MAX_DAMAGE = 83.333336F;
   public static final RegistryObject<AbilityCore<KingPunchAbility>> INSTANCE = ModRegistry.registerAbility("king_punch", "King Punch", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A punch of exceptionally concentrated strength, capable of releasing an immense amount of air pressure, but needs a long time to fully charge", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KingPunchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F, 1400.0F), ChargeComponent.getTooltip(1000.0F), DealDamageComponent.getTooltip(33.333332F, 83.333336F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setNodeFactories(KingPunchAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> comp.getChargePercentage() > 0.2F)).addStartEvent(100, this::startChargingEvent).addTickEvent(100, this::duringChargingEvent).addEndEvent(100, this::endChargingEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private boolean cancelled = false;

   public KingPunchAbility(AbilityCore<KingPunchAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.dealDamageComponent, this.projectileComponent, this.explosionComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 1000.0F);
   }

   private void startChargingEvent(LivingEntity entity, IAbility ability) {
      this.cancelled = false;
   }

   private void duringChargingEvent(LivingEntity entity, IAbility ability) {
      if (entity.f_19802_ > 0) {
         this.cancelled = true;
         this.chargeComponent.stopCharging(entity);
      } else {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
         if (this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KING_PUNCH_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

      }
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      if (!this.cancelled) {
         if (!entity.m_9236_().f_46443_) {
            ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
         }

         this.projectileComponent.shoot(entity, 2.0F, 1.0F);
         entity.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
         float chargeTime = this.chargeComponent.getChargeTime();
         this.cooldownComponent.startCooldown(entity, chargeTime + 400.0F);
      }
   }

   private KingPunchProjectile createProjectile(LivingEntity entity) {
      KingPunchProjectile proj = new KingPunchProjectile(entity.m_9236_(), entity, this);
      float chargeTime = this.chargeComponent.getChargeTime();
      proj.setDamage(chargeTime / 12.0F);
      proj.addBlockHitEvent(100, (pos) -> {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(proj, entity, proj.m_20185_(), proj.m_20186_(), proj.m_20189_(), 1.0F + chargeTime / 70.0F);
         explosion.setStaticDamage(0.0F);
         explosion.setExplosionSound(false);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-5.0F, 8.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)GenkotsuMeteorAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)SpinningBrawlAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)JishinHoAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
