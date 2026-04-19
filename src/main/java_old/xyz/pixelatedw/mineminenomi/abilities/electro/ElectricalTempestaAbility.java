package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalTempestaAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.5F;
   private static final float DAMAGE_BONUS = 2.0F;
   private static final float RANGE_BONUS = 2.0F;
   private static final int COOLDOWN = 160;
   private static final int CHARGE_TIME = 10;
   private static final int RANGE = 10;
   private static final int DAMAGE = 20;
   private static final int ELECLAW_STACKS = 1;
   private static final int DORIKI = 3000;
   public static final RegistryObject<AbilityCore<ElectricalTempestaAbility>> INSTANCE = ModRegistry.registerAbility("electrical_tempesta", "Electrical Tempesta", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("The user releases a charge of energy that deals damage to nearby enemies and knocks them back.", (Object)null), null};
      Object[] var10006 = new Object[]{MentionHelper.tryMentionObject(SulongAbility.INSTANCE), MentionHelper.mentionText(Math.round(50.0F) + "%"), null, null};
      float var10009 = Math.abs(-1.0F);
      var10006[2] = MentionHelper.mentionText(Math.round(var10009 * 100.0F) + "%");
      var10009 = Math.abs(-1.0F);
      var10006[3] = MentionHelper.mentionText(Math.round(var10009 * 100.0F) + "%");
      var10002[1] = ImmutablePair.of("While %s is active the cooldown of this ability is reduced by %s, the damage is increased by %s and the range of the ability is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ElectricalTempestaAbility::new)).addDescriptionLine(desc[0]).addAdvancedDescriptionLine(AbilityDescriptionLine.IDescriptionLine.of(desc[1])).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ChargeComponent.getTooltip(10.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ElectricalTempestaAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private LightningDischargeEntity ballEntity = null;

   public ElectricalTempestaAbility(AbilityCore<ElectricalTempestaAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(ElectroHelper.requireEleclaw(1));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 10.0F);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
      if (this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRO_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      if (this.ballEntity == null) {
         LightningDischargeEntity ball = new LightningDischargeEntity(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
         entity.m_9236_().m_7967_(ball);
         this.ballEntity = ball;
      } else {
         float distance = 0.5F;
         Vec3 lookVec = entity.m_20154_();
         double px = entity.m_20185_() + lookVec.f_82479_ * (double)distance;
         double py = entity.m_20186_() + lookVec.f_82479_ * (double)distance;
         double pz = entity.m_20189_() + lookVec.f_82481_ * (double)distance;
         Vec3 pos = new Vec3(px, py, pz);
         float percentage = 1.0F - this.chargeComponent.getChargeTime() / this.chargeComponent.getMaxChargeTime();
         this.ballEntity.setSize(percentage * 0.1F);
         this.ballEntity.setLightningLength(2.0F);
         this.ballEntity.m_7678_(pos.m_7096_(), pos.m_7098_(), pos.m_7094_(), entity.m_146908_(), entity.m_146909_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         boolean hasSulongActive = ElectroHelper.hasSulongActive(entity);
         this.dealDamageComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
         this.rangeComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_RANGE_BONUS);
         this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
         if (hasSulongActive) {
            this.dealDamageComponent.getBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS, "Sulong Damage Bonus", BonusOperation.MUL, 2.0F);
            this.rangeComponent.getBonusManager().addBonus(ElectroHelper.SULONG_RANGE_BONUS, "Sulong Range Bonus", BonusOperation.MUL, 2.0F);
            this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS, "Sulong Cooldown Bonus", BonusOperation.MUL, 0.5F);
         }

         if (this.ballEntity != null) {
            this.ballEntity.m_146870_();
            this.ballEntity = null;
         }

         for(int i = 0; i < 3; ++i) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRICAL_TEMPESTA_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F);
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
         handler.setUnavoidable();

         for(LivingEntity target : targets) {
            if (this.dealDamageComponent.hurtTarget(entity, target, 20.0F, source)) {
               Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_();
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 10, 0, false, false, true));
               AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_ * (double)4.0F, (double)1.0F, -dirVec.f_82481_ * (double)4.0F);
            }
         }

         float range = this.rangeComponent.getRange();
         int amount = 32;

         for(int j = 0; j < amount; ++j) {
            float boltSize = (float)WyHelper.randomWithRange(3, (int)range);
            ElectroVisualProjectile bolt = new ElectroVisualProjectile(entity.m_9236_(), entity, this, boltSize);
            bolt.m_20219_(bolt.m_20182_().m_82520_((double)0.0F, (double)(-entity.m_20206_() / 3.0F), (double)0.0F));
            entity.m_9236_().m_7967_(bolt);
         }

         EleclawAbility eleclaw = (EleclawAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)EleclawAbility.INSTANCE.get());
         if (eleclaw != null) {
            eleclaw.reduceUsage(entity, 1);
         }

         this.cooldownComponent.startCooldown(entity, 160.0F);
      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(11.0F, 9.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)3000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ElectricalMissileAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
