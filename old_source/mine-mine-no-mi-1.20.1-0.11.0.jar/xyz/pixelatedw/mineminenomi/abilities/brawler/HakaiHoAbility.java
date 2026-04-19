package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HakaiHoAbility extends PunchAbility {
   private static final float COOLDOWN = 240.0F;
   private static final int RANGE = 2;
   private static final int DAMAGE = 20;
   public static final RegistryObject<AbilityCore<HakaiHoAbility>> INSTANCE = ModRegistry.registerAbility("hakai_ho", "Hakai Ho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user punches with enough force to create a small explosion damaging all nearby enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HakaiHoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip(), RangeComponent.getTooltip(2.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(HakaiHoAbility::createNode).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public HakaiHoAbility(AbilityCore<HakaiHoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.dealDamageComponent});
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(target, 2.0F);
      targets.remove(entity);
      targets.remove(target);
      Vec3 dir = entity.m_20154_().m_82542_((double)5.0F, (double)0.0F, (double)5.0F);

      for(LivingEntity aoeTarget : targets) {
         if (this.dealDamageComponent.hurtTarget(entity, aoeTarget, 10.0F)) {
            aoeTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 60, 0, false, false));
            AbilityHelper.setDeltaMovement(aoeTarget, aoeTarget.m_20184_().m_82520_(dir.f_82479_, 0.2, dir.f_82481_));
         }
      }

      AbilityExplosion explosion = new AbilityExplosion(entity, this, target.m_20185_(), target.m_20186_(), target.m_20189_(), 2.0F);
      explosion.setExplosionSound(true);
      explosion.setDamageOwner(false);
      explosion.setDestroyBlocks(false);
      explosion.setFireAfterExplosion(false);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
      explosion.setDamageEntities(false);
      explosion.m_46061_();
      AbilityHelper.disableAbilities(target, 100, (abl) -> abl.hasComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()) && ((PoolComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).get()).containsPool(ModAbilityPools.GUARD_ABILITY));
      return true;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchCooldown() {
      return 240.0F;
   }

   public float getPunchDamage() {
      return 20.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-4.0F, 4.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
