package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.List;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GastanetAbility extends Ability {
   private static final UUID SHINOKUNI_RANGE_BONUS = UUID.fromString("05f3fc04-68e5-4f7b-a513-7237ad6fd643");
   private static final TargetPredicate ALL_TARGETS = new TargetPredicate();
   private static final int COOLDOWN = 160;
   private static final int RANGE = 8;
   public static final RegistryObject<AbilityCore<GastanetAbility>> INSTANCE = ModRegistry.registerAbility("gastanet", "Gastanet", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates an explosion by detonating gas with their hands poisoning every enemy around the user.", (Object)null), ImmutablePair.of("If %s is active the nearby entities will instead get affected by whatever effect the user has at the moment. Allies will receive benefic effects while enemies will receive negative effects.", new Object[]{ShinokuniAbility.INSTANCE}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GastanetAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), RangeComponent.getTooltip(8.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public GastanetAbility(AbilityCore<GastanetAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)32.0F);
      AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_, 5.0F);
      explosion.setStaticDamage(40.0F);
      explosion.setDestroyBlocks(false);
      explosion.m_46061_();
      this.rangeComponent.getBonusManager().removeBonus(SHINOKUNI_RANGE_BONUS);
      if (((MorphInfo)ModMorphs.SHINOKUNI.get()).isActive(entity)) {
         this.rangeComponent.getBonusManager().addBonus(SHINOKUNI_RANGE_BONUS, "Shinokuni Range Bonus", BonusOperation.ADD, 4.0F);
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 8.0F, ALL_TARGETS);
         ShinokuniAbility shinokuniAbility = (ShinokuniAbility)AbilityCapability.get(entity).map((props) -> (ShinokuniAbility)props.getEquippedAbility((AbilityCore)ShinokuniAbility.INSTANCE.get())).orElse((Object)null);
         targets.forEach((target) -> shinokuniAbility.applyEffects(entity, target));
      } else {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 8.0F);
         targets.forEach((target) -> target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 5)));
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GASTANET.get(), entity, mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_);
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }
}
