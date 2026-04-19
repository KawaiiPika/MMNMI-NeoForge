package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KarakusagawaraSeikenAbility extends Ability {
   private static final float COOLDOWN = 500.0F;
   private static final float CHARGE_TIME = 40.0F;
   private static final float RANGE = 10.0F;
   private static final float DAMAGE = 20.0F;
   private static final float WATER_DAMAGE_MUL = 2.5F;
   private static final int DORIKI = 7500;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<KarakusagawaraSeikenAbility>> INSTANCE = ModRegistry.registerAbility("karakusagawara_seiken", "Karakusagawara Seiken", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user punches the air, which sends a shockwave through water vapor in the air", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, KarakusagawaraSeikenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(40.0F), FishmanKarateHelper.getWaterBuffedDamageStat(20.0F, 2.5F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.SHOCKWAVE).setSourceType(SourceType.INTERNAL).setNodeFactories(KarakusagawaraSeikenAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(100, this::onTickChargeEvent).addEndEvent(100, this::onEndChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public KarakusagawaraSeikenAbility(AbilityCore<KarakusagawaraSeikenAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 40.0F);
   }

   private void onTickChargeEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KARAKUSAGAWARA_SEIKEN_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   private void onEndChargeEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F);
      DamageSource source = this.dealDamageComponent.getDamageSource(entity);
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.setUnavoidable();

      for(LivingEntity target : targets) {
         float finalDamage = 20.0F * (!target.m_20069_() && !FishmanKarateHelper.isInWater(entity) ? 1.0F : 2.5F);
         if (this.dealDamageComponent.hurtTarget(entity, target, finalDamage, source)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 200, 1));
         }
      }

      this.cooldownComponent.startCooldown(entity, 500.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(8.0F, -10.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.FISHMAN.get()).and(DorikiUnlockCondition.atLeast((double)7500.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)SamehadaShoteiAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
